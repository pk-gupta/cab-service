package com.marlabs.cab.service.persistance.master.data.dao;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.EmployeeLandmarkEntity;
import com.marlabs.cab.service.persistance.entity.OfficeBranchEntity;
import com.marlabs.cab.service.persistance.entity.OfficeCityEntity;
import com.marlabs.cab.service.persistance.entity.ReasonEntity;
import com.marlabs.cab.service.persistance.entity.ServiceAndTimeEntity;

public interface MasterDataDAO {
	
	public List<EmployeeLandmarkEntity> getLandmarks();
	
	public List<ReasonEntity> getReasons();
	
	public List<ServiceAndTimeEntity> getServiceAndTime();
	
	public List<OfficeBranchEntity> getOfficeBranches();
	
	public List<OfficeCityEntity> getOfficeCities();
	
	public List<ServiceAndTimeEntity> getLogoutTime();
	
	public List<ServiceAndTimeEntity> getLoginTime();
}
