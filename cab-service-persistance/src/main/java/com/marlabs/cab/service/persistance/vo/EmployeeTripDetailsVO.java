package com.marlabs.cab.service.persistance.vo;

public class EmployeeTripDetailsVO {

	
	private String empFirstName;

	private String empMiddleName;

	private String empLastName;
	
	private String employeeGender;
	
	private String employeeLandmark;
	
	private Long sequenceNo;
	
	private String expectedTime;
	
	private String actualTime;

	public String getEmpFirstName() {
		return empFirstName;
	}

	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}

	public String getEmpMiddleName() {
		return empMiddleName;
	}

	public void setEmpMiddleName(String empMiddleName) {
		this.empMiddleName = empMiddleName;
	}

	public String getEmpLastName() {
		return empLastName;
	}

	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}

	public String getEmployeeGender() {
		return employeeGender;
	}

	public void setEmployeeGender(String employeeGender) {
		this.employeeGender = employeeGender;
	}

	public String getEmployeeLandmark() {
		return employeeLandmark;
	}

	public void setEmployeeLandmark(String employeeLandmark) {
		this.employeeLandmark = employeeLandmark;
	}

	public Long getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Long sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getExpectedTime() {
		return expectedTime;
	}

	public void setExpectedTime(String expectedTime) {
		this.expectedTime = expectedTime;
	}

	public String getActualTime() {
		return actualTime;
	}

	public void setActualTime(String actualTime) {
		this.actualTime = actualTime;
	}
	
}
