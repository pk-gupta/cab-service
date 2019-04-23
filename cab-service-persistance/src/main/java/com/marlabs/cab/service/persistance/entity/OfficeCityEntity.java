package com.marlabs.cab.service.persistance.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "OFFICE_CITY")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OfficeCityEntity implements Serializable {

	private static final long serialVersionUID = 4313981110708837881L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "OFFICE_CITY_ID",columnDefinition="BIGINT",insertable=false ,updatable = false)
	private Long officeCityId;

	@JsonIgnore
/*	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "OFFICE_CITY_CAB_DETAIL", joinColumns = @JoinColumn(name = "OFFICE_CITY_ID", nullable = false, updatable = false,columnDefinition="BIGINT"),
				inverseJoinColumns =  @JoinColumn(name = "CAB_DETAIL_ID",nullable = false, updatable = false,columnDefinition="BIGINT")
			)
	private Set<CabDetailEntity> cabDetail = new LinkedHashSet<CabDetailEntity>(0);*/
	//@JsonBackReference(value="cabDetail_officeCity")
	@ManyToMany(mappedBy = "officeCity", fetch = FetchType.EAGER)
	private Set<CabDetailEntity> cabDetail;
	
	@Column(name = "OFFICE_CITY_NAME")
	private String cityName;

	@Column(name = "ACTIVE")
	private String active;

	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;

	public Long getOfficeCityId() {
		return officeCityId;
	}

	public void setOfficeCityId(Long officeCityId) {
		this.officeCityId = officeCityId;
	}

	public Set<CabDetailEntity> getCabDetail() {
		return cabDetail;
	}

	public void setCabDetail(Set<CabDetailEntity> cabDetail) {
		this.cabDetail = cabDetail;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

}
