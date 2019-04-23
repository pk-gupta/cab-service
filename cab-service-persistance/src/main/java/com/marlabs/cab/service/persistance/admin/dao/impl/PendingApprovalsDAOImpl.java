package com.marlabs.cab.service.persistance.admin.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.persistance.admin.dao.PendingApprovalsDAO;
import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;

@Repository
@Transactional
public class PendingApprovalsDAOImpl implements PendingApprovalsDAO {
	
	private static Logger logger = LogManager.getLogger(PendingApprovalsDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeManagerDashboardEntity> pendingApprovalsDashboard() {
		logger.info("pendingApprovalsDashboard() -> Admin - Pending Approvals Dashboard DAO call......");
		
		Session session = null;
		
		List<EmployeeManagerDashboardEntity> pendingApprovalsList = null;
		
		try{
			session = sessionFactory.openSession();
			
			pendingApprovalsList = session.createCriteria(EmployeeManagerDashboardEntity.class)
								      	  .add(Restrictions.eq("approveStatus", Constants.REQUEST_STATUS_APPROVAL_PENDING))
								      	  .addOrder(Order.desc("reqHeaderId"))
								          .list();
			
			logger.info("pendingApprovalsDashboard() -> Admin - Pending Approvals Dashboard DAO call: Success");
		}catch(Exception exception){
			logger.error("pendingApprovalsDashboard() -> ERROR: "+ exception.getCause());
			logger.error(exception.getStackTrace());
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return pendingApprovalsList;
	}

}
