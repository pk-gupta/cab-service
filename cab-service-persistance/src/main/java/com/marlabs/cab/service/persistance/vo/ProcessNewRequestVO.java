package com.marlabs.cab.service.persistance.vo;

import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;

public class ProcessNewRequestVO {
	
	private RequestHeaderEntity requestHeaderEntity = null;
	
	private String statusMessage=null;

	public RequestHeaderEntity getRequestHeaderEntity() {
		return requestHeaderEntity;
	}

	public void setRequestHeaderEntity(RequestHeaderEntity requestHeaderEntity) {
		this.requestHeaderEntity = requestHeaderEntity;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	
	
	

}
