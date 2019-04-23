package com.marlabs.cab.service.persistance.sql.mapper;

public class EmployeeLandmarkSQLMapper {

	private Long employeeLandmarkId;
	
	private String employeeId;
	
	private String employeeLandmarkName;

	public Long getEmployeeLandmarkId() {
		return employeeLandmarkId;
	}

	public void setEmployeeLandmarkId(Long employeeLandmarkId) {
		this.employeeLandmarkId = employeeLandmarkId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeLandmarkName() {
		return employeeLandmarkName;
	}

	public void setEmployeeLandmarkName(String employeeLandmarkName) {
		this.employeeLandmarkName = employeeLandmarkName;
	}

}
