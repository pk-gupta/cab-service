package com.marlabs.cab.service.security.user.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.marlabs.cab.service.security.user.LoginUser;
import com.marlabs.cab.service.security.user.dao.LoginDAO;
import com.marlabs.cab.service.security.user.factory.LoginUserFactory;
import com.marlabs.cab.service.security.user.service.LoginService;
import com.marlabs.cab.service.security.user.vo.LoginVO;

@Service
public class LoginServiceImpl implements LoginService {
	
	private static Logger logger = LogManager.getLogger(LoginServiceImpl.class);
	
	@Autowired
	LoginDAO loginDAO;

	@Override
	public LoginVO login(LoginVO loginVO) {
		
		logger.info("LoginServiceImpl -- login() -> Login Service Call...");
		
		LoginUser loginUser = LoginUserFactory.getLoginUser(loginVO.getUserType());
		
		loginUser.setTransaction(loginDAO);
		
		return loginUser.login(loginVO);
	}

	@Override
	public LoginVO loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}
