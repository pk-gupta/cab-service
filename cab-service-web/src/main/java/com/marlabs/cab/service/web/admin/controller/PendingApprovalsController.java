package com.marlabs.cab.service.web.admin.controller;

import java.sql.Date;
import java.text.ParseException;
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
import com.marlabs.cab.service.common.util.AppServerUtil;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.domain.admin.service.PendingApprovalsService;
import com.marlabs.cab.service.domain.employee.service.EmployeeScheduleRequestService;
import com.marlabs.cab.service.domain.employee.service.EmployeeSingleRequestService;
import com.marlabs.cab.service.domain.manager.service.ManagerApprovalsService;
import com.marlabs.cab.service.domain.search.service.SearchService;
import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;
import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;
import com.marlabs.cab.service.persistance.vo.ManagerApprovalVO;
import com.marlabs.cab.service.persistance.vo.ScheduleRequestVO;

@RestController
public class PendingApprovalsController {

	private static Logger logger = LogManager.getLogger(PendingApprovalsController.class);

	@Autowired
	Environment environment;

	@Autowired
	EmailService emailService;

	@Autowired
	private ManagerApprovalsService managerApprovalsService;

	@Autowired
	private EmployeeScheduleRequestService employeeScheduleRequestService;

	@Autowired
	private EmployeeSingleRequestService employeeSingleRequestService;

	@Autowired
	PendingApprovalsService pendingApprovalsService;

	@Autowired
	SearchService searchService;

	@RequestMapping(value = "/admin/pendingApprovalsDashboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<EmployeeManagerDashboardEntity>> pendingApprovalsDashboard(HttpServletRequest request) {

		logger.info("pendingApprovalsDashboard() -> STARTED ADMIN PENDING APPROVALS DASHBOARD PROCESS...");

		List<EmployeeManagerDashboardEntity> employeeManagerDashboardEntityList = pendingApprovalsService.pendingApprovalsDashboard();

		logger.info("pendingApprovalsDashboard() -> ADMIN PENDING APPROVALS DASHBOARD PROCESS COMPLETE ");

		return new ResponseEntity<>(employeeManagerDashboardEntityList,AppServerUtil.getResponseStatus(employeeManagerDashboardEntityList, HttpStatus.MULTI_STATUS));
	}

