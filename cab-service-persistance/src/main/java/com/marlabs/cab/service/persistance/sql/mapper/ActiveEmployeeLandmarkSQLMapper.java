package com.marlabs.cab.service.persistance.sql.mapper;

import java.math.BigInteger;

public class ActiveEmployeeLandmarkSQLMapper {
	
	private BigInteger employeeLandmarkId;
	
	private Integer activeLandmarkCount;
	
	private Integer requestAvailableCount;	

	public BigInteger getEmployeeLandmarkId() {
		return employeeLandmarkId;
	}

	public void setEmployeeLandmarkId(BigInteger employeeLandmarkId) {
		this.employeeLandmarkId = employeeLandmarkId;
	}

	public Integer getActiveLandmarkCount() {
		return activeLandmarkCount;
	}

	public void setActiveLandmarkCount(Integer activeLandmarkCount) {
		this.activeLandmarkCount = activeLandmarkCount;
	}

	public Integer getRequestAvailableCount() {
		return requestAvailableCount;
	}

	public void setRequestAvailableCount(Integer requestAvailableCount) {
		this.requestAvailableCount = requestAvailableCount;
	}
	
	

}
