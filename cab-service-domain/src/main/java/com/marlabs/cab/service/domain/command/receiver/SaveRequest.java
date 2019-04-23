package com.marlabs.cab.service.domain.command.receiver;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.time.DateUtils;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.domain.command.ServiceTypeValidatorCommand;
import com.marlabs.cab.service.domain.command.invoker.CabServiceRequestManager;
import com.marlabs.cab.service.domain.criteria.RequestCRUDCriteria;
import com.marlabs.cab.service.domain.criteria.impl.RequestCreateCriteria;
import com.marlabs.cab.service.domain.criteria.impl.RequestDeleteCriteria;
import com.marlabs.cab.service.domain.criteria.impl.RequestUpdateCriteria;
import com.marlabs.cab.service.domain.factory.ServiceTypeCommandFactory;
import com.marlabs.cab.service.domain.factory.ServiceTypeFactory;
import com.marlabs.cab.service.persistance.employee.dao.EmployeeScheduleRequestDAO;
import com.marlabs.cab.service.persistance.employee.dao.EmployeeSingleRequestDAO;
import com.marlabs.cab.service.persistance.entity.EmployeeExistingRequestView;
import com.marlabs.cab.service.persistance.entity.EmployeeLandmarkEntity;
import com.marlabs.cab.service.persistance.entity.OfficeBranchEntity;
import com.marlabs.cab.service.persistance.entity.ReasonEntity;
import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;
import com.marlabs.cab.service.persistance.marker.InputDataMarker;
import com.marlabs.cab.service.persistance.vo.ProcessNewRequestVO;
import com.marlabs.cab.service.persistance.vo.ScheduleRequestVO;
import com.marlabs.cab.service.persistance.vo.ScheduleServiceDay;
import com.marlabs.cab.service.persistance.vo.SingleRequestVO;

public class SaveRequest implements ProcessRequest {

	@Override
	public ProcessNewRequestVO save(InputDataMarker inputDataProvider, Object persistance) {
		
		ProcessNewRequestVO savedResult = null;
		
		if(inputDataProvider instanceof ScheduleRequestVO){
			savedResult = saveScheduleRequest((ScheduleRequestVO)inputDataProvider, (EmployeeScheduleRequestDAO)persistance);
		}else{
			savedResult = saveSingleRequest((SingleRequestVO)inputDataProvider, (EmployeeSingleRequestDAO)persistance);
		}
		
		return savedResult;
	}

	@Override
	public ProcessNewRequestVO submit(InputDataMarker inputDataProvider, Object persistance) {
		throw new UnsupportedOperationException();
	}
	
	private ProcessNewRequestVO saveSingleRequest(SingleRequestVO singleRequestVO, EmployeeSingleRequestDAO employeeSingleRequestDAO){
		if(isNewRequest(singleRequestVO)){
			performSingleRequestValidation(singleRequestVO, employeeSingleRequestDAO);
			return createNewSingleRequest(singleRequestVO, employeeSingleRequestDAO);
		}else{
			return updateExistingSingleRequest(singleRequestVO, employeeSingleRequestDAO);
		}
	}
	
	private ProcessNewRequestVO saveScheduleRequest(ScheduleRequestVO scheduleRequestVO, EmployeeScheduleRequestDAO employeeScheduleRequestDAO){
		if(isNewRequest(scheduleRequestVO)){
			scheduleRequestVO.setEmployeeServiceType(scheduleRequestVO.getRequestServiceType());// Remove this once EmployeeServiceType is set from UI
			performScheduleRequestValidation(scheduleRequestVO, employeeScheduleRequestDAO);
			return createNewScheduleRequest(scheduleRequestVO, employeeScheduleRequestDAO);
		}else{
			return updateExistingScheduleRequest(scheduleRequestVO, employeeScheduleRequestDAO);
		}
	}

	private boolean isNewRequest(SingleRequestVO singleRequestVO) {
		return singleRequestVO.getRequestId() == 0;
	}
	
	private ProcessNewRequestVO createNewSingleRequest(SingleRequestVO inputRequestData, EmployeeSingleRequestDAO employeeSingleRequestDAO){
		RequestHeaderEntity requestHeader = setSingleRequestHeaderEntity(inputRequestData);
		requestHeader.setRequestDetailList(setSingleRequestDetailEntries(inputRequestData, requestHeader));
		
		return employeeSingleRequestDAO.processSingleRequest(requestHeader, inputRequestData);
	}
	