	@RequestMapping(value = "/admin/processAdminApproval", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> processAdminApproval(@RequestBody ManagerApprovalVO managerApprovalVO,HttpServletRequest request) throws ParseException {

		logger.info("processAdminApproval() -> STARTED MANAGER APPROVAL PROCESS FOR REQUEST ID -> "+ managerApprovalVO.getRequestId() + "...");

		RequestHeaderEntity requestHeader = managerApprovalsService.processManagerApproval(managerApprovalVO.getRequestId(), managerApprovalVO.getManagerId(),managerApprovalVO.getApprovalStatus(), managerApprovalVO.getApprovalComment());

		String statusMessage = null;
		if (CabServiceUtil.isNULL(requestHeader)) {
			statusMessage = "Failed to Approve Request. Please try again later.";
		} else {
			statusMessage = "Request Approved successfully"; // environment.getProperty("cab.service.request.create.success");

			EmailTemplateVO emailTemplateVO = getEmailDetails(managerApprovalVO);

			sendAdminApprovalEmail(emailTemplateVO);

		}
		logger.info("processAdminApproval() -> MANAGER APPROVAL PROCESS COMPLETE FOR REQUEST ID -> "+ managerApprovalVO.getRequestId());

		return new ResponseEntity<>(statusMessage, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/cancelSelectedPendingRequest/{RequestType}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> cancelSelectedPendingRequest(@PathVariable("RequestType") String requestType, @RequestBody ScheduleRequestVO scheduleRequestVO,HttpServletRequest request) {

		logger.info("cancelSelectedPendingRequest() -> STARTED CANCEL SELECTED PENDING REQUEST PROCESS  -> ");

		String statusMessage = null;

		if (requestType.equalsIgnoreCase(Constants.REQUEST_TYPE_SCHEDULE)) {
			statusMessage = employeeScheduleRequestService.cancelSelectedScheduleRequest(scheduleRequestVO);

		} else {
			statusMessage = employeeSingleRequestService.cancelSelectedSingleRequest(scheduleRequestVO);
		}

		if (statusMessage.equalsIgnoreCase(Constants.SCHEDULE_REQUEST_CANCELLED_SUCCESS)|| statusMessage.equalsIgnoreCase(Constants.SINGLE_REQUEST_CANCELLED_SUCCESS)) {

			EmailTemplateVO emailTemplateVO = getCancelledEmailTemplate(scheduleRequestVO, requestType);

			sendAdminApprovalEmail(emailTemplateVO);

			return new ResponseEntity<>(statusMessage, HttpStatus.OK);
		}
		logger.info("cancelSelectedPendingRequest() -> CANCEL SELECTED PENDING REQUEST PROCESS COMPLETE -> ");
		return new ResponseEntity<>(statusMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void sendAdminApprovalEmail(EmailTemplateVO emailTemplateVO) {

		logger.info("sendAdminApprovalEmail() -> STARTED EMAIL NOTIFICATION PROCESS...");

		Future<String> employeeEmailStatus = null;
		Future<String> managerEmailStatus = null;
		Future<String> adminEmailStatus = null;

		AbstractEmailTemplate employeeTemplate = new EmployeeEmailTemplate();
		emailTemplateVO.setEmailTemplateType(EmailTemplateTypeEnum.EMPLOYEE_REQUEST_DETAILS);
		StringBuilder empEmailTemplate = employeeTemplate.createEmailTemplate(emailTemplateVO);

		if (emailTemplateVO.getApprovalStatus().equalsIgnoreCase(Constants.REQUEST_STATUS_CANCELLED)) {
			employeeEmailStatus = emailService.sendSimpleMessage(emailTemplateVO.getEmployeeEmail(),Constants.EMAIL_CANCEL_REQUEST, empEmailTemplate.toString());
		} else {
			employeeEmailStatus = emailService.sendSimpleMessage(emailTemplateVO.getEmployeeEmail(),Constants.EMAIL_REQUEST_APPROVAL_BY_ADMIN, empEmailTemplate.toString());
		}

		AbstractEmailTemplate managerTemplate = new ManagerEmailTemplate();
		emailTemplateVO.setEmailTemplateType(EmailTemplateTypeEnum.MANAGER_REQUEST_DETAILS);
		StringBuilder mgrEmailTemplate = managerTemplate.createEmailTemplate(emailTemplateVO);

		if (emailTemplateVO.getApprovalStatus().equalsIgnoreCase(Constants.REQUEST_STATUS_CANCELLED)) {
			managerEmailStatus = emailService.sendSimpleMessage(emailTemplateVO.getManagerEmail(),Constants.EMAIL_CANCEL_REQUEST, mgrEmailTemplate.toString());
		} else {
			managerEmailStatus = emailService.sendSimpleMessage(emailTemplateVO.getManagerEmail(),Constants.EMAIL_REQUEST_APPROVAL_BY_ADMIN, mgrEmailTemplate.toString());
		}

		AbstractEmailTemplate adminTemplate = new AdminEmailTemplate();
		emailTemplateVO.setEmailTemplateType(EmailTemplateTypeEnum.ADMIN_REQUEST_DETAILS);
		StringBuilder admEmailTemplate = adminTemplate.createEmailTemplate(emailTemplateVO);

		if (emailTemplateVO.getApprovalStatus().equalsIgnoreCase(Constants.REQUEST_STATUS_CANCELLED)) {
			adminEmailStatus = emailService.sendSimpleMessage(emailTemplateVO.getAdminEmail(),Constants.EMAIL_CANCEL_REQUEST, admEmailTemplate.toString());
		} else {
			adminEmailStatus = emailService.sendSimpleMessage(emailTemplateVO.getAdminEmail(),Constants.EMAIL_REQUEST_APPROVAL_BY_ADMIN, admEmailTemplate.toString());
		}

		logger.info("ADMIN APPROVAL REQUEST -> EMAIL NOTIFICATION TO EMPLOYEE - STATUS : " + employeeEmailStatus);
		logger.info("ADMIN APPROVAL REQUEST -> EMAIL NOTIFICATION TO MANAGER - STATUS : " + managerEmailStatus);
		logger.info("ADMIN APPROVAL REQUEST -> EMAIL NOTIFICATION TO ADMIN - STATUS : " + adminEmailStatus);

		logger.info("sendAdminApprovalEmail() -> EMAIL NOTIFICATION PROCESS COMPLETE");
	}

	private EmailTemplateVO getEmailDetails(ManagerApprovalVO managerApprovalVO) {

		EmailTemplateVO emailTemplateVO = new EmailTemplateVO();

		emailTemplateVO.setEmployeeId(managerApprovalVO.getEmployeeId());
		emailTemplateVO.setRequestHeaderId(managerApprovalVO.getRequestId());
		emailTemplateVO.setManagerName(Constants.REQUEST_APPROVAL_BY_ADMIN);
		emailTemplateVO.setEmployeeName(managerApprovalVO.getEmpFirstName() + " " + managerApprovalVO.getEmpLastName());
		emailTemplateVO.setManagerEmail(managerApprovalVO.getManagerEmail());
		emailTemplateVO.setEmployeeEmail(managerApprovalVO.getEmployeeEmail());
		emailTemplateVO.setApprovalStatus(managerApprovalVO.getApprovalStatus());
		emailTemplateVO.setManagerRemark(managerApprovalVO.getApprovalComment());
		emailTemplateVO.setEmployeeSelectedLogin(managerApprovalVO.getLoginTime());
		emailTemplateVO.setEmployeeSelectedLogout(managerApprovalVO.getLogoutTime());
		emailTemplateVO.setRequestServiceType(managerApprovalVO.getReqServiceType());
		emailTemplateVO.setRequestType(managerApprovalVO.getRequestType());
		emailTemplateVO.setEmployeeRemark(managerApprovalVO.getEmployeeRemark());
		emailTemplateVO.setEmployeeSelectedReason(managerApprovalVO.getReasonDescription());
		emailTemplateVO.setAdminEmail(managerApprovalVO.getAdminEmail());
		emailTemplateVO.setApprovalBy(Constants.REQUEST_APPROVAL_BY_ADMIN);
		emailTemplateVO.setProjectName(managerApprovalVO.getProjectName());

		if (managerApprovalVO.getRequestType().equalsIgnoreCase(Constants.REQUEST_TYPE_SCHEDULE)) {
			emailTemplateVO.setFromDate(managerApprovalVO.getFromDate());
			emailTemplateVO.setToDate(managerApprovalVO.getToDate());
		} else {
			emailTemplateVO.setServiceDate(managerApprovalVO.getFromDate());
		}

		return emailTemplateVO;
	}

	private EmailTemplateVO getCancelledEmailTemplate(ScheduleRequestVO scheduleRequestVO, String requestType) {

		EmailTemplateVO emailTemplateVO = new EmailTemplateVO();

		emailTemplateVO.setRequestType(requestType);
		emailTemplateVO.setEmployeeEmail(scheduleRequestVO.getEmployeeEmail());
		emailTemplateVO.setManagerEmail(scheduleRequestVO.getManagerEmail());
		emailTemplateVO.setEmployeeId(scheduleRequestVO.getEmployeeId());
		emailTemplateVO.setEmployeeName(scheduleRequestVO.getEmployeeFirstName() + " " + scheduleRequestVO.getEmployeeLastName());
		emailTemplateVO.setManagerName(scheduleRequestVO.getManagerFirstName() + " " + scheduleRequestVO.getManagerLastName());
		emailTemplateVO.setApprovalStatus(Constants.REQUEST_STATUS_CANCELLED);
		emailTemplateVO.setRequestHeaderId(scheduleRequestVO.getRequestId());
		emailTemplateVO.setRequestServiceType(scheduleRequestVO.getRequestServiceType());
		emailTemplateVO.setEmployeeRemark(scheduleRequestVO.getEmployeeRemark());
		emailTemplateVO.setEmployeeSelectedLogin(scheduleRequestVO.getEmployeeSelectedLogin());
		emailTemplateVO.setEmployeeSelectedLogout(scheduleRequestVO.getEmployeeSelectedLogout());
		emailTemplateVO.setEmployeeSelectedReason(scheduleRequestVO.getEmployeeSelectedReason());
		emailTemplateVO.setServiceDate(scheduleRequestVO.getServiceDate());
		emailTemplateVO.setEmployeeSelectedReason(scheduleRequestVO.getReasonDescription());
		emailTemplateVO.setApprovalBy(Constants.REQUEST_APPROVAL_BY_ADMIN);

		if (emailTemplateVO.getRequestType().equalsIgnoreCase(Constants.REQUEST_TYPE_SCHEDULE)) {

			emailTemplateVO.setFromDate(scheduleRequestVO.getEmplopyeeSelectedFromDate());
			emailTemplateVO.setToDate(scheduleRequestVO.getEmplopyeeSelectedToDate());
		} else {
			emailTemplateVO.setServiceDate(scheduleRequestVO.getServiceDate());
		}
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
			cancelScheduleDays.put(serviceType, serviceDate);
		}

		emailTemplateVO.setCancelScheduleDays(cancelScheduleDays);
	}

}