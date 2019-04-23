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
@Table(name = "employee_existing_request_view")
public class EmployeeExistingRequestView implements Serializable{

	private static final long serialVersionUID = -8982341410132883213L;
	
	@Column(name = "employeeId")
	private String employeeId;
	
	@Column(name = "fromDate")
	private Date fromDate;
	
	@Column(name = "toDate")
	private Date toDate;
	
	@Column(name = "reqHeaderId")
	private Long reqHeaderId;
	
	@Id
	@Column(name = "reqDetailId")
	private String reqDetailId;

	@Column(name = "serviceDate")
	private Date serviceDate;

	@Column(name = "serviceType")
	private String serviceType;
	
	@Column(name = "loginTime")
	private String loginTime;

	@Column(name = "logoutTime")
	private String logoutTime;
	
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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

	public Long getReqHeaderId() {
		return reqHeaderId;
	}

	public void setReqHeaderId(Long reqHeaderId) {
		this.reqHeaderId = reqHeaderId;
	}

	public String getReqDetailId() {
		return reqDetailId;
	}

	public void setReqDetailId(String reqDetailId) {
		this.reqDetailId = reqDetailId;
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

}
