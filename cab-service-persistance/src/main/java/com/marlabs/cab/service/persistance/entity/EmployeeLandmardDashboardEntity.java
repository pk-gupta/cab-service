package com.marlabs.cab.service.persistance.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "emp_landmark_dashboard_view")
public class EmployeeLandmardDashboardEntity {

	@Column(name = "employeeId")
	private String employeeId;
	
	@Id
	@Column(name = "employeeLandmarkId")
	private Long employeeLandmarkId;
	
	@Column(name = "cityLandmarkMasterId")
	private Long cityLandmarkMasterId;
	
	@Column(name = "officeCityId")
	private Long officeCityId;
	
	@Column(name = "cityLandmarkName")
	private String cityLandmarkName;
	
	@Column(name = "officeCityName")
	private String officeCityName;
	
	@Column(name = "effectiveDate")
	private Date effectiveDate;
	
	@Column(name = "active")
	private String active;
	
	@Column(name = "empFirstName")
	private String empFirstName;
	
	@Column(name = "empMiddleName")
	private String empMiddleName;
	
	@Column(name = "empLastName")
	private String empLastName;
	
	@Column(name = "empAddress1")
	private String empAddress1;
	
	@Column(name = "empAddress2")
	private String empAddress2;
	
	@Column(name = "empAddress3")
	private String empAddress3;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Long getEmployeeLandmarkId() {
		return employeeLandmarkId;
	}

	public void setEmployeeLandmarkId(Long employeeLandmarkId) {
		this.employeeLandmarkId = employeeLandmarkId;
	}

	public Long getCityLandmarkMasterId() {
		return cityLandmarkMasterId;
	}

	public void setCityLandmarkMasterId(Long cityLandmarkMasterId) {
		this.cityLandmarkMasterId = cityLandmarkMasterId;
	}

	public Long getOfficeCityId() {
		return officeCityId;
	}

	public void setOfficeCityId(Long officeCityId) {
		this.officeCityId = officeCityId;
	}

	public String getCityLandmarkName() {
		return cityLandmarkName;
	}

	public void setCityLandmarkName(String cityLandmarkName) {
		this.cityLandmarkName = cityLandmarkName;
	}

	public String getOfficeCityName() {
		return officeCityName;
	}

	public void setOfficeCityName(String officeCityName) {
		this.officeCityName = officeCityName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

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

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getEmpAddress1() {
		return empAddress1;
	}

	public void setEmpAddress1(String empAddress1) {
		this.empAddress1 = empAddress1;
	}

	public String getEmpAddress2() {
		return empAddress2;
	}

	public void setEmpAddress2(String empAddress2) {
		this.empAddress2 = empAddress2;
	}

	public String getEmpAddress3() {
		return empAddress3;
	}

	public void setEmpAddress3(String empAddress3) {
		this.empAddress3 = empAddress3;
	}
	
}
