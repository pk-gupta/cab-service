package com.marlabs.cab.service.domain.command;

import com.marlabs.cab.service.domain.command.receiver.ProcessRequest;
import com.marlabs.cab.service.persistance.marker.InputDataMarker;
import com.marlabs.cab.service.persistance.vo.ProcessNewRequestVO;

public interface ProcessRequestCommand {
	
	public ProcessNewRequestVO execute(InputDataMarker inputDataProvider, Object persistance);
	
	public void setProcessRequest(ProcessRequest processRequest);

}
