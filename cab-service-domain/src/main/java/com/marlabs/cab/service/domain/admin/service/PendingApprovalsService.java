package com.marlabs.cab.service.domain.admin.service;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;

public interface PendingApprovalsService {

	public List<EmployeeManagerDashboardEntity> pendingApprovalsDashboard();
	
}
