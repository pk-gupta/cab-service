package com.marlabs.cab.service.common.exception;

@SuppressWarnings("serial")
public abstract class CabServiceRequestException extends RuntimeException{

	public CabServiceRequestException(String msg) {
		super(msg);
	}
}
