package com.marlabs.cab.service.domain.employee.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import com.marlabs.cab.service.domain.employee.service.EmployeeSingleRequestService;
import com.marlabs.cab.service.domain.factory.ProcessRequestCommandFactory;
import com.marlabs.cab.service.domain.factory.ProcessRequestFactory;
import com.marlabs.cab.service.domain.factory.ServiceTypeCommandFactory;
import com.marlabs.cab.service.domain.factory.ServiceTypeFactory;
import com.marlabs.cab.service.domain.master.data.service.MasterDataService;
import com.marlabs.cab.service.domain.parallel.activity.service.ParallelActivityService;
import com.marlabs.cab.service.persistance.common.dao.CustomQueryDAO;
import com.marlabs.cab.service.persistance.employee.dao.EmployeeSingleRequestDAO;
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
import com.marlabs.cab.service.persistance.vo.SingleRequestVO;

@Service
public class EmployeeSingleRequestServiceImpl implements EmployeeSingleRequestService {

	private static Logger logger = LogManager.getLogger(EmployeeSingleRequestServiceImpl.class);

	@Autowired
	private EmployeeSingleRequestDAO employeeSingleRequestDAO;

	@Autowired
	private MasterDataService masterDataService;

	@Autowired
	private ParallelActivityService parallelActivityService;

	@Autowired
	CustomQueryDAO customQueryDAO;

	@Autowired
	CustomQueryService customQueryService;

	@Autowired
	Environment env;
		
	@Override
	public List<EmployeeManagerDashboardEntity> singleRequestDashboard(String employeeId) {
		logger.info("singleRequestDashboard() -> Single Request Dashboard Service call...");

		return employeeSingleRequestDAO.singleRequestDashboard(employeeId);
	}

	@Override
	public SingleRequestVO createNewSingleRequest(String employeeId) {
		logger.info("createNewSingleRequest() -> Create New Single Request Service call...");

		SingleRequestVO singleRequestVO = new ScheduleRequestVO();

		singleRequestVO = setDropDownAndEmployeeDetails(singleRequestVO, employeeId, 0l, null, "create");

		return singleRequestVO;
	}

	@Override
	public ProcessNewRequestVO processSingleRequest(SingleRequestVO singleRequestVO, String userAction) {
		logger.info("processSingleRequest() -> New Single Request Service call...");
		
		/*if(singleRequestVO.getEmployeeServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_PICKUP_DROP)){
			//Validate for Pick-Up
			singleRequestVO.setEmployeeServiceType(Constants.SERVICE_TYPE_PICKUP);
			validateRequests(singleRequestVO);
			
			//Validate for Drop 
			singleRequestVO.setEmployeeServiceType(Constants.SERVICE_TYPE_DROP);
			validateRequests(singleRequestVO);
		 }else{
			validateRequests(singleRequestVO);
		 }*/

		/*
		 * if(singleRequestVO.getRequestId()!=0){
		 * employeeSingleRequestDAO.deleteRequestDetails(singleRequestVO.
		 * getRequestId()); }
		 */

		/*RequestHeaderEntity requestHeader = setRequestHeaderEntity(singleRequestVO);
		requestHeader.setRequestHeaderId(singleRequestVO.getRequestId());
		
		if (userAction.equalsIgnoreCase(Constants.REQUEST_ACTION_SAVE)) {
			requestHeader.setApproveStatus(Constants.REQUEST_STATUS_DRAFT);
		} else {
			requestHeader.setApproveStatus(Constants.REQUEST_STATUS_APPROVAL_PENDING);
		}

		requestHeader.setRequestDetailList(setRequestDetailEntries(singleRequestVO, requestHeader));

		List<RequestDetailEntity> requestDetailList1 = singleRequestVO.getRequestDetailEntity();

		Integer count = 0;

		if (!CabServiceUtil.isNULL(requestDetailList1)) {

			for (RequestDetailEntity requestDetailList : requestHeader.getRequestDetailList()) {

				if (count < requestDetailList1.size()
						&& requestDetailList.getServiceType().equals(requestDetailList1.get(count).getServiceType())
						|| requestDetailList1.size() == requestHeader.getRequestDetailList().size()) {

					requestDetailList.setRequestDetailId(requestDetailList1.get(count++).getRequestDetailId());

				} else if (requestDetailList1.size() > requestHeader.getRequestDetailList().size()) {

					requestDetailList.setRequestDetailId(requestDetailList1.get(++count).getRequestDetailId());
				}
			}
		}

		return employeeSingleRequestDAO.processSingleRequest(requestHeader, singleRequestVO);*/
		
		ProcessNewRequestVO actionResult;
		
		if (userAction.equalsIgnoreCase(Constants.REQUEST_ACTION_SAVE)) {
			ProcessRequest processRequest = ProcessRequestFactory.getProcessRequest(Constants.REQUEST_ACTION_SAVE);
			ProcessRequestCommand processRequestCommand = ProcessRequestCommandFactory.getProcessCommand(Constants.REQUEST_ACTION_SAVE);
			ProcessRequestManager processRequestManager = new ProcessRequestManager(processRequestCommand);
			actionResult = processRequestManager.performSaveSubmitOperation(singleRequestVO, employeeSingleRequestDAO, processRequest);
			
		}else{
			ProcessRequest processRequest = ProcessRequestFactory.getProcessRequest(Constants.REQUEST_ACTION_SUBMIT);
			ProcessRequestCommand processRequestCommand = ProcessRequestCommandFactory.getProcessCommand(Constants.REQUEST_ACTION_SUBMIT);
			ProcessRequestManager processRequestManager = new ProcessRequestManager(processRequestCommand);
			actionResult = processRequestManager.performSaveSubmitOperation(singleRequestVO, employeeSingleRequestDAO, processRequest);
		}
		
		return actionResult;
	}

