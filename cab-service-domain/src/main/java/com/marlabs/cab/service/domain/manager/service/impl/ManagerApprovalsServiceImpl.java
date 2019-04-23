package com.marlabs.cab.service.domain.manager.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlabs.cab.service.domain.manager.service.ManagerApprovalsService;
import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;
import com.marlabs.cab.service.persistance.manager.dao.ManagerApprovalsDAO;

@Service
public class ManagerApprovalsServiceImpl implements ManagerApprovalsService {
	
	private static Logger logger = LogManager.getLogger(ManagerApprovalsServiceImpl.class);

	@Autowired
	private ManagerApprovalsDAO managerApprovalsDAO;

	@Override
	public List<EmployeeManagerDashboardEntity> getApprovalManagerDashboard(String managerId) {
		logger.info("getApprovalManagerDashboard() -> Manager Dashboard for Cab Service Request DAO call...");
		
		return managerApprovalsDAO.getApprovalManagerDashboard(managerId);
	}

	@Override
	public RequestHeaderEntity processManagerApproval(long requestId, String managerId, String approvalSatus, String comment) {
		logger.info("processManagerApproval() -> Manager Approve/Reject Cab Service Request Service call...");
		return managerApprovalsDAO.processManagerApproval(requestId, managerId, approvalSatus, comment);
				
	}

}
