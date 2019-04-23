package com.marlabs.cab.service.persistance.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "trip_history_dashboard_view")
public class TripHistoryDashboardViewEntity {
	
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
	
	@Column(name = "serviceType")
	private String serviceType;
	
	@Column(name = "tripDate")
	private Date tripDate;
	
	@Column(name = "tripTime")
	private String tripTime;
	
	@Column(name = "vehicleNumber")
	private String vehicleNumber;
	
	@Column(name = "driverName")
	private String driverName;
	
	@Column(name = "driverPhone")
	private String driverPhone;
	
	@Column(name = "numberOfSeats")
	private int numberOfSeats;

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

	public Long getTripHeaderId() {
		return tripHeaderId;
	}

	public void setTripHeaderId(Long tripHeaderId) {
		this.tripHeaderId = tripHeaderId;
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

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

}


