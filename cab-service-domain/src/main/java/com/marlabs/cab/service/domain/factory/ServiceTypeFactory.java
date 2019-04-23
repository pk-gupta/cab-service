package com.marlabs.cab.service.domain.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.enums.ServiceTypeEnum;
import com.marlabs.cab.service.domain.command.receiver.CabServiceRequest;
import com.marlabs.cab.service.domain.command.receiver.DropProcessor;
import com.marlabs.cab.service.domain.command.receiver.PickupProcessor;

public class ServiceTypeFactory {

	private ServiceTypeFactory(){}
	
	private static final Map<String, Supplier<CabServiceRequest>> factoryMap = new HashMap<>();
	
	static{
		factoryMap.put(Constants.SERVICE_TYPE_DROP, DropProcessor::new);
		factoryMap.put(Constants.SERVICE_TYPE_PICKUP, PickupProcessor::new);
	}
	
	public static CabServiceRequest getServiceType(String serviceType){
		
		Supplier<CabServiceRequest> command = factoryMap.get(serviceType);		
		
		return Optional.ofNullable(command)
					   .orElseThrow(IllegalArgumentException::new)
					   .get();
	}
}
