package com.marlabs.cab.service.domain.admin.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.domain.admin.service.AssignCabService;
import com.marlabs.cab.service.persistance.admin.dao.AssignCabDAO;
import com.marlabs.cab.service.persistance.admin.dao.TripServiceAvailedDAO;
import com.marlabs.cab.service.persistance.common.dao.CustomQueryDAO;
import com.marlabs.cab.service.persistance.entity.AssignCabDashboardDetailViewEntity;
import com.marlabs.cab.service.persistance.entity.AssignCabDashboardViewEntity;
import com.marlabs.cab.service.persistance.entity.CabDetailEntity;
import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;
import com.marlabs.cab.service.persistance.entity.TripDetailEntity;
import com.marlabs.cab.service.persistance.entity.TripHeaderEntity;
import com.marlabs.cab.service.persistance.entity.TripServiceAvailedEntity;
import com.marlabs.cab.service.persistance.sql.mapper.CityBranchSQLMapper;
import com.marlabs.cab.service.persistance.vo.AssignCabTripVo;
import com.marlabs.cab.service.persistance.vo.AssignCabVO;
import com.marlabs.cab.service.persistance.vo.DataFilterParamsVO;
import com.marlabs.cab.service.persistance.vo.EmployeeTripDetailsVO;
import com.marlabs.cab.service.persistance.vo.TripPrintVO;


@Service
public class AssignCabServiceImpl implements AssignCabService {

	private static Logger logger = LogManager.getLogger(AssignCabServiceImpl.class);

	@Autowired
	private AssignCabDAO assignCabDAO;

	@Autowired
	CustomQueryDAO customQueryDAO;

	@Autowired
	TripServiceAvailedDAO tripServiceAvailedDAO;

	@Override
	public List<AssignCabDashboardViewEntity> assignCabDashboard(DataFilterParamsVO dataFilterParamsVO) {

		logger.info("assignCabDashboard() -> Assign Cab Dashboard Service call...");

		return assignCabDAO.assignCabDashboard(dataFilterParamsVO);
	}

	@Override
	public List<AssignCabDashboardDetailViewEntity> assignCabDashboardDetail(DataFilterParamsVO dataFilterParamsVO) {

		logger.info("assignCabDashboardDetail() -> Assign Cab Dashboard Detail Service call...");

		return assignCabDAO.assignCabDashboardDetail(dataFilterParamsVO);
	}

	@Override
	public AssignCabVO assignCabView() {

		AssignCabVO assignCabVO = new AssignCabVO();

		List<CityBranchSQLMapper> cityBranchList = customQueryDAO.getCityBranchDetails();

		try {
			assignCabVO.setOfficeBranchCityList(cityBranchList);

		} catch (Exception exception) {
			logger.error("assignCabView() -> ERROR : Failed to get City List " + exception.getCause());
			assignCabVO = null;
		}
		return assignCabVO;
	}

	@Override
	public List<TripPrintVO> printTripSheet(DataFilterParamsVO dataFilterParamsVO) {

		logger.info("printTripSheet() -> Print Trip Sheet Service call...");

		List<AssignCabDashboardDetailViewEntity> assignCabDashboardDetailView = null;
		List<TripPrintVO> tripPrintlist = new ArrayList<>();
		;

		assignCabDashboardDetailView = assignCabDAO.printTripSheet(dataFilterParamsVO);

		if (!CabServiceUtil.isNULL(assignCabDashboardDetailView)) {

			Map<Long, List<AssignCabDashboardDetailViewEntity>> groupTripSheetPrintMap = assignCabDashboardDetailView
					.stream().collect(Collectors.groupingBy(AssignCabDashboardDetailViewEntity::getTripHeaderId));

			groupTripSheetPrintMap.forEach((tripHeaderId, assignCabDetailsViewList) -> {

				List<TripPrintVO> list = getTripPrintDetails(assignCabDetailsViewList);
				tripPrintlist.add(list.get(0));

			});
		}
		return tripPrintlist;
	}

