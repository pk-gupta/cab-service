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
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.domain.admin.service.CabDetailService;
import com.marlabs.cab.service.domain.common.service.CustomQueryService;
import com.marlabs.cab.service.domain.master.data.service.MasterDataService;
import com.marlabs.cab.service.persistance.entity.CabDetailEntity;
import com.marlabs.cab.service.persistance.entity.CabOwnerEntity;
import com.marlabs.cab.service.persistance.entity.OfficeCityEntity;
import com.marlabs.cab.service.persistance.sql.mapper.CityBranchSQLMapper;
import com.marlabs.cab.service.persistance.vo.CabDetailDashboardVO;
import com.marlabs.cab.service.persistance.vo.CabDetailVO;
import com.marlabs.cab.service.persistance.vo.CabOwnerVO;

@RestController
public class CabDetailController {
	
	private static Logger logger = LogManager.getLogger(CabDetailController.class);

	@Autowired
	private Environment environment;
	
	@Autowired
	private CabDetailService cabDetailService;
	
	@Autowired
	private CustomQueryService customQueryService;
	
	@Autowired
	private MasterDataService masterDataService;
	
	@RequestMapping(value = "/admin/cabDetailDashboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CabDetailDashboardVO> cabDetailDashboard(HttpServletRequest request) {

		logger.info("cabDetailDashboard() -> STARTED CAB DETAIL DASHBOARD PROCESS... ");
		
		CabDetailDashboardVO cabDetailVO = null;
		
		List<CabOwnerVO> cabownerList = cabDetailService.cabDetailDashboard();

		if(CabServiceUtil.isNULL(cabownerList)){
			return new ResponseEntity<>(cabDetailVO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		cabDetailVO = new CabDetailDashboardVO();
		cabDetailVO.setCabOwnerList(cabownerList);
		
		List<OfficeCityEntity>  OfficeCityEntityList = masterDataService.getOfficeCities();
		
		if(CabServiceUtil.isNULL(OfficeCityEntityList)){
			return new ResponseEntity<>(cabDetailVO, HttpStatus.PARTIAL_CONTENT);
		}
		
		try {
			
			cabDetailVO.setOfficeCityList(OfficeCityEntityList);
			
		} catch (Exception exception) {
			logger.error("cabDetailDashboard() -> ERROR: "+ exception.getMessage());
			return new ResponseEntity<>(cabDetailVO, HttpStatus.PARTIAL_CONTENT);
		}
		
		List<CityBranchSQLMapper> cityBranchList = customQueryService.getCityBranchDetails();
		
		if(CabServiceUtil.isNULL(cityBranchList)){
			return new ResponseEntity<>(cabDetailVO, HttpStatus.PARTIAL_CONTENT);
		}
		
		cabDetailVO.setOfficeBranchCityList(cityBranchList);
		
		logger.info("cabDetailDashboard() -> CAB DETAIL DASHBOARD PROCESS COMPLETE");

		return new ResponseEntity<>(cabDetailVO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/addUpdateCabOwner", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> addUpdateCabOwner(@RequestBody CabOwnerEntity cabOwnerEntity, HttpServletRequest request) {

		logger.info("addUpdateCabOwner() -> STARTED ADD/UPDATE CAB OWNER PROCESS... ");
		
		if(CabServiceUtil.isNULL(cabOwnerEntity)){
			return new ResponseEntity<>(environment.getProperty("message.admin.cab.detail.data.invalid"), HttpStatus.BAD_REQUEST);
		}

		String status = cabDetailService.addUpdateCabOwner(cabOwnerEntity);
		
		if(status.equalsIgnoreCase(Constants.STATUS_FAILED) ){
			return new ResponseEntity<>(environment.getProperty("message.admin.cab.detail.add.update.fail"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		logger.info("addUpdateCabOwner() -> ADD/UPDATE CAB OWNER PROCESS COMPLETE");

		return new ResponseEntity<>(environment.getProperty("message.admin.cab.detail.add.update.success"), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/editCabOwner/{cabOwnerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CabOwnerEntity> editCabOwner(@PathVariable Long cabOwnerId, HttpServletRequest request) {

		logger.info("editCabOwner() -> STARTED EDIT CAB OWNER PROCESS... ");

		CabOwnerEntity cabOwnerEntity = cabDetailService.editCabOwner(cabOwnerId);

		if(CabServiceUtil.isNULL(cabOwnerEntity)){
			return new ResponseEntity<>(cabOwnerEntity, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("editCabOwner() -> EDIT CAB OWNER PROCESS COMPLETE");

		return new ResponseEntity<>(cabOwnerEntity, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/addUpdateCabDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> addUpdateCabDetail(@RequestBody CabDetailVO cabDetailVO, HttpServletRequest request) {

		logger.info("addUpdateCabDetail() -> STARTED ADD/UPDATE CAB DETAIL PROCESS... ");
		
		if(CabServiceUtil.isNULL(cabDetailVO)){
			return new ResponseEntity<>(environment.getProperty("message.admin.cab.detail.data.invalid"), HttpStatus.BAD_REQUEST);
		}
		
		String status = cabDetailService.addUpdateCabDetail(cabDetailVO);
		
		if(status.equalsIgnoreCase(Constants.STATUS_FAILED) ){
			return new ResponseEntity<>(environment.getProperty("message.admin.cab.detail.add.update.fail"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("addUpdateCabDetail() -> ADD/UPDATE CAB DETAIL PROCESS COMPLETE");

		return new ResponseEntity<>(environment.getProperty("message.admin.cab.detail.add.update.success"), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/editCabDetail/{cabDetailId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CabDetailVO> editCabDetail(@PathVariable Long cabDetailId, HttpServletRequest request) {

		logger.info("editCabDetail() -> STARTED EDIT CAB DETAIL PROCESS... ");
		
		CabDetailVO cabDetail = cabDetailService.editCabDetail(cabDetailId);

		if(CabServiceUtil.isNULL(cabDetail)){
			return new ResponseEntity<>(cabDetail, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("editCabDetail() -> EDIT CAB DETAIL PROCESS COMPLETE");

		return new ResponseEntity<>(cabDetail, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/updateCabOwnerStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> updateCabOwnerStatus(@RequestBody CabOwnerEntity cabOwnerEntity, HttpServletRequest request) {

		logger.info("updateCabOwnerStatus() -> STARTED UPDATE CAB OWNER STATUS PROCESS... ");
		
		if(CabServiceUtil.isNULL(cabOwnerEntity)){
			return new ResponseEntity<>(environment.getProperty("message.admin.cab.detail.data.invalid"), HttpStatus.BAD_REQUEST);
		}

		String status = cabDetailService.updateCabOwnerStatus(cabOwnerEntity);
		
		if(status.equalsIgnoreCase(Constants.STATUS_FAILED) ){
			return new ResponseEntity<>(environment.getProperty("message.admin.cab.detail.add.update.fail"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("updateCabOwnerStatus() -> UPDATE CAB OWNER STATUS PROCESS COMPLETE");

		return new ResponseEntity<>(status, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/updateCabDetailStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> updateCabDetailStatus(@RequestBody CabDetailEntity cabDetailEntity, HttpServletRequest request) {

		logger.info("updateCabDetailStatus() -> STARTED UPDATE CAB DETAIL STATUS PROCESS... ");
		
		if(CabServiceUtil.isNULL(cabDetailEntity)){
			return new ResponseEntity<>(environment.getProperty("message.admin.cab.detail.data.invalid"), HttpStatus.BAD_REQUEST);
		}
		
		String status = cabDetailService.updateCabDetailStatus(cabDetailEntity);
		
		if(status.equalsIgnoreCase(Constants.STATUS_FAILED) ){
			return new ResponseEntity<>(environment.getProperty("message.admin.cab.detail.add.update.fail"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		logger.info("updateCabDetailStatus() -> UPDATE CAB DETAIL STATUS PROCESS COMPLETE");

		return new ResponseEntity<>(status, HttpStatus.OK);
	}
	
}
