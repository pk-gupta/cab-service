package com.marlabs.cab.service.domain.criteria.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;

import com.marlabs.cab.service.domain.criteria.RequestCRUDCriteria;
import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;

public class RequestDeleteCriteria implements RequestCRUDCriteria {

	@Override
	public List<RequestDetailEntity> meetCriteria(List<RequestDetailEntity> inputRequestDetails,
			List<RequestDetailEntity> existingRequestDetails) {
		
		return existingRequestDetails.stream()
								  	 .filter(existingReqDetail -> !containsRequestDetails(existingReqDetail, inputRequestDetails))
								  	 .collect(Collectors.toList());
	}
	
	private boolean containsRequestDetails(RequestDetailEntity existingReqDet, List<RequestDetailEntity> inputRequestDetails){
		return inputRequestDetails.stream().anyMatch(currentReqDetail -> currentReqDetail.getServiceType().equalsIgnoreCase(existingReqDet.getServiceType()) 
						&& DateUtils.isSameDay(currentReqDetail.getServiceDate(), existingReqDet.getServiceDate()) );
	}

}
