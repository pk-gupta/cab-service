package com.marlabs.cab.service.domain.command.impl;

import java.util.List;

import com.marlabs.cab.service.domain.command.ServiceTypeValidatorCommand;
import com.marlabs.cab.service.domain.command.receiver.CabServiceRequest;
import com.marlabs.cab.service.persistance.marker.InputDataMarker;

public class DropValidatorCommand implements ServiceTypeValidatorCommand {
	
	private CabServiceRequest cabServiceRequest;
	
	@Override
	public void execute(InputDataMarker inputDataProvider, List<?> dbData) {
		cabServiceRequest.drop(inputDataProvider, dbData);
	}

	@Override
	public void setCabServiceRequest(CabServiceRequest cabServiceRequest) {
		this.cabServiceRequest = cabServiceRequest;
	}

}
