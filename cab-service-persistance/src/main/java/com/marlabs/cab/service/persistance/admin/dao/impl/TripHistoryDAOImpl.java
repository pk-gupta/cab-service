package com.marlabs.cab.service.persistance.admin.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.persistance.admin.dao.TripHistoryDAO;
import com.marlabs.cab.service.persistance.entity.SingleTripDetailsViewEntity;
import com.marlabs.cab.service.persistance.entity.TripHistoryDashboardViewEntity;
import com.marlabs.cab.service.persistance.vo.DataFilterParamsVO;

@Repository
@Transactional
public class TripHistoryDAOImpl implements TripHistoryDAO {
	
	private static Logger logger = LogManager.getLogger(TripHistoryDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripHistoryDashboardViewEntity> tripHistoryDashboardFilter(DataFilterParamsVO dataFilterParamsVO) {

		logger.info("tripHistoryDashboardFilter() -> Trip History Dashboard Filter DAO call...");

		Session session = null;
		List<TripHistoryDashboardViewEntity> tripHistoryDashboardList = null;

		try {
			session = sessionFactory.openSession();
			Criteria viewQuery = session.createCriteria(TripHistoryDashboardViewEntity.class);
			
			//http://www.java2s.com/Code/Java/Hibernate/CriteriaConditionTwoCriterionAndOr.htm 
			
			viewQuery.add(Restrictions.or(
						   Restrictions.eq("officeCityId", dataFilterParamsVO.getOfficeCityId()),
						   Restrictions.isNull("officeCityId")
					     ));
			
			viewQuery.add(Restrictions.or(
							Restrictions.eq("tripDate", dataFilterParamsVO.getServiceDate()),
							Restrictions.ge("tripDate", "(NOW - 24)")
						 ));
			
			tripHistoryDashboardList = viewQuery.list();
			
			logger.info("tripHistoryDashboardFilter() -> Trip History Dashboard Filter DAO: Success");
		} catch (Exception exception) {
			logger.error("tripHistoryDashboardFilter() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return tripHistoryDashboardList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SingleTripDetailsViewEntity> getSingleTripDetails(Long tripHeaderId) {
		logger.info("getSingleTripDetails() -> Trip History Dashboard Filter DAO call...");

		Session session = null;
		List<SingleTripDetailsViewEntity> singleTripDetailsList = null;

		try {
			session = sessionFactory.openSession();
			Criteria viewQuery = session.createCriteria(SingleTripDetailsViewEntity.class);
			
			viewQuery.add(Restrictions.eq("tripHeaderId", tripHeaderId));
			 	 	
			singleTripDetailsList = viewQuery.list();
			
			logger.info("getSingleTripDetails() -> Trip History Dashboard Filter DAO: Success");
		} catch (Exception exception) {
			logger.error("getSingleTripDetails() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return singleTripDetailsList;
	}

}
