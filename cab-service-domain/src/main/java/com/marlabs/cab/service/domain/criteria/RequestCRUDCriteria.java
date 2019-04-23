package com.marlabs.cab.service.domain.criteria;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;

public interface RequestCRUDCriteria {
	public List<RequestDetailEntity> meetCriteria(List<RequestDetailEntity> inputRequestDetails, List<RequestDetailEntity> existingRequestDetails);
}
