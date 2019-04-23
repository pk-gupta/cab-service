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

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.util.AppServerUtil;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.domain.admin.service.TripServiceAvailedService;
import com.marlabs.cab.service.domain.master.data.service.MasterDataService;
import com.marlabs.cab.service.persistance.entity.OfficeCityEntity;
import com.marlabs.cab.service.persistance.entity.TripHeaderEntity;
import com.marlabs.cab.service.persistance.entity.TripServiceAvailedDashboardViewEntity;
import com.marlabs.cab.service.persistance.vo.DataFilterParamsVO;
import com.marlabs.cab.service.persistance.vo.TripServiceAvailedVO;
import com.marlabs.cab.service.persistance.vo.TripServiceDetailsVO;

@RestController
public class TripServiceAvailedController {

	private static Logger logger = LogManager.getLogger(TripServiceAvailedController.class);

	@Autowired
	Environment environment;
	
	@Autowired
	private MasterDataService masterDataService;

	@Autowired
	private TripServiceAvailedService tripServiceAvailedService;
	
	@RequestMapping(value = "/admin/tripServiceAvailedDashboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<TripServiceAvailedVO> tripAvailedDashboard(@RequestBody DataFilterParamsVO dataFilterVO, HttpServletRequest request) {

		logger.info("tripAvailedDashboard() -> STARTED TRIP SERVICE AVAILED PROCESS... ");
		
		List<OfficeCityEntity> officeCityEntityList= masterDataService.getOfficeCities();

		TripServiceAvailedVO tripServiceAvailedVO = getDashboardData(dataFilterVO);
		
		if (!CabServiceUtil.isNULL(tripServiceAvailedVO)) {
			try{
				tripServiceAvailedVO.setOfficeCityEntityList(officeCityEntityList);
				
			}catch (Exception exception) {
				logger.error("tripAvailedDashboard() -> ERROR : Failed to get City List "+exception.getCause());
				return new ResponseEntity<>(tripServiceAvailedVO, HttpStatus.PARTIAL_CONTENT);
			}
		}

		logger.info("tripAvailedDashboard() -> TRIP SERVICE AVAILED PROCESS COMPLETE");

		return new ResponseEntity<>(tripServiceAvailedVO, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/addUpdateTripServiceAvailed", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<HttpStatus> addUpdateTripServiceAvailed(@RequestBody TripServiceDetailsVO tripServiceDetailsVO, HttpServletRequest request) {

		logger.info("addUpdateTripServiceAvailed() -> STARTED TRIP SERVICE AVAILED DETAILS ADD OR UPDATE  PROCESS... ");
		
		String status = tripServiceAvailedService.addUpdateTripServiceAvailed(tripServiceDetailsVO);
		
		logger.info("addUpdateTripServiceAvailed() -> TRIP SERVICE AVAILED DETAILS ADD OR UPDATE COMPLETE PROCESS COMPLETE");

		if (status.equals(Constants.REQUEST_STATUS_SUCCESS)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		/*if (status.equals(Constants.REQUEST_STATUS_PARTIAL)) {
			return new ResponseEntity<>(environment.getProperty("message.admin.trip.availed.update.status.success"), HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
		}*/
		
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/admin/tripServiceAvailedFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<TripServiceAvailedVO> tripAvailedDashboardFilter(@RequestBody DataFilterParamsVO dataFilterVO, HttpServletRequest request) {

		logger.info("tripAvailedDashboardFilter() -> STARTED TRIP SERVICE AVAILED PROCESS... ");

		TripServiceAvailedVO tripServiceAvailedVO = getDashboardData(dataFilterVO);

		logger.info("tripAvailedDashboardFilter() -> TRIP SERVICE AVAILED PROCESS COMPLETE");

		return new ResponseEntity<>(tripServiceAvailedVO, AppServerUtil.getResponseStatus(tripServiceAvailedVO, HttpStatus.MULTI_STATUS));
	}
	
	private TripServiceAvailedVO getDashboardData(DataFilterParamsVO dataFilterVO){
		return tripServiceAvailedService.tripAvailedDashboardFilter(dataFilterVO);
	}
	
	@RequestMapping(value = "/admin/tripDetails/{tripHeaderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<TripServiceAvailedDashboardViewEntity>> tripDetails(@PathVariable Long tripHeaderId, HttpServletRequest request) {

		logger.info("tripDetails() -> STARTED TRIP SERVICE AVAILED DETAILS PROCESS... ");

		List<TripServiceAvailedDashboardViewEntity> tripDetails = tripServiceAvailedService.tripDetails(tripHeaderId);

		logger.info("tripDetails() -> TRIP SERVICE AVAILED PROCESS DETAILS COMPLETE");

		return new ResponseEntity<>(tripDetails, AppServerUtil.getResponseStatus(tripDetails, HttpStatus.MULTI_STATUS));
	}
	
	@RequestMapping(value = "/admin/autoCompleteTrip/{tripHeaderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<TripHeaderEntity>> autoCompleteTrip(@PathVariable Long tripHeaderId, HttpServletRequest request) {

		logger.info("tripDetails() -> STARTED TRIP SERVICE AVAILED DETAILS PROCESS... ");

		List<TripHeaderEntity> tripList = tripServiceAvailedService.getTripList(tripHeaderId);

		logger.info("tripDetails() -> TRIP SERVICE AVAILED PROCESS DETAILS COMPLETE");

		return new ResponseEntity<>(tripList, AppServerUtil.getResponseStatus(tripList, HttpStatus.MULTI_STATUS));
	}
	
}
