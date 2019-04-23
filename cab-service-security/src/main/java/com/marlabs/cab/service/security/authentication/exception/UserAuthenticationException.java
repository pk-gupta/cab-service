package com.marlabs.cab.service.security.authentication.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAuthenticationException extends AuthenticationException{
	
	public UserAuthenticationException(String message) {
		super(message);
	}

}