	private ProcessNewRequestVO createNewScheduleRequest(ScheduleRequestVO inputRequestData, EmployeeScheduleRequestDAO employeeScheduleRequestDAO){
		RequestHeaderEntity requestHeader = setScheduleRequestHeaderEntity(inputRequestData);
		requestHeader.setRequestDetailList(setScheduleRequestDetailEntries(inputRequestData, requestHeader));
		
		return employeeScheduleRequestDAO.processScheduleRequest(requestHeader, inputRequestData);
	}
	
	private ProcessNewRequestVO updateExistingSingleRequest(SingleRequestVO inputData, EmployeeSingleRequestDAO employeeSingleRequestDAO){
		RequestHeaderEntity inputRequestHeader = setSingleRequestHeaderEntity(inputData);
		List<RequestDetailEntity> transactionReqDetailList = new LinkedList<>();
		
		List<RequestDetailEntity> newRequestDetails = getSingleRequestDetails(inputData, inputRequestHeader);
		List<RequestDetailEntity> existingRequestDetails = employeeSingleRequestDAO.getRequestDetailWithHeader(inputData.getRequestId());
		
		RequestCRUDCriteria updateCriteriaList = new RequestUpdateCriteria();
		List<RequestDetailEntity> updateRequestDetailsList = updateCriteriaList.meetCriteria(newRequestDetails, existingRequestDetails);
		if(!updateRequestDetailsList.isEmpty()){
			transactionReqDetailList.addAll(updateRequestDetailsList);
			removeItems(newRequestDetails, updateRequestDetailsList);
			removeItems(existingRequestDetails, updateRequestDetailsList);
		}
		
		RequestCRUDCriteria createCriteriaList = new RequestCreateCriteria();
		List<RequestDetailEntity> addRequestDetailsList = createCriteriaList.meetCriteria(newRequestDetails, existingRequestDetails);
		if(!addRequestDetailsList.isEmpty()){
			addRequestDetailsList.forEach(requestDetail -> requestDetail.setRequestHeader(inputRequestHeader));
			transactionReqDetailList.addAll(addRequestDetailsList);
			removeItems(newRequestDetails, addRequestDetailsList);
			removeItems(existingRequestDetails, addRequestDetailsList);
		}
		
		RequestCRUDCriteria deleteCriteriaList = new RequestCreateCriteria();
		List<RequestDetailEntity> deleteRequestDetailsList = deleteCriteriaList.meetCriteria(newRequestDetails, existingRequestDetails);
		if(!deleteRequestDetailsList.isEmpty()){
			deleteRequestDetailsList.forEach(requestDetail -> employeeSingleRequestDAO.deleteRequestDetail(requestDetail.getRequestDetailId()));
			transactionReqDetailList.addAll(deleteRequestDetailsList);
			removeItems(newRequestDetails, deleteRequestDetailsList);
			removeItems(existingRequestDetails, deleteRequestDetailsList);
		}
		
		if(!newRequestDetails.isEmpty() && existingRequestDetails.isEmpty()){
			//add
			newRequestDetails.forEach(requestDetail -> requestDetail.setRequestHeader(inputRequestHeader));
			transactionReqDetailList.addAll(newRequestDetails);
		}
		
		if(newRequestDetails.isEmpty() && !existingRequestDetails.isEmpty()){
			//delete
			existingRequestDetails.forEach(requestDetail -> employeeSingleRequestDAO.deleteRequestDetail(requestDetail.getRequestDetailId()));
		}
		
		inputRequestHeader.setRequestDetailList(transactionReqDetailList);
		
		return employeeSingleRequestDAO.processSingleRequest(inputRequestHeader, inputData);
	}
	
	private void removeItems(List<RequestDetailEntity> removeFromList, List<RequestDetailEntity> itemsToRemove){
		for(RequestDetailEntity removeReqDetail : itemsToRemove){
			for(RequestDetailEntity reqDetail : removeFromList){
				if(DateUtils.isSameDay(reqDetail.getServiceDate(), removeReqDetail.getServiceDate()) 
						&& reqDetail.getServiceType().equals(removeReqDetail.getServiceType())){
					removeFromList.remove(reqDetail);
					break;
				}
			}
		}
	}
	
