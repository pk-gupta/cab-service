package com.marlabs.cab.service.persistance.employee.dao;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.EmployeeExistingRequestView;
import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;
import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;
import com.marlabs.cab.service.persistance.vo.ProcessNewRequestVO;
import com.marlabs.cab.service.persistance.vo.SingleRequestVO;


public interface EmployeeSingleRequestDAO {
	
	public List<EmployeeManagerDashboardEntity> singleRequestDashboard(String employeeId);
	
	public SingleRequestVO createNewSingleRequest(String employeeId);
	
	public ProcessNewRequestVO processSingleRequest(RequestHeaderEntity requestHeader,SingleRequestVO singleRequestVO);
	
	public List<RequestHeaderEntity> editSingleRequest(long requestId);
	
	public String deleteSingleRequest(long requestId);
	
	public String cancelSingleRequest(long requestId,String employeeId);
	
	public String deleteRequestDetails(long headerId);
	
	public String cancelSelectedSingleRequest(SingleRequestVO singleRequestVO);
	
	public List<EmployeeExistingRequestView> getExistingRequests(SingleRequestVO singleRequestVO);
	
	public RequestHeaderEntity getRequestHeader(long requestHeaderId);
	
	public void deleteRequestDetail(long requestDetailId);
	
	public List<RequestDetailEntity> getRequestDetailWithHeader(long requestHeaderId);
	
}
