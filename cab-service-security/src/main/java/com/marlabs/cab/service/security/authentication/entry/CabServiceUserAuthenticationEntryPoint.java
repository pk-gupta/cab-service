package com.marlabs.cab.service.security.authentication.entry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class CabServiceUserAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static Logger logger = LogManager.getLogger(CabServiceUserAuthenticationEntryPoint.class);
	
	private static final String ERROR_CODE = "error-code";
	private static final String ERROR_MESSAGE = "error-message";
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.info("commence() -> CAB SERVICE : USER AUTHENTICATION ENTRY POINT ");
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		
		ObjectNode jsonNode = JsonNodeFactory.instance.objectNode();
		jsonNode.put(ERROR_CODE, HttpServletResponse.SC_UNAUTHORIZED);
		jsonNode.put(ERROR_MESSAGE, authException.getMessage());
		response.getWriter().println(jsonNode);
		response.getWriter().flush();
	}
}
