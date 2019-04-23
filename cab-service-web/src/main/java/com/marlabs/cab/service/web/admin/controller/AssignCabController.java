package com.marlabs.cab.service.web.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.marlabs.cab.service.common.email.EmailService;
import com.marlabs.cab.service.common.email.EmailTemplateVO;
import com.marlabs.cab.service.common.email.template.AbstractEmailTemplate;
import com.marlabs.cab.service.common.email.template.EmployeeEmailTemplate;
import com.marlabs.cab.service.common.enums.EmailTemplateTypeEnum;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.domain.admin.service.AssignCabService;
import com.marlabs.cab.service.persistance.entity.AssignCabDashboardDetailViewEntity;
import com.marlabs.cab.service.persistance.entity.AssignCabDashboardViewEntity;
import com.marlabs.cab.service.persistance.vo.AssignCabTripVo;
import com.marlabs.cab.service.persistance.vo.AssignCabVO;
import com.marlabs.cab.service.persistance.vo.DataFilterParamsVO;
import com.marlabs.cab.service.persistance.vo.TripPrintVO;




@RestController
public class AssignCabController {

	private static Logger logger = LogManager.getLogger(AssignCabController.class);

	@Autowired
	Environment environment;

	@Autowired
	AssignCabService assignCabService;
	
	@Autowired
	EmailService emailService;
	
