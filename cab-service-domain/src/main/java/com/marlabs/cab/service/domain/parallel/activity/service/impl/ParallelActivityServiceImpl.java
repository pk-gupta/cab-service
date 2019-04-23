package com.marlabs.cab.service.domain.parallel.activity.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlabs.cab.service.domain.parallel.activity.service.ParallelActivityService;
import com.marlabs.cab.service.persistance.entity.CityLandmarkMasterEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeProjectAllocationView;
import com.marlabs.cab.service.persistance.entity.EmployeeView;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;
import com.marlabs.cab.service.persistance.search.dao.SearchDAO;
import com.marlabs.cab.service.persistance.view.dao.CabServiceViewDAO;

@Service
public class ParallelActivityServiceImpl implements ParallelActivityService {
	
	private static Logger logger = LogManager.getLogger(ParallelActivityServiceImpl.class);
	
	@Autowired
	SearchDAO searchDAO;
	
	@Autowired
	CabServiceViewDAO cabServiceViewDAO;

	@Override
	public List<RequestHeaderEntity> searchInLocalData(String searchText) {
		logger.info("searchInLocalData() -> Search Text Parallel Serivce call...");
		
		return searchDAO.searchLocalData(searchText);
	}

	@Override
	public List<EmployeeProjectAllocationView> getEmployeeProjectAllocationViewData(String employeeId) {
		logger.info("getEmployeeProjectAllocationViewData() -> Employee Project List Serivce call...");
		
		return cabServiceViewDAO.getEmployeeProjectList(employeeId);
	}
	
	
	 @Override
	 public List<EmployeeView> searchInEmployeeView(String searchText){
		
		logger.info("searchInEmployeeView() -> Search Text Parallel View Serivce call...");
		
		return searchDAO.searchViewData(searchText);
	}

	@Override
	public List<EmployeeView> getEmployeeViewData(String employeeId) {
		logger.info("getEmployeeViewData() -> Employee Project List Serivce call...");
		
		return cabServiceViewDAO.getEmployeeView(employeeId);
	}

	@Override
	public List<CityLandmarkMasterEntity> getEmployeeLandmarkData(String employeeId) {
     
		logger.info("getEmployeeViewData() -> Employee Project List Serivce call...");
                 
		return cabServiceViewDAO.getEmployeeLandmarkData(employeeId);
   
	}
	
	
	
	
	
	
	
	
	
	
}
