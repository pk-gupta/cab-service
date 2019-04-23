package com.marlabs.cab.service.web.employee.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

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

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.email.EmailService;
import com.marlabs.cab.service.common.email.EmailTemplateVO;
import com.marlabs.cab.service.common.email.template.AbstractEmailTemplate;
import com.marlabs.cab.service.common.email.template.AdminEmailTemplate;
import com.marlabs.cab.service.common.email.template.EmployeeEmailTemplate;
import com.marlabs.cab.service.common.email.template.ManagerEmailTemplate;
import com.marlabs.cab.service.common.enums.EmailTemplateTypeEnum;
import com.marlabs.cab.service.common.exception.CabServiceRequestException;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.domain.employee.service.EmployeeScheduleRequestService;
import com.marlabs.cab.service.domain.search.service.SearchService;
import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;
import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;
import com.marlabs.cab.service.persistance.vo.ProcessNewRequestVO;
import com.marlabs.cab.service.persistance.vo.ScheduleRequestVO;

@RestController
// @CrossOrigin
public class EmployeeScheduleRequestController {

	private static Logger logger = LogManager.getLogger(EmployeeScheduleRequestController.class);

	@Autowired
	Environment environment;

	@Autowired
	EmailService emailService;

	@Autowired
	SearchService searchService;

	@Autowired
	private EmployeeScheduleRequestService employeeScheduleRequestService;

