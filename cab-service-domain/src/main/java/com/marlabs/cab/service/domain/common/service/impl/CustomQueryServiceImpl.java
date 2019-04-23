package com.marlabs.cab.service.domain.common.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlabs.cab.service.domain.common.service.CustomQueryService;
import com.marlabs.cab.service.persistance.common.dao.CustomQueryDAO;
import com.marlabs.cab.service.persistance.sql.mapper.CityBranchSQLMapper;
import com.marlabs.cab.service.persistance.sql.mapper.EmployeeLandmarkSQLMapper;
import com.marlabs.cab.service.persistance.sql.mapper.EmployeeProjectManagerSQLMapper;
import com.marlabs.cab.service.persistance.sql.mapper.OfficeBranchSQLMapper;

@Service
public class CustomQueryServiceImpl implements CustomQueryService {

	private static Logger logger = LogManager.getLogger(CustomQueryServiceImpl.class);

	@Autowired
	CustomQueryDAO customQueryDAO;

	@Override
	public List<CityBranchSQLMapper> getCityBranchDetails() {
		logger.info("getCityBranchDetails() ->  City with Office Branch Service call... ");

		return customQueryDAO.getCityBranchDetails();
	}

	@Override
	public List<EmployeeLandmarkSQLMapper> getEmployeeLandmarkDetails(String employeeId) {
		logger.info("getEmployeeLandmarkDetails() ->  Employee Landmark  Service call... ");

		return customQueryDAO.getEmployeeLandmarkDetails(employeeId);
	}

	@Override
	public List<EmployeeProjectManagerSQLMapper> getEmployeeProjectManager(long requestId) {

		logger.info("getEmployeeProjectManager() -> Employee Project Manager Serivce call...");

		return customQueryDAO.getEmployeeProjectManager(requestId);
	}

	@Override
	public List<OfficeBranchSQLMapper> getCitySpecificOfficeBranches(String officeBranchCode) {
		logger.info("getCitySpecificOfficeBranches() -> City SpecificOffice Branches Serivce call...");

		return customQueryDAO.getCitySpecificOfficeBranches(officeBranchCode);
	}

	@Override
	public List<OfficeBranchSQLMapper> getLandmarkSpecificOfficeBranches(String employeeId) {
		logger.info("getLandmarkSpecificOfficeBranches() -> Landmark Specific Office Branches Serivce call...");

		return customQueryDAO.getLandmarkSpecificOfficeBranches(employeeId);
	}		
}
