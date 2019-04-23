package com.marlabs.cab.service.security.authentication.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.stereotype.Component;

import com.marlabs.cab.service.security.authentication.entry.CabServiceUserAuthenticationEntryPoint;
import com.marlabs.cab.service.security.authentication.handler.CabServiceUserAuthenticationFailureHandler;
import com.marlabs.cab.service.security.authentication.handler.CabServiceUserAuthenticationSuccessHandler;
import com.marlabs.cab.service.security.user.vo.LoginVO;

@Component
public class CabServiceUserAunthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private static Logger log = LogManager.getLogger(CabServiceUserAunthenticationFilter.class);
	
	@Autowired
	private SessionFixationProtectionStrategy sessionFixationProtectionStrategy;
	
	@Autowired
	private CabServiceUserAuthenticationEntryPoint authenticationEntryPoint;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
	 	
		log.info("doFilter() ->  Cab Service User Authentication Filter ... ");
		
		setPostOnly(false);
		
		/*HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpReponse = (HttpServletResponse) response;

		Authentication sessionUser = SecurityContextHolder.getContext().getAuthentication();
		
		if(Optional.ofNullable(sessionUser).isPresent()){
			log.info("doFilter() -> Session User " + sessionUser.getName());
			log.info("doFilter() -> Request User " + httpRequest.getHeader("Authorization").toString());
			
			if(!isValidSessionUser(httpRequest.getHeader("Authorization"), sessionUser.getName())){
				log.error("doFilter() ->  Unauthorized Session User ... ");
				AuthenticationException authenticationException = new SessionAuthenticationException("Unauthorized Session User ...!!!");
	            authenticationEntryPoint.commence(httpRequest, httpReponse, authenticationException);
	            return;
			}
		}*/
		
		setSessionAuthenticationStrategy(sessionFixationProtectionStrategy);
		
		super.doFilter(request, response, filterChain);
	}
	
	/*private boolean isValidSessionUser(String requestUserPrincipal, String sessionUserPrincipal){
		 return sessionUserPrincipal.equalsIgnoreCase(requestUserPrincipal);
	}*/

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
	    return super.attemptAuthentication(request, response); 
	} 
	 
	 @Override
	 protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		 log.info("setDetails() -> Setting Login details that is processed in Authentication Provider. "); 
		 LoginVO loginVO = new LoginVO();
		 loginVO.setUserEmail(request.getParameter("userEmail"));
		 loginVO.setUserId(request.getParameter("userId"));
		 loginVO.setUserPassword(request.getParameter("password"));
		 loginVO.setUserType(request.getParameter("userType"));
		
		 authRequest.setDetails(loginVO);
	 }
	 
	 @Override
	 @Autowired
	 public void setAuthenticationManager(AuthenticationManager authenticationManager) {
	     super.setAuthenticationManager(authenticationManager);
	 }
	 
	 @Override
	 protected void successfulAuthentication(HttpServletRequest request,
				HttpServletResponse response, FilterChain chain, Authentication authResult)
				throws IOException, ServletException {
		 
		 log.info("successfulAuthentication() -> Authentication Success ... "); 
		 
		 setAuthenticationSuccessHandler(new CabServiceUserAuthenticationSuccessHandler());
		 
		 super.successfulAuthentication(request, response, chain, authResult);
	 }
	 
	 @Override
	 protected void unsuccessfulAuthentication(HttpServletRequest request,
				HttpServletResponse response, AuthenticationException failed)
				throws IOException, ServletException {
		 
		 log.info("unsuccessfulAuthentication() -> Authentication Failed ... "); 
		 
		 setAuthenticationFailureHandler(new CabServiceUserAuthenticationFailureHandler());
		 
		 super.unsuccessfulAuthentication(request, response, failed);
		 
	 }
	 
}
