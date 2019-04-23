package com.marlabs.cab.service.domain.landmark.service;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.EmployeeLandmarkEntity;
import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;
import com.marlabs.cab.service.persistance.vo.EmployeeLandmarkVO;

public interface EmployeeLandmarkService {

	public EmployeeLandmarkEntity processEmployeeLandmarkRequest(EmployeeLandmarkVO employeelandmarkVO);

	public List<RequestDetailEntity> updateEmployeeLandmarkStatus(EmployeeLandmarkVO employeelandmarkVO);

	public String updateEmployeeLandmarkRequest(EmployeeLandmarkVO employeelandmarkVO);

	public EmployeeLandmarkVO employeeLandmarkRequestDashboard();
	
	public String deleteEmployeeLandmark(long employeeLandmarkId);

}
