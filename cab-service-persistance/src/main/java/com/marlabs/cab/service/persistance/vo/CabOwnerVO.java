package com.marlabs.cab.service.persistance.vo;

import java.sql.Date;
import java.util.List;

public class CabOwnerVO {
	
	private Long cabOwnerId;
	
	private String ownerCityName;
	
	private String agencyName;
	
	private String ownerFirstName;
	
	private String ownerMiddleName;
	
	private String ownerLastName;
	
	private String ownerPhone1;
	
	private String ownerPhone2;
	
	private String ownerAddress;
	
	private String ownerLicense;
	
	private String contactPerson;
	
	private String contactPersonPhone1;
	
	private String contactPersonPhone2;
	
	private String attachments;
	
	private Date startDate;
	
	private Date endDate;
	
	private String active;
	
	private List<CabDetailVO> cabDetailList;

	public Long getCabOwnerId() {
		return cabOwnerId;
	}

	public void setCabOwnerId(Long cabOwnerId) {
		this.cabOwnerId = cabOwnerId;
	}

	public String getOwnerCityName() {
		return ownerCityName;
	}

	public void setOwnerCityName(String ownerCityName) {
		this.ownerCityName = ownerCityName;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getOwnerFirstName() {
		return ownerFirstName;
	}

	public void setOwnerFirstName(String ownerFirstName) {
		this.ownerFirstName = ownerFirstName;
	}

	public String getOwnerMiddleName() {
		return ownerMiddleName;
	}

	public void setOwnerMiddleName(String ownerMiddleName) {
		this.ownerMiddleName = ownerMiddleName;
	}

	public String getOwnerLastName() {
		return ownerLastName;
	}

	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}

	public String getOwnerPhone1() {
		return ownerPhone1;
	}

	public void setOwnerPhone1(String ownerPhone1) {
		this.ownerPhone1 = ownerPhone1;
	}

	public String getOwnerPhone2() {
		return ownerPhone2;
	}

	public void setOwnerPhone2(String ownerPhone2) {
		this.ownerPhone2 = ownerPhone2;
	}

	public String getOwnerAddress() {
		return ownerAddress;
	}

	public void setOwnerAddress(String ownerAddress) {
		this.ownerAddress = ownerAddress;
	}

	public String getOwnerLicense() {
		return ownerLicense;
	}

	public void setOwnerLicense(String ownerLicense) {
		this.ownerLicense = ownerLicense;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPersonPhone1() {
		return contactPersonPhone1;
	}

	public void setContactPersonPhone1(String contactPersonPhone1) {
		this.contactPersonPhone1 = contactPersonPhone1;
	}

	public String getContactPersonPhone2() {
		return contactPersonPhone2;
	}

	public void setContactPersonPhone2(String contactPersonPhone2) {
		this.contactPersonPhone2 = contactPersonPhone2;
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

	public List<CabDetailVO> getCabDetailList() {
		return cabDetailList;
	}

	public void setCabDetailList(List<CabDetailVO> cabDetailList) {
		this.cabDetailList = cabDetailList;
	}
	
}
