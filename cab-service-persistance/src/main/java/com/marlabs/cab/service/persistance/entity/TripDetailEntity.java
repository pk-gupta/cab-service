package com.marlabs.cab.service.persistance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TRIP_DETAIL")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TripDetailEntity implements Serializable{

	private static final long serialVersionUID = 5281289594608887032L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TRIP_DETAIL_ID")
	private Long tripDetailId;
	
	@JsonBackReference(value="tripHeader_tripDetail")
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "FK_TRIP_HEADER_ID", referencedColumnName = "TRIP_HEADER_ID")
	private TripHeaderEntity tripHeader;
	
	@JsonBackReference(value="requestDetail_tripDetail")
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "FK_REQ_DETAIL_ID", referencedColumnName = "REQ_DETAIL_ID")
	private RequestDetailEntity requestDetail;
	
	@Column(name = "TRIP_GROUP")
	private String tripGroup;

	@Column(name = "SEQUENCE_NO")
	private Long sequenceNo;
	
	@Column(name = "EMPLOYEE_ID")
	private String employeeId;
	
	@Column(name = "ESTIMATED_TIME")
	private String estimatedtime;

	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATE_DATE",insertable=false)
	private Timestamp updateDate;

	public Long getTripDetailId() {
		return tripDetailId;
	}

	public void setTripDetailId(Long tripDetailId) {
		this.tripDetailId = tripDetailId;
	}

	public TripHeaderEntity getTripHeader() {
		return tripHeader;
	}

	public void setTripHeader(TripHeaderEntity tripHeader) {
		this.tripHeader = tripHeader;
	}

	public RequestDetailEntity getRequestDetail() {
		return requestDetail;
	}

	public void setRequestDetail(RequestDetailEntity requestDetail) {
		this.requestDetail = requestDetail;
	}

	public Long getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Long sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getEstimatedtime() {
		return estimatedtime;
	}

	public void setEstimatedtime(String estimatedtime) {
		this.estimatedtime = estimatedtime;
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

	public String getTripGroup() {
		return tripGroup;
	}

	public void setTripGroup(String tripGroup) {
		this.tripGroup = tripGroup;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
}
