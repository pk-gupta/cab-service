
package com.marlabs.cab.service.domain.batch.roster;

public class RosterMapperVO {

	private String employeeId;
	private String employeePhone;
	private String managerId;
	private String projectId;
	private String fromDate;
	private String toDate;
	private String requestServiceType;
	private String requestSpecificDate;
	private String requestType;
	private String oficeBranch;
	private String weekOff1;
	private String weekOff2;
	private String weekOff3;
	private String noOffWeekOffs;
	private String landMark;
	private String loginTime;
	private String logOutTime;
	private String reason;
	private String managerRemark;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeePhone() {
		return employeePhone;
	}

	public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
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

	public String getRequestSpecificDate() {
		return requestSpecificDate;
	}

	public void setRequestSpecificDate(String requestSpecificDate) {
		this.requestSpecificDate = requestSpecificDate;
	}

	public String getRequestServiceType() {
		return requestServiceType;
	}

	public void setRequestServiceType(String requestServiceType) {
		this.requestServiceType = requestServiceType;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getOficeBranch() {
		return oficeBranch;
	}

	public String getNoOffWeekOffs() {
		return noOffWeekOffs;
	}

	public void setNoOffWeekOffs(String noOffWeekOffs) {
		this.noOffWeekOffs = noOffWeekOffs;
	}

	public void setOficeBranch(String oficeBranch) {
		this.oficeBranch = oficeBranch;
	}

	public String getWeekOff1() {
		return weekOff1;
	}

	public void setWeekOff1(String weekOff1) {
		this.weekOff1 = weekOff1;
	}

	public String getWeekOff2() {
		return weekOff2;
	}

	public void setWeekOff2(String weekOff2) {
		this.weekOff2 = weekOff2;
	}

	public String getWeekOff3() {
		return weekOff3;
	}

	public void setWeekOff3(String weekOff3) {
		this.weekOff3 = weekOff3;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLogOutTime() {
		return logOutTime;
	}

	public void setLogOutTime(String logOutTime) {
		this.logOutTime = logOutTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getManagerRemark() {
		return managerRemark;
	}

	public void setManagerRemark(String managerRemark) {
		this.managerRemark = managerRemark;
	}

}
