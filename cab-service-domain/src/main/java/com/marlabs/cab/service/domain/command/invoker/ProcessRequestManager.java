package com.marlabs.cab.service.domain.command.invoker;

import com.marlabs.cab.service.domain.command.ProcessRequestCommand;
import com.marlabs.cab.service.domain.command.receiver.ProcessRequest;
import com.marlabs.cab.service.persistance.marker.InputDataMarker;
import com.marlabs.cab.service.persistance.vo.ProcessNewRequestVO;

public class ProcessRequestManager {
	
	private ProcessRequestCommand processRequestCommand;
	
	public ProcessRequestManager(ProcessRequestCommand processRequestCommand){
		this.processRequestCommand = processRequestCommand;
	}
	
	public ProcessNewRequestVO performSaveSubmitOperation(InputDataMarker inputDataProvider, Object persistance, ProcessRequest processRequest){
		processRequestCommand.setProcessRequest(processRequest);
		return processRequestCommand.execute(inputDataProvider, persistance);
	}
}
