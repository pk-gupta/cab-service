package com.marlabs.cab.service.domain.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.domain.common.service.CabServiceCommonService;
import com.marlabs.cab.service.persistance.common.dao.CabServiceCommonDAO;
import com.marlabs.cab.service.persistance.entity.CabDetailEntity;
import com.marlabs.cab.service.persistance.entity.CityLandmarkMasterEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeView;
import com.marlabs.cab.service.persistance.entity.TripHeaderEntity;
import com.marlabs.cab.service.persistance.sql.mapper.EmployeeRequestSQLMapper;
import com.marlabs.cab.service.persistance.sql.mapper.PendingRequestSQLMapper;
import com.marlabs.cab.service.persistance.vo.AutoCompleteSearchTripVO;
import com.marlabs.cab.service.persistance.vo.CityLandmarkMasterVO;
import com.marlabs.cab.service.persistance.vo.SingleRequestVO;

@Service
public class CabServiceCommonServiceImpl implements CabServiceCommonService {

	private static Logger logger = LogManager.getLogger(CabServiceCommonServiceImpl.class);

	@Autowired
	private CabServiceCommonDAO cabServiceCommonDAO;

	@Override
	public List<EmployeeView> autoCompleteSearch(String searchText) {
		logger.info("autoCompleteSearch() -> Auto Search Request Service call...");
		
		return cabServiceCommonDAO.autoCompleteSearch(searchText);
	}

	@Override
	public List<CabDetailEntity> autoCompleteSearchCab(String searchText) {
		logger.info("autoCompleteSearchCab() -> Auto Search Cab Request Service call...");
		
		return cabServiceCommonDAO.autoCompleteSearchCab(searchText);
	}

	@Override
	public AutoCompleteSearchTripVO autoCompleteSearchTripNumber(Long tripHeaderId) {
		logger.info("autoCompleteSearchTripNumber() -> Auto Search Trip Number Request Service call...");
		
		List<TripHeaderEntity> tripNumbers = cabServiceCommonDAO.autoCompleteSearchTripNumber(tripHeaderId);
		
		AutoCompleteSearchTripVO autoCompleteSearchTripVO = null;
		
		if(!CabServiceUtil.isNULL(tripNumbers)){
			autoCompleteSearchTripVO =  getTripNumbers(tripNumbers);
		}
		
		return autoCompleteSearchTripVO;
	}
	
	@Override
	public CityLandmarkMasterVO getCityLandmarks(Long officeCityId) {
		logger.info("getCityLandmarks() -> City Landmark Request Service call...");
		
		List<CityLandmarkMasterEntity> cityLandmarkMasterEntityList = cabServiceCommonDAO
				.getCityLandmarks(officeCityId);
		CityLandmarkMasterVO cityLandmarkMasterVO = new CityLandmarkMasterVO();
		cityLandmarkMasterVO.setCityLandmarkMasterEntityList(cityLandmarkMasterEntityList);
		
		return cityLandmarkMasterVO;

	}

	@Override
	public CityLandmarkMasterVO getBranchLandmarks(String officeBranchCode) {

        logger.info("getBranchLandmarks() -> Branch Landmarks Request Service call...");
		
		List<CityLandmarkMasterEntity> cityLandmarkMasterEntityList = cabServiceCommonDAO
				.getBranchLandmarks(officeBranchCode);
		CityLandmarkMasterVO cityLandmarkMasterVO = new CityLandmarkMasterVO();
		cityLandmarkMasterVO.setCityLandmarkMasterEntityList(cityLandmarkMasterEntityList);
		
		return cityLandmarkMasterVO;
	}

	@Override
	public EmployeeView getProjectApprover(SingleRequestVO singleRequestVO) {
		
		 logger.info("getProjectApprover()->Project Approver Request Service call...");
		 return cabServiceCommonDAO.getProjectApprover(singleRequestVO);		
	}
	
	@Override
	public List<EmployeeRequestSQLMapper> requestDetails(Long requestHeaderId) {
		logger.info("requestDetails() -> Request Details Service call...");
		
		return cabServiceCommonDAO.requestDetails(requestHeaderId);
	}
	
	private AutoCompleteSearchTripVO getTripNumbers(List<TripHeaderEntity> tripHeaderIdList){
		
		List<Long> tripNumberList = new ArrayList<>();
		AutoCompleteSearchTripVO autoCompleteSearchTripVO = new AutoCompleteSearchTripVO();
		
		tripHeaderIdList.forEach(tripNumber -> tripNumberList.add((tripNumber.getTripHeaderId())));
		autoCompleteSearchTripVO.setTripHeaderIdList(tripNumberList);
		
		return autoCompleteSearchTripVO;
		
	}

	@Override
	public List<PendingRequestSQLMapper> restrictMultipleCreateRequest(String employeeId) {
		logger.info("restrictMultipleCreateRequest()->Restrict Multiple Create Request Service call...");
		return cabServiceCommonDAO.restrictMultipleCreateRequest(employeeId);
	}
	
}
