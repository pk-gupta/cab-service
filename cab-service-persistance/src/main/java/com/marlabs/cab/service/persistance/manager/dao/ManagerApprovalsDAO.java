package com.marlabs.cab.service.persistance.manager.dao;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;

public interface ManagerApprovalsDAO {
	
	public List<EmployeeManagerDashboardEntity>  getApprovalManagerDashboard(String managerId);
	
	public RequestHeaderEntity processManagerApproval(long requestId, String managerId, String approvalSatus, String comment);

}
