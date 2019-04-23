package com.marlabs.cab.service.domain.command.receiver;

import com.marlabs.cab.service.persistance.marker.InputDataMarker;
import com.marlabs.cab.service.persistance.vo.ProcessNewRequestVO;

public interface ProcessRequest {
	
	public ProcessNewRequestVO save(InputDataMarker inputDataProvider, Object persistance);
	
	public ProcessNewRequestVO submit(InputDataMarker inputDataProvider, Object persistance);
}
