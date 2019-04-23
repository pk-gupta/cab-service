package com.marlabs.cab.service.persistance.entity;

import java.io.Serializable;
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
@Table(name = "CITY_LANDMARK_MASTER")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CityLandmarkMasterEntity implements Serializable {

	private static final long serialVersionUID = -7102701230864963137L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CITY_LANDMARK_MASTER_ID")
	private Long cityLandmarkMasterId;

/*	@JsonManagedReference("cityLandmarkMaster_employeeLandmark")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cityLandmarkMaster", fetch = FetchType.EAGER)
	@Column(insertable=false, updatable=false)
	private Set<EmployeeLandmarkEntity> employeeLandmarkList;*/
	
	
	@JsonBackReference(value="officeCity_cityLandmarkMaster")
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "FK_OFFICE_CITY_ID", referencedColumnName = "OFFICE_CITY_ID")
	private OfficeCityEntity officeCity;

	@Column(name = "CITY_LANDMARK_NAME")
	private String CityLandmarkName;

	@Column(name = "ACTIVE")
	private String active;

	@Column(name = "IS_DELETED")
	private String isDelated;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updateBy;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;

	public OfficeCityEntity getOfficeCity() {
		return officeCity;
	}

	public void setOfficeCity(OfficeCityEntity officeCity) {
		this.officeCity = officeCity;
	}

	public String getCityLandmarkName() {
		return CityLandmarkName;
	}

	public void setCityLandmarkName(String cityLandmarkName) {
		CityLandmarkName = cityLandmarkName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getIsDelated() {
		return isDelated;
	}

	public void setIsDelated(String isDelated) {
		this.isDelated = isDelated;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
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

	public Long getCityLandmarkMasterId() {
		return cityLandmarkMasterId;
	}

	public void setCityLandmarkMasterId(Long cityLandmarkMasterId) {
		this.cityLandmarkMasterId = cityLandmarkMasterId;
	}

/*	public Set<EmployeeLandmarkEntity> getEmployeeLandmarkList() {
		return employeeLandmarkList;
	}

	public void setEmployeeLandmarkList(Set<EmployeeLandmarkEntity> employeeLandmarkList) {
		this.employeeLandmarkList = employeeLandmarkList;
	}*/

}
