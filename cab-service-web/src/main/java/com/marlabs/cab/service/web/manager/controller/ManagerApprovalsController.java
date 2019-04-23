package com.marlabs.cab.service.web.manager.controller;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.marlabs.cab.service.common.email.template.EmployeeEmailTemplate;
import com.marlabs.cab.service.common.email.template.ManagerEmailTemplate;
import com.marlabs.cab.service.common.enums.EmailTemplateTypeEnum;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.domain.manager.service.ManagerApprovalsService;
import com.marlabs.cab.service.domain.search.service.SearchService;
import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;
import com.marlabs.cab.service.persistance.vo.ManagerApprovalVO;

@RestController
//@CrossOrigin
public class ManagerApprovalsController {
	
	private static Logger logger = LogManager.getLogger(ManagerApprovalsController.class);
	
	@Autowired
    Environment environment;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	private ManagerApprovalsService managerApprovalsService;
	
	@Autowired
	SearchService searchService;
	
	
	@RequestMapping( value="/employee/approvalManagerDashboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<EmployeeManagerDashboardEntity>> approvalManagerDashboard(@RequestParam("managerId") String managerId, HttpServletRequest request) {
		
		logger.info("approvalManagerDashboard() -> STARTED MANAGER  APPROVAL DASHBOARD PROCESS FOR MANAGER ID -> "+ managerId +"...");
		
		List<EmployeeManagerDashboardEntity> employeeManagerDashboardEntityList = managerApprovalsService.getApprovalManagerDashboard(managerId);
		
		logger.info("approvalManagerDashboard() -> MANAGER  APPROVAL DASHBOARD PROCESS COMPLETE FOR MANAGER ID -> "+ managerId);
		
		return new ResponseEntity<>(employeeManagerDashboardEntityList, HttpStatus.OK);
	}
	
	@RequestMapping( value="/employee/processManagerApproval", method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> processManagerApproval(@RequestBody ManagerApprovalVO managerApprovalVO, HttpServletRequest request) throws ParseException {	
	
		logger.info("processManagerApproval() -> STARTED MANAGER APPROVAL PROCESS FOR REQUEST ID -> "+ managerApprovalVO.getRequestId() +"...");
		
		RequestHeaderEntity requestHeader = managerApprovalsService.processManagerApproval(managerApprovalVO.getRequestId(), managerApprovalVO.getManagerId(), managerApprovalVO.getApprovalStatus(), managerApprovalVO.getApprovalComment());
		
		String statusMessage = null;
		if(CabServiceUtil.isNULL(requestHeader)){
			statusMessage =  "Failed to Approve Request. Please try again later.";
		}else {
			statusMessage = "Request Approved successfully"; //environment.getProperty("cab.service.request.create.success"); 
			
			EmailTemplateVO emailTemplateVO = getEmailDetails(managerApprovalVO);
			
			sendManagerApprovalEmail(emailTemplateVO);
			
		}
		
		logger.info("processManagerApproval() -> MANAGER APPROVAL PROCESS COMPLETE FOR REQUEST ID -> "+ managerApprovalVO.getRequestId());
		
		return new ResponseEntity<>(statusMessage, HttpStatus.OK);
	}

	
	@RequestMapping( value="/employee/managerDashboardSearch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<RequestHeaderEntity>> managerDashboardSearch(@RequestParam("searchText") String searchText, HttpServletRequest request) {
		
		logger.info("managerDashboardSearch() -> STARTED SEARCH IN MANAGER DASHBOARD FOR TEXT -> "+ searchText +"...");
		
		List<RequestHeaderEntity> searchResults = searchService.searchText(searchText);
		
		logger.info("managerDashboardSearch() -> SEARCH IN MANAGER DASHBOARD FOR TEXT -> "+ searchText + " COMPLETE");
		
		return new ResponseEntity<>(searchResults, HttpStatus.OK);
	}
	
	private void sendManagerApprovalEmail(EmailTemplateVO emailTemplateVO) {
		logger.info("sendManagerApprovalEmail() -> STARTED EMAIL NOTIFICATION PROCESS...");

		AbstractEmailTemplate employeeTemplate = new EmployeeEmailTemplate();
		StringBuilder empEmailTemplate = employeeTemplate.createEmailTemplate(emailTemplateVO);
		
		Future<String> employeeEmailStatus = emailService.sendSimpleMessage(emailTemplateVO.getEmployeeEmail(),Constants.EMAIL_REQUEST_APPROVAL_BY_MANAGER,empEmailTemplate.toString());
		
		AbstractEmailTemplate managerTemplate = new ManagerEmailTemplate();
		emailTemplateVO.setEmailTemplateType(EmailTemplateTypeEnum.MANAGER_REQUEST_DETAILS);
		StringBuilder mgrEmailTemplate = managerTemplate.createEmailTemplate(emailTemplateVO);
		
		Future<String> managerEmailStatus = emailService.sendSimpleMessage(emailTemplateVO.getManagerEmail(),Constants.EMAIL_REQUEST_APPROVAL_BY_MANAGER, mgrEmailTemplate.toString());
		
	/*	AbstractEmailTemplate adminTemplate = new AdminEmailTemplate();
		emailTemplateVO.setEmailTemplateType(EmailTemplateTypeEnum.ADMIN_REQUEST_DETAILS);
		StringBuilder admEmailTemplate = adminTemplate.createEmailTemplate(emailTemplateVO);
		
		Future<String> adminEmailStatus = emailService.sendSimpleMessage(emailTemplateVO.getAdminEmail(), "mFleet - Request No:- "+emailTemplateVO.getRequestHeaderId()+" - "+emailTemplateVO.getApprovalStatus(), admEmailTemplate.toString());
		*/
		logger.info("MANAGER APPROVAL REQUEST -> EMAIL NOTIFICATION TO EMPLOYEE - STATUS : " + employeeEmailStatus);
		logger.info("MANAGER APPROVAL REQUEST -> EMAIL NOTIFICATION TO MANAGER - STATUS : " + managerEmailStatus);

		logger.info("sendManagerApprovalEmail() -> EMAIL NOTIFICATION PROCESS COMPLETE");
	}
	
	private EmailTemplateVO getEmailDetails(ManagerApprovalVO managerApprovalVO) {
		
		EmailTemplateVO emailTemplateVO = new EmailTemplateVO();

		emailTemplateVO.setRequestHeaderId(managerApprovalVO.getRequestId());
		emailTemplateVO.setManagerName(managerApprovalVO.getMngrFirstName()+" "+managerApprovalVO.getMngrLastName());
		emailTemplateVO.setEmployeeName(managerApprovalVO.getEmpFirstName()+" "+managerApprovalVO.getEmpLastName());
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
		emailTemplateVO.setProjectName(managerApprovalVO.getProjectName());
		
		if(managerApprovalVO.getRequestType().equalsIgnoreCase(Constants.REQUEST_TYPE_SCHEDULE)){
			emailTemplateVO.setFromDate(managerApprovalVO.getFromDate());
			emailTemplateVO.setToDate(managerApprovalVO.getToDate());
		}
		emailTemplateVO.setServiceDate(managerApprovalVO.getFromDate());
		emailTemplateVO.setEmailTemplateType(EmailTemplateTypeEnum.EMPLOYEE_REQUEST_DETAILS);
		return emailTemplateVO;
	}
	
	
}
