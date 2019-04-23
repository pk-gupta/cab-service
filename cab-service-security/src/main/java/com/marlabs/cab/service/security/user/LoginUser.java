package com.marlabs.cab.service.security.user;

import com.marlabs.cab.service.security.user.dao.LoginDAO;
import com.marlabs.cab.service.security.user.vo.LoginVO;

public interface LoginUser {
	
	public LoginVO login(LoginVO loginVO);
	
	public void setTransaction(LoginDAO loginDAO);

}
