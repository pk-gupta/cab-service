package com.marlabs.cab.service.persistance.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "USER_ROLE_MASTER")
public class UserRoleMasterEntity implements Serializable{

	private static final long serialVersionUID = -7497990258928327334L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ROLE_MASTER_ID")
	private Long userRoleMasterId;
	
    @JsonManagedReference(value="userRoleMaster_userAssignedRole")
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "userRoleMaster", fetch = FetchType.EAGER)
	@Column(insertable=false, updatable=false)
	private List<UserAssignedRoleEntity> userAssignedRoleList;
	
	@Column(name = "USER_ROLE")
	private String userId;
	
	@Column(name = "ACTIVE")
	private String active;
	
	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;

	public Long getUserRoleMasterId() {
		return userRoleMasterId;
	}

	public void setUserRoleMasterId(Long userRoleMasterId) {
		this.userRoleMasterId = userRoleMasterId;
	}

	public List<UserAssignedRoleEntity> getUserAssignedRoleList() {
		return userAssignedRoleList;
	}

	public void setUserAssignedRoleList(List<UserAssignedRoleEntity> userAssignedRoleList) {
		this.userAssignedRoleList = userAssignedRoleList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
}
