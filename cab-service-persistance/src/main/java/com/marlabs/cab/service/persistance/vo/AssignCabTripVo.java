package com.marlabs.cab.service.persistance.vo;

import java.sql.Date;

public class AssignCabTripVo {

	private String serviceType;
	private Date tripDate;
	private String tripTime;
	private Long cabDetailId;
	private String otherVechicleRegNo;
	private String otherDriverName;
	private String otherDriverPhone;
	private String tripStatus;
	private String tripStartTime;
	private String tripEndTime;
	private String employeeId;
	private Long tripHeaderId;
	private Long reqDetailId;
	private String tripGroup;
	private Long sequenceNo;
	private String estimatedTime;
	private String offBranchCode;
	
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public Date getTripDate() {
		return tripDate;
	}
	public void setTripDate(Date tripDate) {
		this.tripDate = tripDate;
	}
	public String getTripTime() {
		return tripTime;
	}
	public void setTripTime(String tripTime) {
		this.tripTime = tripTime;
	}
	public Long getCabDetailId() {
		return cabDetailId;
	}
	public void setCabDetailId(Long cabDetailId) {
		this.cabDetailId = cabDetailId;
	}
	public String getOtherVechicleRegNo() {
		return otherVechicleRegNo;
	}
	public void setOtherVechicleRegNo(String otherVechicleRegNo) {
		this.otherVechicleRegNo = otherVechicleRegNo;
	}
	public String getOtherDriverName() {
		return otherDriverName;
	}
	public void setOtherDriverName(String otherDriverName) {
		this.otherDriverName = otherDriverName;
	}
	public String getOtherDriverPhone() {
		return otherDriverPhone;
	}
	public void setOtherDriverPhone(String otherDriverPhone) {
		this.otherDriverPhone = otherDriverPhone;
	}
	public String getTripStatus() {
		return tripStatus;
	}
	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}
	public String getTripStartTime() {
		return tripStartTime;
	}
	public void setTripStartTime(String tripStartTime) {
		this.tripStartTime = tripStartTime;
	}
	public String getTripEndTime() {
		return tripEndTime;
	}
	public void setTripEndTime(String tripEndTime) {
		this.tripEndTime = tripEndTime;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public Long getTripHeaderId() {
		return tripHeaderId;
	}
	public void setTripHeaderId(Long tripHeaderId) {
		this.tripHeaderId = tripHeaderId;
	}
	public Long getReqDetailId() {
		return reqDetailId;
	}
	public void setReqDetailId(Long reqDetailId) {
		this.reqDetailId = reqDetailId;
	}
	public String getTripGroup() {
		return tripGroup;
	}
	public void setTripGroup(String tripGroup) {
		this.tripGroup = tripGroup;
	}
	public Long getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(Long sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public String getEstimatedTime() {
		return estimatedTime;
	}
	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	public String getOffBranchCode() {
		return offBranchCode;
	}
	public void setOffBranchCode(String offBranchCode) {
		this.offBranchCode = offBranchCode;
	}
}
