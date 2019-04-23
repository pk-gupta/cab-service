package com.marlabs.cab.service.persistance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "USER_ASSIGNED_ROLE")
public class UserAssignedRoleEntity implements Serializable{

	private static final long serialVersionUID = -8751938337508483672L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ASSIGNED_ROLE_ID")
	private Long userAssignedRoleId;
	
	@JsonBackReference(value="userRoleMaster_userAssignedRole")
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "FK_USER_ROLE_MASTER_ID", referencedColumnName = "USER_ROLE_MASTER_ID")
	private UserRoleMasterEntity userRoleMaster;
	
	@JsonBackReference(value="officeBranch_userAssignedRole")
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "FK_OFFICE_BRANCH_CODE", referencedColumnName = "OFFICE_BRANCH_CODE")
	private OfficeBranchEntity officeBranch;
	
	@Column(name = "USER_TYPE")
	private String userType;
	
	@Column(name = "ACTIVE")
	private String active;
	
	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;

	public Long getUserAssignedRoleId() {
		return userAssignedRoleId;
	}

	public UserRoleMasterEntity getUserRoleMaster() {
		return userRoleMaster;
	}

	public void setUserRoleMaster(UserRoleMasterEntity userRoleMaster) {
		this.userRoleMaster = userRoleMaster;
	}

	public void setUserAssignedRoleId(Long userAssignedRoleId) {
		this.userAssignedRoleId = userAssignedRoleId;
	}
	
	public OfficeBranchEntity getOfficeBranch() {
		return officeBranch;
	}

	public void setOfficeBranch(OfficeBranchEntity officeBranch) {
		this.officeBranch = officeBranch;
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
}
