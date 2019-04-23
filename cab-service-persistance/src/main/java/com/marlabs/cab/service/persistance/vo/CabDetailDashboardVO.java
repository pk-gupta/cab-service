package com.marlabs.cab.service.persistance.vo;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.OfficeCityEntity;
import com.marlabs.cab.service.persistance.sql.mapper.CityBranchSQLMapper;

public class CabDetailDashboardVO {
	
	private List<CabOwnerVO> cabOwnerList;

	
	private List<CityBranchSQLMapper> officeBranchCityList;
	
	private List<OfficeCityEntity> officeCityList;
	
	public List<OfficeCityEntity> getOfficeCityList() {
		return officeCityList;
	}

	public void setOfficeCityList(List<OfficeCityEntity> officeCityList) {
		this.officeCityList = officeCityList;
	}

	public List<CabOwnerVO> getCabOwnerList() {
		return cabOwnerList;
	}

	public void setCabOwnerList(List<CabOwnerVO> cabOwnerList) {
		this.cabOwnerList = cabOwnerList;
	}

	public List<CityBranchSQLMapper> getOfficeBranchCityList() {
		return officeBranchCityList;
	}

	public void setOfficeBranchCityList(List<CityBranchSQLMapper> officeBranchCityList) {
		this.officeBranchCityList = officeBranchCityList;
	}
	
}
