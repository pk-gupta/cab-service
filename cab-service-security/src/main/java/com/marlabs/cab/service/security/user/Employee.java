package com.marlabs.cab.service.security.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.persistance.entity.EmployeeView;
import com.marlabs.cab.service.persistance.entity.UserAdditionalDetailsView;
import com.marlabs.cab.service.security.user.dao.LoginDAO;
import com.marlabs.cab.service.security.user.vo.LoginVO;
import com.marlabs.cab.service.security.user.vo.UserRole;

public class Employee implements LoginUser {
	
	private static Logger logger = LogManager.getLogger(Employee.class);
	
	private static final String USER_COUNTRY = "India";
	
	private LoginDAO loginDAO;
	
	@Override
	public LoginVO login(LoginVO loginVO) {
		logger.info("Employee -- login() -> Employee Login ...");
		
		Object employeeDetail = loginDAO.employeeLogin(loginVO);
		Optional<Object> empOptional = Optional.ofNullable(employeeDetail);
		
		if(!empOptional.isPresent()){
			return null;
		}
			
		if(Constants.SERVER_ERROR.equals(empOptional.get().toString())){
			loginVO.setErrorMessage(Constants.SERVER_ERROR);
			return loginVO;
		}
		
		EmployeeView empDetails = (EmployeeView)employeeDetail;
		
		if(!empDetails.getCountry().equalsIgnoreCase(USER_COUNTRY)){
			loginVO.setErrorMessage(Constants.USER_UNAUTHORIZED);
			return loginVO;
		}
		
		setUserPrimaryDetails(empDetails, loginVO);
		
		Optional<Object> otherDetails = empOptional.map(eDetails -> getUserAdditionalDetails( ((EmployeeView)eDetails).getGlobalEmpId()));
		assignUserAdditionalDetails(otherDetails, loginVO);
		
		return loginVO;
		
	}
	
	private Object getUserAdditionalDetails(String userId){
		return loginDAO.userAdditionalDetails(userId);
	}
	
	private void assignUserAdditionalDetails(Optional<Object> otherDetails, LoginVO userDetails){
		otherDetails.ifPresent(additionalDetails -> {
			if(!otherDetails.toString().equals(Constants.SERVER_ERROR)){
				UserAdditionalDetailsView userAdditionalDetails = (UserAdditionalDetailsView)additionalDetails;
				userDetails.setUserType(userAdditionalDetails.getUserType());
				userDetails.setUserRole(userAdditionalDetails.getUserRole());
				
				setUserRoles(userAdditionalDetails.getUserRole(), userDetails);
			}
		});
	}
	
	private void setUserPrimaryDetails(EmployeeView eDetails, LoginVO loginVO){
		loginVO.setUserId(eDetails.getGlobalEmpId());
		loginVO.setUserFirstName(eDetails.getEmployeeFirstName());
		loginVO.setUserMiddleName(eDetails.getEmployeeMiddleName());
		loginVO.setUserLastName(eDetails.getEmployeeLastName());
		loginVO.setUserRole(loginVO.getUserType());
		loginVO.setUserPassword(null);
		loginVO.setIsManager(eDetails.getIsManager());
		
		setUserRoles(loginVO.getUserType(), loginVO);
	}

	private void setUserRoles(String uRole, LoginVO loginVO) {
		UserRole userRole = new UserRole();
		userRole.setName(uRole.toUpperCase());
		List<UserRole> roles = new ArrayList<UserRole>();
		roles.add(userRole);
		loginVO.setAuthorities(roles);
	}

	@Override
	public void setTransaction(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}
	
}
