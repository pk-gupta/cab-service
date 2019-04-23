package com.marlabs.cab.service.persistance.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "trip_service_availed_dashboard_view")
public class TripServiceAvailedDashboardViewEntity implements Serializable{
	
	private static final long serialVersionUID = 160598314821948657L;

	@Id
	@Column(name = "employeeId")
	private String employeeId;
	
	@Column(name = "empFirstName")
	private String empFirstName;
	
	@Column(name = "empMiddleName")
	private String empMiddleName;
	
	@Column(name = "empLastName")
	private String empLastName;
	
	@Column(name = "empAddress1")
	private String empAddress1;
	
	@Column(name = "empAddress2")
	private String empAddress2;
	
	@Column(name = "empAddress3")
	private String empAddress3;
	
	@Column(name = "cityLandmarkId")
	private Long cityLandmarkId;
	
	@Column(name = "cityLandmarkName")
	private String cityLandmarkName;
	
	@Column(name = "officeCityId")
	private Long officeCityId;

	@Column(name = "officeCityName")
	private String officeCityName;
	
	@Column(name = "officeBranchCode")
	private String officeBranchCode;
	
	@Column(name = "officeBranchName")
	private String officeBranchName;
	
	@Id
	@Column(name = "tripHeaderId")
	private Long tripHeaderId;
	
	@Column(name = "estimatedTime")
	private String estimatedTime;
	
	@Column(name = "reqDetailId")
	private Long reqDetailId;
	
	@Column(name = "serviceType")
	private String serviceType;
	
	@Column(name = "tripDate")
	private Date tripDate;
	
	@Column(name = "tripTime")
	private String tripTime;
	
	@Column(name = "tripStartTime")
	private String tripStartTime;
	
	@Column(name = "tripEndTime")
	private String tripEndTime;
	
	@Column(name = "vehicleNumber")
	private String vehicleNumber;
	
	@Column(name = "driverName")
	private String driverName;
	
	@Column(name = "actualTime")
	private String actualTime;
	
	@Column(name = "tripAvailedId")
	private Long tripAvailedId;
	
	@Column(name = "isTripAvailed")
	private String isTripAvailed;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmpFirstName() {
		return empFirstName;
	}

	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}

	public String getEmpMiddleName() {
		return empMiddleName;
	}

	public void setEmpMiddleName(String empMiddleName) {
		this.empMiddleName = empMiddleName;
	}

	public String getEmpLastName() {
		return empLastName;
	}

	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}

	public String getEmpAddress1() {
		return empAddress1;
	}

	public void setEmpAddress1(String empAddress1) {
		this.empAddress1 = empAddress1;
	}

	public String getEmpAddress2() {
		return empAddress2;
	}

	public void setEmpAddress2(String empAddress2) {
		this.empAddress2 = empAddress2;
	}

	public String getEmpAddress3() {
		return empAddress3;
	}

	public void setEmpAddress3(String empAddress3) {
		this.empAddress3 = empAddress3;
	}

	public Long getCityLandmarkId() {
		return cityLandmarkId;
	}

	public void setCityLandmarkId(Long cityLandmarkId) {
		this.cityLandmarkId = cityLandmarkId;
	}

	public String getCityLandmarkName() {
		return cityLandmarkName;
	}

	public void setCityLandmarkName(String cityLandmarkName) {
		this.cityLandmarkName = cityLandmarkName;
	}

	public Long getOfficeCityId() {
		return officeCityId;
	}

	public void setOfficeCityId(Long officeCityId) {
		this.officeCityId = officeCityId;
	}

	public String getOfficeCityName() {
		return officeCityName;
	}

	public void setOfficeCityName(String officeCityName) {
		this.officeCityName = officeCityName;
	}

	public String getOfficeBranchCode() {
		return officeBranchCode;
	}

	public void setOfficeBranchCode(String officeBranchCode) {
		this.officeBranchCode = officeBranchCode;
	}

	public String getOfficeBranchName() {
		return officeBranchName;
	}

	public void setOfficeBranchName(String officeBranchName) {
		this.officeBranchName = officeBranchName;
	}
	
	public String getActualTime() {
		return actualTime;
	}

	public void setActualTime(String actualTime) {
		this.actualTime = actualTime;
	}

	public Long getTripHeaderId() {
		return tripHeaderId;
	}

	public void setTripHeaderId(Long tripHeaderId) {
		this.tripHeaderId = tripHeaderId;
	}

	public Long getReqDetailId() {
		return reqDetailId;
	}

	public void setReqDetailId(Long reqDetailId) {
		this.reqDetailId = reqDetailId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Date getTripDate() {
		return tripDate;
	}

	public void setTripDate(Date tripDate) {
		this.tripDate = tripDate;
	}

	public String getTripTime() {
		return tripTime;
	}

	public void setTripTime(String tripTime) {
		this.tripTime = tripTime;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public Long getTripAvailedId() {
		return tripAvailedId;
	}

	public void setTripAvailedId(Long tripAvailedId) {
		this.tripAvailedId = tripAvailedId;
	}

	public String getIsTripAvailed() {
		return isTripAvailed;
	}

	public void setIsTripAvailed(String isTripAvailed) {
		this.isTripAvailed = isTripAvailed;
	}

	public String getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public String getTripStartTime() {
		return tripStartTime;
	}

	public void setTripStartTime(String tripStartTime) {
		this.tripStartTime = tripStartTime;
	}

	public String getTripEndTime() {
		return tripEndTime;
	}

	public void setTripEndTime(String tripEndTime) {
		this.tripEndTime = tripEndTime;
	}
	
}

