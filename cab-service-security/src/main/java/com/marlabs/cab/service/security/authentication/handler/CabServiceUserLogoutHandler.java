package com.marlabs.cab.service.security.authentication.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.stereotype.Component;

@Component
public class CabServiceUserLogoutHandler implements LogoutSuccessHandler{
	
	private static Logger logger = LogManager.getLogger(CabServiceUserLogoutHandler.class);
	
	@Autowired
	RedisOperationsSessionRepository redisOperationsSessionRepository;
	
	@Autowired
	SessionRegistry sessionRegistry;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		logger.info("onLogoutSuccess() : LOG OUT - User Session .");
		
		SecurityContextLogoutHandler secConxtLogout = new SecurityContextLogoutHandler();
		secConxtLogout.setClearAuthentication(true);
		secConxtLogout.setInvalidateHttpSession(true);
		secConxtLogout.logout(request, response, authentication);
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().flush();
		
		logger.info("onLogoutSuccess() : LOG OUT Success. ");
	}
	
}
