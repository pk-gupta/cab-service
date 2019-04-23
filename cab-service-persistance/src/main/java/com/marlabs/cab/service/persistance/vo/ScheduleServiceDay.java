package com.marlabs.cab.service.persistance.vo;

import java.sql.Date;

public class ScheduleServiceDay {

	private Date serviceDate;

	private boolean serviceLogin;

	private boolean serviceLogout;
	
	private boolean isCancelled;
	
	private String cancelledReason;

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public boolean isServiceLogin() {
		return serviceLogin;
	}

	public void setServiceLogin(boolean serviceLogin) {
		this.serviceLogin = serviceLogin;
	}

	public boolean isServiceLogout() {
		return serviceLogout;
	}

	public void setServiceLogout(boolean serviceLogout) {
		this.serviceLogout = serviceLogout;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public String getCancelledReason() {
		return cancelledReason;
	}

	public void setCancelledReason(String cancelledReason) {
		this.cancelledReason = cancelledReason;
	}

}
