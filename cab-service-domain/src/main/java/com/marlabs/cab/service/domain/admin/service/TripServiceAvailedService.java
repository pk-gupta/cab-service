package com.marlabs.cab.service.domain.admin.service;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.TripHeaderEntity;
import com.marlabs.cab.service.persistance.entity.TripServiceAvailedDashboardViewEntity;
import com.marlabs.cab.service.persistance.vo.DataFilterParamsVO;
import com.marlabs.cab.service.persistance.vo.TripServiceAvailedVO;
import com.marlabs.cab.service.persistance.vo.TripServiceDetailsVO;

public interface TripServiceAvailedService {
	
	public String addUpdateTripServiceAvailed(TripServiceDetailsVO tripServiceDetailsVO);
	
	public TripServiceAvailedVO tripAvailedDashboardFilter(DataFilterParamsVO dataFilterParamsVO);
	
	public List<TripServiceAvailedDashboardViewEntity> tripDetails(Long tripHeaderId);
	
	public List<TripHeaderEntity> getTripList(Long tripHeaderId);
	
}
