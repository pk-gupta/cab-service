package com.marlabs.cab.service.domain.command.receiver;

import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.marlabs.cab.service.common.exception.DuplicateRequestException;
import com.marlabs.cab.service.common.exception.RequestTimeDifferenceException;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.persistance.entity.EmployeeExistingRequestView;
import com.marlabs.cab.service.persistance.marker.InputDataMarker;
import com.marlabs.cab.service.persistance.vo.ScheduleRequestVO;
import com.marlabs.cab.service.persistance.vo.ScheduleServiceDay;
import com.marlabs.cab.service.persistance.vo.SingleRequestVO;

public class DropProcessor implements CabServiceRequest {
	
	private static Logger logger = LogManager.getLogger(DropProcessor.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void drop(InputDataMarker inputDataProvider, List<?> dbData) {
		
		SingleRequestVO inputSingleRequest;
		ScheduleRequestVO inputScheduleRequest;
		
		logger.info("Validating DROP Request...");
		
		List<EmployeeExistingRequestView> existingRequests = (List<EmployeeExistingRequestView>)dbData;
		
		if(inputDataProvider instanceof ScheduleRequestVO){
			inputScheduleRequest = (ScheduleRequestVO)inputDataProvider;
			//checkForScheduleRequestCount(inputScheduleRequest, existingRequests);
			checkForScheduleRequestDuplicate(inputScheduleRequest, existingRequests);
			checkForScheduleRequestTimeDifference(inputScheduleRequest, existingRequests);
		}else {
			inputSingleRequest = (SingleRequestVO)inputDataProvider;
			//checkForSingleRequestCount(existingRequests);
			checkForSingleRequestDuplicate(inputSingleRequest, existingRequests);
			checkForSingleRequestTimeDifference(inputSingleRequest, existingRequests);
		}
	}
	
	@Override
	public void pickup(InputDataMarker inputDataProvider, List<?> dbData) {
		throw new UnsupportedOperationException();
	}
	
	private void checkForSingleRequestDuplicate(SingleRequestVO inputRequests, List<EmployeeExistingRequestView> existingRequests){
		existingRequests.forEach(existing -> { 
			//if(inputRequests.getServiceDate().equals(existing.getServiceDate())
			if(DateUtils.isSameDay(inputRequests.getServiceDate(), existing.getServiceDate())
					&& inputRequests.getEmployeeSelectedLogout().equals(existing.getLogoutTime())){
				
				throw new DuplicateRequestException("Duplicate Drop request for Date/Time - " + CabServiceUtil.formatDate(existing.getServiceDate()) + "/" + existing.getLogoutTime() + ". [ Request Id - " + existing.getReqHeaderId() + " ].");
			}
		});
	}
	
	private void checkForSingleRequestTimeDifference(SingleRequestVO inputRequests, List<EmployeeExistingRequestView> existingRequests){
		existingRequests.forEach(existing -> {
			//if(existing.getServiceDate().equals(inputRequests.getServiceDate())){
			if(DateUtils.isSameDay(existing.getServiceDate(), inputRequests.getServiceDate())){
				String currentTime = inputRequests.getEmployeeSelectedLogout();
				String existingTime = existing.getLogoutTime(); 
				int timeDifference = Integer.parseInt(currentTime.replaceAll(":", ""))/100 - Integer.parseInt(existingTime.replaceAll(":", ""))/100;
				
				if(Math.abs(timeDifference) < 4){
					throw new RequestTimeDifferenceException("Drop request for " + CabServiceUtil.formatDate(existing.getServiceDate()) + " exists in Request Id - " + existing.getReqHeaderId() + ". Time difference for next request should be 4 Hrs/greater than 4 Hrs.");
				}
			}
		});
	}
	
	private void checkForSingleRequestCount(List<EmployeeExistingRequestView> existingRequests){
		if(existingRequests.size() >= 2){
			throw new RequestTimeDifferenceException("Exceeded 2 Drop requests for date - " + CabServiceUtil.formatDate(existingRequests.get(0).getServiceDate()));
		}
	}
	
	private void checkForScheduleRequestDuplicate(ScheduleRequestVO inputRequests, List<EmployeeExistingRequestView> existingRequests){
		List<ScheduleServiceDay> currentRequests = inputRequests.getScheduleServiceDays();
		
		currentRequests.forEach(currentReq -> {
			SingleRequestVO request = new SingleRequestVO();
			request.setServiceDate(currentReq.getServiceDate());
			request.setEmployeeSelectedLogout(inputRequests.getEmployeeSelectedLogout());
			
			checkForSingleRequestDuplicate(request, existingRequests);
		});
	}
	
	private void checkForScheduleRequestTimeDifference(ScheduleRequestVO inputRequests, List<EmployeeExistingRequestView> existingRequests){
		List<ScheduleServiceDay> currentRequests = inputRequests.getScheduleServiceDays();
		
		currentRequests.forEach(currentReq -> {
			SingleRequestVO request = new SingleRequestVO();
			request.setServiceDate(currentReq.getServiceDate());
			request.setEmployeeSelectedLogout(inputRequests.getEmployeeSelectedLogout());
			
			checkForSingleRequestTimeDifference(request, existingRequests);
		});
	}
	
	private void checkForScheduleRequestCount(ScheduleRequestVO inputRequests, List<EmployeeExistingRequestView> existingRequests){
		List<ScheduleServiceDay> currentRequests = inputRequests.getScheduleServiceDays();
		int requestCount;
		long[] headerIdArray;
		
		for(ScheduleServiceDay cRequestDate : currentRequests){
			requestCount = 0;
			headerIdArray = new long[3];
			for(EmployeeExistingRequestView eRequestDate : existingRequests){
				//if(cRequestDate.getServiceDate().equals(eRequestDate.getServiceDate())){
				if(DateUtils.isSameDay(cRequestDate.getServiceDate(), eRequestDate.getServiceDate())){
					headerIdArray[requestCount] = eRequestDate.getReqHeaderId();
					requestCount++;
				}
				
				if(requestCount >= 2){
					throw new RequestTimeDifferenceException("Exceeded 2 Drop requests for date - " + CabServiceUtil.formatDate(cRequestDate.getServiceDate()) + " [Request Id(s) - " + headerIdArray[0] + ", " + headerIdArray[1] + "]");
				}
			}
		}
	}

}
