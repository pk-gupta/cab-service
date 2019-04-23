package com.marlabs.cab.service.persistance.vo;

import java.sql.Date;
import java.util.List;

import com.marlabs.cab.service.persistance.entity.EmployeeView;
import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;
import com.marlabs.cab.service.persistance.marker.InputDataMarker;

public class SingleRequestVO extends SelectProviderVO implements InputDataMarker {

	private long requestId;

	private String employeeId;
	private String employeeName;
	private String employeeFirstName;
	private String employeeMiddleName;
	private String employeeLastName;
	
	private String managerFirstName;
	private String managerMiddleName;
	private String managerLastName;
	
	private String globalEmployeeId;

	private String employeeEmail;

	private String employeeApproveStatus;
	private String approveDate;

	private String managerId;
	private String managerName;

	private String managerEmail;

	private String employeeRole;

	private String employeePhone;

	private Date emplopyeeSelectedFromDate;
	private Date emplopyeeSelectedToDate;

	private String employeeSelectedBranchCode;
	private String employeeSelectedBranchName;

	private String employeeSelectedProjectId;
	private String employeeSelectedProject;

	private Long employeeSelectedLandmarkId;
	private String employeeSelectedLandmark;

	private Long employeeSelectedReasonId;
	private String employeeSelectedReason;

	private Long employeeServiceTypeId;
	private String employeeServiceType;

	private Date serviceDate;

	private String employeeSelectedLogin;
	private String employeeSelectedLogout;

	private String employeeRemark;
	private String managerRemark;

	private String requestServiceType;

	private String reasonDescription;

	private String officeBranchCode;

	private String userAction;
	
	private List<RequestDetailEntity> requestDetailEntity;

	private List<RequestHeaderEntity> requestHeaderEntity;

	private List<EmployeeView> employeeView;

	public List<RequestHeaderEntity> getRequestHeaderEntity() {
		return requestHeaderEntity;
	}

	public void setRequestHeaderEntity(List<RequestHeaderEntity> requestHeaderEntity) {
		this.requestHeaderEntity = requestHeaderEntity;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getUserAction() {
		return userAction;
	}

	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getEmployeePhone() {
		return employeePhone;
	}

	public Date getEmplopyeeSelectedFromDate() {
		return emplopyeeSelectedFromDate;
	}

	public String getEmployeeFirstName() {
		return employeeFirstName;
	}

	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}

	public String getEmployeeMiddleName() {
		return employeeMiddleName;
	}

	public void setEmployeeMiddleName(String employeeMiddleName) {
		this.employeeMiddleName = employeeMiddleName;
	}

