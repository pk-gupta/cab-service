package com.marlabs.cab.service.domain.factory;

import java.util.EnumMap;
import java.util.Optional;
import java.util.function.Supplier;

import com.marlabs.cab.service.common.enums.ServiceTypeEnum;
import com.marlabs.cab.service.domain.command.ServiceTypeValidatorCommand;
import com.marlabs.cab.service.domain.command.impl.DropValidatorCommand;
import com.marlabs.cab.service.domain.command.impl.PickupValidatorCommand;

public class ServiceTypeCommandFactory {
	
	private ServiceTypeCommandFactory(){}
	
	private static final EnumMap<ServiceTypeEnum, Supplier<ServiceTypeValidatorCommand>> factoryMap = new EnumMap<>(ServiceTypeEnum.class);
	
	static{
		factoryMap.put(ServiceTypeEnum.PICKUP, PickupValidatorCommand::new);
		factoryMap.put(ServiceTypeEnum.DROP, DropValidatorCommand::new);
	}
	
	public static ServiceTypeValidatorCommand getServiceType(String userType){
		
		Supplier<ServiceTypeValidatorCommand> command = factoryMap.get(ServiceTypeEnum.valueOf(userType.replace("-", "").trim().toUpperCase()));
		
		return Optional.ofNullable(command)
					   .orElseThrow(IllegalArgumentException::new)
					   .get();
	}
}
