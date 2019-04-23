package com.marlabs.cab.service.domain.employee.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.domain.command.ProcessRequestCommand;
import com.marlabs.cab.service.domain.command.ServiceTypeValidatorCommand;
import com.marlabs.cab.service.domain.command.invoker.CabServiceRequestManager;
import com.marlabs.cab.service.domain.command.invoker.ProcessRequestManager;
import com.marlabs.cab.service.domain.command.receiver.CabServiceRequest;
import com.marlabs.cab.service.domain.command.receiver.ProcessRequest;
import com.marlabs.cab.service.domain.common.service.CustomQueryService;
import com.marlabs.cab.service.domain.employee.service.EmployeeScheduleRequestService;
import com.marlabs.cab.service.domain.factory.ProcessRequestCommandFactory;
import com.marlabs.cab.service.domain.factory.ProcessRequestFactory;
import com.marlabs.cab.service.domain.factory.ServiceTypeCommandFactory;
import com.marlabs.cab.service.domain.factory.ServiceTypeFactory;
import com.marlabs.cab.service.domain.master.data.service.MasterDataService;
import com.marlabs.cab.service.domain.parallel.activity.service.ParallelActivityService;
import com.marlabs.cab.service.persistance.common.dao.CustomQueryDAO;
import com.marlabs.cab.service.persistance.employee.dao.EmployeeScheduleRequestDAO;
import com.marlabs.cab.service.persistance.entity.EmployeeExistingRequestView;
import com.marlabs.cab.service.persistance.entity.EmployeeLandmarkEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeProjectAllocationView;
import com.marlabs.cab.service.persistance.entity.EmployeeView;
import com.marlabs.cab.service.persistance.entity.OfficeBranchEntity;
import com.marlabs.cab.service.persistance.entity.ReasonEntity;
import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;
import com.marlabs.cab.service.persistance.sql.mapper.EmployeeLandmarkSQLMapper;
import com.marlabs.cab.service.persistance.sql.mapper.EmployeeProjectManagerSQLMapper;
import com.marlabs.cab.service.persistance.vo.EmployeeProjectVO;
import com.marlabs.cab.service.persistance.vo.ProcessNewRequestVO;
import com.marlabs.cab.service.persistance.vo.ScheduleRequestVO;
import com.marlabs.cab.service.persistance.vo.ScheduleServiceDay;
import com.marlabs.cab.service.persistance.vo.SingleRequestVO;

@Service
public class EmployeeScheduleRequestServiceImpl implements EmployeeScheduleRequestService{

	private static Logger logger = LogManager.getLogger(EmployeeScheduleRequestServiceImpl.class);

	@Autowired
	private EmployeeScheduleRequestDAO employeeScheduleRequestDAO;

	@Autowired
	private MasterDataService masterDataService;

	@Autowired
	private ParallelActivityService parallelActivityService;

	@Autowired
	CustomQueryDAO customQueryDAO;

	@Autowired
	CustomQueryService customQueryService;

	@Override
	public List<EmployeeManagerDashboardEntity> scheduleRequestDashboard(String employeeId) {
		logger.info("scheduleRequestDashboard() -> Schedule Request Dashboard Service call...");

		return employeeScheduleRequestDAO.scheduleRequestDashboard(employeeId);
	}

	@Override
	public ScheduleRequestVO createNewScheduleRequest(String employeeId) {
		logger.info("createNewScheduleRequest() -> Create New Schedule Request Service call...");

		ScheduleRequestVO scheduleRequestVO = new ScheduleRequestVO();

		scheduleRequestVO = setDropDownAndEmployeeDetails(scheduleRequestVO, employeeId, 0l, null, "create");

		return scheduleRequestVO;
	}

