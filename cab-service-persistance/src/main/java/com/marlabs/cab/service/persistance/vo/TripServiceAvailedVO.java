package com.marlabs.cab.service.persistance.vo;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.OfficeCityEntity;
import com.marlabs.cab.service.persistance.entity.TripServiceAvailedDashboardViewEntity;

public class TripServiceAvailedVO {
	
	private List<TripServiceAvailedDashboardViewEntity> tripAvaileddashboardList;
	
	private List<OfficeCityEntity> officeCityEntityList;
	
	private Long tripAvailedId;
	
	private String isTripAvailed;
	
	private Long tripHeaderId;
	
	private Long reqDetailId;

	private String estimatedTime;
	
	private String actualTime;
	
	private String employeeId;
	
	/*private String tripStartTime;
	
	private String tripEndTime;*/
	
	public List<OfficeCityEntity> getOfficeCityEntityList() {
		return officeCityEntityList;
	}

	public void setOfficeCityEntityList(List<OfficeCityEntity> officeCityEntityList) {
		this.officeCityEntityList = officeCityEntityList;
	}

	public List<TripServiceAvailedDashboardViewEntity> getTripAvaileddashboardList() {
		return tripAvaileddashboardList;
	}

	public void setTripAvaileddashboardList(List<TripServiceAvailedDashboardViewEntity> tripAvaileddashboardList) {
		this.tripAvaileddashboardList = tripAvaileddashboardList;
	}

	public Long getTripAvailedId() {
		return tripAvailedId;
	}

	public void setTripAvailedId(Long tripAvailedId) {
		this.tripAvailedId = tripAvailedId;
	}

	public String getIsTripAvailed() {
		return isTripAvailed;
	}

	public void setIsTripAvailed(String isTripAvailed) {
		this.isTripAvailed = isTripAvailed;
	}

	public Long getTripHeaderId() {
		return tripHeaderId;
	}

	public void setTripHeaderId(Long tripHeaderId) {
		this.tripHeaderId = tripHeaderId;
	}

	public Long getReqDetailId() {
		return reqDetailId;
	}

	public void setReqDetailId(Long reqDetailId) {
		this.reqDetailId = reqDetailId;
	}

	public String getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public String getActualTime() {
		return actualTime;
	}

	public void setActualTime(String actualTime) {
		this.actualTime = actualTime;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

/*	public String getTripStartTime() {
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
	}*/
	
}
