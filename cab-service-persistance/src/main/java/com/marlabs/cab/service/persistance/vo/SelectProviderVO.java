package com.marlabs.cab.service.persistance.vo;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.EmployeeLandmarkEntity;
import com.marlabs.cab.service.persistance.entity.ReasonEntity;
import com.marlabs.cab.service.persistance.entity.ServiceAndTimeEntity;
import com.marlabs.cab.service.persistance.sql.mapper.OfficeBranchSQLMapper;

public class SelectProviderVO {

	private List<EmployeeProjectVO> projectList;
	private List<EmployeeLandmarkEntity> landMarksList;
	private List<ReasonEntity> reasonsList;
	private List<OfficeBranchSQLMapper> officeBranchesList;
	private List<ServiceAndTimeEntity> loginTimeList;
	private List<ServiceAndTimeEntity> logoutTimeList;

	public List<EmployeeProjectVO> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<EmployeeProjectVO> projectList) {
		this.projectList = projectList;
	}

	public List<EmployeeLandmarkEntity> getLandMarksList() {
		return landMarksList;
	}

	public void setLandMarksList(List<EmployeeLandmarkEntity> landMarksList) {
		this.landMarksList = landMarksList;
	}

	public List<ReasonEntity> getReasonsList() {
		return reasonsList;
	}

	public void setReasonsList(List<ReasonEntity> reasonsList) {
		this.reasonsList = reasonsList;
	}

	public List<OfficeBranchSQLMapper> getOfficeBranchesList() {
		return officeBranchesList;
	}

	public void setOfficeBranchesList(List<OfficeBranchSQLMapper> officeBranchesList) {
		this.officeBranchesList = officeBranchesList;
	}

	public List<ServiceAndTimeEntity> getLoginTimeList() {
		return loginTimeList;
	}

	public void setLoginTimeList(List<ServiceAndTimeEntity> loginTimeList) {
		this.loginTimeList = loginTimeList;
	}

	public List<ServiceAndTimeEntity> getLogoutTimeList() {
		return logoutTimeList;
	}

	public void setLogoutTimeList(List<ServiceAndTimeEntity> logoutTimeList) {
		this.logoutTimeList = logoutTimeList;
	}

}
