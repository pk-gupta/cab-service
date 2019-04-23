package com.marlabs.cab.service.persistance.vo;

import java.sql.Date;
import java.util.List;

import com.marlabs.cab.service.persistance.entity.EmployeeLandmardDashboardEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeLandmarkEntity;
import com.marlabs.cab.service.persistance.entity.OfficeCityEntity;

public class EmployeeLandmarkVO {

	private Long employeeLandmarkId;
	
	private Long officeCityId;
	
	private String employeeId;
	
	private Long cityLandmarkMasterId;
	
	private String active;
	
	private String cityLandmarkName;

	private String officeCityName;

	private String empFirstName;

	private String empMiddleName;

	private String empLastName;
	
	private Date effectiveDate;

	private List<EmployeeLandmarkEntity> landmarkList;

	private List<OfficeCityEntity> officeCityEntityList;
	
	private List<EmployeeLandmardDashboardEntity> employeeLandmarkDashboardList;

	public Long getOfficeCityId() {
		return officeCityId;
	}

	public void setOfficeCityId(Long officeCityId) {
		this.officeCityId = officeCityId;
	}

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

	public List<EmployeeLandmarkEntity> getLandmarkList() {
		return landmarkList;
	}

	public void setLandmarkList(List<EmployeeLandmarkEntity> landmarkList) {
		this.landmarkList = landmarkList;
	}

	public List<OfficeCityEntity> getOfficeCityEntityList() {
		return officeCityEntityList;
	}

	public void setOfficeCityEntityList(List<OfficeCityEntity> officeCity) {
		this.officeCityEntityList = officeCity;
	}

	public Long getCityLandmarkMasterId() {
		return cityLandmarkMasterId;
	}

	public void setCityLandmarkMasterId(Long cityLandmarkMasterId) {
		this.cityLandmarkMasterId = cityLandmarkMasterId;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public List<EmployeeLandmardDashboardEntity> getEmployeeLandmarkDashboardList() {
		return employeeLandmarkDashboardList;
	}

	public void setEmployeeLandmarkDashboardList(List<EmployeeLandmardDashboardEntity> employeeLandmarkDashboard) {
		this.employeeLandmarkDashboardList = employeeLandmarkDashboard;
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

}
