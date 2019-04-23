package com.marlabs.cab.service.domain.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.domain.admin.service.TripServiceAvailedService;
import com.marlabs.cab.service.persistance.admin.dao.TripServiceAvailedDAO;
import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;
import com.marlabs.cab.service.persistance.entity.TripHeaderEntity;
import com.marlabs.cab.service.persistance.entity.TripServiceAvailedDashboardViewEntity;
import com.marlabs.cab.service.persistance.entity.TripServiceAvailedEntity;
import com.marlabs.cab.service.persistance.vo.DataFilterParamsVO;
import com.marlabs.cab.service.persistance.vo.TripServiceAvailedVO;
import com.marlabs.cab.service.persistance.vo.TripServiceDetailsVO;

@Service
public class TripServiceAvailedServiceImpl implements TripServiceAvailedService{
	
	private static Logger logger = LogManager.getLogger(TripServiceAvailedServiceImpl.class);

	@Autowired
	private TripServiceAvailedDAO tripServiceAvailedDAO;
	
	@Override
	public TripServiceAvailedVO tripAvailedDashboardFilter(DataFilterParamsVO dataFilterParamsVO) {
		logger.info("tripAvailedDashboard() -> Trip Service Availed Dashboard for specific Date Service call...");
		TripServiceAvailedVO tripServiceAvailedVO = null;
		List<TripServiceAvailedDashboardViewEntity> tripAvailedDashboardList = tripServiceAvailedDAO.tripAvailedDashboardFilter(dataFilterParamsVO);
		
		if(!CabServiceUtil.isNULL(tripAvailedDashboardList)){
			tripServiceAvailedVO = new TripServiceAvailedVO();
			tripServiceAvailedVO.setTripAvaileddashboardList(tripAvailedDashboardList);
		}
		
		return tripServiceAvailedVO;
	}

	@Override
	public String addUpdateTripServiceAvailed(TripServiceDetailsVO tripServiceDetailsVO) {
		logger.info("addUpdateTripServiceAvailed() -> Trip Service Availed Add Update Service call...");
		
		List<TripServiceAvailedEntity> tripServiceAvailedList = getTripAvailedEntityList(tripServiceDetailsVO);
		
		//TripServiceAvailedVO tripServiceAvailedVO = getTripHeaderDetails(tripServiceDetailsVO);
		
		return tripServiceAvailedDAO.addUpdateTripServiceAvailed(tripServiceAvailedList,tripServiceDetailsVO);
	}

	@Override
	public List<TripServiceAvailedDashboardViewEntity> tripDetails(Long tripHeaderId) {
		logger.info("tripDetails() -> Trip Service Availed Details Service call...");
		
		return tripServiceAvailedDAO.tripDetails(tripHeaderId);
	}

	@Override
	public List<TripHeaderEntity> getTripList(Long tripHeaderId) {
		logger.info("getTripList() -> Trip List Service call...");
		
		return tripServiceAvailedDAO.getTripList(tripHeaderId);
	}
	
	private List<TripServiceAvailedEntity> getTripAvailedEntityList(TripServiceDetailsVO tripServiceDetailsVO){
		
		List<TripServiceAvailedEntity> tripAvailedList= new ArrayList<>();
		
		tripServiceDetailsVO.getTripServiceAvailedList().forEach(serviceAvailed -> {
			
			tripAvailedList.add(getAvailedEntity(serviceAvailed));
			
		});
		
		return tripAvailedList;
	}

	private TripServiceAvailedEntity getAvailedEntity(TripServiceAvailedVO serviceAvailed) {
		TripServiceAvailedEntity tripAvailed = new TripServiceAvailedEntity();
		
		TripHeaderEntity tripHeader = new TripHeaderEntity();
		tripHeader.setTripHeaderId(serviceAvailed.getTripHeaderId());
		
		RequestDetailEntity requestDetail = new RequestDetailEntity();
		requestDetail.setRequestDetailId(serviceAvailed.getReqDetailId());
		
		tripAvailed.setTripHeader(tripHeader);
		tripAvailed.setRequestDetail(requestDetail);
		tripAvailed.setTripAvailedId(serviceAvailed.getTripAvailedId());
		tripAvailed.setActualTime(serviceAvailed.getActualTime());
		tripAvailed.setEmployeeId(serviceAvailed.getEmployeeId());
		tripAvailed.setIsTripAvailed(serviceAvailed.getIsTripAvailed());
		tripAvailed.setUpdatedBy(serviceAvailed.getEmployeeId());
		
		return tripAvailed;
	}
}
