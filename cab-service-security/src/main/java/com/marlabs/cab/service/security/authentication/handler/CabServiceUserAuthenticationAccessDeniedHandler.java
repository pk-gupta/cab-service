package com.marlabs.cab.service.security.authentication.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class CabServiceUserAuthenticationAccessDeniedHandler implements AccessDeniedHandler {
	
	private static Logger logger = LogManager.getLogger(CabServiceUserAuthenticationAccessDeniedHandler.class);
	
	private static final String ERROR_CODE = "error-code";
	private static final String ERROR_MESSAGE = "error-message";

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException authException) throws IOException, ServletException {
		
		logger.error("commence() -> CAB SERVICE : USER NOT AUTHENTICATED : ACCESS DENIED ");
		
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		
		ObjectNode jsonNode = JsonNodeFactory.instance.objectNode();
		jsonNode.put(ERROR_CODE, HttpServletResponse.SC_FORBIDDEN);
		jsonNode.put(ERROR_MESSAGE, authException.getMessage());
		response.getWriter().println(jsonNode);
		response.getWriter().flush();
	}

}