	public String getEmployeeLastName() {
		return employeeLastName;
	}

	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}

	public String getManagerFirstName() {
		return managerFirstName;
	}

	public void setManagerFirstName(String managerFirstName) {
		this.managerFirstName = managerFirstName;
	}

	public String getManagerMiddleName() {
		return managerMiddleName;
	}

	public void setManagerMiddleName(String managerMiddleName) {
		this.managerMiddleName = managerMiddleName;
	}

	public String getManagerLastName() {
		return managerLastName;
	}

	public void setManagerLastName(String managerLastName) {
		this.managerLastName = managerLastName;
	}
	
	public void setEmplopyeeSelectedFromDate(Date emplopyeeSelectedFromDate) {
		this.emplopyeeSelectedFromDate = emplopyeeSelectedFromDate;
	}

	public Date getEmplopyeeSelectedToDate() {
		return emplopyeeSelectedToDate;
	}

	public void setEmplopyeeSelectedToDate(Date emplopyeeSelectedToDate) {
		this.emplopyeeSelectedToDate = emplopyeeSelectedToDate;
	}

	public String getEmployeeSelectedBranchCode() {
		return employeeSelectedBranchCode;
	}

	public void setEmployeeSelectedBranchCode(String employeeSelectedBranchCode) {
		this.employeeSelectedBranchCode = employeeSelectedBranchCode;
	}

	public String getEmployeeSelectedProjectId() {
		return employeeSelectedProjectId;
	}

	public void setEmployeeSelectedProjectId(String employeeSelectedProjectId) {
		this.employeeSelectedProjectId = employeeSelectedProjectId;
	}

	public Long getEmployeeSelectedLandmarkId() {
		return employeeSelectedLandmarkId;
	}

	public void setEmployeeSelectedLandmarkId(Long employeeSelectedLandmarkId) {
		this.employeeSelectedLandmarkId = employeeSelectedLandmarkId;
	}

	public Long getEmployeeSelectedReasonId() {
		return employeeSelectedReasonId;
	}

	public void setEmployeeSelectedReasonId(Long employeeSelectedReasonId) {
		this.employeeSelectedReasonId = employeeSelectedReasonId;
	}

	public String getEmployeeServiceType() {
		return employeeServiceType;
	}

	public void setEmployeeServiceType(String employeeServiceType) {
		this.employeeServiceType = employeeServiceType;
	}

	public String getEmployeeSelectedLogin() {
		return employeeSelectedLogin;
	}

	public void setEmployeeSelectedLogin(String employeeSelectedLogin) {
		this.employeeSelectedLogin = employeeSelectedLogin;
	}

	public String getEmployeeSelectedLogout() {
		return employeeSelectedLogout;
	}

	public void setEmployeeSelectedLogout(String employeeSelectedLogout) {
		this.employeeSelectedLogout = employeeSelectedLogout;
	}

	public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
	}

	public String getRequestServiceType() {
		return requestServiceType;
	}

	public void setRequestServiceType(String requestServiceType) {
		this.requestServiceType = requestServiceType;
	}

	public String getEmployeeSelectedBranch() {
		return employeeSelectedBranchName;
	}

	public void setEmployeeSelectedBranch(String employeeSelectedBranch) {
		this.employeeSelectedBranchName = employeeSelectedBranch;
	}

	public String getEmployeeSelectedBranchName() {
		return employeeSelectedBranchName;
	}

	public void setEmployeeSelectedBranchName(String employeeSelectedBranchName) {
		this.employeeSelectedBranchName = employeeSelectedBranchName;
	}

	public String getEmployeeSelectedProject() {
		return employeeSelectedProject;
	}

	public void setEmployeeSelectedProject(String employeeSelectedProject) {
		this.employeeSelectedProject = employeeSelectedProject;
	}

	public String getEmployeeSelectedLandmark() {
		return employeeSelectedLandmark;
	}

	public void setEmployeeSelectedLandmark(String employeeSelectedLandmark) {
		this.employeeSelectedLandmark = employeeSelectedLandmark;
	}

	public String getEmployeeApproveStatus() {
		return employeeApproveStatus;
	}

	public void setEmployeeApproveStatus(String employeeApproveStatus) {
		this.employeeApproveStatus = employeeApproveStatus;
	}

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	public String getEmployeeSelectedReason() {
		return employeeSelectedReason;
	}

	public void setEmployeeSelectedReason(String employeeSelectedReason) {
		this.employeeSelectedReason = employeeSelectedReason;
	}

	public Long getEmployeeServiceTypeId() {
		return employeeServiceTypeId;
	}

	public void setEmployeeServiceTypeId(Long employeeServiceTypeId) {
		this.employeeServiceTypeId = employeeServiceTypeId;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
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

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public String getEmployeeRole() {
		return employeeRole;
	}

	public void setEmployeeRole(String employeeRole) {
		this.employeeRole = employeeRole;
	}

	public String getGlobalEmployeeId() {
		return globalEmployeeId;
	}

	public void setGlobalEmployeeId(String globalEmployeeId) {
		this.globalEmployeeId = globalEmployeeId;
	}

	public String getReasonDescription() {
		return reasonDescription;
	}

	public void setReasonDescription(String reasonDescription) {
		this.reasonDescription = reasonDescription;
	}

	public String getOfficeBranchCode() {
		return officeBranchCode;
	}

	public void setOfficeBranchCode(String officeBranchCode) {
		this.officeBranchCode = officeBranchCode;
	}

	public List<EmployeeView> getEmployeeView() {
		return employeeView;
	}

	public void setEmployeeView(List<EmployeeView> employeeView) {
		this.employeeView = employeeView;
	}

	public List<RequestDetailEntity> getRequestDetailEntity() {
		return requestDetailEntity;
	}

	public void setRequestDetailEntity(List<RequestDetailEntity> requestDetailEntity) {
		this.requestDetailEntity = requestDetailEntity;
	}

}
