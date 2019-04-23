package com.marlabs.cab.service.domain.command.receiver;

import java.util.List;

import com.marlabs.cab.service.persistance.marker.InputDataMarker;

public interface CabServiceRequest {
	
	public void pickup(InputDataMarker dataMarker, List<?> dbData);
	
	public void drop(InputDataMarker dataMarker, List<?> dbData);

}
