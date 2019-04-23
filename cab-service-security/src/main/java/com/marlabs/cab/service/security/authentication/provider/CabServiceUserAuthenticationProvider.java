package com.marlabs.cab.service.security.authentication.provider;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.security.authentication.exception.LocaleAuthenticationException;
import com.marlabs.cab.service.security.authentication.exception.ServerProcessingException;
import com.marlabs.cab.service.security.authentication.exception.UserAuthenticationException;
import com.marlabs.cab.service.security.user.service.LoginService;
import com.marlabs.cab.service.security.user.vo.LoginVO;

@Component
public class CabServiceUserAuthenticationProvider implements AuthenticationProvider{
	
	private static Logger logger = LogManager.getLogger(CabServiceUserAuthenticationProvider.class);
	
	@Autowired
	private LoginService loginService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException  {
		
		logger.info("authenticate() -> Looking User in Database "); 
		
		LoginVO loginVO = loginService.login((LoginVO)authentication.getDetails());
		
		if(CabServiceUtil.isNULL(loginVO)){
			logger.info("authenticate() -> LOGIN USER : INVALID USER => Username : Not found in databse."); 
			
			if(!CabServiceUtil.isNULL(authentication.getDetails()) && ((LoginVO)authentication.getDetails()).getUserType().equalsIgnoreCase("vendor")){
				throw new UserAuthenticationException("Invalid Username/Password.");
			}else{
				throw new UserAuthenticationException("Username not matched. Contact HR/IT team.");
			}
		}
		
		if(Constants.USER_UNAUTHORIZED.equals(loginVO.getErrorMessage())){
			logger.info("authenticate() -> LOGIN USER : INVALID LOCALE USER : Non India User. "); 
			throw new LocaleAuthenticationException(("Supported for India users only."));
		}
		
		if(Constants.SERVER_ERROR.equals(loginVO.getErrorMessage())){
			logger.info("authenticate() -> LOGIN USER : SERVER ERROR "); 
			throw new ServerProcessingException(("Failed to process request. Please try later."));
			//return null;
		}
		
		Collection<? extends GrantedAuthority> userAuthorities = loginVO.getAuthorities();
		
		UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(loginVO.getUserEmail(), loginVO.getUserPassword(), userAuthorities);
		userAuthentication.setDetails(loginVO);//Set Custom Object to be sent back to UI through response object from Authentication Success Handler
		
		logger.info("authenticate() -> LOGIN USER :  VALID USER ");
		
		return userAuthentication; 
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication));
	}

}