	@RequestMapping(value = "/employee/scheduleRequestDashboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<EmployeeManagerDashboardEntity>> scheduleRequestDashboard(@RequestParam("employeeId") String employeeId, HttpServletRequest request) {

		logger.info("scheduleRequestDashboard() -> STARTED SCHEDULE REQUEST DASHBOARD DATA PROCESS FOR EMPLOYEE ID -> "+ employeeId + "...");

		List<EmployeeManagerDashboardEntity> employeeManagerDashboardEntityList = employeeScheduleRequestService.scheduleRequestDashboard(employeeId);

		logger.info("ScheduleRequestDashboard() -> SCHEDULE REQUEST DASHBOARD DATA PROCESS COMPLETE FOR EMPLOYEE ID -> "+ employeeId);

		return new ResponseEntity<>(employeeManagerDashboardEntityList, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/createNewScheduleRequest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ScheduleRequestVO> createNewScheduleRequest(@RequestParam("employeeId") String employeeId, HttpServletRequest request) {

		logger.info("createNewScheduleRequest() -> STARTED CREATE NEW SCHEDULE REQUEST PROCESS FOR EMPLOYEE ID -> "+ employeeId + "...");

		ScheduleRequestVO scheduleRequestVO = employeeScheduleRequestService.createNewScheduleRequest(employeeId);

		logger.info("createNewScheduleRequest() -> CREATE NEW SCHEDULE REQUEST PROCESS COMPLETE FOR EMPLOYEE ID -> "+ employeeId);

		return new ResponseEntity<>(scheduleRequestVO, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/processNewScheduleRequest/{userAction}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> processScheduleRequest(@RequestBody ScheduleRequestVO scheduleRequestVO,@PathVariable("userAction") String userAction, HttpServletRequest request) {

		logger.info("processScheduleRequest() -> STARTED NEW SCHEDULE REQUEST PROCESSING...");

		ProcessNewRequestVO createNewScheduleRequestVO = null;
		try {
			createNewScheduleRequestVO = employeeScheduleRequestService.processScheduleRequest(scheduleRequestVO,
					userAction);
		} catch (CabServiceRequestException requestException) {
			logger.info("processScheduleRequest() -> " + requestException.getMessage());

			return new ResponseEntity<>(requestException.getMessage(), HttpStatus.ACCEPTED);
		}

		/*
		 * ProcessNewRequestVO createNewScheduleRequestVO =
		 * employeeScheduleRequestService.processScheduleRequest(
		 * scheduleRequestVO,userAction);
		 * 
		 * if(createNewScheduleRequestVO.getStatusMessage().equalsIgnoreCase(
		 * Constants.STATUS_SUCCESS)){ logger.
		 * info("processScheduleRequest() -> Failed to Create New Request due to Duplicate Request"
		 * );
		 * 
		 * return new ResponseEntity<>(HttpStatus.ACCEPTED); }
		 */

		// logger.info("processScheduleRequest() -> Failed to Create New Request
		// due to Duplicate Request");

		logger.info("processScheduleRequest() -> NEW SCHEDULE REQUEST PROCESS COMPLETE");

		if (userAction.equalsIgnoreCase(Constants.REQUEST_ACTION_SUBMIT) && !CabServiceUtil.isNULL(createNewScheduleRequestVO.getRequestHeaderEntity())) {
			RequestHeaderEntity requestHeader = createNewScheduleRequestVO.getRequestHeaderEntity();
			EmailTemplateVO emailTemplateVO = getEmailTemplate(scheduleRequestVO, requestHeader);
			sendEmail(emailTemplateVO);
		}

		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/updateScheduleRequest/{userAction}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> updateScheduleRequest(@RequestBody ScheduleRequestVO scheduleRequestVO,@PathVariable("userAction") String userAction, HttpServletRequest request) {

		logger.info("updateScheduleRequest() -> STARTED SCHEDULE REQUEST UPDATE PROCESS FOR REQUEST ID -> "+ scheduleRequestVO.getRequestId() + "...");

		ProcessNewRequestVO createNewScheduleRequestVO = null;

		try {
			createNewScheduleRequestVO = employeeScheduleRequestService.processScheduleRequest(scheduleRequestVO,userAction);
		} catch (CabServiceRequestException requestException) {
			logger.info("processScheduleRequest() -> " + requestException.getMessage());

			return new ResponseEntity<>(requestException.getMessage(), HttpStatus.ACCEPTED);
		}

		/*
		 * String statusMessage = null; if
		 * (CabServiceUtil.isNULL(createNewScheduleRequestVO.
		 * getRequestHeaderEntity())) { statusMessage =
		 * "Failed to create Request. Please try again later. "; } else {
		 * statusMessage = "Request created successfully"; //
		 * environment.getProperty("cab.service.request.create.success"); }
		 */

		logger.info("updateScheduleRequest() -> SCHEDULE REQUEST UPDATE PROCESS FOR REQUEST ID -> "+ scheduleRequestVO.getRequestId());

		if (userAction.equalsIgnoreCase(Constants.REQUEST_ACTION_SUBMIT)) {

			RequestHeaderEntity requestHeader = createNewScheduleRequestVO.getRequestHeaderEntity();

			EmailTemplateVO emailTemplateVO = getEmailTemplate(scheduleRequestVO, requestHeader);

			sendEmail(emailTemplateVO);
		}
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/editScheduleRequest/{requestId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ScheduleRequestVO> editScheduleRequest(@PathVariable("requestId") Long requestId, HttpServletRequest request) {

		logger.info("editScheduleRequest() -> STARTED EDIT SCHEDULE REQUEST PROCESS FOR REQUEST ID -> " + requestId+ "...");

		ScheduleRequestVO scheduleRequestVO = employeeScheduleRequestService.editScheduleRequest(requestId);

		logger.info("editScheduleRequest() -> EDIT SCHEDULE REQUEST PROCESS COMPLETE FOR REQUEST ID -> " + requestId);

		return new ResponseEntity<>(scheduleRequestVO, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/deleteScheduleRequest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> deleteScheduleRequest(@RequestParam("requestId") Long requestId,HttpServletRequest request) {

		logger.info("deleteScheduleRequest() -> STARTED DELETE SCHEDULE REQUEST PROCESS FOR REQUEST ID -> " + requestId+ "...");

		String statusMessage = employeeScheduleRequestService.deleteScheduleRequest(requestId);

		logger.info("deleteScheduleRequest() -> DELETE SCHEDULE REQUEST PROCESS COMPLETE FOR REQUEST ID -> " + requestId);

		return new ResponseEntity<>(statusMessage, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/cancelScheduleRequest/{requestId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> cancelScheduleRequest(@PathVariable("requestId") Long requestId,@RequestParam("employeeId") String employeeId, HttpServletRequest request) {

		logger.info("cancelScheduleRequest() -> STARTED CANCEL SCHEDULE REQUEST PROCESS FOR REQUEST ID -> " + requestId+ "...");

		String statusMessage = employeeScheduleRequestService.cancelScheduleRequest(requestId, employeeId);

		logger.info("cancelScheduleRequest() -> CANCEL SCHEDULE REQUEST PROCESS COMPLETE FOR REQUEST ID -> " + requestId);

		return new ResponseEntity<>(statusMessage, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/scheduleDashboardSearch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<RequestHeaderEntity>> dashboardSearch(@RequestParam("searchText") String searchText, HttpServletRequest request) {

		logger.info("singleRequestDashboard() -> STARTED SEARCH IN SINGLE DASHBOARD FOR TEXT -> " + searchText + "...");

		List<RequestHeaderEntity> searchResults = searchService.searchText(searchText);

		logger.info("singleRequestDashboard() -> SEARCH IN SINGLE DASHBOARD FOR TEXT -> " + searchText + " COMPLETE");

		return new ResponseEntity<>(searchResults, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/cancelSelectedScheduleRequest/{UserAction}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> cancelSelectedScheduleRequest(@PathVariable("UserAction") String userAction, @RequestBody ScheduleRequestVO scheduleRequestVO,HttpServletRequest request) {

		logger.info("cancelSelectedScheduleRequest() -> STARTED CANCEL SELECTED SCHEDULE REQUEST PROCESS  -> ");

		String statusMessage = employeeScheduleRequestService.cancelSelectedScheduleRequest(scheduleRequestVO);

		if (statusMessage.equalsIgnoreCase(Constants.SCHEDULE_REQUEST_CANCELLED_SUCCESS)) {

			EmailTemplateVO emailTemplateVO = getCancelledEmailTemplate(scheduleRequestVO);
			sendEmail(emailTemplateVO);
		}
		logger.info("cancelSelectedScheduleRequest() -> CANCEL SELECTED SCHEDULE REQUEST PROCESS COMPLETE -> ");

		return new ResponseEntity<>(statusMessage, HttpStatus.OK);
	}

	private void sendEmail(EmailTemplateVO emailTemplateVO) {

		logger.info("sendEmail() -> STARTED EMAIL NOTIFICATION PROCESS...");
		Future<String> employeeEmailStatus = null;
		Future<String> managerEmailStatus = null;
		Future<String> adminEmailStatus = null;

		AbstractEmailTemplate employeeTemplate = new EmployeeEmailTemplate();
		emailTemplateVO.setEmailTemplateType(EmailTemplateTypeEnum.EMPLOYEE_REQUEST_DETAILS);
		StringBuilder empEmailTemplate = employeeTemplate.createEmailTemplate(emailTemplateVO);

		if (emailTemplateVO.getApprovalStatus().equalsIgnoreCase(Constants.REQUEST_STATUS_CANCELLED)) {
			employeeEmailStatus = emailService.sendSimpleMessage(emailTemplateVO.getEmployeeEmail(),Constants.EMAIL_CANCEL_REQUEST, empEmailTemplate.toString());
		} else {
			employeeEmailStatus = emailService.sendSimpleMessage(emailTemplateVO.getEmployeeEmail(),Constants.EMAIL_NEW_REQUEST, empEmailTemplate.toString());
		}

		AbstractEmailTemplate managerTemplate = new ManagerEmailTemplate();
		emailTemplateVO.setEmailTemplateType(EmailTemplateTypeEnum.MANAGER_REQUEST_DETAILS);
		StringBuilder mgrEmailTemplate = managerTemplate.createEmailTemplate(emailTemplateVO);

		if (emailTemplateVO.getApprovalStatus().equalsIgnoreCase(Constants.REQUEST_STATUS_CANCELLED)) {
			managerEmailStatus = emailService.sendSimpleMessage(emailTemplateVO.getManagerEmail(),Constants.EMAIL_CANCEL_REQUEST, mgrEmailTemplate.toString());
		} else {
			managerEmailStatus = emailService.sendSimpleMessage(emailTemplateVO.getManagerEmail(),Constants.EMAIL_NEW_REQUEST, mgrEmailTemplate.toString());
		}
		adminEmailStatus = sendMailToAdmin(emailTemplateVO, adminEmailStatus);

		logger.info("SCHEDULE REQUEST -> EMAIL NOTIFICATION TO EMPLOYEE - STATUS : " + employeeEmailStatus);
		logger.info("SCHEDULE REQUEST -> EMAIL NOTIFICATION TO MANAGER - STATUS : " + managerEmailStatus);
		logger.info("SCHEDULE REQUEST -> EMAIL NOTIFICATION TO ADMIN - STATUS : " + adminEmailStatus);

		logger.info("sendEmail() -> EMAIL NOTIFICATION PROCESS COMPLETE");
	}

	private Future<String> sendMailToAdmin(EmailTemplateVO emailTemplateVO, Future<String> adminEmailStatus) {

		if (emailTemplateVO.getApprovalStatus().equalsIgnoreCase(Constants.REQUEST_STATUS_CANCELLED)) {

			AbstractEmailTemplate adminTemplate = new AdminEmailTemplate();
			emailTemplateVO.setEmailTemplateType(EmailTemplateTypeEnum.ADMIN_REQUEST_DETAILS);
			StringBuilder admEmailTemplate = adminTemplate.createEmailTemplate(emailTemplateVO);

			adminEmailStatus = emailService.sendSimpleMessage(Constants.ADMIN_EMAIL_ID,Constants.EMAIL_CANCEL_REQUEST, admEmailTemplate.toString());
		}

		return adminEmailStatus;
	}

	private EmailTemplateVO getEmailTemplate(ScheduleRequestVO scheduleRequestVO, RequestHeaderEntity requestHeader) {
		EmailTemplateVO emailTemplateVO = new EmailTemplateVO();

		emailTemplateVO.setRequestType(Constants.REQUEST_TYPE_SCHEDULE);
		emailTemplateVO.setEmployeeEmail(scheduleRequestVO.getEmployeeEmail());
		emailTemplateVO.setManagerEmail(scheduleRequestVO.getManagerEmail());
		emailTemplateVO.setEmployeeId(scheduleRequestVO.getEmployeeId());
		emailTemplateVO.setEmployeeName(scheduleRequestVO.getEmployeeName());
		emailTemplateVO.setManagerName(scheduleRequestVO.getManagerName());
		emailTemplateVO.setRequestHeaderId(requestHeader.getRequestHeaderId());
		emailTemplateVO.setRequestServiceType(requestHeader.getRequestServiceType());
		emailTemplateVO.setApprovalStatus(Constants.REQUEST_STATUS_APPROVAL_PENDING);
		emailTemplateVO.setRequestHeaderId(requestHeader.getRequestHeaderId());
		emailTemplateVO.setRequestServiceType(requestHeader.getRequestServiceType());
		emailTemplateVO.setEmployeeRemark(scheduleRequestVO.getEmployeeRemark());
		emailTemplateVO.setEmployeeSelectedLogin(scheduleRequestVO.getEmployeeSelectedLogin());
		emailTemplateVO.setEmployeeSelectedLogout(scheduleRequestVO.getEmployeeSelectedLogout());
		emailTemplateVO.setEmployeeSelectedReason(scheduleRequestVO.getEmployeeSelectedReason());
		emailTemplateVO.setFromDate(scheduleRequestVO.getEmplopyeeSelectedFromDate());
		emailTemplateVO.setToDate(scheduleRequestVO.getEmplopyeeSelectedToDate());

		if (CabServiceUtil.isNULL(scheduleRequestVO.getEmployeeSelectedProject())) {
			emailTemplateVO.setProjectName("NA");
		} else {
			emailTemplateVO.setProjectName(scheduleRequestVO.getEmployeeSelectedProject());
		}
		return emailTemplateVO;
	}

	private EmailTemplateVO getCancelledEmailTemplate(ScheduleRequestVO scheduleRequestVO) {

		EmailTemplateVO emailTemplateVO = new EmailTemplateVO();

		emailTemplateVO.setRequestType(Constants.REQUEST_TYPE_SCHEDULE);
		emailTemplateVO.setEmployeeEmail(scheduleRequestVO.getEmployeeEmail());
		emailTemplateVO.setManagerEmail(scheduleRequestVO.getManagerEmail());
		emailTemplateVO.setEmployeeId(scheduleRequestVO.getGlobalEmployeeId());
		emailTemplateVO.setEmployeeName(
				scheduleRequestVO.getEmployeeFirstName() + " " + scheduleRequestVO.getEmployeeLastName());
		emailTemplateVO
				.setManagerName(scheduleRequestVO.getManagerFirstName() + " " + scheduleRequestVO.getManagerLastName());
		emailTemplateVO.setApprovalStatus(Constants.REQUEST_STATUS_CANCELLED);
		emailTemplateVO.setRequestHeaderId(scheduleRequestVO.getRequestId());
		emailTemplateVO.setRequestServiceType(scheduleRequestVO.getRequestServiceType());
		emailTemplateVO.setEmployeeRemark(scheduleRequestVO.getEmployeeRemark());
		emailTemplateVO.setEmployeeSelectedLogin(scheduleRequestVO.getEmployeeSelectedLogin());
		emailTemplateVO.setEmployeeSelectedLogout(scheduleRequestVO.getEmployeeSelectedLogout());
		emailTemplateVO.setEmployeeSelectedReason(scheduleRequestVO.getEmployeeSelectedReason());
		emailTemplateVO.setServiceDate(scheduleRequestVO.getServiceDate());
		emailTemplateVO.setEmployeeSelectedReason(scheduleRequestVO.getReasonDescription());
		emailTemplateVO.setFromDate(scheduleRequestVO.getEmplopyeeSelectedFromDate());
		emailTemplateVO.setToDate(scheduleRequestVO.getEmplopyeeSelectedToDate());

		getCancelledServiceDatesAndType(scheduleRequestVO, emailTemplateVO);

		if (CabServiceUtil.isNULL(scheduleRequestVO.getEmployeeSelectedProject())) {
			emailTemplateVO.setProjectName(scheduleRequestVO.getEmployeeSelectedProjectId());
		} else {
			emailTemplateVO.setProjectName(scheduleRequestVO.getEmployeeSelectedProject());
		}

		return emailTemplateVO;
	}

	private void getCancelledServiceDatesAndType(ScheduleRequestVO scheduleRequestVO, EmailTemplateVO emailTemplateVO) {

		Map<List<String>, List<Date>> cancelScheduleDays = new HashMap<>();
		List<String> serviceType = new ArrayList<>();
		List<java.sql.Date> serviceDate = new ArrayList<>();

		for (RequestDetailEntity requestDetail : scheduleRequestVO.getRequestDetailEntity()) {

			serviceType.add(requestDetail.getServiceType());
			serviceDate.add(requestDetail.getServiceDate());

		}
		cancelScheduleDays.put(serviceType, serviceDate);
		emailTemplateVO.setCancelScheduleDays(cancelScheduleDays);
	}

}
