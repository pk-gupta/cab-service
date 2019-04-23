package com.marlabs.cab.service.persistance.admin.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.marlabs.cab.service.persistance.entity.AssignCabDashboardDetailViewEntity;
import com.marlabs.cab.service.persistance.entity.AssignCabDashboardViewEntity;
import com.marlabs.cab.service.persistance.entity.TripHeaderEntity;
import com.marlabs.cab.service.persistance.vo.DataFilterParamsVO;

@Repository
@Transactional
public class AssignCabDAOImpl implements AssignCabDAO {

	private static Logger logger = LogManager.getLogger(AssignCabDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<AssignCabDashboardViewEntity> assignCabDashboard(DataFilterParamsVO dataFilterParamsVO) {

		logger.info("assignCabDashboard() -> Admin - Assign Cab Dashboard DAO call......");

		Session session = null;

		List<AssignCabDashboardViewEntity> assignCabDashBoardList = null;

		try {
			session = sessionFactory.openSession();

			assignCabDashBoardList = session.createCriteria(AssignCabDashboardViewEntity.class)
					.add(Restrictions.eq("serviceDate", dataFilterParamsVO.getServiceDate()))
					// .add(Restrictions.eq("officeCityId",
					// dataFilterParamsVO.getOfficeCityId()))
					.add(Restrictions.eq("officeBranchCode", dataFilterParamsVO.getOfficeBranchCode()))
					.add(Restrictions.eq("serviceType", dataFilterParamsVO.getServiceType())).list();

			logger.info("assignCabDashboard() -> Admin - Assign Cab Dashboard DAO call: Success");
		} catch (Exception exception) {
			logger.error("assignCabDashboard() -> ERROR: " + exception.getCause());
			logger.error(exception.getStackTrace());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return assignCabDashBoardList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssignCabDashboardDetailViewEntity> assignCabDashboardDetail(DataFilterParamsVO dataFilterParamsVO) {

		logger.info("assignCabDashboardDetail() -> Admin - Assign Cab Dashboard Detail DAO call......");

		Session session = null;

		List<AssignCabDashboardDetailViewEntity> assignCabDashBoardDetailList = null;

		try {
			session = sessionFactory.openSession();

			assignCabDashBoardDetailList = session.createCriteria(AssignCabDashboardDetailViewEntity.class)
					.add(Restrictions.eq("serviceDate", dataFilterParamsVO.getServiceDate()))
					.add(Restrictions.eq("serviceType", dataFilterParamsVO.getServiceType()))
					.add(Restrictions.eq("officeBranchCode", dataFilterParamsVO.getOfficeBranchCode()))
					.add(Restrictions.or(Restrictions.eq("loginTime", dataFilterParamsVO.getServiceTime()),
							Restrictions.eq("logoutTime", dataFilterParamsVO.getServiceTime())))
					.list();

			logger.info("assignCabDashboardDetail() -> Admin - Assign Cab Dashboard Detail DAO call: Success");
		} catch (Exception exception) {
			logger.error("assignCabDashboardDetail() -> ERROR: " + exception.getCause());
			logger.error(exception.getStackTrace());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return assignCabDashBoardDetailList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignCabDashboardDetailViewEntity> printTripSheet(DataFilterParamsVO dataFilterParamsVO) {

		logger.info("assignCabDashboardDetail() -> Admin - Assign Cab Dashboard Detail DAO call......");

		Session session = null;

		List<AssignCabDashboardDetailViewEntity> assignCabDashBoardDetailList = null;

		try {
			session = sessionFactory.openSession();

			assignCabDashBoardDetailList = session.createCriteria(AssignCabDashboardDetailViewEntity.class)
					.add(Restrictions.isNotNull("tripHeaderId"))
					.add(Restrictions.eq("serviceDate", dataFilterParamsVO.getServiceDate()))
					.add(Restrictions.eq("serviceType", dataFilterParamsVO.getServiceType()))
					.add(Restrictions.eq("officeBranchCode", dataFilterParamsVO.getOfficeBranchCode()))
					.add(Restrictions.or(Restrictions.eq("loginTime", dataFilterParamsVO.getServiceTime()),
							Restrictions.eq("logoutTime", dataFilterParamsVO.getServiceTime())))
					.list();

			logger.info("assignCabDashboardDetail() -> Admin - Assign Cab Dashboard Detail DAO call: Success");
		} catch (Exception exception) {
			logger.error("assignCabDashboardDetail() -> ERROR: " + exception.getCause());
			logger.error(exception.getStackTrace());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return assignCabDashBoardDetailList;
	}

	@Override
	public TripHeaderEntity addUpdateAssignCab(TripHeaderEntity tripHeader) {

		logger.info("addUpdateAssignCab() -> Add/Update Assign Cab DAO call...");
		Session session = null;
		Transaction transaction = null;
		TripHeaderEntity tripHeaderEntity = null;

		try {

			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			tripHeaderEntity = (TripHeaderEntity) session.merge(tripHeader);
			if (tripHeader.getTripDetaillList().size() % 50 == 0) { // 50 same
																	// as the
																	// JDBC
																	// batch
																	// size
				// flush a batch of update and release memory:
				session.flush();
			}
		
			transaction.commit();
			logger.info("addUpdateAssignCab() -> Add/Update Assign Cab DAO: Success");
		} catch (Exception exception) {
			logger.error("addUpdateAssignCab() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
		return tripHeaderEntity;
	}

	@Override
	public String deleteTripDetails(Long tripHeaderId) {
		logger.info("deleteTripDetails() -> Delete Trip Details DAO call...");
		Session session = null;
		String status = Constants.STATUS_SUCCESS;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery("DELETE TripHeaderEntity WHERE tripHeaderId=:tripheaderid");
			query.setLong("tripheaderid", tripHeaderId).executeUpdate();
			logger.info("deleteTripDetails() -> Delete Trip Details DAO : Success");
		} catch (Exception exception) {
			logger.error("deleteTripDetails() -> ERROR: " + exception.getCause());
			status = Constants.STATUS_FAILED;
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return status;
	}

	@Override
	public TripHeaderEntity getTripHeaderDetails(Long tripHeaderId) {
		
		logger.info("getTripHeaderDetails() -> Get Trip Header Details DAO call......");
		
		Session session = null;
	
		TripHeaderEntity headerEntity = null;
		
		try{
			session = sessionFactory.openSession();
			
			headerEntity =  (TripHeaderEntity) session.createCriteria(TripHeaderEntity.class)
							       .add(Restrictions.eq("tripHeaderId",tripHeaderId))
							       .uniqueResult();
			
			logger.info("getTripHeaderDetails() -> Get Trip Header Details DAO call: Success");
		}catch(Exception exception){
			logger.error("getTripHeaderDetails() -> ERROR: "+ exception.getCause());
			logger.error(exception.getStackTrace());
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return headerEntity;
	}
}
