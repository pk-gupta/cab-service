package com.marlabs.cab.service.domain.master.data.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.marlabs.cab.service.domain.master.data.service.MasterDataService;
import com.marlabs.cab.service.persistance.entity.EmployeeLandmarkEntity;
import com.marlabs.cab.service.persistance.entity.OfficeBranchEntity;
import com.marlabs.cab.service.persistance.entity.OfficeCityEntity;
import com.marlabs.cab.service.persistance.entity.ReasonEntity;
import com.marlabs.cab.service.persistance.entity.ServiceAndTimeEntity;
import com.marlabs.cab.service.persistance.master.data.dao.MasterDataDAO;

@Service
public class MasterDataServiceImpl implements MasterDataService {
	
	private static Logger logger = LogManager.getLogger(MasterDataServiceImpl.class);
	
	@Autowired
	private MasterDataDAO masterDataDAO;
	
	@Autowired
    Environment env;

	@Override
	public List<EmployeeLandmarkEntity> getLandmarks() {
		
		logger.info("getLandmarks() -> Landmark Master Data Serivce call...");
		
		return masterDataDAO.getLandmarks();
	}

	@Override
	public List<ReasonEntity> getReasons() {
		logger.info("getReasons() -> Reasons Master Data Serivce call...");
		
		return masterDataDAO.getReasons();
	}

    @Override
	public List<ServiceAndTimeEntity> getServiceAndTime() {
		logger.info("getServiceAndTime() -> Serivce And Time Master Data Serivce call...");
		
		return masterDataDAO.getServiceAndTime();
	}

	@Override
	public List<OfficeBranchEntity> getOfficeBranches() {
		logger.info("getOfficeBranches() -> Office Branches Master Data Serivce call...");
		
		return masterDataDAO.getOfficeBranches();
	}

	@Override
	public List<OfficeCityEntity> getOfficeCities() {
		logger.info("getOfficeCities() -> Office Cities Master Data Serivce call...");
		
		return masterDataDAO.getOfficeCities();
	}
	
	
	@Override
	public List<ServiceAndTimeEntity> getLogoutTime() {
		logger.info("getLogoutTime() -> Service And Time  Master Data Serivce call...");
		
		return masterDataDAO.getLogoutTime();
	}
	
	@Override
	public List<ServiceAndTimeEntity> getLoginTime() {
		logger.info("getLoginTime() -> Service And Time  Master Data Serivce call...");
		
		return masterDataDAO.getLoginTime();
	}

	
}
