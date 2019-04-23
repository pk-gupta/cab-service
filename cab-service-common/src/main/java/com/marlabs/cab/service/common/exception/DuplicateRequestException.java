package com.marlabs.cab.service.common.exception;

@SuppressWarnings("serial")
public class DuplicateRequestException extends CabServiceRequestException {

	public DuplicateRequestException(String msg) {
		super(msg);
	}

}
