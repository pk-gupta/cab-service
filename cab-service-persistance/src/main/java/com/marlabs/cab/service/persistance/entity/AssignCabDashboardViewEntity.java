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
@Table(name = "assign_cab_dashboard_view")
public class AssignCabDashboardViewEntity implements Serializable{
	
	private static final long serialVersionUID = -6729222763416572377L;

	@Id
	@Column(name = "uniqueId")
	private String uniqueId;

	@Column(name = "serviceDate")
	private Date serviceDate;

	@Column(name = "serviceType")
	private String serviceType;
	
	@Column(name = "serviceTime")
	private String serviceTime;

	/*@Column(name = "loginTime")
	private String loginTime;

	@Column(name = "logoutTime")
	private String logoutTime;*/

	@Column(name = "officeCityId")
	private Long officeCityId;

	@Column(name = "officeCityName")
	private String officeCityName;

	@Column(name = "officeBranchCode")
	private String officeBranchCode;

	@Column(name = "officeBranchName")
	private String officeBranchName;

	@Column(name = "requestCount")
	private Long requestCount;

	@Column(name = "assignedCount")
	private Long assignedCount;
	
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

	/*public String getLoginTime() {
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
*/
	public Long getOfficeCityId() {
		return officeCityId;
	}

	public void setOfficeCityId(Long officeCityId) {
		this.officeCityId = officeCityId;
	}

	public String getOfficeCityName() {
		return officeCityName;
	}

	public void setOfficeCityName(String officeCityName) {
		this.officeCityName = officeCityName;
	}

	public String getOfficeBranchCode() {
		return officeBranchCode;
	}

	public Long getAssignedCount() {
		return assignedCount;
	}

	public void setAssignedCount(Long assignedCount) {
		this.assignedCount = assignedCount;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public void setOfficeBranchCode(String officeBranchCode) {
		this.officeBranchCode = officeBranchCode;
	}

	public String getOfficeBranchName() {
		return officeBranchName;
	}

	public void setOfficeBranchName(String officeBranchName) {
		this.officeBranchName = officeBranchName;
	}

	public Long getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(Long requestCount) {
		this.requestCount = requestCount;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
}
