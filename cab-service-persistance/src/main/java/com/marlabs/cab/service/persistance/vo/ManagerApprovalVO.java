package com.marlabs.cab.service.persistance.vo;



import java.sql.Date;

import javax.persistence.Column;

public class ManagerApprovalVO {
	
	private String managerId;
	private Long requestId;
	private String employeeId;
	private String mngrFirstName;
	private String mngrMiddleName;
	private String mngrLastName;
	private String empFirstName;
	private String empMiddleName;
	private String empLastName;
	private String managerEmail;
	private String employeeEmail;
	private String approvalComment;
	private String approvalStatus;
	private String requestType;
	private String mngrRemark;
	private String loginTime;
	private String logoutTime;
	private String reqServiceType;
	private String employeeRemark;
	private String reasonDescription;
	private Date fromDate;
	private Date toDate;
	private String adminEmail;
	private String userAction;
	private String projectName;
	
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public Long getRequestId() {
		return requestId;
	}
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getMngrFirstName() {
		return mngrFirstName;
	}
	public void setMngrFirstName(String mngrFirstName) {
		this.mngrFirstName = mngrFirstName;
	}
	public String getMngrMiddleName() {
		return mngrMiddleName;
	}
	public void setMngrMiddleName(String mngrMiddleName) {
		this.mngrMiddleName = mngrMiddleName;
	}
	public String getMngrLastName() {
		return mngrLastName;
	}
	public void setMngrLastName(String mngrLastName) {
		this.mngrLastName = mngrLastName;
	}
	public String getEmpFirstName() {
		return empFirstName;
	}
	public String getUserAction() {
		return userAction;
	}
	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
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
	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}
	public String getManagerEmail() {
		return managerEmail;
	}
	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public String getApprovalComment() {
		return approvalComment;
	}
	public void setApprovalComment(String approvalComment) {
		this.approvalComment = approvalComment;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getMngrRemark() {
		return mngrRemark;
	}
	public void setMngrRemark(String mngrRemark) {
		this.mngrRemark = mngrRemark;
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
	public String getEmployeeRemark() {
		return employeeRemark;
	}
	public void setEmployeeRemark(String employeeRemark) {
		this.employeeRemark = employeeRemark;
	}
	public String getReasonDescription() {
		return reasonDescription;
	}
	public void setReasonDescription(String reasonDescription) {
		this.reasonDescription = reasonDescription;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
}
