package com.marlabs.cab.service.persistance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SERVICE_AND_TIME_MASTER")
public class ServiceAndTimeEntity implements Serializable {

	private static final long serialVersionUID = -3400659981736278410L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SERVICE_AND_TIME_MASTER_ID")
	private Long serviceAndTimeId;

	@JsonBackReference(value="officeCity_serviceAndTime")
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "FK_OFFICE_CITY_ID", referencedColumnName = "OFFICE_CITY_ID")
	private OfficeCityEntity officeCity;
	
	@Column(name = "SERVICE_TYPE")
	private String serviceType;

	@Column(name = "SERVICE_TIME")
	private String serviceTime;

	@Column(name = "ACTIVE")
	private String active;

	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;

	public Long getServiceAndTimeId() {
		return serviceAndTimeId;
	}

	public void setServiceAndTimeId(Long serviceAndTimeId) {
		this.serviceAndTimeId = serviceAndTimeId;
	}

	public OfficeCityEntity getOfficeCity() {
		return officeCity;
	}

	public void setOfficeCity(OfficeCityEntity officeCity) {
		this.officeCity = officeCity;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
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

}
