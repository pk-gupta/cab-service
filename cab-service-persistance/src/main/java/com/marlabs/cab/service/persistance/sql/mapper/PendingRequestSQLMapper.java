package com.marlabs.cab.service.persistance.sql.mapper;

public class PendingRequestSQLMapper {
	
	private Integer pendingApprCount;
	
	private Integer noShowCount;
	
	private String  landmark;
	
	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public Integer getPendingApprCount() {
		return pendingApprCount;
	}

	public void setPendingApprCount(Integer pendingApprCount) {
		this.pendingApprCount = pendingApprCount;
	}

	public Integer getNoShowCount() {
		return noShowCount;
	}

	public void setNoShowCount(Integer noShowCount) {
		this.noShowCount = noShowCount;
	}
	
	

}
