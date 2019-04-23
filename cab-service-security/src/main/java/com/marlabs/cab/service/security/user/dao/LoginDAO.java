package com.marlabs.cab.service.security.user.dao;

import com.marlabs.cab.service.security.user.vo.LoginVO;

public interface LoginDAO {
	
	public Object employeeLogin(LoginVO loginVO);
	
	public Object vendorLogin(LoginVO loginVO);

	public Object userAdditionalDetails(String userId);
}
