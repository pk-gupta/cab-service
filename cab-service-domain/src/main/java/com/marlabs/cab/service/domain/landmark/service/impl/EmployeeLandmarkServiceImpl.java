package com.marlabs.cab.service.domain.landmark.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlabs.cab.service.common.exception.DuplicateEmployeeLandmarkException;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.domain.landmark.service.EmployeeLandmarkService;
import com.marlabs.cab.service.domain.master.data.service.MasterDataService;
import com.marlabs.cab.service.persistance.entity.CityLandmarkMasterEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeLandmardDashboardEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeLandmarkEntity;
import com.marlabs.cab.service.persistance.entity.OfficeCityEntity;
import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;
import com.marlabs.cab.service.persistance.landmark.dao.EmployeeLandmarkDAO;
import com.marlabs.cab.service.persistance.vo.EmployeeLandmarkVO;

@Service
public class EmployeeLandmarkServiceImpl implements EmployeeLandmarkService {

	private static Logger logger = LogManager.getLogger(EmployeeLandmarkServiceImpl.class);

	@Autowired
	private MasterDataService masterDataService;

	@Autowired
	private EmployeeLandmarkDAO employeeLandmarkDAO;

	@Override
	public EmployeeLandmarkEntity processEmployeeLandmarkRequest(EmployeeLandmarkVO employeelandmarkVO) {
		logger.info("processEmployeeLandmarkRequest() -> New Employee Landmark Request Service call...");
		
		if(employeeLandmarkDAO.isEmployeeLandmarkExists(employeelandmarkVO.getEmployeeId())){
			throw new DuplicateEmployeeLandmarkException("Landmark already exists for this employee.");
		}

		EmployeeLandmarkEntity employeeLandmarkEntity = new EmployeeLandmarkEntity();

		OfficeCityEntity cityEntity=new OfficeCityEntity();
		cityEntity.setOfficeCityId(employeelandmarkVO.getOfficeCityId());
		
		CityLandmarkMasterEntity cityLandmarkMaster=new CityLandmarkMasterEntity();
		cityLandmarkMaster.setCityLandmarkMasterId(employeelandmarkVO.getCityLandmarkMasterId());
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		employeeLandmarkEntity.setOfficeCity(cityEntity);
		employeeLandmarkEntity.setCityLandmarkMaster(cityLandmarkMaster);
		employeeLandmarkEntity.setEmployeeId(employeelandmarkVO.getEmployeeId());
		employeeLandmarkEntity.setActive(employeelandmarkVO.getActive());
		employeeLandmarkEntity.setIsDeleted("false");
		employeeLandmarkEntity.setCreatedBy(employeelandmarkVO.getEmployeeId());
		employeeLandmarkEntity.setUpdatedBy(employeelandmarkVO.getEmployeeId());
		employeeLandmarkEntity.setCreateDate(timestamp);
		employeeLandmarkEntity.setUpdateDate(timestamp);
		employeeLandmarkEntity.setEffectiveDate(employeelandmarkVO.getEffectiveDate());

		return employeeLandmarkDAO.processEmployeeLandmarkRequest(employeeLandmarkEntity);

	}

	@Override
	public List<RequestDetailEntity> updateEmployeeLandmarkStatus(EmployeeLandmarkVO employeelandmarkVO) {
		logger.info("updateEmployeeLandmarkStatus() -> Change Status Employee Landmark Request Service call...");
		return employeeLandmarkDAO.updateEmployeeLandmarkStatus(employeelandmarkVO);
	}

	@Override
	public String updateEmployeeLandmarkRequest(EmployeeLandmarkVO employeelandmarkVO) {
		logger.info("updateEmployeeLandmarkRequest() -> Update Employee Landmark Request Service call...");
		return employeeLandmarkDAO.updateEmployeeLandmarkRequest(employeelandmarkVO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public EmployeeLandmarkVO employeeLandmarkRequestDashboard() {

		logger.info("employeeLandmarkRequestDashboard() -> Employee Landmark Request Dashboard Service call...");
		
		EmployeeLandmarkVO employeelandmarkVO =  new EmployeeLandmarkVO();
		
		List<OfficeCityEntity> officeCityEntityList = masterDataService.getOfficeCities();

		List<EmployeeLandmardDashboardEntity> empLandmarkDashboardList = employeeLandmarkDAO.employeeLandmarkRequestDashboard();
		
		if (!CabServiceUtil.isNULL(empLandmarkDashboardList)) {
			employeelandmarkVO.setEmployeeLandmarkDashboardList(empLandmarkDashboardList);
		}
		try{
			employeelandmarkVO.setOfficeCityEntityList(officeCityEntityList);
			
		}catch (Exception exception) {
			logger.error("employeeLandmarkRequestDashboard() -> ERROR : Failed to get City List "+exception.getCause());
			employeelandmarkVO = null;
		}
		
		return employeelandmarkVO;
	}

	@Override
	public String deleteEmployeeLandmark(long employeeLandmarkId) {
		logger.info("deleteEmployeeLandmark() -> Delete Employee Landmark Service call...");
		return employeeLandmarkDAO.deleteEmployeeLandmark(employeeLandmarkId);
	}

}
