package com.marlabs.cab.service.persistance.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "assign_cab_dashboard_detail_view")
public class AssignCabDashboardDetailViewEntity implements Serializable{

	private static final long serialVersionUID = 8548536275178245576L;

	@Id
	@Column(name = "employeeId")
	private String employeeId;

	@Id
	@Column(name = "reqDetailId")
	private Long reqDetailId;
	
	@Column(name = "serviceDate")
	private Date serviceDate;

	@Column(name = "serviceType")
	private String serviceType;

	@Column(name = "officeBranchCode")
	private String officeBranchCode;
	
	@Column(name = "officeBranchName")
	private String officeBranchName;
	
	@Column(name = "officeCityName")
	private String officeCityName;
	
	@Column(name = "loginTime")
	private String loginTime;

	@Column(name = "logoutTime")
	private String logoutTime;

	@Column(name = "empFirstName")
	private String empFirstName;

	@Column(name = "empMiddleName")
	private String empMiddleName;

	@Column(name = "empLastName")
	private String empLastName;

	@Column(name = "empAddress1")
	private String empAddress1;

	@Column(name = "empAddress2")
	private String empAddress2;

	@Column(name = "empAddress3")
	private String empAddress3;

	@Column(name = "empGender")
	private String empGender;

	@Column(name = "empPhone")
	private String empPhone;

	@Id
	@Column(name = "emailId")
	private String emailId;

	@Column(name = "empLandmarkName")
	private String empLandmarkName;
	
	@Id
	@Column(name = "tripHeaderId")
	private Long tripHeaderId;
	
	@Column(name = "tripStatus")
	private String tripStatus;
	
	@Column(name = "vehicleNo")
	private String vehicleNo;
	
	@Column(name = "cabDetailId")
	private String cabDetailId;
	
	@Column(name = "driverFirstName")
	private String driverFirstName;
	
	@Column(name = "driverMiddleName")
	private String driverMiddleName;
	
	@Column(name = "driverLastName")
	private String driverLastName;

	@Column(name = "tripGroup")
	private String tripGroup;
	   
	@Column(name = "sequenceNo")
	private Long sequenceNo;
	
	@Column(name = "estimatedTime")
	private String estimatedTime;
			
	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Long getReqDetailId() {
		return reqDetailId;
	}

	public void setReqDetailId(Long reqDetailId) {
		this.reqDetailId = reqDetailId;
	}

	public String getOfficeCityName() {
		return officeCityName;
	}

	public void setOfficeCityName(String officeCityName) {
		this.officeCityName = officeCityName;
	}

	public Long getTripHeaderId() {
		return tripHeaderId;
	}

	public void setTripHeaderId(Long tripHeaderId) {
		this.tripHeaderId = tripHeaderId;
	}

	public String getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
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

	public String getEmpFirstName() {
		return empFirstName;
	}

	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}

	public String getEmpMiddleName() {
		return empMiddleName;
	}

	public void setEmpMiddleName(String empMiddleName) {
		this.empMiddleName = empMiddleName;
	}

	public String getEmpLastName() {
		return empLastName;
	}

	public String getCabDetailId() {
		return cabDetailId;
	}

	public void setCabDetailId(String cabDetailId) {
		this.cabDetailId = cabDetailId;
	}

	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}

	public String getEmpAddress1() {
		return empAddress1;
	}

	public void setEmpAddress1(String empAddress1) {
		this.empAddress1 = empAddress1;
	}

	public String getEmpAddress2() {
		return empAddress2;
	}

	public void setEmpAddress2(String empAddress2) {
		this.empAddress2 = empAddress2;
	}

	public String getEmpAddress3() {
		return empAddress3;
	}

	public void setEmpAddress3(String empAddress3) {
		this.empAddress3 = empAddress3;
	}

	public String getEmpGender() {
		return empGender;
	}

	public void setEmpGender(String empGender) {
		this.empGender = empGender;
	}

	public String getEmpPhone() {
		return empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getOfficeBranchName() {
		return officeBranchName;
	}

	public void setOfficeBranchName(String officeBranchName) {
		this.officeBranchName = officeBranchName;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmpLandmarkName() {
		return empLandmarkName;
	}

	public void setEmpLandmarkName(String empLandmarkName) {
		this.empLandmarkName = empLandmarkName;
	}

	public String getOfficeBranchCode() {
		return officeBranchCode;
	}

	public void setOfficeBranchCode(String officeBranchCode) {
		this.officeBranchCode = officeBranchCode;
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

}
