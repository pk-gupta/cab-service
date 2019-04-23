package com.marlabs.cab.service.persistance.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

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
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "REQUEST_HEADER")
public class RequestHeaderEntity implements Serializable{

	private static final long serialVersionUID = -5641876593647593800L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REQ_HEADER_ID")
	private Long requestHeaderId;

	@JsonManagedReference(value="requestHeader_requestDetail")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "requestHeader", fetch = FetchType.EAGER)
	private List<RequestDetailEntity> requestDetailList;
	
	@JsonBackReference(value="employeeLandmark_requestHeader")
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "FK_EMPLOYEE_LANDMARK_ID", referencedColumnName = "EMPLOYEE_LANDMARK_ID")
	private EmployeeLandmarkEntity employeeLandmark;
	
	@JsonBackReference(value="reason_requestHeader") 
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "FK_REASON_MASTER_ID", referencedColumnName = "REASON_MASTER_ID")
	private ReasonEntity reason;
	
	@JsonBackReference(value="officeBranch_requestHeader")
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "FK_OFFICE_BRANCH_CODE", referencedColumnName = "OFFICE_BRANCH_CODE")
	private OfficeBranchEntity officeBranch;
	
	@Column(name = "EMPLOYEE_ID")
	private String employeeId;

	@Column(name = "OTHER_EMPLOYEE_ID")
	private String otherEmployeeId;

	@Column(name = "PROJECT_ID")
	private String projectId;

	@Column(name = "OTHER_PROJECT")
	private String otherProject;

	@Column(name = "MANAGER_ID")
	private String managerId;

	@Column(name = "EMPLOYEE_PHONE")
	private String employeePhone;

	@Column(name = "REQUEST_TYPE ")
	private String requestType;

	@Column(name = "REQ_SERVICE_TYPE ")
	private String requestServiceType;

	@Column(name = "FROM_DATE ")
	private Date fromDate;

	@Column(name = "TO_DATE ")
	private Date toDate;

	@Column(name = "LOGIN_TIME")
	private String loginTime;

	@Column(name = "LOGOUT_TIME")
	private String logoutTime;

	@Column(name = "WEEKOFF_DAY1")
	private String weekOffDay1;

	@Column(name = "WEEKOFF_DAY2")
	private String weekOffDay2;

	@Column(name = "WEEKOFF_DAY3")
	private String weekOffDay3;

	@Column(name = "OTHER_REASON")
	private String otherReason;

	@Column(name = "EMPLOYEE_REMARK")
	private String employeeRemark;

	@Column(name = "MANAGER_REMARK")
	private String managerRemark;

	@Column(name = "APPROVE_STATUS")
	private String approveStatus;

	@Column(name = "APPROVE_DATE")
	private Timestamp approveDate;

	@Column(name = "IS_DELETED")
	private String isDeleted;

	@Column(name = "IS_CANCELLED")
	private String isCancelled;

	@Column(name = "CANCELLED_BY")
	private String cancelledBy;

	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;

	@Column(name = "NO_OF_WEEKOFFS")
	private Long noOfWeekOffs;


	public Long getRequestHeaderId() {
		return requestHeaderId;
	}

	public void setRequestHeaderId(Long requestHeaderId) {
		this.requestHeaderId = requestHeaderId;
	}

	public List<RequestDetailEntity> getRequestDetailList() {
		return requestDetailList;
	}

	public void setRequestDetailList(List<RequestDetailEntity> requestDetailList) {
		this.requestDetailList = requestDetailList;
	}

	public EmployeeLandmarkEntity getEmployeeLandmark() {
		return employeeLandmark;
	}

	public void setEmployeeLandmark(EmployeeLandmarkEntity employeeLandmark) {
		this.employeeLandmark = employeeLandmark;
	}

	public ReasonEntity getReason() {
		return reason;
	}

	public void setReason(ReasonEntity reason) {
		this.reason = reason;
	}

	public OfficeBranchEntity getOfficeBranch() {
		return officeBranch;
	}

	public void setOfficeBranch(OfficeBranchEntity officeBranch) {
		this.officeBranch = officeBranch;
	}

	public String getOtherProject() {
		return otherProject;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setOtherProject(String otherProject) {
		this.otherProject = otherProject;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getEmployeePhone() {
		return employeePhone;
	}

	public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestServiceType() {
		return requestServiceType;
	}

	public void setRequestServiceType(String requestServiceType) {
		this.requestServiceType = requestServiceType;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getOtherReason() {
		return otherReason;
	}

	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}

	public String getEmployeeRemark() {
		return employeeRemark;
	}

	public void setEmployeeRemark(String employeeRemark) {
		this.employeeRemark = employeeRemark;
	}

	public String getManagerRemark() {
		return managerRemark;
	}

	public void setManagerRemark(String managerRemark) {
		this.managerRemark = managerRemark;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getIsCancelled() {
		return isCancelled;
	}

	public void setIsCancelled(String isCancelled) {
		this.isCancelled = isCancelled;
	}

	public String getCancelledBy() {
		return cancelledBy;
	}

	public void setCancelledBy(String cancelledBy) {
		this.cancelledBy = cancelledBy;
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

	public String getOtherEmployeeId() {
		return otherEmployeeId;
	}

	public void setOtherEmployeeId(String otherEmployeeId) {
		this.otherEmployeeId = otherEmployeeId;
	}

	public String getWeekOffDay1() {
		return weekOffDay1;
	}

	public void setWeekOffDay1(String weekOffDay1) {
		this.weekOffDay1 = weekOffDay1;
	}

	public String getWeekOffDay2() {
		return weekOffDay2;
	}

	public void setWeekOffDay2(String weekOffDay2) {
		this.weekOffDay2 = weekOffDay2;
	}

	public String getWeekOffDay3() {
		return weekOffDay3;
	}

	public void setWeekOffDay3(String weekOffDay3) {
		this.weekOffDay3 = weekOffDay3;
	}

	public Long getNoOfWeekOffs() {
		return noOfWeekOffs;
	}

	public void setNoOfWeekOffs(Long noOfWeekOffs) {
		this.noOfWeekOffs = noOfWeekOffs;
	}

	public Timestamp getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Timestamp approveDate) {
		this.approveDate = approveDate;
	}

}
