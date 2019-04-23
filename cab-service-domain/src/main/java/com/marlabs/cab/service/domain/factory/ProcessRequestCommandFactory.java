package com.marlabs.cab.service.domain.factory;

import java.util.EnumMap;
import java.util.Optional;
import java.util.function.Supplier;

import com.marlabs.cab.service.common.enums.UserActionEnum;
import com.marlabs.cab.service.domain.command.ProcessRequestCommand;
import com.marlabs.cab.service.domain.command.impl.SaveRequestCommand;
import com.marlabs.cab.service.domain.command.impl.SubmitRequestCommand;

public class ProcessRequestCommandFactory {
	
	private ProcessRequestCommandFactory(){}
	
	private static final EnumMap<UserActionEnum, Supplier<ProcessRequestCommand>> factoryMap = new EnumMap<>(UserActionEnum.class);
	
	static{
		factoryMap.put(UserActionEnum.SAVE, SaveRequestCommand::new);
		factoryMap.put(UserActionEnum.SUBMIT, SubmitRequestCommand::new);
	}
	
	public static ProcessRequestCommand getProcessCommand(String userType){
		
		Supplier<ProcessRequestCommand> command = factoryMap.get(UserActionEnum.valueOf(userType.toUpperCase()));
		
		return Optional.ofNullable(command)
					   .orElseThrow(IllegalArgumentException::new)
					   .get();
	}
}
