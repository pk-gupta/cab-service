package com.marlabs.cab.service.domain.parallel.activity.service;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.CityLandmarkMasterEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeProjectAllocationView;
import com.marlabs.cab.service.persistance.entity.EmployeeView;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;

public interface ParallelActivityService {
	
	public List<EmployeeView> getEmployeeViewData(String employeeId);
	
	public List<EmployeeProjectAllocationView> getEmployeeProjectAllocationViewData(String employeeId);
	
	public List<RequestHeaderEntity> searchInLocalData(String searchText);
	
	public List<EmployeeView> searchInEmployeeView(String searchText);
	
	public List<CityLandmarkMasterEntity> getEmployeeLandmarkData(String employeeId);
}
