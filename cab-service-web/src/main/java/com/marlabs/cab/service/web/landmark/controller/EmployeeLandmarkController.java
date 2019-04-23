package com.marlabs.cab.service.web.landmark.controller;

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
import com.marlabs.cab.service.common.exception.CabServiceRequestException;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.domain.landmark.service.EmployeeLandmarkService;
import com.marlabs.cab.service.persistance.entity.EmployeeLandmarkEntity;
import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;
import com.marlabs.cab.service.persistance.vo.EmployeeLandmarkVO;

@RestController
public class EmployeeLandmarkController {

	private static Logger logger = LogManager.getLogger(EmployeeLandmarkController.class);

	@Autowired
	Environment environment;

	@Autowired
	private EmployeeLandmarkService employeeLandmarkService;

	@RequestMapping(value = "/admin/processNewEmployeeLandmarkRequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> processNewEmployeeLandmarkRequest(
			@RequestBody EmployeeLandmarkVO employeelandmarkVO, HttpServletRequest request) {

		logger.info("processNewEmployeeLandmarkRequest() -> STARTED NEW EMPLOYEE LANDMARK REQUEST PROCESSING...");

		EmployeeLandmarkEntity employeeLandmarkEntity = null;

		try {
			employeeLandmarkEntity = employeeLandmarkService.processEmployeeLandmarkRequest(employeelandmarkVO);
		}catch (CabServiceRequestException requestException) {
			logger.info("processNewEmployeeLandmarkRequest() -> " + requestException.getMessage());
			return new ResponseEntity<>(requestException.getMessage(), HttpStatus.ACCEPTED);
		}
		
		if(CabServiceUtil.isNULL(employeeLandmarkEntity)){
			logger.error("processNewEmployeeLandmarkRequest() -> Server Error. Could not process.");
			return new ResponseEntity<>("Failed to process request. Please try later.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(CabServiceUtil.isNULL(employeeLandmarkEntity.getActive()) && employeeLandmarkEntity.getEmployeeLandmarkId()!=0 ){
			logger.info("processNewEmployeeLandmarkRequest() -> Landmark have Future Request.");
			return new ResponseEntity<>("Unable to process your request. Please cancel future request attached with current landmark before change current landmark.", HttpStatus.ACCEPTED);
		}
		logger.info("processNewEmployeeLandmarkRequest() -> NEW EMPLOYEE LANDMARK REQUEST PROCESS COMPLETE");

		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/updateEmployeeLandmarkStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<HttpStatus> updateEmployeeLandmarkStatus(
			@RequestBody EmployeeLandmarkVO employeelandmarkVO, HttpServletRequest request) {

		logger.info("updateEmployeeLandmarkStatus() -> STARTED EMPLOYEE LANDMARK STATUS PROCESS FOR LANDMARK ID -> "
				+ employeelandmarkVO.getEmployeeLandmarkId() + "...");

		List<RequestDetailEntity> requestDetailList = employeeLandmarkService.updateEmployeeLandmarkStatus(employeelandmarkVO);
		 
		if(CabServiceUtil.isNULL(requestDetailList)){
			logger.error("updateEmployeeLandmarkStatus() -> Server Error. Could not process.");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(!requestDetailList.isEmpty()){
			logger.info("updateEmployeeLandmarkStatus() -> Requests are mapped to the Landmark.");
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		
		logger.info("updateEmployeeLandmarkStatus() -> No Requests are mapped to the Landmark");
		
		logger.info("updateEmployeeLandmarkStatus() -> EMPLOYEE LANDMARK STATUS PROCESS COMPLETE FOR LANDMARK ID -> "
				+ employeelandmarkVO.getEmployeeLandmarkId());

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/updateEmployeeLandmark", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> updateEmployeeLandmark(
			@RequestBody EmployeeLandmarkVO employeelandmarkVO, HttpServletRequest request) {

		logger.info("updateEmployeeLandmark() -> STARTED EMPLOYEE LANDMARK  UPDATE PROCESS FOR REQUEST ID -> "
				+ employeelandmarkVO.getEmployeeLandmarkId() + "...");

		String statusMessage = employeeLandmarkService.updateEmployeeLandmarkRequest(employeelandmarkVO);

		if (statusMessage.equals(Constants.REQUEST_STATUS_SUCCESS)) {
			statusMessage = environment.getProperty("message.admin.employeelandmark.update.success");
		} else {
			statusMessage = environment.getProperty("message.admin.employeelandmark.update.fail");
		}

		logger.info("updateEmployeeLandmark() -> EMPLOYEE LANDMARK UPDATE PROCESS COMPLETE FOR REQUEST ID -> "
				+ employeelandmarkVO.getEmployeeLandmarkId());

		return new ResponseEntity<>(statusMessage, HttpStatus.OK);

	}

	@RequestMapping(value = "/admin/employeeLandmarkDashboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<EmployeeLandmarkVO> employeeLandmarkDashboard(HttpServletRequest request) {

		logger.info("employeeLandmarkDashboard() -> STARTED EMPLOYEE LANDMARK DASHBOARD DATA PROCESS...");

		EmployeeLandmarkVO employeelandmarkVO = employeeLandmarkService.employeeLandmarkRequestDashboard();

		logger.info("employeeLandmarkDashboard() -> EMPLOYEE LANDMARK DASHBOARD DATA PROCESS COMPLETE ");

		return new ResponseEntity<>(employeelandmarkVO, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/deleteEmployeeLandmark/{employeeLandmarkId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> deleteEmployeeLandmark(
			@PathVariable("employeeLandmarkId") Long employeeLandmarkId, HttpServletRequest request) {

		logger.info("deleteEmployeeLandmark() -> STARTED DELETE EMPLOYEE LANDMARK PROCESS FOR REQUEST LANDMARK ID -> "
				+ employeeLandmarkId + "...");

		String statusMessage = employeeLandmarkService.deleteEmployeeLandmark(employeeLandmarkId);
		if (statusMessage.equals(Constants.REQUEST_STATUS_SUCCESS)) {
			statusMessage = environment.getProperty("message.admin.employeelandmark.delete.success");
		} else {
			statusMessage = environment.getProperty("message.admin.employeelandmark.delete.fail");
		}

		logger.info("deleteEmployeeLandmark()-> DELETE EMPLOYEE LANDMARK PROCESS COMPLETE FOR REQUEST LANDMARK ID -> "
				+ employeeLandmarkId);

		return new ResponseEntity<>(statusMessage, HttpStatus.OK);
	}

}
