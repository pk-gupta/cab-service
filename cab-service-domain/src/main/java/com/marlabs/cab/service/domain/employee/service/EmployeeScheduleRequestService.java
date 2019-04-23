package com.marlabs.cab.service.domain.employee.service;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;
import com.marlabs.cab.service.persistance.vo.ProcessNewRequestVO;
import com.marlabs.cab.service.persistance.vo.ScheduleRequestVO;


public interface EmployeeScheduleRequestService {
	
	public List<EmployeeManagerDashboardEntity> scheduleRequestDashboard(String employeeId);
	
	public ScheduleRequestVO createNewScheduleRequest(String employeeId);
	
	public ProcessNewRequestVO processScheduleRequest(ScheduleRequestVO scheduleRequestVO, String userAction);
	
	public ScheduleRequestVO editScheduleRequest(long requestId);
	
	public String deleteScheduleRequest(long requestId);
	
	public String cancelScheduleRequest(long requestId, String employeeId);
	
	public String cancelSelectedScheduleRequest(ScheduleRequestVO scheduleRequestVO);

}