	private void validateRequests(SingleRequestVO singleRequestVO){
		List<EmployeeExistingRequestView> existingRequest = employeeSingleRequestDAO.getExistingRequests(singleRequestVO);
		Optional<List<EmployeeExistingRequestView>> resultOptional = Optional.ofNullable(existingRequest);
		if(resultOptional.isPresent() && !resultOptional.get().isEmpty()){
			validate(singleRequestVO, existingRequest);
		}
	}
	
	private void validate(SingleRequestVO singleRequestVO, List<EmployeeExistingRequestView> existingRequest) {
		CabServiceRequest request = ServiceTypeFactory.getServiceType(singleRequestVO.getEmployeeServiceType());
		ServiceTypeValidatorCommand validatorCommand = ServiceTypeCommandFactory.getServiceType(singleRequestVO.getEmployeeServiceType());
		CabServiceRequestManager requestManager = new CabServiceRequestManager(validatorCommand);
		requestManager.performPickupDropValidation(request, singleRequestVO, existingRequest);
	}
	
	private List<RequestDetailEntity> setRequestDetailEntries(SingleRequestVO singleRequestVO,
			RequestHeaderEntity requestHeader) {

		List<RequestDetailEntity> requestDetailList = new ArrayList<>();

		if (singleRequestVO.getEmployeeServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_PICKUP_DROP)) {
			// Login should be empty for Drop & Logout should be empty Pick-Up
			requestDetailList.add(getrequestDetail(singleRequestVO, requestHeader, Constants.SERVICE_TYPE_PICKUP));
			requestDetailList.add(getrequestDetail(singleRequestVO, requestHeader, Constants.SERVICE_TYPE_DROP));
		} else {
			requestDetailList
					.add(getrequestDetail(singleRequestVO, requestHeader, singleRequestVO.getEmployeeServiceType()));
		}

