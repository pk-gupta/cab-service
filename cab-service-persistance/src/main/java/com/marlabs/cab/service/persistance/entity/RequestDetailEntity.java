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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "REQUEST_DETAIL")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RequestDetailEntity implements Serializable {

	private static final long serialVersionUID = 3930070674670058084L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REQ_DETAIL_ID")
	private Long requestDetailId;

	/*@JsonManagedReference(value="requestDetail_tripDetail")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "requestDetail", fetch = FetchType.EAGER)
	private List<TripDetailEntity> tripDetailList;*/
	
	
	@JsonBackReference(value="requestHeader_requestDetail")
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "FK_REQ_HEADER_ID", referencedColumnName = "REQ_HEADER_ID")
	private RequestHeaderEntity requestHeader;
	
	@Column(name = "SERVICE_TYPE")
	private String serviceType;

	@Column(name = "SERVICE_DATE")
	private Date serviceDate;
	
	@Column(name = "IS_CANCELLED")
	private String isCancelled;

	@Column(name = "CANCEL_REASON")
	private String cancelReason;

	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;

	public Long getRequestDetailId() {
		return requestDetailId;
	}

	public void setRequestDetailId(Long requestDetailId) {
		this.requestDetailId = requestDetailId;
	}

	public RequestHeaderEntity getRequestHeader() {
		return requestHeader;
	}

	public void setRequestHeader(RequestHeaderEntity requestHeader) {
		this.requestHeader = requestHeader;
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

/*	public List<TripDetailEntity> getTripDetailList() {
		return tripDetailList;
	}

	public void setTripDetailList(List<TripDetailEntity> tripDetailList) {
		this.tripDetailList = tripDetailList;
	}*/
}
