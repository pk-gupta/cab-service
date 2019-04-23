package com.marlabs.cab.service.security.user.factory;

import java.util.EnumMap;
import java.util.Optional;
import java.util.function.Supplier;

import com.marlabs.cab.service.security.user.Employee;
import com.marlabs.cab.service.security.user.LoginUser;
import com.marlabs.cab.service.security.user.Vendor;
import com.marlabs.cab.service.security.user.enums.LoginUserEnum;

public class LoginUserFactory {
	
	private LoginUserFactory(){}
	
	private static final EnumMap<LoginUserEnum, Supplier<LoginUser>> factoryMap = new EnumMap<>(LoginUserEnum.class);
	
	static{
		factoryMap.put(LoginUserEnum.EMPLOYEE, Employee::new);
		factoryMap.put(LoginUserEnum.VENDOR, Vendor::new);
	}
	
	public static LoginUser getLoginUser(String userType){
		
		Supplier<LoginUser> user = factoryMap.get(LoginUserEnum.valueOf(userType.toUpperCase()));
		
		return Optional.ofNullable(user)
					   .orElseThrow(IllegalArgumentException::new)
					   .get();
	}
}