		return requestDetailList;
	}

	private RequestDetailEntity getrequestDetail(SingleRequestVO singleRequestVO, RequestHeaderEntity requestHeader,
			String serviceType) {
		RequestDetailEntity requestDetail = new RequestDetailEntity();

		requestDetail.setRequestHeader(requestHeader);
		requestDetail.setServiceType(serviceType);
		requestDetail.setServiceDate(singleRequestVO.getServiceDate());
		requestDetail.setCreatedBy(singleRequestVO.getGlobalEmployeeId());
		requestDetail.setUpdateDate(new Timestamp(new Date().getTime()));
		requestDetail.setUpdatedBy(singleRequestVO.getGlobalEmployeeId());
		requestDetail.setIsCancelled(Constants.STATUS_FALSE);

		return requestDetail;
	}

	private RequestHeaderEntity setRequestHeaderEntity(SingleRequestVO singleRequestVO) {
		RequestHeaderEntity requestHeader = new RequestHeaderEntity();

		EmployeeLandmarkEntity employeeLandmarkEntity = new EmployeeLandmarkEntity();
		employeeLandmarkEntity.setEmployeeLandmarkId(singleRequestVO.getEmployeeSelectedLandmarkId());

		ReasonEntity reasonEntity = new ReasonEntity();
		reasonEntity.setReasonId(singleRequestVO.getEmployeeSelectedReasonId());

		OfficeBranchEntity officeBranchEntity = new OfficeBranchEntity();
		officeBranchEntity.setOfficeBranchcode(singleRequestVO.getEmployeeSelectedBranchCode());

		requestHeader.setEmployeeLandmark(employeeLandmarkEntity);
		requestHeader.setReason(reasonEntity);
		requestHeader.setOfficeBranch(officeBranchEntity);
		requestHeader.setManagerId(singleRequestVO.getManagerId());
		requestHeader.setEmployeeId(singleRequestVO.getGlobalEmployeeId());
		requestHeader.setProjectId(singleRequestVO.getEmployeeSelectedProjectId());
		requestHeader.setRequestType(Constants.REQUEST_TYPE_SINGLE);
		requestHeader.setEmployeePhone(singleRequestVO.getEmployeePhone());
		requestHeader.setRequestServiceType(singleRequestVO.getEmployeeServiceType());
		requestHeader.setFromDate(singleRequestVO.getServiceDate());
		requestHeader.setToDate(singleRequestVO.getServiceDate());
		requestHeader.setLoginTime(singleRequestVO.getEmployeeSelectedLogin());
		requestHeader.setLogoutTime(singleRequestVO.getEmployeeSelectedLogout());
		requestHeader.setApproveStatus(singleRequestVO.getEmployeeApproveStatus());
		requestHeader.setCreatedBy(singleRequestVO.getGlobalEmployeeId());
		requestHeader.setUpdatedBy(singleRequestVO.getGlobalEmployeeId());
		requestHeader.setEmployeeRemark(singleRequestVO.getEmployeeRemark());
		requestHeader.setIsDeleted("false");
		requestHeader.setIsCancelled("false");
		requestHeader.setUpdateDate(new Timestamp(new Date().getTime()));

		return requestHeader;
	}

	@Override
	public SingleRequestVO editSingleRequest(long requestId) {
		logger.info("editSingleRequest() -> Edit Single Request Service call...");

		List<RequestHeaderEntity> requestheaderList = employeeSingleRequestDAO.editSingleRequest(requestId);
		SingleRequestVO singleRequestVO = null;

		if (!CabServiceUtil.isNULL(requestheaderList)) {
			singleRequestVO = new SingleRequestVO();
			singleRequestVO.setRequestHeaderEntity(requestheaderList);

			RequestHeaderEntity requestHeaderEntity = requestheaderList.get(0);
			singleRequestVO.setEmployeeSelectedBranchCode(requestHeaderEntity.getOfficeBranch().getOfficeBranchcode());
			singleRequestVO.setEmployeeSelectedBranch(requestHeaderEntity.getOfficeBranch().getOfficeBranchName());
			singleRequestVO.setEmployeeSelectedReasonId(requestHeaderEntity.getReason().getReasonId());
			singleRequestVO.setEmployeeSelectedReason(requestHeaderEntity.getReason().getReasonDescription());
			singleRequestVO
					.setEmployeeSelectedLandmarkId(requestHeaderEntity.getEmployeeLandmark().getEmployeeLandmarkId());
			singleRequestVO.setEmployeeSelectedLandmark(
					requestHeaderEntity.getEmployeeLandmark().getCityLandmarkMaster().getCityLandmarkName());

			singleRequestVO.setRequestId(requestId);

			setDropDownAndEmployeeDetails(singleRequestVO, requestHeaderEntity.getEmployeeId(), requestId,
					requestHeaderEntity.getOfficeBranch().getOfficeBranchcode(), "edit");
			// setDropDownAndEmployeeDetails(singleRequestVO,
			// requestheaderList.get(0).getEmployeeId());
		}

		return singleRequestVO;
	}

	@Override
	public String deleteSingleRequest(long requestId) {
		logger.info("deleteSingleRequest() -> Delete Single Request Service call...");
		return employeeSingleRequestDAO.deleteSingleRequest(requestId);
	}

	@Override
	public String cancelSingleRequest(long requestId, String employeeId) {
		logger.info("cancelSingleRequest() -> Cancel Single Request Service call...");
		return employeeSingleRequestDAO.cancelSingleRequest(requestId, employeeId);
	}

	private SingleRequestVO setDropDownAndEmployeeDetails(SingleRequestVO singleRequestVO, String employeeId,Long reuestId, String officeBranchCode, String action) {
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

		dropdownTaskMap.forEach((k, v) -> setSingleRequestVODropdowns(k, v, singleRequestVO, employeeId, officeBranchCode, action));

		return singleRequestVO;
	}

	private void setSingleRequestVODropdowns(String dropdown, Object objectTask, SingleRequestVO singleRequestVO,String employeeId, String officeBranchCode, String action) {
		try {
			switch (dropdown) {
			case "landmark":
				singleRequestVO.setLandMarksList(masterDataService.getLandmarks());
				break;
			case "reasons":
				singleRequestVO.setReasonsList(masterDataService.getReasons());
				break;
			case "branch":
				if ("edit".equals(action)) {
					singleRequestVO
							.setOfficeBranchesList(customQueryService.getCitySpecificOfficeBranches(officeBranchCode));
				} else {
					singleRequestVO.setOfficeBranchesList(customQueryService.getLandmarkSpecificOfficeBranches(employeeId));
				}
				break;
			case "login":
				singleRequestVO.setLoginTimeList(masterDataService.getLoginTime());
				break;
			case "logout":
				singleRequestVO.setLogoutTimeList(masterDataService.getLogoutTime());
				break;
			case "projects":
				setEmployeeProjectList(objectTask, singleRequestVO);
				break;
			case "employeeDetails":
				if ("edit".equals(action)) {
					setEmployeeDetails(singleRequestVO);
				} else {
					setEmployeeDetails(objectTask, singleRequestVO);
				}
				break;
			case "employeeLandmark":
				setEmployeeLandmark(employeeId, singleRequestVO);
				break;
			default:
				throw new IllegalArgumentException();
	
			}
		} catch (InterruptedException | ExecutionException exception) {
			logger.error("setDropDownValues() -> ERROR: " + exception.getCause());
		}
	}

	@SuppressWarnings("unchecked")
	private void setEmployeeProjectList(Object objectTask, SingleRequestVO singleRequestVO){

		List<EmployeeProjectAllocationView> employeeViewList = (List<EmployeeProjectAllocationView>) objectTask;

		List<EmployeeProjectVO> employeeProjectList = new LinkedList<>();

		employeeViewList.forEach(project -> {
			EmployeeProjectVO employeeProject = new EmployeeProjectVO();
			employeeProject.setProjectId(project.getProjectID());
			employeeProject.setProjectName(project.getEmployeeProjectName());
			employeeProjectList.add(employeeProject);

		});

		singleRequestVO.setProjectList(employeeProjectList);
	}

	private void setEmployeeDetails(Object objectTask, SingleRequestVO singleRequestVO)
			throws InterruptedException, ExecutionException {
		 List<EmployeeView> employeeDetails = (List<EmployeeView>) objectTask;
		    employeeDetails.forEach(employeeDetail -> {
			singleRequestVO.setEmployeeName(
			employeeDetail.getEmployeeFirstName() + " " + employeeDetail.getEmployeeLastName());
			singleRequestVO.setEmployeeEmail(employeeDetail.getOffEmailID());
			singleRequestVO.setEmployeePhone(employeeDetail.getEmployeeMobile());
			singleRequestVO.setEmployeeId(employeeDetail.getEmployeeId());
			singleRequestVO.setManagerId(employeeDetail.getManagerID());
			singleRequestVO.setManagerEmail(employeeDetail.getManagerEmail());
			singleRequestVO
					.setManagerName(employeeDetail.getManagerFirstName() + " " + employeeDetail.getManagerLastName());
		});
	}

	private void setEmployeeDetails(SingleRequestVO singleRequestVO) {
		List<EmployeeProjectManagerSQLMapper> projectApproverVO = customQueryService.getEmployeeProjectManager(singleRequestVO.getRequestId());
		projectApproverVO.forEach(projectApprover -> {
			singleRequestVO.setEmployeeName(projectApprover.getEmpFirstName() + " " + projectApprover.getEmpLastName());
			singleRequestVO.setEmployeeEmail(projectApprover.getEmpOffEmailID());
			singleRequestVO.setEmployeePhone(projectApprover.getEmpMobile());
			singleRequestVO.setEmployeeId(projectApprover.getEmployeeId());
			singleRequestVO.setGlobalEmployeeId(projectApprover.getEmpGlobalEmpId());
			singleRequestVO.setManagerId(projectApprover.getMgrGlobalEmpId());
			singleRequestVO.setManagerEmail(projectApprover.getMgrOffEmailID());
			singleRequestVO.setManagerName(projectApprover.getMgrFirstName() + " " + projectApprover.getMgrLastName());
		});
	}

	private SingleRequestVO setEmployeeLandmark(String employeeId, SingleRequestVO singleRequestVO) {
		List<EmployeeLandmarkSQLMapper> employeeLandmarkList = customQueryDAO.getEmployeeLandmarkDetails(employeeId);

		employeeLandmarkList.forEach(employeeLandmark -> {
			singleRequestVO.setEmployeeSelectedLandmark(employeeLandmark.getEmployeeLandmarkName());
			singleRequestVO.setEmployeeSelectedLandmarkId(employeeLandmark.getEmployeeLandmarkId());
		});

		return singleRequestVO;
	}

	@Override
	public String cancelSelectedSingleRequest(SingleRequestVO singleRequestVO) {
		logger.info("cancelSelectedSingleRequest() -> Cancel Selected Single Request Service call...");
		return employeeSingleRequestDAO.cancelSelectedSingleRequest(singleRequestVO);
	}

}
