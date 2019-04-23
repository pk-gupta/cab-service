package com.marlabs.cab.service.persistance.vo;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;
import com.marlabs.cab.service.persistance.marker.InputDataMarker;

public class ScheduleRequestVO extends SingleRequestVO implements InputDataMarker {

	// List<Map<Date, String>> scheduleSelectedDays;

	// private Map<String, List<Map<Date, String>>> scheduleSelectedDays;

	/*
	 * private Map<String, List<Date>> scheduleSelectedDays;//To collect Login &
	 * Logout section rows private List<Integer> updateScheduleUnSelectedDays;//
	 * To collects rows that are unselected during Edit mode
	 * 
	 */
	private List<ScheduleServiceDay> scheduleServiceDays;// To collect Selected
															// Days section rows
	private Long employeeNoOfWeekOffs ;

	private String employeeWeekOffDay1;
	private String employeeWeekOffDay2;
	private String employeeWeekOffDay3;

	private List<RequestHeaderEntity> requestHeaderEntity;

	public List<RequestHeaderEntity> getRequestHeaderEntity() {
		return requestHeaderEntity;
	}

	public void setRequestHeaderEntity(List<RequestHeaderEntity> requestHeaderEntity) {
		this.requestHeaderEntity = requestHeaderEntity;
	}

	public String getEmployeeWeekOffDay1() {
		return employeeWeekOffDay1;
	}

	public void setEmployeeWeekOffDay1(String employeeWeekOffDay1) {
		this.employeeWeekOffDay1 = employeeWeekOffDay1;
	}

	public String getEmployeeWeekOffDay2() {
		return employeeWeekOffDay2;
	}

	public void setEmployeeWeekOffDay2(String employeeWeekOffDay2) {
		this.employeeWeekOffDay2 = employeeWeekOffDay2;
	}

	public String getEmployeeWeekOffDay3() {
		return employeeWeekOffDay3;
	}

	public void setEmployeeWeekOffDay3(String employeeWeekOffDay3) {
		this.employeeWeekOffDay3 = employeeWeekOffDay3;
	}

	public List<ScheduleServiceDay> getScheduleServiceDays() {
		return scheduleServiceDays;
	}

	public void setScheduleServiceDays(List<ScheduleServiceDay> scheduleServiceDays) {
		this.scheduleServiceDays = scheduleServiceDays;
	}

	public Long getEmployeeNoOfWeekOffs() {
		return employeeNoOfWeekOffs;
	}

	public void setEmployeeNoOfWeekOffs(Long employeeNoOfWeekOffs) {
		this.employeeNoOfWeekOffs = employeeNoOfWeekOffs;
	}
}
