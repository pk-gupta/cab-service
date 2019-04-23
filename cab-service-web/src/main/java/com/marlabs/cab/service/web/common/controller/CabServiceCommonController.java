package com.marlabs.cab.service.web.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.marlabs.cab.service.common.util.AppServerUtil;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.domain.common.service.CabServiceCommonService;
import com.marlabs.cab.service.persistance.entity.CabDetailEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeView;
import com.marlabs.cab.service.persistance.sql.mapper.EmployeeRequestSQLMapper;
import com.marlabs.cab.service.persistance.sql.mapper.PendingRequestSQLMapper;
import com.marlabs.cab.service.persistance.vo.AutoCompleteSearchTripVO;
import com.marlabs.cab.service.persistance.vo.CityLandmarkMasterVO;
import com.marlabs.cab.service.persistance.vo.SingleRequestVO;

@RestController
// @CrossOrigin
public class CabServiceCommonController {

	private static Logger logger = LogManager.getLogger(CabServiceCommonController.class);

	@Autowired
	Environment environment;

	@Autowired
	CabServiceCommonService cabServiceCommonService;

	@RequestMapping(value = "/employee/autoCompleteSearch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<EmployeeView>> autoCompleteSearch(
			@RequestParam("searchText") String searchText, HttpServletRequest request) {

		logger.info("autoCompleteSearch()-> STARTED AUTO SEARCH FOR EMPLOYEE -> " + searchText + "...");

		List<EmployeeView> searchResults = cabServiceCommonService.autoCompleteSearch(searchText);

		logger.info("autoCompleteSearch()-> AUTO SEARCH FOR -> " + searchText + " COMPLETE");

		return new ResponseEntity<>(searchResults, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/assigncab/autoCompleteSearchCab", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<CabDetailEntity>> autoCompleteSearchCab(@RequestParam("searchText") String searchText) {

		logger.info("autoCompleteSearchCab()-> STARTED AUTO SEARCH FOR CAB NO -> " + searchText+ "...");

		List<CabDetailEntity> searchResults = cabServiceCommonService.autoCompleteSearchCab(searchText);

		if(CabServiceUtil.isNULL(searchResults)){
			return new ResponseEntity<>(searchResults, HttpStatus.NOT_FOUND);
		}
		
		logger.info("autoCompleteSearchCab()-> AUTO SEARCH FOR CAB NO -> " + searchText + " COMPLETE"); 

		return new ResponseEntity<>(searchResults, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/autoCompleteSearchTripNumber", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<AutoCompleteSearchTripVO> autoCompleteSearchTripNumber(@RequestParam("tripHeaderId") Long tripHeaderId) {

		logger.info("autoCompleteSearchTripNumber()-> STARTED AUTO SEARCH FOR TRIP NO -> " + tripHeaderId+ "...");

		AutoCompleteSearchTripVO searchTripNumbers = cabServiceCommonService.autoCompleteSearchTripNumber(tripHeaderId);

		if(CabServiceUtil.isNULL(searchTripNumbers)){
			return new ResponseEntity<>(searchTripNumbers, HttpStatus.NOT_FOUND);
		}
		
		logger.info("autoCompleteSearchTripNumber()-> AUTO SEARCH FOR TRIP NO -> " + tripHeaderId + " COMPLETE"); 

		return new ResponseEntity<>(searchTripNumbers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/getListOfCityLandmarks/{officeCityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CityLandmarkMasterVO> getCityLandmarks(
			@PathVariable("officeCityId") Long officeCityId, HttpServletRequest request) {

		logger.info("getCityLandmarks()-> STARTED CITY LANDMARKS SEARCH -> " + officeCityId + "...");

		CityLandmarkMasterVO cityLandmarkMasterVO = cabServiceCommonService.getCityLandmarks(officeCityId);

		logger.info("getCityLandmarks()-> CITY LANDMARKS SEARCH FOR -> " + officeCityId + " COMPLETE");

		return new ResponseEntity<>(cityLandmarkMasterVO, HttpStatus.OK);

	}

	@RequestMapping(value = "/employee/getListOfBranchLandmarks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CityLandmarkMasterVO> getBranchLandmarks(
			@RequestParam("officeBranchCode") String officeBranchCode, HttpServletRequest request) {

		logger.info("getBranchLandmarks()-> STARTED BRANCH LANDMARKS SEARCH -> " + officeBranchCode + "...");

		CityLandmarkMasterVO cityLandmarkMasterVO = cabServiceCommonService.getBranchLandmarks(officeBranchCode);

		logger.info("getBranchLandmarks()-> BRANCH LANDMARKS SEARCH FOR -> " + officeBranchCode + " COMPLETE");

		return new ResponseEntity<>(cityLandmarkMasterVO, HttpStatus.OK);

	}
	
	@RequestMapping( value="/employee/requestDetails/{requestHeaderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<EmployeeRequestSQLMapper>> requestDetails(@PathVariable("requestHeaderId") Long requestHeaderId, HttpServletRequest request) {
		
		logger.info("requestDetails() -> STARTED ADMIN REQUEST DETAILS PROCESS FOR REQUEST HEADER ID -> "+ requestHeaderId +"...");
		
		List<EmployeeRequestSQLMapper> employeeRequestList = cabServiceCommonService.requestDetails(requestHeaderId);
		
		logger.info("requestDetails() -> ADMIN REQUEST DETAILS PROCESS COMPLETE FOR REQUEST HEADER ID -> "+ requestHeaderId);
		
		return new ResponseEntity<>(employeeRequestList, AppServerUtil.getResponseStatus(employeeRequestList, HttpStatus.MULTI_STATUS));
	}
	
	@RequestMapping(value = "/employee/getProjectApprover", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<EmployeeView> getProjectApprover(
			@RequestBody SingleRequestVO singleRequestVO, HttpServletRequest request) {

		logger.info("getProjectApprover-> STARTED PROJECT APPROVER PROCESS FOR EMPLOYEE ID -> "
				+ singleRequestVO.getEmployeeId() + "...");

		EmployeeView employeeView = cabServiceCommonService.getProjectApprover(singleRequestVO);

		logger.info("getProjectApprover->PROJECT APPROVER PROCESS COMPLETE FOR EMPLOYEE ID -> "
				+ singleRequestVO.getEmployeeId());

		return new ResponseEntity<>(employeeView, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/employee/restrictMultipleCreateRequest/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<PendingRequestSQLMapper>> restrictMultipleCreateRequest(@PathVariable("employeeId") String employeeId,
			HttpServletRequest request) {

		logger.info("restrictMultipleCreateRequest() -> STARTED RESTRICT MULTIPLE CREATE REQUEST PROCESS FOR EMPLOYEE ID -> " + employeeId + "...");

		List<PendingRequestSQLMapper> pendingSQLMapperEntity = cabServiceCommonService.restrictMultipleCreateRequest(employeeId);

		logger.info("restrictMultipleCreateRequest() -> RESTRICT MULTIPLE CREATE REQUEST PROCESS COMPLETE FOR EMPLOYEE ID -> " + employeeId);

		return new ResponseEntity<>(pendingSQLMapperEntity, HttpStatus.OK);
	}

}
