package com.marlabs.cab.service.persistance.admin.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.persistance.admin.dao.AssignCabDAO;
import com.marlabs.cab.service.persistance.admin.dao.TripServiceAvailedDAO;
import com.marlabs.cab.service.persistance.entity.TripHeaderEntity;
import com.marlabs.cab.service.persistance.entity.TripServiceAvailedDashboardViewEntity;
import com.marlabs.cab.service.persistance.entity.TripServiceAvailedEntity;
import com.marlabs.cab.service.persistance.vo.DataFilterParamsVO;
import com.marlabs.cab.service.persistance.vo.TripServiceAvailedVO;
import com.marlabs.cab.service.persistance.vo.TripServiceDetailsVO;

@Repository
@Transactional
public class TripServiceAvailedDAOImpl implements TripServiceAvailedDAO {
	
	private static Logger logger = LogManager.getLogger(TripServiceAvailedDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private AssignCabDAO assignCabDAO;

	@SuppressWarnings("unchecked")
	@Override
	public List<TripServiceAvailedDashboardViewEntity> tripAvailedDashboardFilter(DataFilterParamsVO dataFilterParamsVO) {
		logger.info("tripAvailedDashboard() -> Employee Landmark Request Dashboard DAO call...");

		Session session = null;
		List<TripServiceAvailedDashboardViewEntity> tripAvailedDashboardList = null;

		try {
			session = sessionFactory.openSession();
			Criteria viewQuery = session.createCriteria(TripServiceAvailedDashboardViewEntity.class);
			
			viewQuery.add(Restrictions.eq("officeCityId", dataFilterParamsVO.getOfficeCityId()));
			viewQuery.add(Restrictions.eq("tripDate", dataFilterParamsVO.getServiceDate()));
			
			/*Optional.ofNullable(dataFilterVO)
					.ifPresent(fDate -> viewQuery.add(Restrictions.eq("?", fDate)));*/
			 	 	
			tripAvailedDashboardList = viewQuery.list();
			
			logger.info("tripAvailedDashboard() -> Employee Landmark Request Dashboard DAO: Success");
		} catch (Exception exception) {
			logger.error("tripAvailedDashboard() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return tripAvailedDashboardList;
	}

	@Override
	public String addTripAvailedDetails(TripServiceAvailedEntity tripServiceAvailed) {
		logger.info("addTripAvailedDetails() -> Trip Availed add Details DAO call...");
		
		Session session = null;
		Transaction transaction = null;
		String status = null;
		
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.merge(tripServiceAvailed);
			transaction.commit();
			status = Constants.REQUEST_STATUS_SUCCESS;
			logger.info("addTripAvailedDetails() -> Trip Service Availed add Details DAO: Success");
		} catch (Exception exception) {
			logger.error("addTripAvailedDetails() -> ERROR: " + exception.getCause());
			status = Constants.REQUEST_STATUS_FAILED;
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return status;
	}
	
	@Override
	public String addUpdateTripServiceAvailed(List<TripServiceAvailedEntity> tripServiceAvailedList,TripServiceDetailsVO tripServiceDetailsVO) {
		logger.info("addUpdateTripServiceAvailed() -> Trip Service Availed status update DAO call...");
		
		Session session = null;
		Transaction transaction = null;
		String status = null;
		
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			
			for(TripServiceAvailedEntity serviceAvailed : tripServiceAvailedList){
				session.merge(serviceAvailed);
			}

			transaction.commit();
			
			updateTripStatus(tripServiceAvailedList,tripServiceDetailsVO, session);
			
			status = Constants.REQUEST_STATUS_SUCCESS;
			logger.info("addUpdateTripServiceAvailed() -> Trip Service Availed status update DAO: Success");
		} catch (Exception exception) {
			logger.error("addUpdateTripServiceAvailed() -> ERROR: " + exception.getCause());
			status = Constants.REQUEST_STATUS_FAILED;
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return status;
	}

	@SuppressWarnings("unchecked")
	private void updateTripStatus(List<TripServiceAvailedEntity> tripServiceAvailedList,TripServiceDetailsVO tripServiceDetailsVO, Session session) {
		
		Transaction transaction = session.beginTransaction();
		
		Long tripHeaderId = tripServiceAvailedList.get(0).getTripHeader().getTripHeaderId();
		
		TripHeaderEntity tripHeader = new TripHeaderEntity();
		tripHeader.setTripHeaderId(tripHeaderId);
		
		List<TripServiceAvailedEntity> tripAvailedStatusList = session.createCriteria(TripServiceAvailedEntity.class)
												      	    		  .add(Restrictions.eq("tripHeader", tripHeader))
												      	    		  .add(Restrictions.isNull("isTripAvailed"))
												      	    		  .list();
		
		// Update Trip Header Status to Complete once all employees have avail the trip 
		tripHeader = assignCabDAO.getTripHeaderDetails(tripHeaderId);
		if(tripAvailedStatusList.isEmpty()){
			
			tripHeader.setTripStatus(Constants.TRIP_STATUS_COMPLETED);
			tripHeader.setTripStartTime(tripServiceDetailsVO.getTripStartTime());
			tripHeader.setTripEndTime(tripServiceDetailsVO.getTripEndTime());
			tripHeader.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			
		}else{
			
			tripHeader.setTripStartTime(tripServiceDetailsVO.getTripStartTime());
			tripHeader.setTripEndTime(tripServiceDetailsVO.getTripEndTime());
			tripHeader.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		}
		session.merge(tripHeader);
		transaction.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TripServiceAvailedDashboardViewEntity> tripDetails(Long tripHeaderId) {
		logger.info("tripDetails() -> Trip Service Availed Details DAO call......");
		
		Session session = null;
		
		List<TripServiceAvailedDashboardViewEntity> tripDetails = null;
		
		try{
			session = sessionFactory.openSession();
			
			tripDetails = session.createCriteria(TripServiceAvailedDashboardViewEntity.class)
						      	 .add(Restrictions.eq("tripHeaderId", tripHeaderId))
						         .list();
			
			logger.info("tripDetails() -> Trip Service Availed Details DAO call: Success");
		}catch(Exception exception){
			logger.error("tripDetails() -> ERROR: "+ exception.getCause());
			logger.error(exception.getStackTrace());
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return tripDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TripHeaderEntity> getTripList(Long tripHeaderId) {
		logger.info("getTripList() -> Trip List DAO call......");
		
		Session session = null;
		
		List<TripHeaderEntity> tripList = null;
		
		try{
			session = sessionFactory.openSession();
			
			tripList = session.createCriteria(TripHeaderEntity.class)
					      	  .add(Restrictions.ilike("tripHeaderId", tripHeaderId))
					          .list();
			
			logger.info("getTripList() -> Trip List DAO call: Success");
		}catch(Exception exception){
			logger.error("getTripList() -> ERROR: "+ exception.getCause());
			logger.error(exception.getStackTrace());
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return tripList;
	}

	@Override
	public String deleteTripHeaderDetails(Long tripHeaderId) {
		logger.info("deleteTripHeaderDetails() -> Delete Trip Header Details DAO call...");
		Session session = null;
		String status = Constants.STATUS_SUCCESS;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery("DELETE TripServiceAvailedEntity WHERE tripHeader.tripHeaderId=:tripheaderid");
			query.setLong("tripheaderid", tripHeaderId).executeUpdate();
			logger.info("deleteTripHeaderDetails() -> Delete Trip Header Details DAO : Success");
		} catch (Exception exception) {
			logger.error("deleteTripHeaderDetails() -> ERROR: " + exception.getCause());
			status = Constants.STATUS_FAILED;
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return status;
	}
	
	
	
}
