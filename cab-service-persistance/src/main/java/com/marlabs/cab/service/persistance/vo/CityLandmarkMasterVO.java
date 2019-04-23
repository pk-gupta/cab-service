package com.marlabs.cab.service.persistance.vo;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.CityLandmarkMasterEntity;

public class CityLandmarkMasterVO {

	List<CityLandmarkMasterEntity> cityLandmarkList;

	public List<CityLandmarkMasterEntity> getCityLandmarkMasterEntityList() {
		return cityLandmarkList;
	}

	public void setCityLandmarkMasterEntityList(List<CityLandmarkMasterEntity> cityLandmarkList) {
		this.cityLandmarkList = cityLandmarkList;
	}

}
