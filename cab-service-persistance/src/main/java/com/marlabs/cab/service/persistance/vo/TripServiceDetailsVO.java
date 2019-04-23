package com.marlabs.cab.service.persistance.vo;

import java.util.List;

public class TripServiceDetailsVO {

	private String tripStartTime;
	
	private String tripEndTime;
	
	List<TripServiceAvailedVO> tripServiceAvailedList;

	public String getTripStartTime() {
		return tripStartTime;
	}

	public void setTripStartTime(String tripStartTime) {
		this.tripStartTime = tripStartTime;
	}

	public String getTripEndTime() {
		return tripEndTime;
	}

	public void setTripEndTime(String tripEndTime) {
		this.tripEndTime = tripEndTime;
	}

	public List<TripServiceAvailedVO> getTripServiceAvailedList() {
		return tripServiceAvailedList;
	}

	public void setTripServiceAvailedList(List<TripServiceAvailedVO> tripServiceAvailedList) {
		this.tripServiceAvailedList = tripServiceAvailedList;
	}
}
