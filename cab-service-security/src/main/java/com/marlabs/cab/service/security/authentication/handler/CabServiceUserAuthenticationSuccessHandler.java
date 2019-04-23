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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.marlabs.cab.service.security.user.vo.LoginVO;

@Component
public class CabServiceUserAuthenticationSuccessHandler implements AuthenticationSuccessHandler { 

	private final Logger log = LogManager.getLogger(CabServiceUserAuthenticationSuccessHandler.class);
	
	@Autowired
	RedisOperationsSessionRepository redisOperationsSessionRepository;
	
	@Autowired
	SessionRegistry sessionRegistry;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                    Authentication authentication) throws IOException, ServletException {
		
		LoginVO loginVO = (LoginVO)authentication.getDetails();
		
		response.setStatus(HttpServletResponse.SC_OK);
		
		/*ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String loginJson = ow.writeValueAsString(loginVO);
		response.getWriter().println(loginJson);
		response.getWriter().flush();*/
		
		
		ObjectNode jsonNode = JsonNodeFactory.instance.objectNode();
		jsonNode.put("userId", loginVO.getUserId());
		jsonNode.put("userEmail", loginVO.getUserEmail());
		jsonNode.put("userFirstName", loginVO.getUserFirstName());
		jsonNode.put("userMiddleName", loginVO.getUserMiddleName());
		jsonNode.put("userLastName", loginVO.getUserLastName());
		jsonNode.put("userRole", loginVO.getUserRole());
		 
		response.getWriter().println(jsonNode);
		 
		response.getWriter().flush();
		
		log.info("onAuthenticationSuccess() -> USER AUTHENTICATED SUCCESSFULLY : Email :: " + loginVO.getUserEmail());
		
	}
	
	/* public String getUserExistingSession(String username) {
		 String currentUserSessionId = null;
	    for (Object principal : sessionRegistry.getAllPrincipals()) {
	        if (principal instanceof User) {
	            UserDetails userDetails = (UserDetails) principal;
	            if (userDetails.getUsername().equalsIgnoreCase(username)) {
	           	 currentUserSessionId = sessionRegistry.getAllSessions(userDetails, true).get(0).getSessionId();
	                break;
	            }
	        }
	    }
	    
	    return currentUserSessionId;
	}*/
	
	/*private void setExistingSessionOfUser(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
		String userExistingSession) throws JsonProcessingException, IOException {
		if(!CabServiceUtil.isNULL(userExistingSession)){
			LoginVO loginVO = (LoginVO)authentication.getDetails();
			loginVO.setSession(httpRequest.getSession().getId());
			
			httpResponse.setStatus(HttpServletResponse.SC_OK);
			
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String loginJson = ow.writeValueAsString(loginVO);
			
			httpResponse.getWriter().println(loginJson);
			httpResponse.getWriter().flush();
		}
}*/
	
}

