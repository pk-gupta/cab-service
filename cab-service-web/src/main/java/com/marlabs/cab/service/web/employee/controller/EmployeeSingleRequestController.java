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
import com.marlabs.cab.service.domain.employee.service.EmployeeSingleRequestService;
import com.marlabs.cab.service.domain.search.service.SearchService;
import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;
import com.marlabs.cab.service.persistance.vo.ProcessNewRequestVO;
import com.marlabs.cab.service.persistance.vo.SingleRequestVO;

@RestController
// @CrossOrigin
public class EmployeeSingleRequestController {

	private static Logger logger = LogManager.getLogger(EmployeeSingleRequestController.class);

	@Autowired
	Environment environment;

	@Autowired
	EmailService emailService;

	@Autowired
	SearchService searchService;

	@Autowired
	private EmployeeSingleRequestService employeeSingleRequestService;

	@RequestMapping(value = "/employee/singleRequestDashboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<EmployeeManagerDashboardEntity>> singleRequestDashboard(@RequestParam("employeeId") String employeeId, HttpServletRequest request) {

		logger.info("singleRequestDashboard() -> STARTED SINGLE REQUEST DASHBOARD DATA PROCESS FOR EMPLOYEE ID -> "+ employeeId + "...");

		List<EmployeeManagerDashboardEntity> employeeManagerDashboardEntityList = employeeSingleRequestService.singleRequestDashboard(employeeId);

		logger.info("singleRequestDashboard() -> SINGLE REQUEST DASHBOARD DATA PROCESS COMPLETE FOR EMPLOYEE ID -> "+ employeeId);

		return new ResponseEntity<>(employeeManagerDashboardEntityList, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/createNewSingleRequest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<SingleRequestVO> createNewSingleRequest(@RequestParam("employeeId") String employeeId, HttpServletRequest request) {

		logger.info("createNewSingleRequest() -> STARTED CREATE NEW SINGLE REQUEST PROCESS FOR EMPLOYEE ID -> "+ employeeId + "...");

		SingleRequestVO singleRequestVO = employeeSingleRequestService.createNewSingleRequest(employeeId);

		logger.info("createNewSingleRequest() -> CREATE NEW SINGLE REQUEST PROCESS COMPLETE FOR EMPLOYEE ID -> "+ employeeId);

		return new ResponseEntity<>(singleRequestVO, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/processNewSingleRequest/{userAction}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> processNewSingleRequest(@RequestBody SingleRequestVO singleRequestVO,@PathVariable(value = "userAction") String userAction, HttpServletRequest request) {

		logger.info("processNewSingleRequest() -> STARTED NEW SINGLE REQUEST PROCESSING...");

		ProcessNewRequestVO createNewSingleRequestVO = null;
		try {
			createNewSingleRequestVO = employeeSingleRequestService.processSingleRequest(singleRequestVO, userAction);
		} catch (CabServiceRequestException requestException) {
			logger.info("processNewSingleRequest() -> " + requestException.getMessage());

			return new ResponseEntity<>(requestException.getMessage(), HttpStatus.ACCEPTED);
		}

		/*
		 * ProcessNewRequestVO createNewSingleRequestVO =
		 * employeeSingleRequestService.processSingleRequest(singleRequestVO,
		 * userAction);
		 * 
		 * if(createNewSingleRequestVO.getStatusMessage().equalsIgnoreCase(
		 * Constants.STATUS_SUCCESS)){ logger.
		 * info("processNewSingleRequest() -> Failed to Create New Request due to Duplicate Request"
		 * );
		 * 
		 * return new ResponseEntity<>(HttpStatus.ACCEPTED); } logger.
		 * info("processNewSingleRequest() -> Failed to Create New Request due to Duplicate Request"
		 * );
		 */

		logger.info("processNewSingleRequest() -> NEW SINGLE REQUEST PROCESS COMPLETE");

		if (userAction.equalsIgnoreCase(Constants.REQUEST_ACTION_SUBMIT)
				&& !CabServiceUtil.isNULL(createNewSingleRequestVO.getRequestHeaderEntity())) {
			RequestHeaderEntity requestHeader = createNewSingleRequestVO.getRequestHeaderEntity();
			EmailTemplateVO emailTemplateVO = getEmailTemplate(singleRequestVO, requestHeader);
			sendEmail(emailTemplateVO);
		}

		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/updateSingleRequest/{userAction}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> updateSingleRequest(@RequestBody SingleRequestVO singleRequestVO,@PathVariable("userAction") String userAction, HttpServletRequest request) {

		logger.info("updateSingleRequest() -> STARTED SINGLE REQUEST UPDATE PROCESS FOR REQUEST ID -> "+ singleRequestVO.getRequestId() + "...");

		ProcessNewRequestVO createNewSingleRequestVO = null;

		try {
			createNewSingleRequestVO = employeeSingleRequestService.processSingleRequest(singleRequestVO, userAction);
		} catch (CabServiceRequestException requestException) {
			logger.info("updateSingleRequest() -> " + requestException.getMessage());

			return new ResponseEntity<>(requestException.getMessage(), HttpStatus.ACCEPTED);
		}
		/*String statusMessage = null;
		if (CabServiceUtil.isNULL(createNewSingleRequestVO)) {
			statusMessage = "Failed to create Request. Please try again later. ";
		} else {
			statusMessage = "Request created successfully"; // environment.getProperty("cab.service.request.create.success");
		}*/

		logger.info("updateSingleRequest() -> SINGLE REQUEST UPDATE PROCESS COMPLETE FOR REQUEST ID -> "+ singleRequestVO.getRequestId());

		if (userAction.equalsIgnoreCase(Constants.REQUEST_ACTION_SUBMIT)) {

			RequestHeaderEntity requestHeader = createNewSingleRequestVO.getRequestHeaderEntity();

			EmailTemplateVO emailTemplateVO = getEmailTemplate(singleRequestVO, requestHeader);

			sendEmail(emailTemplateVO);
		}

		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/editSingleRequest/{requestId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<SingleRequestVO> editSingleRequest(@PathVariable("requestId") Long requestId,HttpServletRequest request) {

		logger.info("editSingleRequest() -> STARTED EDIT SINGLE REQUEST PROCESS FOR REQUEST ID -> " + requestId + "...");

		SingleRequestVO singleRequestVO = employeeSingleRequestService.editSingleRequest(requestId);

		logger.info("editSingleRequest() -> EDIT SINGLE REQUEST PROCESS COMPLETE FOR REQUEST ID -> " + requestId);

		return new ResponseEntity<>(singleRequestVO, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/deleteSingleRequest/{requestId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> deleteSingleRequest(@PathVariable("requestId") Long requestId,HttpServletRequest request) {

		logger.info("deleteSingleRequest() -> STARTED DELETE SINGLE REQUEST PROCESS FOR REQUEST ID -> " + requestId+ "...");

		String statusMessage = employeeSingleRequestService.deleteSingleRequest(requestId);

		logger.info("deleteSingleRequest() -> DELETE SINGLE REQUEST PROCESS COMPLETE FOR REQUEST ID -> " + requestId);

		return new ResponseEntity<>(statusMessage, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/cancelSingleRequest/{requestId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> cancelSingleRequest(@PathVariable("requestId") Long requestId,@RequestParam("employeeId") String employeeId, HttpServletRequest request) {

		logger.info("cancelSingleRequest() -> STARTED CANCEL SINGLE REQUEST PROCESS FOR REQUEST ID -> " + requestId+ "...");

		String statusMessage = employeeSingleRequestService.cancelSingleRequest(requestId, employeeId);

		logger.info("cancelSingleRequest() -> CANCEL SINGLE REQUEST PROCESS COMPLETE FOR REQUEST ID -> " + requestId);

		return new ResponseEntity<>(statusMessage, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/singleDashboardSearch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<RequestHeaderEntity>> dashboardSearch(@RequestParam("searchText") String searchText, HttpServletRequest request) {

		logger.info("singleRequestDashboard() -> STARTED SEARCH IN SINGLE DASHBOARD FOR TEXT -> " + searchText + "...");

		List<RequestHeaderEntity> searchResults = searchService.searchText(searchText);

		logger.info("singleRequestDashboard() -> SEARCH IN SINGLE DASHBOARD FOR TEXT -> " + searchText + " COMPLETE");

		return new ResponseEntity<>(searchResults, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/cancelSelectedSingleRequest/{userAction}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> cancelSelectedSingleRequest(@PathVariable("userAction") String userAction, @RequestBody SingleRequestVO singleRequestVO,HttpServletRequest request) {

		logger.info("cancelSelectedSingleRequest() -> STARTED CANCEL SELECTED SINGLE REQUEST PROCESS  -> ");

		String statusMessage = employeeSingleRequestService.cancelSelectedSingleRequest(singleRequestVO);

		if (statusMessage.equalsIgnoreCase(Constants.SINGLE_REQUEST_CANCELLED_SUCCESS)) {

			SingleRequestVO singleRequest = new SingleRequestVO();
			singleRequest.setUserAction(userAction);
			EmailTemplateVO emailTemplateVO = getCancelledEmailTemplate(singleRequestVO);
			sendEmail(emailTemplateVO);

		} else {
			return new ResponseEntity<>(statusMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("cancelSelectedSingleRequest() -> CANCEL SELECTED SINGLE REQUEST PROCESS COMPLETE -> ");

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

		logger.info("SINGLE REQUEST -> EMAIL NOTIFICATION TO EMPLOYEE - STATUS : " + employeeEmailStatus);
		logger.info("SINGLE REQUEST -> EMAIL NOTIFICATION TO MANAGER - STATUS : " + managerEmailStatus);
		logger.info("SINGLE REQUEST -> EMAIL NOTIFICATION TO ADMIN - STATUS : " + adminEmailStatus);

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

	private EmailTemplateVO getEmailTemplate(SingleRequestVO singleRequestVO, RequestHeaderEntity requestHeader) {

		EmailTemplateVO emailTemplateVO = new EmailTemplateVO();

		emailTemplateVO.setRequestType(Constants.REQUEST_TYPE_SINGLE);
		emailTemplateVO.setEmployeeEmail(singleRequestVO.getEmployeeEmail());
		emailTemplateVO.setManagerEmail(singleRequestVO.getManagerEmail());
		emailTemplateVO.setEmployeeId(singleRequestVO.getEmployeeId());
		emailTemplateVO.setEmployeeName(singleRequestVO.getEmployeeName());
		emailTemplateVO.setManagerName(singleRequestVO.getManagerName());
		emailTemplateVO.setApprovalStatus(Constants.REQUEST_STATUS_APPROVAL_PENDING);
		emailTemplateVO.setRequestHeaderId(requestHeader.getRequestHeaderId());
		emailTemplateVO.setRequestServiceType(requestHeader.getRequestServiceType());
		emailTemplateVO.setEmployeeRemark(singleRequestVO.getEmployeeRemark());
		emailTemplateVO.setEmployeeSelectedLogin(singleRequestVO.getEmployeeSelectedLogin());
		emailTemplateVO.setEmployeeSelectedLogout(singleRequestVO.getEmployeeSelectedLogout());
		emailTemplateVO.setEmployeeSelectedReason(singleRequestVO.getEmployeeSelectedReason());
		emailTemplateVO.setServiceDate(singleRequestVO.getServiceDate());

		if (singleRequestVO.getEmployeeSelectedProjectId().equalsIgnoreCase("NA")) {
			emailTemplateVO.setProjectName(singleRequestVO.getEmployeeSelectedProjectId());
		} else {
			emailTemplateVO.setProjectName(singleRequestVO.getEmployeeSelectedProject());
		}
		emailTemplateVO.setEmailTemplateType(EmailTemplateTypeEnum.EMPLOYEE_REQUEST_DETAILS);

		return emailTemplateVO;
	}

	private EmailTemplateVO getCancelledEmailTemplate(SingleRequestVO singleRequestVO) {

		EmailTemplateVO emailTemplateVO = new EmailTemplateVO();

		emailTemplateVO.setRequestType(Constants.REQUEST_TYPE_SINGLE);
		emailTemplateVO.setEmployeeEmail(singleRequestVO.getEmployeeEmail());
		emailTemplateVO.setManagerEmail(singleRequestVO.getManagerEmail());
		emailTemplateVO.setEmployeeId(singleRequestVO.getGlobalEmployeeId());
		emailTemplateVO.setEmployeeName(singleRequestVO.getEmployeeFirstName() + " " + singleRequestVO.getEmployeeLastName());
		emailTemplateVO.setManagerName(singleRequestVO.getManagerFirstName() + " " + singleRequestVO.getManagerLastName());
		emailTemplateVO.setApprovalStatus(Constants.REQUEST_STATUS_CANCELLED);
		emailTemplateVO.setRequestHeaderId(singleRequestVO.getRequestId());

		getCancelledServiceDateAndType(singleRequestVO, emailTemplateVO);

		emailTemplateVO.setEmployeeRemark(singleRequestVO.getEmployeeRemark());
		emailTemplateVO.setEmployeeSelectedLogin(singleRequestVO.getEmployeeSelectedLogin());
		emailTemplateVO.setEmployeeSelectedLogout(singleRequestVO.getEmployeeSelectedLogout());
		emailTemplateVO.setEmployeeSelectedReason(singleRequestVO.getEmployeeSelectedReason());
		emailTemplateVO.setServiceDate(singleRequestVO.getServiceDate());
		emailTemplateVO.setEmployeeSelectedReason(singleRequestVO.getReasonDescription());

		return emailTemplateVO;
	}

	private void getCancelledServiceDateAndType(SingleRequestVO singleRequestVO, EmailTemplateVO emailTemplateVO) {

		Map<List<String>, List<Date>> cancelScheduleDays = new HashMap<>();
		List<String> serviceType = new ArrayList<>();
		List<java.sql.Date> serviceDate = new ArrayList<>();

		if (singleRequestVO.getRequestDetailEntity().size() == 2) {
			serviceType.add(Constants.SERVICE_TYPE_PICKUP_DROP);
			serviceDate.add(singleRequestVO.getServiceDate());
			emailTemplateVO.setRequestServiceType(Constants.SERVICE_TYPE_PICKUP_DROP);
		} else {
			serviceType.add(singleRequestVO.getRequestServiceType());
			serviceDate.add(singleRequestVO.getServiceDate());
			emailTemplateVO.setRequestServiceType(singleRequestVO.getRequestServiceType());
		}

		cancelScheduleDays.put(serviceType, serviceDate);
		emailTemplateVO.setCancelScheduleDays(cancelScheduleDays);
	}
}