	@Override
	public List<AssignCabDashboardDetailViewEntity> addUpdateAssignCab(List<AssignCabTripVo> assignCabTripList) {

		logger.info("addUpdateAssignCab() -> Add/Update Assign Cab Service call...");

		List<AssignCabDashboardDetailViewEntity> assignCabDashboardDetailView = null;
		DataFilterParamsVO dataFilterParamsVO = null;
		String deleteStatus;

		for (AssignCabTripVo cabTrip : assignCabTripList) {
			if (!CabServiceUtil.isNULL(cabTrip.getTripHeaderId())) {

				deleteStatus = tripServiceAvailedDAO.deleteTripHeaderDetails(cabTrip.getTripHeaderId());
				if (Constants.STATUS_FAILED.equals(deleteStatus)) {
					return assignCabDashboardDetailView;
				}

				deleteStatus = assignCabDAO.deleteTripDetails(cabTrip.getTripHeaderId());
				if (Constants.STATUS_FAILED.equals(deleteStatus)) {
					return assignCabDashboardDetailView;
				}

			}
		}

		/*
		 * if(deleteStatus == 1){ return assignCabDashboardDetailView; }
		 */

		Map<Long, List<AssignCabTripVo>> groupAssignedCabMap = new HashMap<>();

		// Grouping TripHeader and TripDetail by Cab number
		for (AssignCabTripVo assignCabTripVo : assignCabTripList) {
			if (!CabServiceUtil.isNULL(assignCabTripVo.getCabDetailId())) {
				groupSubmittedTripInformation(groupAssignedCabMap, assignCabTripVo);
			}
		}

		// Process each Group at a time
		groupAssignedCabMap.forEach((cabDetailId, tripDetailList) -> addUpdateEachItem(tripDetailList));

		dataFilterParamsVO = getDataFilterParams(assignCabTripList);
		assignCabDashboardDetailView = assignCabDAO.assignCabDashboardDetail(dataFilterParamsVO);

		return assignCabDashboardDetailView;

	}

	private void groupSubmittedTripInformation(Map<Long, List<AssignCabTripVo>> groupAssignedCabMap,
			AssignCabTripVo assignCabTripVo) {
		if (!groupAssignedCabMap.containsKey(assignCabTripVo.getCabDetailId())) {

			/*
			 * if(!CabServiceUtil.isNULL(assignCabTripVo.getCabDetailId())){
			 * groupAssignedCabMap.put(assignCabTripVo.getCabDetailId(), new
			 * ArrayList<>()); }
			 */
			groupAssignedCabMap.put(assignCabTripVo.getCabDetailId(), new ArrayList<>());
		}
		groupAssignedCabMap.get(assignCabTripVo.getCabDetailId()).add(assignCabTripVo);

	}

	private void addUpdateEachItem(List<AssignCabTripVo> tripDetails) {
		TripHeaderEntity tripHeader = setTripHeaderEntity(tripDetails.get(0));

		tripHeader.setTripHeaderId(tripDetails.get(0).getTripHeaderId());// set to Update for Specific TripHeaderID
		tripHeader.setTripDetaillList(setTripDetail(tripDetails, tripHeader));

		TripHeaderEntity tripSubmited = assignCabDAO.addUpdateAssignCab(tripHeader);

		if (!CabServiceUtil.isNULL(tripSubmited)) {

			insertTripServiceAvailedEntity(tripSubmited, tripDetails);

		}

	}

	private List<TripDetailEntity> setTripDetail(List<AssignCabTripVo> tripDetails, TripHeaderEntity tripHeader) {

		List<TripDetailEntity> tripDetailList = new ArrayList<>();

		tripDetails.forEach(tripVO -> addTripDetail(tripDetailList, tripVO, tripHeader));

		return tripDetailList;
	}

	private void addTripDetail(List<TripDetailEntity> tripDetailList, AssignCabTripVo tripVO,TripHeaderEntity tripHeader) {

		RequestDetailEntity requestDetail = new RequestDetailEntity();
		requestDetail.setRequestDetailId(tripVO.getReqDetailId());

		TripDetailEntity tripDetail = new TripDetailEntity();

		tripDetail.setTripHeader(tripHeader);
		tripDetail.setRequestDetail(requestDetail);
		tripDetail.setTripGroup(tripVO.getTripGroup());
		tripDetail.setSequenceNo(tripVO.getSequenceNo());
		tripDetail.setEstimatedtime(tripVO.getEstimatedTime());
		tripDetail.setEmployeeId(tripVO.getEmployeeId());

		tripDetail.setCreatedBy(tripVO.getEmployeeId());
		tripDetail.setUpdatedBy(tripVO.getEmployeeId());

		tripDetailList.add(tripDetail);
	}

