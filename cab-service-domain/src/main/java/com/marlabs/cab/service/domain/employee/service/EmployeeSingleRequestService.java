package com.marlabs.cab.service.domain.employee.service;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;
import com.marlabs.cab.service.persistance.vo.ProcessNewRequestVO;
import com.marlabs.cab.service.persistance.vo.SingleRequestVO;


public interface EmployeeSingleRequestService {
	
	public List<EmployeeManagerDashboardEntity>  singleRequestDashboard(String employeeId);
	
	public SingleRequestVO createNewSingleRequest(String employeeId);
	
	public ProcessNewRequestVO processSingleRequest(SingleRequestVO singleRequestVO, String userAction);
	
	public SingleRequestVO editSingleRequest(long requestId);
	
	public String deleteSingleRequest(long requestId);
	
	public String cancelSingleRequest(long requestId,String employeeId);
	
	public String cancelSelectedSingleRequest(SingleRequestVO singleRequestVO);
	
}
