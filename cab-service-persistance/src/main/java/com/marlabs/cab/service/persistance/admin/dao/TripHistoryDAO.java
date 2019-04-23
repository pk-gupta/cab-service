package com.marlabs.cab.service.persistance.admin.dao;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.SingleTripDetailsViewEntity;
import com.marlabs.cab.service.persistance.entity.TripHistoryDashboardViewEntity;
import com.marlabs.cab.service.persistance.vo.DataFilterParamsVO;

public interface TripHistoryDAO {
	
	public List<TripHistoryDashboardViewEntity> tripHistoryDashboardFilter(DataFilterParamsVO dataFilterVO);
	
	public List<SingleTripDetailsViewEntity> getSingleTripDetails(Long tripHeaderId);
}

