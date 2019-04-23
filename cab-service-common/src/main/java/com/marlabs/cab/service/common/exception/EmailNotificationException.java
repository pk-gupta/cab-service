package com.marlabs.cab.service.common.exception;

public class EmailNotificationException extends RuntimeException{
	
	private static final long serialVersionUID = 6736789544269862239L;

	public EmailNotificationException(String message){
		super(message);
	}
}
