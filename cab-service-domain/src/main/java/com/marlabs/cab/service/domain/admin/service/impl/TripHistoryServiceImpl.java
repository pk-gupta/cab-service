package com.marlabs.cab.service.domain.admin.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.domain.admin.service.TripHistoryService;
import com.marlabs.cab.service.persistance.admin.dao.TripHistoryDAO;
import com.marlabs.cab.service.persistance.entity.SingleTripDetailsViewEntity;
import com.marlabs.cab.service.persistance.entity.TripHistoryDashboardViewEntity;
import com.marlabs.cab.service.persistance.vo.DataFilterParamsVO;
import com.marlabs.cab.service.persistance.vo.TripHistoryVO;

@Service
public class TripHistoryServiceImpl implements TripHistoryService {
	
	private static Logger logger = LogManager.getLogger(TripHistoryServiceImpl.class);

	@Autowired
	private TripHistoryDAO tripHistoryDAO;
	

	@Override
	public TripHistoryVO tripHistoryDashboardFilter(DataFilterParamsVO dataFilterParamsVO) {
		logger.info("tripHistoryDashboardFilter() -> Trip History Dashboard Filter Service call...");
		
		TripHistoryVO tripHistoryVO = null;
		List<TripHistoryDashboardViewEntity> tripHistoryDashboardList = tripHistoryDAO.tripHistoryDashboardFilter(dataFilterParamsVO);
		
		if(!CabServiceUtil.isNULL(tripHistoryDashboardList)){
			tripHistoryVO = new TripHistoryVO();
			tripHistoryVO.setTripHistoryDashboardList(tripHistoryDashboardList);
		}
		
		return tripHistoryVO;
	}

	@Override
	public TripHistoryVO getSingleTripDetails(Long tripHeaderId) {
		logger.info("getSingleTripDetails() -> Single Trip Details Service call...");
		
		TripHistoryVO tripHistoryVO = null;
		List<SingleTripDetailsViewEntity> singleTripDetailsList = tripHistoryDAO.getSingleTripDetails(tripHeaderId);
		
		if(!CabServiceUtil.isNULL(singleTripDetailsList)){
			tripHistoryVO = new TripHistoryVO();
			tripHistoryVO.setSingleTripDetailsList(singleTripDetailsList);
		}
		
		return tripHistoryVO;
		
	}

}
