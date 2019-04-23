package com.marlabs.cab.service.domain.command;

import java.util.List;

import com.marlabs.cab.service.domain.command.receiver.CabServiceRequest;
import com.marlabs.cab.service.persistance.marker.InputDataMarker;

public interface ServiceTypeValidatorCommand {
	
	public void execute(InputDataMarker inputDataProvider, List<?> dbData);
	
	public void setCabServiceRequest(CabServiceRequest cabServiceRequest);
	
}
