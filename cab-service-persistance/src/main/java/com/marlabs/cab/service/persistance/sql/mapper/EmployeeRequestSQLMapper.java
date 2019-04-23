package com.marlabs.cab.service.persistance.sql.mapper;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

public class EmployeeRequestSQLMapper {
	
	private BigInteger requestDetailId; 
	
	private String serviceType;
	
	private Date serviceDate;
	
	private String isCancelled;
	
	private String cancelReason;
	
	private String createdBy;
	
	private String updatedBy;
	
	private Timestamp updateDate;
	
	private String registrationNo;

	public BigInteger getRequestDetailId() {
		return requestDetailId;
	}

	public void setRequestDetailId(BigInteger requestDetailId) {
		this.requestDetailId = requestDetailId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getIsCancelled() {
		return isCancelled;
	}

	public void setIsCancelled(String isCancelled) {
		this.isCancelled = isCancelled;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
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

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}
	
}
