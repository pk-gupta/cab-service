package com.marlabs.cab.service.domain.admin.service;

import com.marlabs.cab.service.persistance.vo.DataFilterParamsVO;
import com.marlabs.cab.service.persistance.vo.TripHistoryVO;

public interface TripHistoryService {

	public TripHistoryVO getSingleTripDetails(Long tripHeaderId);
	
	public TripHistoryVO tripHistoryDashboardFilter(DataFilterParamsVO dataFilterParamsVO);
}
