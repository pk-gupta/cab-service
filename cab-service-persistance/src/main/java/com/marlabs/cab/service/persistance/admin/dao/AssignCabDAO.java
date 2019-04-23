package com.marlabs.cab.service.persistance.admin.dao;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.AssignCabDashboardDetailViewEntity;
import com.marlabs.cab.service.persistance.entity.AssignCabDashboardViewEntity;
import com.marlabs.cab.service.persistance.entity.TripHeaderEntity;
import com.marlabs.cab.service.persistance.vo.DataFilterParamsVO;

public interface AssignCabDAO {

	public List<AssignCabDashboardViewEntity> assignCabDashboard(DataFilterParamsVO dataFilterParamsVO);

	public List<AssignCabDashboardDetailViewEntity> assignCabDashboardDetail(DataFilterParamsVO dataFilterParamsVO);
	
	public TripHeaderEntity addUpdateAssignCab(TripHeaderEntity tripHeader);
	
	public TripHeaderEntity getTripHeaderDetails(Long tripHeaderId);
	
	public String deleteTripDetails(Long tripHeaderId);
	
	public List<AssignCabDashboardDetailViewEntity> printTripSheet(DataFilterParamsVO dataFilterParamsVO);
	
}
