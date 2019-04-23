package com.marlabs.cab.service.persistance.common.dao;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.CabDetailEntity;
import com.marlabs.cab.service.persistance.entity.CityLandmarkMasterEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeView;
import com.marlabs.cab.service.persistance.entity.TripHeaderEntity;
import com.marlabs.cab.service.persistance.sql.mapper.EmployeeRequestSQLMapper;
import com.marlabs.cab.service.persistance.sql.mapper.PendingRequestSQLMapper;
import com.marlabs.cab.service.persistance.vo.SingleRequestVO;

public interface CabServiceCommonDAO {

	public List<EmployeeView> autoCompleteSearch(String searchText);

	public List<CabDetailEntity> autoCompleteSearchCab(String searchText);
	
	public List<TripHeaderEntity> autoCompleteSearchTripNumber(Long tripHeaderId);
	
	public List<CityLandmarkMasterEntity> getCityLandmarks(Long officeCityId);

	public List<CityLandmarkMasterEntity> getBranchLandmarks(String officeBranchCode);
	
	public List<EmployeeRequestSQLMapper> requestDetails(Long requestHeaderId);

	public EmployeeView getProjectApprover(SingleRequestVO singleRequestVO);
	
	public List<PendingRequestSQLMapper> restrictMultipleCreateRequest(String employeeId);
}
