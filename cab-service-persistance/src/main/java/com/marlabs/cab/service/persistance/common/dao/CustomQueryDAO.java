package com.marlabs.cab.service.persistance.common.dao;

import java.util.List;

import com.marlabs.cab.service.persistance.sql.mapper.CityBranchSQLMapper;
import com.marlabs.cab.service.persistance.sql.mapper.EmployeeLandmarkSQLMapper;
import com.marlabs.cab.service.persistance.sql.mapper.EmployeeProjectManagerSQLMapper;
import com.marlabs.cab.service.persistance.sql.mapper.OfficeBranchSQLMapper;

public interface CustomQueryDAO {

	public List<CityBranchSQLMapper> getCityBranchDetails();

	public List<EmployeeLandmarkSQLMapper> getEmployeeLandmarkDetails(String employeeId);

	public List<EmployeeProjectManagerSQLMapper> getEmployeeProjectManager(long requestId);

	public List<OfficeBranchSQLMapper> getCitySpecificOfficeBranches(String officeBranchCode);
	
	public List<OfficeBranchSQLMapper> getLandmarkSpecificOfficeBranches(String employeeId);

}
