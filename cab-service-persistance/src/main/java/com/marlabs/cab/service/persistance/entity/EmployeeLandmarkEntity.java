package com.marlabs.cab.service.persistance.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "EMPLOYEE_LANDMARK")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EmployeeLandmarkEntity implements Serializable {

	private static final long serialVersionUID = -7102701230864963137L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EMPLOYEE_LANDMARK_ID")
	private Long employeeLandmarkId;

	@JsonManagedReference("employeeLandmark_requestHeader")
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "employeeLandmark", fetch = FetchType.EAGER)
	@Column(insertable=false, updatable=false)
	private Set<RequestHeaderEntity> requestHeaderList;
	
	@JsonBackReference(value="officeCity_employeeLandmark")
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "FK_OFFICE_CITY_ID", referencedColumnName = "OFFICE_CITY_ID")
	private OfficeCityEntity officeCity;
	
	@JsonBackReference(value="cityLandmarkMaster_employeeLandmark")
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "FK_CITY_LANDMARK_MASTER_ID", referencedColumnName = "CITY_LANDMARK_MASTER_ID")
	private CityLandmarkMasterEntity cityLandmarkMaster;
	
	@Column(name = "EMPLOYEE_ID")
	private String employeeId;

	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;

	@Column(name = "ACTIVE")
	private String active;

	@Column(name = "IS_DELETED")
	private String isDeleted;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public OfficeCityEntity getOfficeCity() {
		return officeCity;
	}

	public void setOfficeCity(OfficeCityEntity officeCity) {
		this.officeCity = officeCity;
	}

	public CityLandmarkMasterEntity getCityLandmarkMaster() {
		return cityLandmarkMaster;
	}

	public void setCityLandmarkMaster(CityLandmarkMasterEntity cityLandmarkMaster) {
		this.cityLandmarkMaster = cityLandmarkMaster;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public Long getEmployeeLandmarkId() {
		return employeeLandmarkId;
	}

	public void setEmployeeLandmarkId(Long employeeLandmarkId) {
		this.employeeLandmarkId = employeeLandmarkId;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

}
