package com.marlabs.cab.service.persistance.admin.dao;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;

public interface PendingApprovalsDAO {
	
	public List<EmployeeManagerDashboardEntity> pendingApprovalsDashboard();
	
}