	private ProcessNewRequestVO updateExistingScheduleRequest(ScheduleRequestVO inputData, EmployeeScheduleRequestDAO employeeScheduleRequestDAO){
		RequestHeaderEntity inputRequestHeader = setScheduleRequestHeaderEntity(inputData);
		inputRequestHeader.setRequestHeaderId(inputData.getRequestId());
		
		List<RequestDetailEntity> transactionReqDetailList = new LinkedList<>();
		
		List<RequestDetailEntity> newRequestDetails = setScheduleRequestDetailEntries(inputData, inputRequestHeader);
		List<RequestDetailEntity> existingRequestDetails = employeeScheduleRequestDAO.getRequestDetailWithHeader(inputData.getRequestId());
		
		RequestCRUDCriteria updateCriteriaList = new RequestUpdateCriteria();
		List<RequestDetailEntity> updateRequestDetailsList = updateCriteriaList.meetCriteria(newRequestDetails, existingRequestDetails);
		if(!updateRequestDetailsList.isEmpty()){
			transactionReqDetailList.addAll(updateRequestDetailsList);
			removeItems(newRequestDetails, updateRequestDetailsList);
			removeItems(existingRequestDetails, updateRequestDetailsList);
		}
		
		RequestCRUDCriteria createCriteriaList = new RequestCreateCriteria();
		List<RequestDetailEntity> addRequestDetailsList = createCriteriaList.meetCriteria(newRequestDetails, existingRequestDetails);
		if(!addRequestDetailsList.isEmpty()){
			addRequestDetailsList.forEach(requestDetail -> requestDetail.setRequestHeader(inputRequestHeader));
			transactionReqDetailList.addAll(addRequestDetailsList);
			removeItems(newRequestDetails, addRequestDetailsList);
			removeItems(existingRequestDetails, addRequestDetailsList);
		}
		
		RequestCRUDCriteria deleteCriteriaList = new RequestCreateCriteria();
		List<RequestDetailEntity> deleteRequestDetailsList = deleteCriteriaList.meetCriteria(newRequestDetails, existingRequestDetails);
		if(!deleteRequestDetailsList.isEmpty()){
			deleteRequestDetailsList.forEach(requestDetail -> employeeScheduleRequestDAO.deleteRequestDetail(requestDetail.getRequestDetailId()));
			transactionReqDetailList.addAll(deleteRequestDetailsList);
			removeItems(newRequestDetails, deleteRequestDetailsList);
			removeItems(existingRequestDetails, deleteRequestDetailsList);
		}
		
		if(!newRequestDetails.isEmpty() && existingRequestDetails.isEmpty()){
			//add
			newRequestDetails.forEach(requestDetail -> requestDetail.setRequestHeader(inputRequestHeader));
			transactionReqDetailList.addAll(newRequestDetails);
		}
		
		if(newRequestDetails.isEmpty() && !existingRequestDetails.isEmpty()){
			//delete
			existingRequestDetails.forEach(requestDetail -> employeeScheduleRequestDAO.deleteRequestDetail(requestDetail.getRequestDetailId()));
		}
		
		inputRequestHeader.setRequestDetailList(transactionReqDetailList);
		
		return employeeScheduleRequestDAO.processScheduleRequest(inputRequestHeader, inputData);
	}

