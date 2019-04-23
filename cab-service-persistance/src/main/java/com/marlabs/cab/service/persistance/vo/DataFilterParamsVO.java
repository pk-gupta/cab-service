package com.marlabs.cab.service.persistance.vo;

import java.sql.Date;

public class DataFilterParamsVO {
	
	private String officeBranchCode;
	
	private Long officeCityId;
	
	private String officeCityName;
	
	private Long officeLocationId;
	
	private Long landmarkId;
	
	private Date serviceDate;
	
	private Date filterDate;
	
	private String serviceType;
	
	private String allOfficeCity;
	
	private String allOfficeBranch;
	
	private String loginTime;
	
	private String logoutTime;
	
	private String searchText;
	
	private String serviceTime;
	
	public Long getLandmarkId() {
		return landmarkId;
	}

	public void setLandmarkId(Long landmarkId) {
		this.landmarkId = landmarkId;
	}

	public String getAllOfficeCity() {
		return allOfficeCity;
	}

	public void setAllOfficeCity(String allOfficeCity) {
		this.allOfficeCity = allOfficeCity;
	}

	public String getAllOfficeBranch() {
		return allOfficeBranch;
	}

	public void setAllOfficeBranch(String allOfficeBranch) {
		this.allOfficeBranch = allOfficeBranch;
	}

	public String getOfficeBranchCode() {
		return officeBranchCode;
	}

	public void setOfficeBranchCode(String officeBranchCode) {
		this.officeBranchCode = officeBranchCode;
	}

	public String getOfficeCityName() {
		return officeCityName;
	}

	public void setOfficeCityName(String officeCityName) {
		this.officeCityName = officeCityName;
	}

	public Long getOfficeCityId() {
		return officeCityId;
	}

	public void setOfficeCityId(Long officeCityId) {
		this.officeCityId = officeCityId;
	}

	public Long getOfficeLocationId() {
		return officeLocationId;
	}

	public void setOfficeLocationId(Long officeLocationId) {
		this.officeLocationId = officeLocationId;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	public Date getFilterDate() {
		return filterDate;
	}

	public void setFilterDate(Date filterDate) {
		this.filterDate = filterDate;
	}
	
	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
}