	private TripHeaderEntity setTripHeaderEntity(AssignCabTripVo assignCabTripVo) {

		TripHeaderEntity tripHeader = new TripHeaderEntity();

		CabDetailEntity cabDetail = new CabDetailEntity();
		cabDetail.setCabDetailId(assignCabTripVo.getCabDetailId());

		tripHeader.setTripDate(assignCabTripVo.getTripDate());
		tripHeader.setTripTime(assignCabTripVo.getTripTime());
		// tripHeader.setTripStartTime(assignCabTripVo.getTripStartTime());
		tripHeader.setServiceType(assignCabTripVo.getServiceType());
		tripHeader.setOtherDriverName(assignCabTripVo.getOtherDriverName());
		tripHeader.setOtherDriverPhone(assignCabTripVo.getOtherDriverPhone());
		tripHeader.setOtherVehicleRegNo(assignCabTripVo.getOtherVechicleRegNo());
		tripHeader.setCabDetail(cabDetail);
		tripHeader.setCreatedBy(assignCabTripVo.getEmployeeId());
		tripHeader.setUpdatedBy(assignCabTripVo.getEmployeeId());
		tripHeader.setUpdateDate(new Timestamp(new Date(0).getTime()));
		tripHeader.setTripStatus(Constants.TRIP_STATUS_NEW);

		return tripHeader;
	}

	private DataFilterParamsVO getDataFilterParams(List<AssignCabTripVo> tripDetails) {

		DataFilterParamsVO dataFilterParams = new DataFilterParamsVO();

		dataFilterParams.setServiceDate(tripDetails.get(0).getTripDate());
		dataFilterParams.setOfficeBranchCode(tripDetails.get(0).getOffBranchCode());
		dataFilterParams.setServiceType(tripDetails.get(0).getServiceType());
		dataFilterParams.setServiceTime(tripDetails.get(0).getTripTime());

		return dataFilterParams;

	}

	private void insertTripServiceAvailedEntity(TripHeaderEntity tripSubmited, List<AssignCabTripVo> tripDetail) {

		TripServiceAvailedEntity tripServiceAvailed = new TripServiceAvailedEntity();

		tripSubmited.getTripDetaillList().forEach(tDetails -> {
			
			TripHeaderEntity tripHeader = new TripHeaderEntity();
			tripHeader.setTripHeaderId(tripSubmited.getTripHeaderId());

			RequestDetailEntity requestDetail = new RequestDetailEntity();
			requestDetail.setRequestDetailId(tDetails.getRequestDetail().getRequestDetailId());

			tripServiceAvailed.setRequestDetail(requestDetail);
			tripServiceAvailed.setTripHeader(tripHeader);

			// tripServiceAvailed.setIsTripAvailed(Constants.STATUS_FALSE);
			tripServiceAvailed.setEmployeeId(tDetails.getEmployeeId());
			tripServiceAvailed.setCreatedBy(tripDetail.get(0).getEmployeeId());
			tripServiceAvailed.setUpdatedBy(tripDetail.get(0).getEmployeeId());

			tripServiceAvailedDAO.addTripAvailedDetails(tripServiceAvailed);
		});

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<TripPrintVO> getTripPrintDetails(List<AssignCabDashboardDetailViewEntity> assignCabDashboardDetailView) {

		List<EmployeeTripDetailsVO> employeeDetailsist = new ArrayList();
		List<TripPrintVO> tripPrintList = new ArrayList();

		assignCabDashboardDetailView.forEach(tripPrintDetail -> {

			TripPrintVO tripPrint = new TripPrintVO();
			EmployeeTripDetailsVO employeeTripDetailsVO = new EmployeeTripDetailsVO();

			tripPrint.setDriverFirstName(tripPrintDetail.getDriverFirstName());
			tripPrint.setDriverMiddletName(tripPrintDetail.getDriverMiddleName());
			tripPrint.setDriverLastName(tripPrintDetail.getDriverLastName());
			tripPrint.setTripHeaderId(tripPrintDetail.getTripHeaderId());
			tripPrint.setTripDate(tripPrintDetail.getServiceDate());
			tripPrint.setServiceType(tripPrintDetail.getServiceType());
			tripPrint.setOfficeCityName(tripPrintDetail.getOfficeCityName());
			
			if (tripPrintDetail.getServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_DROP)) {
				tripPrint.setTripTime(tripPrintDetail.getLogoutTime());
			}
			tripPrint.setTripTime(tripPrintDetail.getLoginTime());

			tripPrint.setVehicleNo(tripPrintDetail.getVehicleNo());
			tripPrint.setOfficeBranchName(tripPrintDetail.getOfficeBranchName());

			employeeTripDetailsVO.setEmpFirstName(tripPrintDetail.getEmpFirstName());
			employeeTripDetailsVO.setEmpMiddleName(tripPrintDetail.getEmpMiddleName());
			employeeTripDetailsVO.setEmpLastName(tripPrintDetail.getEmpLastName());
			employeeTripDetailsVO.setEmployeeGender(tripPrintDetail.getEmpGender());
			employeeTripDetailsVO.setExpectedTime(tripPrintDetail.getEstimatedTime());
			employeeTripDetailsVO.setEmployeeLandmark(tripPrintDetail.getEmpLandmarkName());
			employeeTripDetailsVO.setSequenceNo(tripPrintDetail.getSequenceNo());
			employeeDetailsist.add(employeeTripDetailsVO);
			tripPrintList.add(tripPrint);
			tripPrint.setEmployeeTripDetails(employeeDetailsist);

		});

		return tripPrintList;
	}

