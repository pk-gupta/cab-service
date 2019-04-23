package com.marlabs.cab.service.persistance.entity;

import java.io.Serializable;

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
@Table(name = "TRIP_AVAILED")
public class TripServiceAvailedEntity implements Serializable{

	private static final long serialVersionUID = -5829812237475534031L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TRIP_AVAILED_ID")
	private Long tripAvailedId;

	@JsonBackReference(value="tripHeader_tripServiceAvailed")
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "FK_TRIP_HEADER_ID", referencedColumnName = "TRIP_HEADER_ID")
	private TripHeaderEntity tripHeader;
	
	@JsonBackReference(value="requestDetail_tripServiceAvailed")
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "FK_REQ_DETAIL_ID", referencedColumnName = "REQ_DETAIL_ID")
	private RequestDetailEntity requestDetail;

	@Column(name = "EMPLOYEE_ID")
	private String employeeId;
	
	@Column(name = "ACTUAL_TIME")
	private String actualTime;
	
	@Column(name = "IS_TRIP_AVAILED")
	private String isTripAvailed;

	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	/*@Column(name = "UPDATE_DATE",insertable = false,updatable= true)
	private Timestamp updateDate;*/

	public Long getTripAvailedId() {
		return tripAvailedId;
	}

	public void setTripAvailedId(Long tripAvailedId) {
		this.tripAvailedId = tripAvailedId;
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

	public String getIsTripAvailed() {
		return isTripAvailed;
	}

	public void setIsTripAvailed(String isTripAvailed) {
		this.isTripAvailed = isTripAvailed;
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

	/*public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}*/

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getActualTime() {
		return actualTime;
	}

	public void setActualTime(String actualTime) {
		this.actualTime = actualTime;
	}
	
}
