package com.marlabs.cab.service.persistance.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "CAB_OWNER")
public class CabOwnerEntity implements Serializable{

	private static final long serialVersionUID = -8423210794896524950L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CAB_OWNER_ID")
	private Long cabOwnerId;
	
	@JsonManagedReference("cabOwner_cabDetail")
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "cabOwner", fetch = FetchType.EAGER)
	@Column(insertable=false, updatable=false)
	private List<CabDetailEntity> cabDetailList;
	
	@Column(name = "OWNER_CITY_NAME")
	private String ownerCityName;
	
	@Column(name = "AGENCY_NAME")
	private String agencyName;
	
	@Column(name = "OWNER_FIRST_NAME")
	private String ownerFirstName;
	
	@Column(name = "OWNER_MIDDLE_NAME")
	private String ownerMiddleName;
	
	@Column(name = "OWNER_LAST_NAME")
	private String ownerLastName;
	
	@Column(name = "OWNER_PHONE1")
	private String ownerPhone1;
	
	@Column(name = "OWNER_PHONE2")
	private String ownerPhone2;
	
	@Column(name = "OWNER_ADDRESS")
	private String ownerAddress;
	
	@Column(name = "OWNER_LICENSE")
	private String ownerLicense;
	
	@Column(name = "CONTACT_PERSON")
	private String contactPerson;
	
	@Column(name = "CONTACT_PERSON_PHONE1")
	private String contactPersonPhone1;
	
	@Column(name = "CONTACT_PERSON_PHONE2")
	private String contactPersonPhone2;
	
	@Column(name = "ATTACHMENTS")
	private String attachments;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "ACTIVE")
	private String active;
	
	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATE_DATE",insertable=false)
	private Timestamp updateDate;

	public Long getCabOwnerId() {
		return cabOwnerId;
	}

	public void setCabOwnerId(Long cabOwnerId) {
		this.cabOwnerId = cabOwnerId;
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

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
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

	public String getOwnerCityName() {
		return ownerCityName;
	}

	public void setOwnerCityName(String ownerCityName) {
		this.ownerCityName = ownerCityName;
	}

	public List<CabDetailEntity> getCabDetailList() {
		return cabDetailList;
	}

	public void setCabDetailList(List<CabDetailEntity> cabDetailList) {
		this.cabDetailList = cabDetailList;
	}

}
