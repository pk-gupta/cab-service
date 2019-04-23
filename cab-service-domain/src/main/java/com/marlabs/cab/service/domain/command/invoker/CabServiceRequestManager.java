package com.marlabs.cab.service.domain.command.invoker;

import java.util.List;

import com.marlabs.cab.service.domain.command.ServiceTypeValidatorCommand;
import com.marlabs.cab.service.domain.command.receiver.CabServiceRequest;
import com.marlabs.cab.service.persistance.marker.InputDataMarker;

public class CabServiceRequestManager {
	
	private ServiceTypeValidatorCommand serviceCommand;
	
	public CabServiceRequestManager(ServiceTypeValidatorCommand serviceCommand){
		this.serviceCommand = serviceCommand;
	}
	
	public void performPickupDropValidation(CabServiceRequest cabServiceRequest, InputDataMarker inputDataProvider
			,List<?> dbData){
		serviceCommand.setCabServiceRequest(cabServiceRequest);
		serviceCommand.execute(inputDataProvider, dbData);
	}
}
