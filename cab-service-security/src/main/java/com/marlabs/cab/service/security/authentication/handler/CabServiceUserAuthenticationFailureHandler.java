package com.marlabs.cab.service.security.authentication.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.marlabs.cab.service.security.authentication.exception.LocaleAuthenticationException;
import com.marlabs.cab.service.security.authentication.exception.ServerProcessingException;
import com.marlabs.cab.service.security.authentication.exception.UserAuthenticationException;

@Component
public class CabServiceUserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler  {
	
	private static Logger log = LogManager.getLogger(CabServiceUserAuthenticationFailureHandler.class);
	
	private static final String ERROR_CODE = "error-code";
	private static final String ERROR_MESSAGE = "error-message";
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	                                    AuthenticationException authException) throws IOException, ServletException {
		
		log.error("onAuthenticationFailure() -> USER AUTHENTICATION FAILED !!! " + authException.getMessage());
		
		if(authException instanceof UserAuthenticationException){
			setErrorDetails(response, HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		}
		
		if(authException instanceof LocaleAuthenticationException){
			setErrorDetails(response, HttpServletResponse.SC_FORBIDDEN, authException.getMessage());
		}
		
		if(authException instanceof SessionAuthenticationException){
			setErrorDetails(response, HttpServletResponse.SC_FORBIDDEN, authException.getMessage());
		}
		
		if(authException instanceof ServerProcessingException){
			setErrorDetails(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, authException.getMessage());
		}
	}
	
	private void setErrorDetails(HttpServletResponse response, int errorCode, String errorMessage) throws IOException, ServletException{
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		response.setStatus(errorCode);
		
		ObjectNode jsonNode = JsonNodeFactory.instance.objectNode();
		jsonNode.put(ERROR_CODE, Integer.toString(errorCode));
		jsonNode.put(ERROR_MESSAGE, errorMessage);
		response.getWriter().println(jsonNode);
		response.getWriter().flush();
	}
}
