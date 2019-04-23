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
@Table(name = "CANCEL_REASON")
public class CancelReasonEntity implements Serializable{

	private static final long serialVersionUID = -668773094118497454L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CANCEL_REASON_ID")
	private Long cancelReasonId;

	@Column(name = "CANCEL_REASON_DESC")
	private String cancelReasonDescr;

	@Column(name = "ACTIVE")
	private String active;

	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;

	public Long getCancelReasonId() {
		return cancelReasonId;
	}

	public void setCancelReasonId(Long cancelReasonId) {
		this.cancelReasonId = cancelReasonId;
	}

	public String getCancelReasonDescr() {
		return cancelReasonDescr;
	}

	public void setCancelReasonDescr(String cancelReasonDescr) {
		this.cancelReasonDescr = cancelReasonDescr;
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
