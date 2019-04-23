package com.marlabs.cab.service.security.authentication.exception;

import org.springframework.security.core.AuthenticationException;

public class LocaleAuthenticationException extends AuthenticationException {
	
	public LocaleAuthenticationException(String message) {
		super(message);
	}
}
