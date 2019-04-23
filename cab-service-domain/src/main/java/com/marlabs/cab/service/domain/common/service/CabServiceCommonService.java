package com.marlabs.cab.service.domain.common.service;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.CabDetailEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeView;
import com.marlabs.cab.service.persistance.sql.mapper.EmployeeRequestSQLMapper;
import com.marlabs.cab.service.persistance.sql.mapper.PendingRequestSQLMapper;
import com.marlabs.cab.service.persistance.vo.AutoCompleteSearchTripVO;
import com.marlabs.cab.service.persistance.vo.CityLandmarkMasterVO;
import com.marlabs.cab.service.persistance.vo.SingleRequestVO;

public interface CabServiceCommonService {
	
	public List<EmployeeView> autoCompleteSearch(String searchText);
	
	public List<CabDetailEntity> autoCompleteSearchCab(String searchText);
	
	public AutoCompleteSearchTripVO autoCompleteSearchTripNumber(Long tripHeaderId);
	
	public CityLandmarkMasterVO getCityLandmarks(Long officeCityId);
	
	public CityLandmarkMasterVO getBranchLandmarks(String officeBranchCode);
	
	public EmployeeView getProjectApprover(SingleRequestVO singleRequestVO);

	public List<EmployeeRequestSQLMapper> requestDetails(Long requestHeaderId);
	
	public List<PendingRequestSQLMapper> restrictMultipleCreateRequest(String employeeId);
	
	
}
