package com.marlabs.cab.service.persistance.admin.dao;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.TripHeaderEntity;
import com.marlabs.cab.service.persistance.entity.TripServiceAvailedDashboardViewEntity;
import com.marlabs.cab.service.persistance.entity.TripServiceAvailedEntity;
import com.marlabs.cab.service.persistance.vo.DataFilterParamsVO;
import com.marlabs.cab.service.persistance.vo.TripServiceAvailedVO;
import com.marlabs.cab.service.persistance.vo.TripServiceDetailsVO;

public interface TripServiceAvailedDAO {

	public List<TripServiceAvailedDashboardViewEntity> tripAvailedDashboardFilter(DataFilterParamsVO dataFilterParamsVO);
	
	public String addTripAvailedDetails(TripServiceAvailedEntity tripServiceAvailed);
	
	public String addUpdateTripServiceAvailed(List<TripServiceAvailedEntity> tripServiceAvailed,TripServiceDetailsVO tripServiceDetailsVO);
	
	public List<TripServiceAvailedDashboardViewEntity> tripDetails(Long tripId);
	
	public List<TripHeaderEntity> getTripList(Long tripHeaderId);
	
	public String deleteTripHeaderDetails(Long tripHeaderId);
}
