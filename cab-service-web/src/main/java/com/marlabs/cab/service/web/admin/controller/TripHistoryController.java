package com.marlabs.cab.service.web.admin.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.domain.admin.service.TripHistoryService;
import com.marlabs.cab.service.domain.master.data.service.MasterDataService;
import com.marlabs.cab.service.persistance.entity.OfficeCityEntity;
import com.marlabs.cab.service.persistance.vo.DataFilterParamsVO;
import com.marlabs.cab.service.persistance.vo.TripHistoryVO;

@RestController
public class TripHistoryController {
	
	private static Logger logger = LogManager.getLogger(TripHistoryController.class);

	@Autowired
	Environment environment;
	
	@Autowired
	private MasterDataService masterDataService;

	@Autowired
	private TripHistoryService tripHistoryService;
	
	@RequestMapping(value = "/admin/tripHistoryDashboard", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<TripHistoryVO> tripHistoryDashboard(@RequestBody DataFilterParamsVO dataFilterVO, HttpServletRequest request) {

		logger.info("tripHistoryDashboard() -> STARTED TRIP HISTORY PROCESS... ");
		
		List<OfficeCityEntity> officeCityEntityList= masterDataService.getOfficeCities();

		TripHistoryVO tripHistoryVO = getDashboardData(dataFilterVO);
		
		if (!CabServiceUtil.isNULL(tripHistoryVO)) {
			try{
				tripHistoryVO.setOfficeCityEntityList(officeCityEntityList);
				
			}catch (Exception exception) {
				logger.error("tripHistoryDashboard() -> ERROR : Failed to get City List "+exception.getCause());
				return new ResponseEntity<>(tripHistoryVO, HttpStatus.PARTIAL_CONTENT);
			}
		}

		logger.info("tripHistoryDashboard() -> TRIP HISTORY PROCESS COMPLETE");

		return new ResponseEntity<>(tripHistoryVO, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/tripHistoryFilter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<TripHistoryVO> tripHistoryDashboardFilter(@RequestBody DataFilterParamsVO dataFilterVO, HttpServletRequest request) {

		logger.info("tripHistoryDashboardFilter() -> STARTED TRIP HISTORY DASHBOARD FILETR PROCESS... ");

		TripHistoryVO tripHistoryVO = getDashboardData(dataFilterVO);

		logger.info("tripHistoryDashboardFilter() -> TRIP HISTORY DASHBOARD FILETR PROCESS COMPLETE");

		return new ResponseEntity<>(tripHistoryVO, HttpStatus.OK);
	}
	
	private TripHistoryVO getDashboardData(DataFilterParamsVO dataFilterVO){
		return tripHistoryService.tripHistoryDashboardFilter(dataFilterVO);
	}
	
	@RequestMapping(value = "/admin/singleTripDetails/{tripHeaderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<TripHistoryVO> singleTripDetails(@PathVariable Long tripHeaderId, HttpServletRequest request) {

		logger.info("singleTripDetails() -> STARTED SINGLE TRIP DETAILS PROCESS... ");

		TripHistoryVO tripHistoryVO = tripHistoryService.getSingleTripDetails(tripHeaderId);

		logger.info("singleTripDetails() -> SINGLE TRIP DETAILS PROCESS COMPLETE");

		return new ResponseEntity<>(tripHistoryVO, HttpStatus.OK);
	}

}
