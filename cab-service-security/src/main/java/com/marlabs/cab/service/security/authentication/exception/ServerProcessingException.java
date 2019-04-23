package com.marlabs.cab.service.security.authentication.exception;

import org.springframework.security.core.AuthenticationException;

public class ServerProcessingException extends AuthenticationException{

	public ServerProcessingException(String message) {
		super(message);
	}
}
