package com.marlabs.cab.service.persistance.manager.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;
import com.marlabs.cab.service.persistance.manager.dao.ManagerApprovalsDAO;

@Repository
@Transactional
public class ManagerApprovalsDAOImpl implements ManagerApprovalsDAO {

	private static Logger logger = LogManager.getLogger(ManagerApprovalsDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public List<EmployeeManagerDashboardEntity> getApprovalManagerDashboard(String managerId) {
		logger.info("approvalManagerDashboard() -> Manager Dashboard for Cab Service Request DAO call...");
		
		return getManagerApprovalDashboard(managerId, Constants.REQUEST_TYPE_SCHEDULE);
	}
	
	private List<EmployeeManagerDashboardEntity> getManagerApprovalDashboard(String managerId, String requestType){
		logger.info("getManagerApprovalDashboard() ->  Retrieve Manager Dashboard details...");
		
		Session session = null;
		List<EmployeeManagerDashboardEntity> employeeManagerDashboardEntity = null;
		
		try{
			session = sessionFactory.openSession();
			String dashBoardView = "FROM EmployeeManagerDashboardEntity WHERE  managerId=:managerid AND approveStatus = :approvestatus ORDER BY reqHeaderId DESC";
			Query queryString = session.createQuery(dashBoardView);
			queryString.setString("managerid", managerId);
			queryString.setString("approvestatus", Constants.REQUEST_STATUS_APPROVAL_PENDING);
			
			employeeManagerDashboardEntity = queryString.list();
			logger.info("getManagerApprovalDashboard() ->  Retrieve Manager Dashboard details: Success");
		}catch(Exception exception){
			logger.error("getManagerApprovalDashboard() -> Retrieve Manager Dashboard details: ERROR: "+ exception.getCause());
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		return employeeManagerDashboardEntity;
	}

	@Override
	public RequestHeaderEntity processManagerApproval(long requestId, String managerId, String approvalSatus, String comment) {
		logger.info("processManagerApproval() ->  Manager Approval  Transaction call...");
		Session session = null;
		Transaction transaction = null;
		RequestHeaderEntity requestHeaderEntity =null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			RequestHeaderEntity requestHeader = session.get(RequestHeaderEntity.class, requestId);
			requestHeader.setApproveStatus(approvalSatus);
			requestHeader.setManagerRemark(comment);
			requestHeader.setUpdatedBy(managerId);//To Change to Manager ID

			requestHeaderEntity=(RequestHeaderEntity) session.merge(requestHeader);
			transaction.commit();
			logger.info("processManagerApproval() -> Manager Approval Transaction: Success ");
		} catch (Exception exception) {
			logger.error("processManagerApproval() -> Manager Approval Transaction: Failed -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
		return requestHeaderEntity;
	}
	
}