	@Override
	public ProcessNewRequestVO processScheduleRequest(ScheduleRequestVO scheduleRequestVO, String userAction) {
		logger.info("processScheduleRequest() -> New Schedule Request Service call...");
		
		/*List<EmployeeExistingRequestView> existingRequest = employeeScheduleRequestDAO.getExistingRequests(scheduleRequestVO);
		
		Optional<List<EmployeeExistingRequestView>> resultOptional = Optional.ofNullable(existingRequest);
		
		if(resultOptional.isPresent()){
			if(scheduleRequestVO.getEmployeeServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_PICKUP_DROP)){
				//Check for Pick-Up duplicates
				scheduleRequestVO.setEmployeeServiceType(Constants.SERVICE_TYPE_PICKUP);
				checkForDuplicates(scheduleRequestVO, existingRequest);
				
				//Check for Drop duplicates
				scheduleRequestVO.setEmployeeServiceType(Constants.SERVICE_TYPE_DROP);
				checkForDuplicates(scheduleRequestVO, existingRequest);
			}else{
				checkForDuplicates(scheduleRequestVO, existingRequest);
			}
		}
		

		if(scheduleRequestVO.getRequestId()!=0){
			employeeScheduleRequestDAO.deleteRequestDetails(scheduleRequestVO.getRequestId());
		}

		RequestHeaderEntity requestHeader = setRequestHeaderEntity(scheduleRequestVO);
		requestHeader.setRequestHeaderId(scheduleRequestVO.getRequestId());//set to Update for Specific RequestID
		if(userAction.equalsIgnoreCase(Constants.REQUEST_ACTION_SAVE)){
			requestHeader.setApproveStatus(Constants.REQUEST_STATUS_DRAFT);
		}else{
			requestHeader.setApproveStatus(Constants.REQUEST_STATUS_APPROVAL_PENDING);
		}

		requestHeader.setRequestDetailList(setRequestDetailEntries(scheduleRequestVO, requestHeader));
		
        List<RequestDetailEntity> existingRequestList=scheduleRequestVO.getRequestDetailEntity();
		
		if (!CabServiceUtil.isNULL(existingRequestList)) {
			
			List<RequestDetailEntity> newRequestList = requestHeader.getRequestDetailList();
		
		 for(RequestDetailEntity requestDetail:newRequestList){
			
		 for(RequestDetailEntity requestDetail1:existingRequestList ){
						
		 if(requestDetail.getServiceType().equalsIgnoreCase(requestDetail1.getServiceType()) && requestDetail.getServiceDate().equals(requestDetail1.getServiceDate())){
				
			    requestDetail.setRequestDetailId(requestDetail1.getRequestDetailId());break;
				
			  }else if( newRequestList.indexOf(requestDetail) < newRequestList.size()-1 && requestDetail.getServiceDate().equals(requestDetail1.getServiceDate()) && !newRequestList.get(newRequestList.indexOf(requestDetail)+1).getServiceDate().equals(requestDetail1.getServiceDate())){
				
				 requestDetail.setRequestDetailId(requestDetail1.getRequestDetailId());
				  
			    }else if(newRequestList.indexOf(requestDetail) >= newRequestList.size()-1 && requestDetail.getServiceDate().equals(requestDetail1.getServiceDate())){
			    	
			      requestDetail.setRequestDetailId(requestDetail1.getRequestDetailId());
			    }			
		     }
			
		   }
		
		}	
		
		return employeeScheduleRequestDAO.processScheduleRequest(requestHeader,scheduleRequestVO);*/
		
		ProcessNewRequestVO actionResult;
		
		if (userAction.equalsIgnoreCase(Constants.REQUEST_ACTION_SAVE)) {
			ProcessRequest processRequest = ProcessRequestFactory.getProcessRequest(Constants.REQUEST_ACTION_SAVE);
			ProcessRequestCommand processRequestCommand = ProcessRequestCommandFactory.getProcessCommand(Constants.REQUEST_ACTION_SAVE);
			ProcessRequestManager processRequestManager = new ProcessRequestManager(processRequestCommand);
			actionResult = processRequestManager.performSaveSubmitOperation(scheduleRequestVO, employeeScheduleRequestDAO, processRequest);
			
		}else{
			ProcessRequest processRequest = ProcessRequestFactory.getProcessRequest(Constants.REQUEST_ACTION_SUBMIT);
			ProcessRequestCommand processRequestCommand = ProcessRequestCommandFactory.getProcessCommand(Constants.REQUEST_ACTION_SUBMIT);
			ProcessRequestManager processRequestManager = new ProcessRequestManager(processRequestCommand);
			actionResult = processRequestManager.performSaveSubmitOperation(scheduleRequestVO, employeeScheduleRequestDAO, processRequest);
		}
		
		return actionResult;
	}
	
	private void checkForDuplicates(SingleRequestVO singleRequestVO, List<EmployeeExistingRequestView> existingRequest) {
		CabServiceRequest request = ServiceTypeFactory.getServiceType(singleRequestVO.getEmployeeServiceType());
		ServiceTypeValidatorCommand validatorCommand = ServiceTypeCommandFactory.getServiceType(singleRequestVO.getEmployeeServiceType());
		CabServiceRequestManager requestManager = new CabServiceRequestManager(validatorCommand);
		requestManager.performPickupDropValidation(request, singleRequestVO, existingRequest);
	}

