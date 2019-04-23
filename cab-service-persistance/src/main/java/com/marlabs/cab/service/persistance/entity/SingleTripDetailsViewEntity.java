package com.marlabs.cab.service.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Id;

public class SingleTripDetailsViewEntity {
	
	@Id
	@Column(name = "employeeId")
	private String employeeId;
	
	@Column(name = "empFirstName")
	private String empFirstName;
	
	@Column(name = "empMiddleName")
	private String empMiddleName;
	
	@Column(name = "empLastName")
	private String empLastName;
	
	@Column(name = "empPhone")
	private String empPhone;
	
	@Column(name = "empAddress1")
	private String empAddress1;
	
	@Column(name = "empAddress2")
	private String empAddress2;
	
	@Column(name = "empAddress3")
	private String empAddress3;
	
	@Column(name = "cityLandmarkId")
	private Long cityLandmarkId;
	
	@Column(name = "cityLandmarkName")
	private String cityLandmarkName;
	
	@Column(name = "officeCityId")
	private Long officeCityId;
	
	@Column(name = "officeCityName")
	private String officeCityName;
	
	@Column(name = "officeBranchCode")
	private String officeBranchCode;
	
	@Column(name = "officeBranchName")
	private String officeBranchName;
	
	@Column(name = "tripHeaderId")
	private Long tripHeaderId;
	
	@Column(name = "tripAvailedId")
	private Long tripAvailedId;
	
	@Column(name = "isTripAvailed")
	private String isTripAvailed;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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

	public String getEmpPhone() {
		return empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
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

	public Long getCityLandmarkId() {
		return cityLandmarkId;
	}

	public void setCityLandmarkId(Long cityLandmarkId) {
		this.cityLandmarkId = cityLandmarkId;
	}

	public String getCityLandmarkName() {
		return cityLandmarkName;
	}

	public void setCityLandmarkName(String cityLandmarkName) {
		this.cityLandmarkName = cityLandmarkName;
	}

	public Long getOfficeCityId() {
		return officeCityId;
	}

	public void setOfficeCityId(Long officeCityId) {
		this.officeCityId = officeCityId;
	}

	public String getOfficeCityName() {
		return officeCityName;
	}

	public void setOfficeCityName(String officeCityName) {
		this.officeCityName = officeCityName;
	}

	public String getOfficeBranchCode() {
		return officeBranchCode;
	}

	public void setOfficeBranchCode(String officeBranchCode) {
		this.officeBranchCode = officeBranchCode;
	}

	public String getOfficeBranchName() {
		return officeBranchName;
	}

	public void setOfficeBranchName(String officeBranchName) {
		this.officeBranchName = officeBranchName;
	}

	public Long getTripHeaderId() {
		return tripHeaderId;
	}

	public void setTripHeaderId(Long tripHeaderId) {
		this.tripHeaderId = tripHeaderId;
	}

	public Long getTripAvailedId() {
		return tripAvailedId;
	}

	public void setTripAvailedId(Long tripAvailedId) {
		this.tripAvailedId = tripAvailedId;
	}

	public String getIsTripAvailed() {
		return isTripAvailed;
	}

	public void setIsTripAvailed(String isTripAvailed) {
		this.isTripAvailed = isTripAvailed;
	}
	
}

