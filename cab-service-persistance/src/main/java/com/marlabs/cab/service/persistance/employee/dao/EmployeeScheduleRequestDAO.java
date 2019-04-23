package com.marlabs.cab.service.persistance.employee.dao;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.EmployeeExistingRequestView;
import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;
import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;
import com.marlabs.cab.service.persistance.vo.ProcessNewRequestVO;
import com.marlabs.cab.service.persistance.vo.ScheduleRequestVO;


public interface EmployeeScheduleRequestDAO {
	
	public List<EmployeeManagerDashboardEntity> scheduleRequestDashboard(String employeeId);
	
	public ScheduleRequestVO createNewScheduleRequest(String employeeId);
	
	public ProcessNewRequestVO processScheduleRequest(RequestHeaderEntity requestHeader,ScheduleRequestVO scheduleRequestVO);
	
	public List<RequestHeaderEntity> editScheduleRequest(long requestId );
	
	public String deleteScheduleRequest(long requestId);
	
	public String cancelScheduleRequest(long requestId, String employeeId);
	
	public String deleteRequestDetails(long headerId);
	
	public String cancelSelectedScheduleRequest(ScheduleRequestVO scheduleRequestVO);
	
	public List<EmployeeExistingRequestView> getExistingRequests(ScheduleRequestVO scheduleRequestVO);
	
	public void deleteRequestDetail(long requestDetailId);
	
	public RequestHeaderEntity getRequestHeader(long requestHeaderId);
	
	public List<RequestDetailEntity> getRequestDetailWithHeader(long requestHeaderId);
	
}
