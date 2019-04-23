package com.marlabs.cab.service.persistance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REASON_MASTER")
public class ReasonEntity implements Serializable {

	private static final long serialVersionUID = -1647822778759966458L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REASON_MASTER_ID")
	private Long reasonId;

/*	@JsonManagedReference("reason_requestHeader")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reason", fetch = FetchType.EAGER)
	@Column(insertable=false, updatable=false)
	private Set<RequestHeaderEntity> requestHeaderList;*/
	
	
	@Column(name = "REASON_DESCRIPTION")
	private String reasonDescription;

	@Column(name = "ACTIVE")
	private String active;

	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;

	public Long getReasonId() {
		return reasonId;
	}

	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}

	public String getReasonDescription() {
		return reasonDescription;
	}

	public void setReasonDescription(String reasonDescription) {
		this.reasonDescription = reasonDescription;
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
