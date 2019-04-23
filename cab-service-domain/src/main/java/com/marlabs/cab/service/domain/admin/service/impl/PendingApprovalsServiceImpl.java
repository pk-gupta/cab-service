package com.marlabs.cab.service.domain.admin.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlabs.cab.service.domain.admin.service.PendingApprovalsService;
import com.marlabs.cab.service.persistance.admin.dao.PendingApprovalsDAO;
import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;

@Service
public class PendingApprovalsServiceImpl implements PendingApprovalsService {

	private static Logger logger = LogManager.getLogger(PendingApprovalsServiceImpl.class);
	
	@Autowired
	PendingApprovalsDAO pendingApprovalsDAO;
	
	@Override
	public List<EmployeeManagerDashboardEntity> pendingApprovalsDashboard() {
		logger.info("pendingApprovalsDashboard() -> Admin Pending Approvals Dashboard Service call...");
		
		return pendingApprovalsDAO.pendingApprovalsDashboard();
	}

}
