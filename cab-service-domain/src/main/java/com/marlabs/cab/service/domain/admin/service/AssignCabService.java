package com.marlabs.cab.service.domain.admin.service;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.marlabs.cab.service.persistance.entity.AssignCabDashboardDetailViewEntity;
import com.marlabs.cab.service.persistance.entity.AssignCabDashboardViewEntity;
import com.marlabs.cab.service.persistance.vo.AssignCabTripVo;
import com.marlabs.cab.service.persistance.vo.AssignCabVO;
import com.marlabs.cab.service.persistance.vo.DataFilterParamsVO;
import com.marlabs.cab.service.persistance.vo.TripPrintVO;

public interface AssignCabService {

	public List<AssignCabDashboardViewEntity> assignCabDashboard(DataFilterParamsVO dataFilterParamsVO);
	
	public List<AssignCabDashboardDetailViewEntity> assignCabDashboardDetail(DataFilterParamsVO dataFilterParamsVO);
	
	public List<TripPrintVO> printTripSheet(DataFilterParamsVO dataFilterParamsVO);
	
	public List<AssignCabDashboardDetailViewEntity> addUpdateAssignCab(List<AssignCabTripVo> assignCabTripVo);
	
	public AssignCabVO assignCabView();
	
	public XSSFWorkbook createTripExcelFile(DataFilterParamsVO dataFilterParamsVO);
	
}
