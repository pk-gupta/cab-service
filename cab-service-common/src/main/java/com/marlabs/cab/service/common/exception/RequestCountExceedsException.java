package com.marlabs.cab.service.common.exception;

@SuppressWarnings("serial")
public class RequestCountExceedsException extends CabServiceRequestException{

	public RequestCountExceedsException(String msg) {
		super(msg);
	}
}
