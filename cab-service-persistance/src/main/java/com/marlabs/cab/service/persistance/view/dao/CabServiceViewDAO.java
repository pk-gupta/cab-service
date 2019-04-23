package com.marlabs.cab.service.persistance.view.dao;

import java.util.List;
import java.util.concurrent.Future;

import com.marlabs.cab.service.persistance.entity.CityLandmarkMasterEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeProjectAllocationView;
import com.marlabs.cab.service.persistance.entity.EmployeeView;

public interface CabServiceViewDAO {

	public List<EmployeeView> getEmployeeView(String employeeId);
	
	public List<EmployeeProjectAllocationView> getEmployeeProjectList(String employeeId);
	
	public List<CityLandmarkMasterEntity> getEmployeeLandmarkData(String employeeId);
}
