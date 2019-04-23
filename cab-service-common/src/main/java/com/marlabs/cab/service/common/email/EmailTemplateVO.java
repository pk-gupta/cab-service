package com.marlabs.cab.service.common.email;



import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.marlabs.cab.service.common.enums.EmailTemplateTypeEnum;

public class EmailTemplateVO {

	private String employeeEmail;
	private String managerEmail;
	private String employeeId;
	private String employeeName;
	private String managerName;
	private long requestId;
	private String employeeApproveStatus;
	private Date  serviceDate;
	private String employeeRemark;
	private String employeeSelectedLogin;
	private String employeeSelectedLogout;
	private String employeeSelectedReason;
	private Long requestHeaderId;
	private String requestServiceType;
	private Date emplopyeeSelectedFromDate;
	private Date emplopyeeSelectedToDate;
	private String requestType;
	private String managerFirstName;
	private String managerMiddleName;
	private String managerLastName;
	private String employeeFirstName;
	private String employeeMiddleName;
	private String employeeLastName;
	private String approvalStatus;
	private String managerRemark;
	private String reasonDescription;
	private Date fromDate;
	private Date toDate;
	private String projectName;
	private String projectId;
	private String approvalBy;
	
	private String adminEmail;
	private long requestNumber;
	private String serviceType;
	private String loginTime;
	private String logoutTime;
	private String reason;
	private String employeeLandmark;
	private String serviceTime;
	private Long tripNumber;
	private String vehicleNumber;
	private String driverFirstName;
	private String driverMiddleName;
	private String driverLastName;
	private String expectedTime;
	
	Map<List<String>,List<Date>> cancelScheduleDays;
	
	private EmailTemplateTypeEnum emailTemplateType;
	
	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
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

	public EmailTemplateTypeEnum getEmailTemplateType() {
		return emailTemplateType;
	}

	public void setEmailTemplateType(EmailTemplateTypeEnum emailTemplateType) {
		this.emailTemplateType = emailTemplateType;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public String getLogoutTime() {
		return logoutTime;
	}

	public String getApprovalBy() {
		return approvalBy;
	}

	public Map<List<String>, List<Date>> getCancelScheduleDays() {
		return cancelScheduleDays;
	}

	public void setCancelScheduleDays(Map<List<String>, List<Date>> cancelScheduleDays) {
		this.cancelScheduleDays = cancelScheduleDays;
	}

	public void setApprovalBy(String approvalBy) {
		this.approvalBy = approvalBy;
	}

	public Long getTripNumber() {
		return tripNumber;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setTripNumber(Long tripNumber) {
		this.tripNumber = tripNumber;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getReason() {
		return reason;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public long getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(long requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public String getEmployeeApproveStatus() {
		return employeeApproveStatus;
	}

	public String getExpectedTime() {
		return expectedTime;
	}

	public void setExpectedTime(String expectedTime) {
		this.expectedTime = expectedTime;
	}

	public void setEmployeeApproveStatus(String employeeApproveStatus) {
		this.employeeApproveStatus = employeeApproveStatus;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getEmployeeRemark() {
		return employeeRemark;
	}

	public void setEmployeeRemark(String employeeRemark) {
		this.employeeRemark = employeeRemark;
	}

	public String getEmployeeSelectedLogin() {
		return employeeSelectedLogin;
	}

	public void setEmployeeSelectedLogin(String employeeSelectedLogin) {
		this.employeeSelectedLogin = employeeSelectedLogin;
	}

	public String getEmployeeSelectedLogout() {
		return employeeSelectedLogout;
	}

	public void setEmployeeSelectedLogout(String employeeSelectedLogout) {
		this.employeeSelectedLogout = employeeSelectedLogout;
	}

	public String getEmployeeSelectedReason() {
		return employeeSelectedReason;
	}

	public void setEmployeeSelectedReason(String employeeSelectedReason) {
		this.employeeSelectedReason = employeeSelectedReason;
	}

	public Long getRequestHeaderId() {
		return requestHeaderId;
	}

	public void setRequestHeaderId(Long requestHeaderId) {
		this.requestHeaderId = requestHeaderId;
	}

	public String getRequestServiceType() {
		return requestServiceType;
	}

	public void setRequestServiceType(String requestServiceType) {
		this.requestServiceType = requestServiceType;
	}

	public Date getEmplopyeeSelectedFromDate() {
		return emplopyeeSelectedFromDate;
	}

	public void setEmplopyeeSelectedFromDate(Date emplopyeeSelectedFromDate) {
		this.emplopyeeSelectedFromDate = emplopyeeSelectedFromDate;
	}

	public Date getEmplopyeeSelectedToDate() {
		return emplopyeeSelectedToDate;
	}

	public void setEmplopyeeSelectedToDate(Date emplopyeeSelectedToDate) {
		this.emplopyeeSelectedToDate = emplopyeeSelectedToDate;
	}

	public String getManagerFirstName() {
		return managerFirstName;
	}

	public void setManagerFirstName(String managerFirstName) {
		this.managerFirstName = managerFirstName;
	}

	public String getManagerMiddleName() {
		return managerMiddleName;
	}

	public void setManagerMiddleName(String managerMiddleName) {
		this.managerMiddleName = managerMiddleName;
	}

	public String getManagerLastName() {
		return managerLastName;
	}

	public void setManagerLastName(String managerLastName) {
		this.managerLastName = managerLastName;
	}

	public String getEmployeeFirstName() {
		return employeeFirstName;
	}

	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}

	public String getEmployeeMiddleName() {
		return employeeMiddleName;
	}

	public void setEmployeeMiddleName(String employeeMiddleName) {
		this.employeeMiddleName = employeeMiddleName;
	}

	public String getEmployeeLastName() {
		return employeeLastName;
	}

	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getManagerRemark() {
		return managerRemark;
	}

	public void setManagerRemark(String managerRemark) {
		this.managerRemark = managerRemark;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
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

	public String getEmployeeLandmark() {
		return employeeLandmark;
	}

	public void setEmployeeLandmark(String employeeLandmark) {
		this.employeeLandmark = employeeLandmark;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	
}
