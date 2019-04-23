package com.marlabs.cab.service.common.util;

import org.springframework.http.HttpStatus;

public class AppServerUtil {

	public static HttpStatus getResponseStatus(Object object, HttpStatus httpStatus){
		httpStatus = HttpStatus.OK;
		
		if(CabServiceUtil.isNULL(object)){
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		/*Optional.ofNullable(object)
				.orElse(httpStatus = HttpStatus.INTERNAL_SERVER_ERROR);*/
				
		return httpStatus;
	}
}
