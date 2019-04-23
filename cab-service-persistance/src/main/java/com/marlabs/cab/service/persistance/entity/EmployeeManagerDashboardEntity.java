package com.marlabs.cab.service.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "emp_mgr_dashboard_view")
public class EmployeeManagerDashboardEntity {

	@Column(name = "employeeId")
	private String employeeID;

	@Id
	@Column(name = "reqHeaderId")
	private long reqHeaderId;

	@Column(name = "fromDate")
	private String fromDate;

	@Column(name = "toDate")
	private String toDate;

	@Column(name = "requestType")
	private String requestType;

	@Column(name = "loginTime")
	private String loginTime;

	@Column(name = "logoutTime")
	private String logoutTime;

	@Column(name = "reqServiceType ")
	private String reqServiceType;

	@Column(name = "empFirstName")
	private String empFirstName;

	@Column(name = "empFiddleName")
	private String empFiddleName;

	@Column(name = "empLastName")
	private String empLastName;

	@Column(name = "empEmailId")
	private String empEmailId;

	@Column(name = "managerId")
	private String managerId;
	
	@Column(name = "projectName")
	private String projectName;
	
	@Column(name = "mgrFirstName")
	private String mgrFirstName;

	@Column(name = "mgrMiddleName")
	private String mgrMiddleName;

	@Column(name = "mgrLastName")
	private String mgrLastName;

	@Column(name = "mgrEmailId")
	private String mgrEmailId;

	@Column(name = "approveStatus")
	private String approveStatus;

	@Column(name = "employeeRemark")
	private String employeeRemark;

	@Column(name = "managerRemark")
	private String managerRemark;

	@Column(name = "reasonDescription")
	private String reasonDescription;

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public long getReqHeaderId() {
		return reqHeaderId;
	}

	public void setReqHeaderId(long reqHeaderId) {
		this.reqHeaderId = reqHeaderId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
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

	public String getReqServiceType() {
		return reqServiceType;
	}

	public void setReqServiceType(String reqServiceType) {
		this.reqServiceType = reqServiceType;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getEmpFirstName() {
		return empFirstName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}

	public String getEmpFiddleName() {
		return empFiddleName;
	}

	public void setEmpFiddleName(String empFiddleName) {
		this.empFiddleName = empFiddleName;
	}

	public String getEmpLastName() {
		return empLastName;
	}

	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}

	public String getEmpEmailId() {
		return empEmailId;
	}

	public void setEmpEmailId(String empEmailId) {
		this.empEmailId = empEmailId;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getMgrFirstName() {
		return mgrFirstName;
	}

	public void setMgrFirstName(String mgrFirstName) {
		this.mgrFirstName = mgrFirstName;
	}

	public String getMgrMiddleName() {
		return mgrMiddleName;
	}

	public void setMgrMiddleName(String mgrMiddleName) {
		this.mgrMiddleName = mgrMiddleName;
	}

	public String getMgrLastName() {
		return mgrLastName;
	}

	public void setMgrLastName(String mgrLastName) {
		this.mgrLastName = mgrLastName;
	}

	public String getMgrEmailId() {
		return mgrEmailId;
	}

	public void setMgrEmailId(String mgrEmailId) {
		this.mgrEmailId = mgrEmailId;
	}

	public String getEmployeeRemark() {
		return employeeRemark;
	}

	public void setEmployeeRemark(String employeeRemark) {
		this.employeeRemark = employeeRemark;
	}

	public String getManagerRemark() {
		return managerRemark;
	}

	public void setManagerRemark(String managerRemark) {
		this.managerRemark = managerRemark;
	}

	public String getReasonDescription() {
		return reasonDescription;
	}

	public void setReasonDescription(String reasonDescription) {
		this.reasonDescription = reasonDescription;
	}

}
