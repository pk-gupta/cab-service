package com.marlabs.cab.service.domain.factory;

import java.util.EnumMap;
import java.util.Optional;
import java.util.function.Supplier;

import com.marlabs.cab.service.common.enums.UserActionEnum;
import com.marlabs.cab.service.domain.command.receiver.ProcessRequest;
import com.marlabs.cab.service.domain.command.receiver.SaveRequest;
import com.marlabs.cab.service.domain.command.receiver.SubmitRequest;

public class ProcessRequestFactory {
	
	private ProcessRequestFactory(){}
	
	private static final EnumMap<UserActionEnum, Supplier<ProcessRequest>> factoryMap = new EnumMap<>(UserActionEnum.class);
	
	static{
		factoryMap.put(UserActionEnum.SAVE, SaveRequest::new);
		factoryMap.put(UserActionEnum.SUBMIT, SubmitRequest::new);
	}
	
	public static ProcessRequest getProcessRequest(String userType){
		
		Supplier<ProcessRequest> command = factoryMap.get(UserActionEnum.valueOf(userType.toUpperCase()));
		
		return Optional.ofNullable(command)
					   .orElseThrow(IllegalArgumentException::new)
					   .get();
	}
}
