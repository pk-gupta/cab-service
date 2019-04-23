package com.marlabs.cab.service.domain.command.impl;

import com.marlabs.cab.service.domain.command.ProcessRequestCommand;
import com.marlabs.cab.service.domain.command.receiver.ProcessRequest;
import com.marlabs.cab.service.persistance.marker.InputDataMarker;
import com.marlabs.cab.service.persistance.vo.ProcessNewRequestVO;

public class SaveRequestCommand implements ProcessRequestCommand {
	
	private ProcessRequest processRequest;

	@Override
	public ProcessNewRequestVO execute(InputDataMarker inputDataProvider, Object persistance) {
		return processRequest.save(inputDataProvider, persistance);
	}

	@Override
	public void setProcessRequest(ProcessRequest processRequest) {
		this.processRequest = processRequest;
	}
}