	private void performSingleRequestValidation(SingleRequestVO singleRequestVO, EmployeeSingleRequestDAO employeeSingleRequestDAO) {
		if(singleRequestVO.getEmployeeServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_PICKUP_DROP)){
			//Validate for Pick-Up
			singleRequestVO.setEmployeeServiceType(Constants.SERVICE_TYPE_PICKUP);
			validateSingleRequests(singleRequestVO, employeeSingleRequestDAO);
			
			//Validate for Drop 
			singleRequestVO.setEmployeeServiceType(Constants.SERVICE_TYPE_DROP);
			validateSingleRequests(singleRequestVO, employeeSingleRequestDAO);
			
			//Reset Service Type back to original once validation for Drop & Pickup is completed
			singleRequestVO.setEmployeeServiceType(Constants.SERVICE_TYPE_PICKUP_DROP);
		 }else{
			 validateSingleRequests(singleRequestVO, employeeSingleRequestDAO);
		 }
	}
	
	private void performScheduleRequestValidation(ScheduleRequestVO scheduleRequestVO, EmployeeScheduleRequestDAO employeeScheduleRequestDAO) {
		if(scheduleRequestVO.getEmployeeServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_PICKUP_DROP)){
			//Validate for Pick-Up
			scheduleRequestVO.setEmployeeServiceType(Constants.SERVICE_TYPE_PICKUP);
			validateScheduleRequests(scheduleRequestVO, employeeScheduleRequestDAO);
			
			//Validate for Drop 
			scheduleRequestVO.setEmployeeServiceType(Constants.SERVICE_TYPE_DROP);
			validateScheduleRequests(scheduleRequestVO, employeeScheduleRequestDAO);
			
			//Reset Service Type back to original once validation for Drop & Pickup is completed
			scheduleRequestVO.setEmployeeServiceType(Constants.SERVICE_TYPE_PICKUP_DROP);
		 }else{
			 validateScheduleRequests(scheduleRequestVO, employeeScheduleRequestDAO);
		 }
	}
	
	private void validateScheduleRequests(ScheduleRequestVO scheduleRequestVO, EmployeeScheduleRequestDAO employeeScheduleRequestDAO){
		List<EmployeeExistingRequestView> existingRequest = employeeScheduleRequestDAO.getExistingRequests(scheduleRequestVO);
		Optional<List<EmployeeExistingRequestView>> resultOptional = Optional.ofNullable(existingRequest);
		if(resultOptional.isPresent() && !resultOptional.get().isEmpty()){
			validateSchedule(scheduleRequestVO, existingRequest);
		}
	}
	
	private void validateSingleRequests(SingleRequestVO singleRequestVO, EmployeeSingleRequestDAO employeeSingleRequestDAO){
		List<EmployeeExistingRequestView> existingRequest = employeeSingleRequestDAO.getExistingRequests(singleRequestVO);
		Optional<List<EmployeeExistingRequestView>> resultOptional = Optional.ofNullable(existingRequest);
		if(resultOptional.isPresent() && !resultOptional.get().isEmpty()){
			validateSingle(singleRequestVO, existingRequest);
		}
	}
	
	private void validateSchedule(ScheduleRequestVO scheduleRequestVO, List<EmployeeExistingRequestView> existingRequest) {
		CabServiceRequest request = ServiceTypeFactory.getServiceType(scheduleRequestVO.getEmployeeServiceType());
		ServiceTypeValidatorCommand validatorCommand = ServiceTypeCommandFactory.getServiceType(scheduleRequestVO.getEmployeeServiceType());
		CabServiceRequestManager requestManager = new CabServiceRequestManager(validatorCommand);
		requestManager.performPickupDropValidation(request, scheduleRequestVO, existingRequest);
	}
	
	private void validateSingle(SingleRequestVO singleRequestVO, List<EmployeeExistingRequestView> existingRequest) {
		CabServiceRequest request = ServiceTypeFactory.getServiceType(singleRequestVO.getEmployeeServiceType());
		ServiceTypeValidatorCommand validatorCommand = ServiceTypeCommandFactory.getServiceType(singleRequestVO.getEmployeeServiceType());
		CabServiceRequestManager requestManager = new CabServiceRequestManager(validatorCommand);
		requestManager.performPickupDropValidation(request, singleRequestVO, existingRequest);
	}
	
	
	private RequestHeaderEntity setSingleRequestHeaderEntity(SingleRequestVO singleRequestVO) {
		RequestHeaderEntity requestHeader = new RequestHeaderEntity();

		EmployeeLandmarkEntity employeeLandmarkEntity = new EmployeeLandmarkEntity();
		employeeLandmarkEntity.setEmployeeLandmarkId(singleRequestVO.getEmployeeSelectedLandmarkId());

		ReasonEntity reasonEntity = new ReasonEntity();
		reasonEntity.setReasonId(singleRequestVO.getEmployeeSelectedReasonId());

		OfficeBranchEntity officeBranchEntity = new OfficeBranchEntity();
		officeBranchEntity.setOfficeBranchcode(singleRequestVO.getEmployeeSelectedBranchCode());
		
		requestHeader.setRequestHeaderId(singleRequestVO.getRequestId());

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
		requestHeader.setUpdateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		requestHeader.setApproveStatus(Constants.REQUEST_STATUS_DRAFT);

		return requestHeader;
	}
	
	private RequestHeaderEntity setScheduleRequestHeaderEntity(ScheduleRequestVO scheduleRequestVO) {
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
		requestHeader.setApproveStatus(Constants.REQUEST_STATUS_DRAFT);

		return requestHeader;
	}
	
	private List<RequestDetailEntity> getSingleRequestDetails(SingleRequestVO inputData, RequestHeaderEntity inputRequestHeader){
		return setSingleRequestDetailEntries(inputData,inputRequestHeader);
	}
	
	private List<RequestDetailEntity> setSingleRequestDetailEntries(SingleRequestVO singleRequestVO,
			RequestHeaderEntity requestHeader) {

		List<RequestDetailEntity> requestDetailList = new ArrayList<>();

		if (singleRequestVO.getEmployeeServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_PICKUP_DROP)) {
			// Login should be empty for Drop & Logout should be empty Pick-Up
			requestDetailList.add(getSingleRequestDetail(singleRequestVO, requestHeader, Constants.SERVICE_TYPE_PICKUP));
			requestDetailList.add(getSingleRequestDetail(singleRequestVO, requestHeader, Constants.SERVICE_TYPE_DROP));
		} else {
			requestDetailList
					.add(getSingleRequestDetail(singleRequestVO, requestHeader, singleRequestVO.getEmployeeServiceType()));
		}

		return requestDetailList;
	}
	
	private List<RequestDetailEntity> setScheduleRequestDetailEntries(ScheduleRequestVO scheduleRequestVO,
			RequestHeaderEntity requestHeader) {

		List<RequestDetailEntity> requestDetailList = new ArrayList<>();

		List<ScheduleServiceDay> scheduleServiceDayList = scheduleRequestVO.getScheduleServiceDays();

		scheduleServiceDayList.forEach(day -> {
			if (day.isServiceLogin()) {
				requestDetailList.add(setSceduleRequestDetailEntity(requestHeader, scheduleRequestVO, day.getServiceDate(),
						Constants.SERVICE_TYPE_PICKUP));
			}

			if (day.isServiceLogout()) {
				requestDetailList.add(setSceduleRequestDetailEntity(requestHeader, scheduleRequestVO, day.getServiceDate(),
						Constants.SERVICE_TYPE_DROP));
			}
		});

		return requestDetailList;
	}
	
	private RequestDetailEntity getSingleRequestDetail(SingleRequestVO singleRequestVO, RequestHeaderEntity requestHeader,
			String serviceType) {
		RequestDetailEntity requestDetail = new RequestDetailEntity();

		requestDetail.setRequestHeader(requestHeader);
		requestDetail.setServiceType(serviceType);
		requestDetail.setServiceDate(singleRequestVO.getServiceDate());
		requestDetail.setCreatedBy(singleRequestVO.getGlobalEmployeeId());
		requestDetail.setUpdateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		requestDetail.setUpdatedBy(singleRequestVO.getGlobalEmployeeId()); 
		requestDetail.setIsCancelled(Constants.STATUS_FALSE);

		return requestDetail;
	}
	
	private RequestDetailEntity setSceduleRequestDetailEntity(RequestHeaderEntity requestHeader,
			ScheduleRequestVO scheduleRequestVO, Date scheduleDate, String serviceType) {
		RequestDetailEntity requestDetail = new RequestDetailEntity();
		
		requestDetail.setRequestHeader(requestHeader);
		requestDetail.setServiceType(serviceType);
		requestDetail.setServiceDate(scheduleDate);
		requestDetail.setCreatedBy(scheduleRequestVO.getGlobalEmployeeId());
		requestDetail.setUpdateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		requestDetail.setUpdatedBy(requestHeader.getEmployeeId());
		requestDetail.setIsCancelled(Constants.STATUS_FALSE);

		return requestDetail;
	}

}
