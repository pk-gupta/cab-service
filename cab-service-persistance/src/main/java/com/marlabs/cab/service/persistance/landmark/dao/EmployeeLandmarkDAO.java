package com.marlabs.cab.service.persistance.landmark.dao;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.EmployeeLandmardDashboardEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeLandmarkEntity;
import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;
import com.marlabs.cab.service.persistance.vo.EmployeeLandmarkVO;

public interface EmployeeLandmarkDAO {

	public EmployeeLandmarkEntity processEmployeeLandmarkRequest(EmployeeLandmarkEntity landmark);

	public List<RequestDetailEntity> updateEmployeeLandmarkStatus(EmployeeLandmarkVO employeelandmarkVO);

	public String updateEmployeeLandmarkRequest(EmployeeLandmarkVO employeelandmarkVO);

	public List<EmployeeLandmardDashboardEntity> employeeLandmarkRequestDashboard();
	
	public String deleteEmployeeLandmark(long employeeLandmarkId);
	
	public boolean isEmployeeLandmarkExists(String employeeId);

}
