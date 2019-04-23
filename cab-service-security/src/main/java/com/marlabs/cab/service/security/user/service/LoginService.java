package com.marlabs.cab.service.security.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.marlabs.cab.service.security.user.vo.LoginVO;

public interface LoginService extends UserDetailsService{
	
	public LoginVO login(LoginVO loginVO);

}