	@Override
	public XSSFWorkbook createTripExcelFile(DataFilterParamsVO dataFilterParamsVO) {
		
		logger.info("createTripExcelFile() -> Create Trip Excel Sheet Service call...");
		
		String[] HEADERS ={"Trip No","Office Login","Office Logout","Service Type","Service Date","Employee Id","Employee Name","Employee Mobile No","Landmark","Cab No","Driver Name"};
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet workSheet = workbook.createSheet("TripDetail");
		
		CellStyle dataStyle = getHeaderStyle(workbook);
        
        workSheet.setDefaultColumnWidth(15);
        workSheet.setDefaultColumnStyle(9, dataStyle);;
    
        getExcelHeaders(dataFilterParamsVO, HEADERS, workSheet, dataStyle);
        getExcelColumnValues(dataFilterParamsVO, workSheet);
        
		return workbook;

	}

	private CellStyle getHeaderStyle(XSSFWorkbook workbook) {
		CellStyle dataStyle = workbook.createCellStyle();
	  
		Font font = workbook.createFont();
        font.setColor(IndexedColors.DARK_BLUE.getIndex());
        font.setBold(true);
        dataStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        dataStyle.setAlignment(CellStyle.ALIGN_LEFT);
        dataStyle.setFont(font);
		
		return dataStyle;
	}

	private void getExcelHeaders(DataFilterParamsVO dataFilterParamsVO, String[] HEADERS, XSSFSheet workSheet,CellStyle dataStyle) {
		XSSFCell xlsxCell;
		XSSFRow xlsxRow = workSheet.createRow(0);
		 int column = 0;
		 for (String header : HEADERS) {
			 xlsxCell  = xlsxRow.createCell(column);
			 xlsxCell.setCellValue(header);
			 xlsxCell.setCellStyle(dataStyle);
			 column++;
	        }
	 }

	private void getExcelColumnValues(DataFilterParamsVO dataFilterParamsVO, XSSFSheet workSheet) {
		
		XSSFRow xlsxRow;
		int rownum = 1;
		List<AssignCabDashboardDetailViewEntity> assignCabDashboardDetailView = null;

		assignCabDashboardDetailView = assignCabDAO.printTripSheet(dataFilterParamsVO);

		for (AssignCabDashboardDetailViewEntity cabDashboardDetail : assignCabDashboardDetailView) {

		    xlsxRow = workSheet.createRow(rownum++);
			
			xlsxRow.createCell(0).setCellValue(cabDashboardDetail.getTripHeaderId());
			
			if(cabDashboardDetail.getServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_PICKUP)){
				
			 xlsxRow.createCell(1).setCellValue(cabDashboardDetail.getLoginTime());
			 xlsxRow.createCell(2).setCellValue("NA");
			}else{
				xlsxRow.createCell(1).setCellValue("NA");
				xlsxRow.createCell(2).setCellValue(cabDashboardDetail.getLogoutTime());
			}
			
			xlsxRow.createCell(3).setCellValue(cabDashboardDetail.getServiceType());
			xlsxRow.createCell(4).setCellValue(CabServiceUtil.formatDate(cabDashboardDetail.getServiceDate()).toString());
			xlsxRow.createCell(5).setCellValue(cabDashboardDetail.getEmployeeId());
			xlsxRow.createCell(6).setCellValue(cabDashboardDetail.getEmpFirstName() + " " + cabDashboardDetail.getEmpLastName());
			xlsxRow.createCell(7).setCellValue(cabDashboardDetail.getEmpPhone());
			xlsxRow.createCell(8).setCellValue(cabDashboardDetail.getEmpLandmarkName());
			xlsxRow.createCell(9).setCellValue(cabDashboardDetail.getVehicleNo());
			xlsxRow.createCell(10).setCellValue(cabDashboardDetail.getDriverFirstName() + " " + cabDashboardDetail.getDriverLastName());
		}
	}
	
}


