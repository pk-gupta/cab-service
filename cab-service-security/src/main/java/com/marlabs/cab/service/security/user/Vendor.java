package com.marlabs.cab.service.security.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.persistance.entity.OtherUserEntity;
import com.marlabs.cab.service.persistance.entity.UserAdditionalDetailsView;
import com.marlabs.cab.service.security.user.dao.LoginDAO;
import com.marlabs.cab.service.security.user.vo.LoginVO;
import com.marlabs.cab.service.security.user.vo.UserRole;

public class Vendor implements LoginUser {
	
	private static Logger logger = LogManager.getLogger(Vendor.class);
	
	private LoginDAO loginDAO;

	@Override
	public LoginVO login(LoginVO loginVO) {
		logger.info("Vendor -- login() -> Vendor Login ...");
		
		Object vendorDetails = loginDAO.vendorLogin(loginVO);
		Optional<Object> venOptional = Optional.ofNullable(vendorDetails);
		
		if(!venOptional.isPresent()){
			return null;
		}
		
		OtherUserEntity venDetails = (OtherUserEntity)vendorDetails;
		
		if(!isValidUser(loginVO, venDetails)){
			return null;
		}
		
		if(Constants.SERVER_ERROR.equals(venOptional.get().toString())){
			loginVO.setErrorMessage(Constants.SERVER_ERROR);
			return loginVO;
		}
		
		setUserPrimaryDetails(venDetails, loginVO);
		
		Optional<Object> otherDetails = venOptional.map(vDetails -> getUserAdditionalDetails( ((OtherUserEntity)vDetails).getOtherUserId()));
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
	
	private void setUserPrimaryDetails(OtherUserEntity vDetails, LoginVO loginVO){
		//OtherUserEntity vDetails = (OtherUserEntity)vendorDetails;
		loginVO.setUserId(vDetails.getOtherUserId());
		loginVO.setUserFirstName(vDetails.getFirstName());
		loginVO.setUserMiddleName(vDetails.getMiddleName());
		loginVO.setUserLastName(vDetails.getLastName());
		loginVO.setUserPassword(null);
	}
	
	private boolean isValidUser(LoginVO loginVO, OtherUserEntity vDetails){
		//String hashedPwd = BCrypt.hashpw(loginVO.getUserPassword(), BCrypt.gensalt(12));
		//OtherUserEntity vDetails = (OtherUserEntity)vendorDetails;
		 
		if(BCrypt.checkpw(loginVO.getUserPassword().trim(), vDetails.getUserPassword())){
			return true;
		}
		
		return false;
	}

	@Override
	public void setTransaction(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}
	
	private void setUserRoles(String uRole, LoginVO loginVO) {
		UserRole userRole = new UserRole();
		userRole.setName(uRole.toUpperCase());
		List<UserRole> roles = new ArrayList<UserRole>();
		roles.add(userRole);
		loginVO.setAuthorities(roles);
	}
}
