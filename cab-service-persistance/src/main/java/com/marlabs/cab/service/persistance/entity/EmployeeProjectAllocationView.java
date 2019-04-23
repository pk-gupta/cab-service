package com.marlabs.cab.service.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "employee_project_allocation_view")
public class EmployeeProjectAllocationView {

	
	@Column(name = "EmployeeId")
	private String employeeID;
	
	@Column(name = "GlobalEmpId")
	private String globalEmpId;
	
	@Id
	@Column(name = "ProjectID")
	private String projectID;

	@Column(name = "OldProjectId")
	private String oldProjectID;

	@Column(name = "ProjectName")
	private String employeeProjectName;

	@Column(name = "Status")
	private String projStatus;

	@Column(name = "ManagerID")
	private String managerID;

	@Column(name = "AllocationStartDate")
	private String projAllocationStartDate;

	@Column(name = "AllocationEndDate")
	private String projAllocationEndDate;

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

/*	public String getGlobalEmpId() {
		return globalEmpId;
	}

	public void setGlobalEmpId(String globalEmpId) {
		this.globalEmpId = globalEmpId;
	}
*/
	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getOldProjectID() {
		return oldProjectID;
	}

	public void setOldProjectID(String oldProjectID) {
		this.oldProjectID = oldProjectID;
	}

	public String getEmployeeProjectName() {
		return employeeProjectName;
	}

	public void setEmployeeProjectName(String employeeProjectName) {
		this.employeeProjectName = employeeProjectName;
	}

	public String getProjStatus() {
		return projStatus;
	}

	public void setProjStatus(String projStatus) {
		this.projStatus = projStatus;
	}

	public String getManagerID() {
		return managerID;
	}

	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}

	public String getProjAllocationStartDate() {
		return projAllocationStartDate;
	}

	public void setProjAllocationStartDate(String projAllocationStartDate) {
		this.projAllocationStartDate = projAllocationStartDate;
	}

	public String getProjAllocationEndDate() {
		return projAllocationEndDate;
	}

	public void setProjAllocationEndDate(String projAllocationEndDate) {
		this.projAllocationEndDate = projAllocationEndDate;
	}

}
