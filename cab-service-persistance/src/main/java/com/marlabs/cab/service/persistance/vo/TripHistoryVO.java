package com.marlabs.cab.service.persistance.vo;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.OfficeCityEntity;
import com.marlabs.cab.service.persistance.entity.SingleTripDetailsViewEntity;
import com.marlabs.cab.service.persistance.entity.TripHistoryDashboardViewEntity;

public class TripHistoryVO {
	
	private List<OfficeCityEntity> officeCityEntityList;
	
	private List<TripHistoryDashboardViewEntity> tripHistoryDashboardList;
	
	private List<SingleTripDetailsViewEntity> singleTripDetailsList;

	public List<OfficeCityEntity> getOfficeCityEntityList() {
		return officeCityEntityList;
	}

	public void setOfficeCityEntityList(List<OfficeCityEntity> officeCityEntityList) {
		this.officeCityEntityList = officeCityEntityList;
	}

	public List<TripHistoryDashboardViewEntity> getTripHistoryDashboardList() {
		return tripHistoryDashboardList;
	}

	public void setTripHistoryDashboardList(List<TripHistoryDashboardViewEntity> tripHistoryDashboardList) {
		this.tripHistoryDashboardList = tripHistoryDashboardList;
	}

	public List<SingleTripDetailsViewEntity> getSingleTripDetailsList() {
		return singleTripDetailsList;
	}

	public void setSingleTripDetailsList(List<SingleTripDetailsViewEntity> singleTripDetailsList) {
		this.singleTripDetailsList = singleTripDetailsList;
	}

}