	private List<RequestDetailEntity> setRequestDetailEntries(ScheduleRequestVO scheduleRequestVO,
			RequestHeaderEntity requestHeader) {

		List<RequestDetailEntity> requestDetailList = new ArrayList<>();

		List<ScheduleServiceDay> scheduleServiceDayList = scheduleRequestVO.getScheduleServiceDays();

		scheduleServiceDayList.forEach(day -> {
			if (day.isServiceLogin()) {
				requestDetailList.add(setRequestDetailEntity(requestHeader, scheduleRequestVO, day.getServiceDate(),
						Constants.SERVICE_TYPE_PICKUP));
			}

			if (day.isServiceLogout()) {
				requestDetailList.add(setRequestDetailEntity(requestHeader, scheduleRequestVO, day.getServiceDate(),
						Constants.SERVICE_TYPE_DROP));
			}
		});

		return requestDetailList;
	}

	private RequestDetailEntity setRequestDetailEntity(RequestHeaderEntity requestHeader,
			ScheduleRequestVO scheduleRequestVO, Date scheduleDate, String serviceType) {
		RequestDetailEntity requestDetail = new RequestDetailEntity();
		requestDetail.setRequestHeader(requestHeader);
		requestDetail.setServiceType(serviceType);
		requestDetail.setServiceDate(scheduleDate);
		requestDetail.setCreatedBy(scheduleRequestVO.getEmployeeId());
		requestDetail.setUpdateDate(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		requestDetail.setUpdatedBy(requestHeader.getEmployeeId());
		requestDetail.setIsCancelled(Constants.STATUS_FALSE);

		return requestDetail;
	}

	private RequestHeaderEntity setRequestHeaderEntity(ScheduleRequestVO scheduleRequestVO) {
		RequestHeaderEntity requestHeader = new RequestHeaderEntity();

		EmployeeLandmarkEntity employeeLandmarkEntity = new EmployeeLandmarkEntity();
		employeeLandmarkEntity.setEmployeeLandmarkId(scheduleRequestVO.getEmployeeSelectedLandmarkId());

		ReasonEntity reasonEntity = new ReasonEntity();
		reasonEntity.setReasonId(scheduleRequestVO.getEmployeeSelectedReasonId());

		OfficeBranchEntity officeBranchEntity = new OfficeBranchEntity();
		officeBranchEntity.setOfficeBranchcode(scheduleRequestVO.getEmployeeSelectedBranchCode());

		requestHeader.setEmployeeLandmark(employeeLandmarkEntity);
		requestHeader.setReason(reasonEntity);
		requestHeader.setOfficeBranch(officeBranchEntity);
		requestHeader.setRequestServiceType(scheduleRequestVO.getRequestServiceType());
		requestHeader.setManagerId(scheduleRequestVO.getManagerId());
		requestHeader.setEmployeeId(scheduleRequestVO.getGlobalEmployeeId());
		requestHeader.setProjectId(scheduleRequestVO.getEmployeeSelectedProjectId());
		requestHeader.setRequestType(Constants.REQUEST_TYPE_SCHEDULE);
		requestHeader.setEmployeePhone(scheduleRequestVO.getEmployeePhone());
		requestHeader.setFromDate(scheduleRequestVO.getEmplopyeeSelectedFromDate());
		requestHeader.setToDate(scheduleRequestVO.getEmplopyeeSelectedToDate());
		requestHeader.setLoginTime(scheduleRequestVO.getEmployeeSelectedLogin());
		requestHeader.setLogoutTime(scheduleRequestVO.getEmployeeSelectedLogout());
		requestHeader.setWeekOffDay1(scheduleRequestVO.getEmployeeWeekOffDay1());
		requestHeader.setWeekOffDay2(scheduleRequestVO.getEmployeeWeekOffDay2());
		requestHeader.setWeekOffDay3(scheduleRequestVO.getEmployeeWeekOffDay3());
		requestHeader.setNoOfWeekOffs(scheduleRequestVO.getEmployeeNoOfWeekOffs());
		requestHeader.setCreatedBy(scheduleRequestVO.getEmployeeId());
		requestHeader.setUpdatedBy(scheduleRequestVO.getEmployeeId());
		requestHeader.setEmployeeRemark(scheduleRequestVO.getEmployeeRemark());
		requestHeader.setUpdateDate(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		requestHeader.setIsDeleted("false");
		requestHeader.setIsCancelled("false");

		return requestHeader;
	}

	@Override
	public ScheduleRequestVO editScheduleRequest(long requestId) {
		logger.info("editScheduleRequest() -> Edit Schedule Request Service call...");

		List<RequestHeaderEntity> requestheaderList = employeeScheduleRequestDAO.editScheduleRequest(requestId);
		ScheduleRequestVO scheduleRequestVO = null;
		if (!CabServiceUtil.isNULL(requestheaderList)) {
			scheduleRequestVO = new ScheduleRequestVO();
			scheduleRequestVO.setRequestHeaderEntity(requestheaderList);

			RequestHeaderEntity requestHeaderEntity = requestheaderList.get(0);
			scheduleRequestVO
					.setEmployeeSelectedBranchCode(requestHeaderEntity.getOfficeBranch().getOfficeBranchcode());
			scheduleRequestVO.setEmployeeSelectedBranch(requestHeaderEntity.getOfficeBranch().getOfficeBranchName());
			scheduleRequestVO.setEmployeeSelectedReasonId(requestHeaderEntity.getReason().getReasonId());
			scheduleRequestVO.setEmployeeSelectedReason(requestHeaderEntity.getReason().getReasonDescription());
			scheduleRequestVO
					.setEmployeeSelectedLandmarkId(requestHeaderEntity.getEmployeeLandmark().getEmployeeLandmarkId());
			scheduleRequestVO.setEmployeeSelectedLandmark(
					requestHeaderEntity.getEmployeeLandmark().getCityLandmarkMaster().getCityLandmarkName());
			
			scheduleRequestVO.setRequestId(requestId);

			setDropDownAndEmployeeDetails(scheduleRequestVO, requestHeaderEntity.getEmployeeId(), requestId, requestHeaderEntity.getOfficeBranch().getOfficeBranchcode(), "edit");
			// setDropDownAndEmployeeDetails(scheduleRequestVO, requestheaderList.get(0).getEmployeeId());
		}

		return scheduleRequestVO;
	}

	@Override
	public String deleteScheduleRequest(long requestId) {
		logger.info("deleteScheduleRequest() -> Delete Schedule Request Service call...");
		return employeeScheduleRequestDAO.deleteScheduleRequest(requestId);
	}

	@Override
	public String cancelScheduleRequest(long requestId, String employeeId) {
		logger.info("cancelScheduleRequest() -> Cancel Schedule Request Service call...");
		return employeeScheduleRequestDAO.cancelScheduleRequest(requestId, employeeId);
	}

	private ScheduleRequestVO setDropDownAndEmployeeDetails(ScheduleRequestVO scheduleRequestVO, String employeeId,Long reuestId,String officeBranchCode, String action) {
		logger.info("setDropDownValues() -> Fetch and Set all Dropdowns values...");

		Map<String,Object> dropdownTaskMap = new HashMap<>();
		dropdownTaskMap.put("landmark", masterDataService.getLandmarks());
		dropdownTaskMap.put("reasons", masterDataService.getReasons());
		
		if ("edit".equals(action)) {
			dropdownTaskMap.put("branch", null);
		} else {
			dropdownTaskMap.put("branch", null);
		}
		
		dropdownTaskMap.put("login", masterDataService.getLoginTime());
		dropdownTaskMap.put("logout", masterDataService.getLogoutTime());
		dropdownTaskMap.put("projects", parallelActivityService.getEmployeeProjectAllocationViewData(employeeId));

		if ("edit".equals(action)) {
			dropdownTaskMap.put("employeeDetails", null);
		} else {
			dropdownTaskMap.put("employeeDetails", parallelActivityService.getEmployeeViewData(employeeId));
		}

		dropdownTaskMap.put("employeeLandmark", null);

		dropdownTaskMap.forEach((k, v) -> setScheduleRequestVODropdowns(k, v, scheduleRequestVO, employeeId,officeBranchCode, action));

		return scheduleRequestVO;
	}

	private void setScheduleRequestVODropdowns(String dropdown, Object asyncTask,ScheduleRequestVO scheduleRequestVO, String employeeId,String officeBranchCode, String action) {
		try {
			switch (dropdown) {
				case "landmark":
					scheduleRequestVO.setLandMarksList(masterDataService.getLandmarks());
					break;
				case "reasons":
					scheduleRequestVO.setReasonsList(masterDataService.getReasons());
					break;
				case "branch":
					if ("edit".equals(action)) {
						scheduleRequestVO.setOfficeBranchesList(customQueryService.getCitySpecificOfficeBranches(officeBranchCode));
					} else {
						scheduleRequestVO.setOfficeBranchesList(customQueryService.getLandmarkSpecificOfficeBranches(employeeId));
					} 
					break;
				case "login":
					scheduleRequestVO.setLoginTimeList(masterDataService.getLoginTime());
					break;
				case "logout":
					scheduleRequestVO.setLogoutTimeList(masterDataService.getLogoutTime());
					break;
				case "projects":
					setEmployeeProjectList(asyncTask, scheduleRequestVO);
					break;
				case "employeeDetails":
					if ("edit".equals(action)) {
						setEmployeeDetails(scheduleRequestVO);
					} else {
						setEmployeeDetails(asyncTask, scheduleRequestVO);
					}
					break;
				case "employeeLandmark":
					setEmployeeLandmark(employeeId, scheduleRequestVO);
					break;
				default:
					throw new IllegalArgumentException();
			  }
		} catch (InterruptedException | ExecutionException exception) {
			logger.error("setDropDownValues() -> ERROR: " + exception.getCause());
		}
	}

	private void setEmployeeProjectList(Object objectTask, ScheduleRequestVO scheduleRequestVO)
			throws InterruptedException, ExecutionException {

		List<EmployeeProjectAllocationView> projectViewList = (List<EmployeeProjectAllocationView>) objectTask;

		List<EmployeeProjectVO> employeeProjectList = new LinkedList<>();

		projectViewList.forEach(project -> {
			EmployeeProjectVO employeeProject = new EmployeeProjectVO();
			employeeProject.setProjectId(project.getProjectID());
			employeeProject.setProjectName(project.getEmployeeProjectName());
			employeeProjectList.add(employeeProject);
		});

		scheduleRequestVO.setProjectList(employeeProjectList);
	}

	private void setEmployeeDetails(Object objectTask, ScheduleRequestVO scheduleRequestVO)
			throws InterruptedException, ExecutionException {
		List<EmployeeView> employeeDetails = (List<EmployeeView>) objectTask;
		employeeDetails.forEach(employeeDetail -> {
			scheduleRequestVO.setEmployeeName(employeeDetail.getEmployeeFirstName() + " " + employeeDetail.getEmployeeLastName());
			scheduleRequestVO.setEmployeeEmail(employeeDetail.getOffEmailID());
			scheduleRequestVO.setEmployeePhone(employeeDetail.getEmployeeMobile());
			scheduleRequestVO.setEmployeeId(employeeDetail.getEmployeeId());
			scheduleRequestVO.setManagerId(employeeDetail.getManagerID());
			scheduleRequestVO.setManagerEmail(employeeDetail.getManagerEmail());
			scheduleRequestVO
					.setManagerName(employeeDetail.getManagerFirstName() + " " + employeeDetail.getManagerLastName());
			scheduleRequestVO.setGlobalEmployeeId(employeeDetail.getGlobalEmpId());
		});
	}

	private void setEmployeeDetails(ScheduleRequestVO scheduleRequestVO) {
		List<EmployeeProjectManagerSQLMapper> projectApproverVO = customQueryService
				.getEmployeeProjectManager(scheduleRequestVO.getRequestId());
		projectApproverVO.forEach(projectApprover -> {
			scheduleRequestVO
					.setEmployeeName(projectApprover.getEmpFirstName() + " " + projectApprover.getEmpLastName());
			scheduleRequestVO.setEmployeeEmail(projectApprover.getEmpOffEmailID());
			scheduleRequestVO.setEmployeePhone(projectApprover.getEmpMobile());
			scheduleRequestVO.setEmployeeId(projectApprover.getEmployeeId());
			scheduleRequestVO.setGlobalEmployeeId(projectApprover.getEmpGlobalEmpId());
			scheduleRequestVO.setManagerId(projectApprover.getMgrGlobalEmpId());
			scheduleRequestVO.setManagerEmail(projectApprover.getMgrOffEmailID());
			scheduleRequestVO
					.setManagerName(projectApprover.getMgrFirstName() + " " + projectApprover.getMgrLastName());
		});
	}

	private ScheduleRequestVO setEmployeeLandmark(String employeeId, ScheduleRequestVO scheduleRequestVO) {
		List<EmployeeLandmarkSQLMapper> employeeLandmarkList = customQueryDAO.getEmployeeLandmarkDetails(employeeId);

		employeeLandmarkList.forEach(employeeLandmark -> {
			scheduleRequestVO.setEmployeeSelectedLandmark(employeeLandmark.getEmployeeLandmarkName());
			scheduleRequestVO.setEmployeeSelectedLandmarkId(employeeLandmark.getEmployeeLandmarkId());
		});

		return scheduleRequestVO;
	}

	@Override
	public String cancelSelectedScheduleRequest(ScheduleRequestVO scheduleRequestVO) {
		logger.info("cancelSelectedScheduleRequest() -> Cancel Selected Schedule Request Service call...");
		return employeeScheduleRequestDAO.cancelSelectedScheduleRequest(scheduleRequestVO);
	}
 
}
