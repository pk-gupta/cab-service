package com.marlabs.cab.service.persistance.vo;

import java.sql.Date;
import java.util.List;

public class TripPrintVO {

	private Long tripHeaderId;
	private Date tripDate;
	private String vehicleNo;
	private String serviceType;
	private String driverFirstName;
	private String driverMiddletName;
	private String driverLastName;
	private String tripTime;
	private String officeBranchName;
	private String officeCityName;
	private String driverMobileNumber;
	
	List<EmployeeTripDetailsVO> employeeTripDetails;

	public Long getTripHeaderId() {
		return tripHeaderId;
	}

	public void setTripHeaderId(Long tripHeaderId) {
		this.tripHeaderId = tripHeaderId;
	}

	public Date getTripDate() {
		return tripDate;
	}

	public void setTripDate(Date tripDate) {
		this.tripDate = tripDate;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getDriverFirstName() {
		return driverFirstName;
	}

	public void setDriverFirstName(String driverFirstName) {
		this.driverFirstName = driverFirstName;
	}

	public String getDriverMiddletName() {
		return driverMiddletName;
	}

	public void setDriverMiddletName(String driverMiddletName) {
		this.driverMiddletName = driverMiddletName;
	}

	public String getDriverLastName() {
		return driverLastName;
	}

	public void setDriverLastName(String driverLastName) {
		this.driverLastName = driverLastName;
	}

	public String getTripTime() {
		return tripTime;
	}

	public void setTripTime(String tripTime) {
		this.tripTime = tripTime;
	}

	public String getOfficeBranchName() {
		return officeBranchName;
	}

	public void setOfficeBranchName(String officeBranchName) {
		this.officeBranchName = officeBranchName;
	}

	public String getDriverMobileNumber() {
		return driverMobileNumber;
	}

	public void setDriverMobileNumber(String driverMobileNumber) {
		this.driverMobileNumber = driverMobileNumber;
	}

	public List<EmployeeTripDetailsVO> getEmployeeTripDetails() {
		return employeeTripDetails;
	}

	public String getOfficeCityName() {
		return officeCityName;
	}

	public void setOfficeCityName(String officeCityName) {
		this.officeCityName = officeCityName;
	}

	public void setEmployeeTripDetails(List<EmployeeTripDetailsVO> employeeTripDetails) {
		this.employeeTripDetails = employeeTripDetails;
	}
	
}
