package com.marlabs.cab.service.persistance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "OFFICE_BRANCH")
public class OfficeBranchEntity implements Serializable {

	private static final long serialVersionUID = 8245361308179046640L;

	@Id
	@Column(name = "OFFICE_BRANCH_CODE")
	private String officeBranchcode;

	/*@JsonManagedReference("officeBranch_requestHeader")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "officeBranch", fetch = FetchType.EAGER)
	@Column(insertable=false, updatable=false)
	private Set<RequestHeaderEntity> requestHeaderList;*/
	
	
	@JsonBackReference(value="officeCity_officeBranch")
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "FK_OFFICE_CITY_ID", referencedColumnName = "OFFICE_CITY_ID")
	private OfficeCityEntity officeCity;
	
	@Column(name = "OFFICE_BRANCH_NAME")
	private String officeBranchName;

	@Column(name = "ACTIVE")
	private String active;

	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;

	public String getOfficeBranchcode() {
		return officeBranchcode;
	}

	public void setOfficeBranchcode(String officeBranchcode) {
		this.officeBranchcode = officeBranchcode;
	}
	
	public OfficeCityEntity getOfficeCity() {
		return officeCity;
	}

	public void setOfficeCity(OfficeCityEntity officeCity) {
		this.officeCity = officeCity;
	}

	public String getOfficeBranchName() {
		return officeBranchName;
	}

	public void setOfficeBranchName(String officeBranchName) {
		this.officeBranchName = officeBranchName;
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
