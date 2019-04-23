package com.marlabs.cab.service.persistance.vo;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.OfficeCityEntity;
import com.marlabs.cab.service.persistance.sql.mapper.CityBranchSQLMapper;

public class AssignCabVO {

	private List<OfficeCityEntity> officeCityEntityList;
	
	private List<CityBranchSQLMapper> officeBranchCityList;

	public List<OfficeCityEntity> getOfficeCityEntityList() {
		return officeCityEntityList;
	}

	public void setOfficeCityEntityList(List<OfficeCityEntity> officeCityEntityList) {
		this.officeCityEntityList = officeCityEntityList;
	}

	public List<CityBranchSQLMapper> getOfficeBranchCityList() {
		return officeBranchCityList;
	}

	public void setOfficeBranchCityList(List<CityBranchSQLMapper> officeBranchCityList) {
		this.officeBranchCityList = officeBranchCityList;
	}
	
}