	@RequestMapping(value = "/admin/assigncab/assignCabView", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<AssignCabVO> assignCabView() {

		logger.info("assignCabView() -> STARTED ASSIGN CAB VIEW PROCESS... ");

		AssignCabVO assignCabVO = assignCabService.assignCabView();
		
		if(CabServiceUtil.isNULL(assignCabVO)){
			return new ResponseEntity<>(assignCabVO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		logger.info("assignCabView() -> ASSIGN CAB VIEW PROCESS COMPLETE");

		return new ResponseEntity<>(assignCabVO, HttpStatus.OK);
	}
	

	@RequestMapping(value = "/admin/assigncab/assignCabDashboard", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<AssignCabDashboardViewEntity>> assignCabDashboard(@RequestBody DataFilterParamsVO dataFilterParamsVO, HttpServletRequest request) {

		logger.info("assignCabDashboard() -> STARTED ASSIGN CAB DASHBOARD PROCESS... ");

		List<AssignCabDashboardViewEntity> assignCabDashboardList = assignCabService.assignCabDashboard(dataFilterParamsVO);
		
		if(CabServiceUtil.isNULL(assignCabDashboardList)){
			return new ResponseEntity<>(assignCabDashboardList, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		logger.info("assignCabDashboard() -> ASSIGN CAB DASHBOARD PROCESS COMPLETE");

		return new ResponseEntity<>(assignCabDashboardList, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/assigncab/assignCabDashboardDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<AssignCabDashboardDetailViewEntity>> assignCabDashboardDetail(@RequestBody DataFilterParamsVO dataFilterParamsVO, HttpServletRequest request) {

		logger.info("assignCabDashboardDetail() -> STARTED ASSIGN CAB DASHBOARD DETAIL PROCESS... ");

		List<AssignCabDashboardDetailViewEntity> assignCabDashboardDetailList = assignCabService.assignCabDashboardDetail(dataFilterParamsVO);

		if(CabServiceUtil.isNULL(assignCabDashboardDetailList)){
			return new ResponseEntity<>(assignCabDashboardDetailList, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		logger.info("assignCabDashboard() -> ASSIGN CAB DASHBOARD DETAIL PROCESS COMPLETE");

		return new ResponseEntity<>(assignCabDashboardDetailList, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/assigncab/printTripSheet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<TripPrintVO>> printTripSheet(@RequestBody DataFilterParamsVO dataFilterParamsVO,HttpServletRequest request) {

		logger.info("printTripSheet() -> STARTED PRINT TRIP SHEET PROCESS... ");

		List<TripPrintVO> assignCabPrintTripDetailList = assignCabService.printTripSheet(dataFilterParamsVO);

		if(CabServiceUtil.isNULL(assignCabPrintTripDetailList)){
			return new ResponseEntity<>(assignCabPrintTripDetailList, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		logger.info("printTripSheet() -> PRINT TRIP SHEET PROCESS COMPLETE");

		return new ResponseEntity<>(assignCabPrintTripDetailList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/assigncab/downloadTripExcel", method = RequestMethod.POST)
	public void downloadTripExcel(@RequestBody DataFilterParamsVO dataFilterParamsVO,HttpServletRequest request,HttpServletResponse response ) {

		logger.info("downloadTripExcel() -> STARTED  DOWNLAOD TRIP EXCEL PROCESS... ");

		 Workbook wb = assignCabService.createTripExcelFile(dataFilterParamsVO);
		 response.setContentType("application/vnd.ms-excel");
		 response.setHeader("Content-disposition", "attachment; filename=tripSheet.xlsx");
		 
		 try {
			wb.write(response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.info("downloadTripExcel() ->  DOWNLOAD TRIP EXCEL PROCESS COMPLETE");

	}
	
	
	@RequestMapping(value = "/admin/assigncab/addUpdateAssignCab", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<AssignCabDashboardDetailViewEntity>> addUpdateAssignCab(@RequestBody List<AssignCabTripVo> assignCabTripVo, HttpServletRequest request) {

		logger.info("addUpdateAssignCab() -> STARTED ADD/UPDATE ASSIGN CAB PROCESS... ");
		
		List<AssignCabDashboardDetailViewEntity> assignCabDashboardDetailList = assignCabService.addUpdateAssignCab(assignCabTripVo);

		if(CabServiceUtil.isNULL(assignCabDashboardDetailList)){
			return new ResponseEntity<>(assignCabDashboardDetailList, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(assignCabDashboardDetailList.isEmpty()){
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		
		sendCabDetailEmailToEmployee(assignCabDashboardDetailList);
		logger.info("addUpdateAssignCab() -> ADD/UPDATE ASSIGN CAB PROCESS COMPLETE");

		return new ResponseEntity<>(assignCabDashboardDetailList, HttpStatus.OK);
	}
	
	private void sendCabDetailEmailToEmployee(List<AssignCabDashboardDetailViewEntity> tripDetailList){
		
		tripDetailList.forEach(tripDetail -> {
			EmailTemplateVO templateVO = setTemplateData(tripDetail);
			
			AbstractEmailTemplate template = new EmployeeEmailTemplate();
			StringBuilder emailTemplate = template.createEmailTemplate(templateVO);
			
			emailService.sendSimpleMessage(tripDetail.getEmailId(), "mFleet - Trip Details", emailTemplate.toString());
			
		});
	}

	private EmailTemplateVO setTemplateData(AssignCabDashboardDetailViewEntity tripDetail) {
		EmailTemplateVO templateVO = new EmailTemplateVO(); 
		templateVO.setEmployeeName(tripDetail.getEmpFirstName()+" "+tripDetail.getEmpLastName());
		templateVO.setEmployeeLandmark(tripDetail.getEmpLandmarkName());
		templateVO.setServiceDate(tripDetail.getServiceDate());
		templateVO.setServiceType(tripDetail.getServiceType());
		templateVO.setLoginTime(tripDetail.getLoginTime());
		templateVO.setLogoutTime(tripDetail.getLogoutTime());
		templateVO.setTripNumber(tripDetail.getTripHeaderId());
		templateVO.setVehicleNumber(tripDetail.getVehicleNo());
		templateVO.setDriverFirstName(tripDetail.getDriverFirstName());
		templateVO.setDriverMiddleName(tripDetail.getDriverMiddleName());
		templateVO.setDriverLastName(tripDetail.getDriverLastName());
		templateVO.setExpectedTime(tripDetail.getEstimatedTime());
		templateVO.setEmailTemplateType(EmailTemplateTypeEnum.EMPLOYEE_CAB_DETAILS);
		
		return templateVO;
	}
	
}
