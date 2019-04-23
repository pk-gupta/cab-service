package com.marlabs.cab.service.persistance.vo;

import java.sql.Date;

public class CabDetailVO {
	
	private Long cabDetailId;

	private Long cabOwnerId;
	
	private Long officeCityId;

	private String officeCityName;
	
	private String driverFirstName;
	
	private String driverMiddleName;
	
	private String driverLastName;
	
	private String driverPhone1;
	
	private String driverPhone2;
	
	private String driverAddress;
	
	private String driverLicense;

	private String registrationNo;
	
	private Long numberOfSeats;
	
	private Date insuranceDate;
	
	private String attachments;
	
	private Date startDate;
	
	private Date endDate;
	
	private String active;

	private String createdBy;
	
	private String updatedBy;
	
	public Long getCabDetailId() {
		return cabDetailId;
	}

	public void setCabDetailId(Long cabDetailId) {
		this.cabDetailId = cabDetailId;
	}

	public Long getCabOwnerId() {
		return cabOwnerId;
	}

	public void setCabOwnerId(Long cabOwnerId) {
		this.cabOwnerId = cabOwnerId;
	}

	public Long getOfficeCityId() {
		return officeCityId;
	}

	public void setOfficeCityId(Long officeCityId) {
		this.officeCityId = officeCityId;
	}

	public String getDriverFirstName() {
		return driverFirstName;
	}

	public void setDriverFirstName(String driverFirstName) {
		this.driverFirstName = driverFirstName;
	}

	public String getDriverMiddleName() {
		return driverMiddleName;
	}

	public void setDriverMiddleName(String driverMiddleName) {
		this.driverMiddleName = driverMiddleName;
	}

	public String getDriverLastName() {
		return driverLastName;
	}

	public void setDriverLastName(String driverLastName) {
		this.driverLastName = driverLastName;
	}

	public String getDriverPhone1() {
		return driverPhone1;
	}

	public String getOfficeCityName() {
		return officeCityName;
	}

	public void setOfficeCityName(String officeCityName) {
		this.officeCityName = officeCityName;
	}

	public void setDriverPhone1(String driverPhone1) {
		this.driverPhone1 = driverPhone1;
	}

	public String getDriverPhone2() {
		return driverPhone2;
	}

	public void setDriverPhone2(String driverPhone2) {
		this.driverPhone2 = driverPhone2;
	}

	public String getDriverAddress() {
		return driverAddress;
	}

	public void setDriverAddress(String driverAddress) {
		this.driverAddress = driverAddress;
	}

	public String getDriverLicense() {
		return driverLicense;
	}

	public void setDriverLicense(String driverLicense) {
		this.driverLicense = driverLicense;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public Long getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Long numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Date getInsuranceDate() {
		return insuranceDate;
	}

	public void setInsuranceDate(Date insuranceDate) {
		this.insuranceDate = insuranceDate;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
