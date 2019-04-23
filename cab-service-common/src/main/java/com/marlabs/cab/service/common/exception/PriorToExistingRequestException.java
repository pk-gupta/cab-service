package com.marlabs.cab.service.common.exception;

@SuppressWarnings("serial")
public class PriorToExistingRequestException extends CabServiceRequestException {

	public PriorToExistingRequestException(String msg) {
		super(msg);
	}

}
