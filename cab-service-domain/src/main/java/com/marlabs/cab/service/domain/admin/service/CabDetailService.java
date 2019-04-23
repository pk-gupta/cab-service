package com.marlabs.cab.service.domain.admin.service;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.CabDetailEntity;
import com.marlabs.cab.service.persistance.entity.CabOwnerEntity;
import com.marlabs.cab.service.persistance.vo.CabDetailDashboardVO;
import com.marlabs.cab.service.persistance.vo.CabOwnerVO;
import com.marlabs.cab.service.persistance.vo.CabDetailVO;

public interface CabDetailService {

	public List<CabOwnerVO>  cabDetailDashboard();
	
	public String addUpdateCabOwner(CabOwnerEntity cabOwnerEntity);
	
	public CabOwnerEntity editCabOwner(Long cabOwnerId);
	
	public String addUpdateCabDetail(CabDetailVO cabDetailVO);
	
	public CabDetailVO editCabDetail(Long cabDetailId);
	
	public String updateCabOwnerStatus(CabOwnerEntity cabOwnerEntity);
	
	public String updateCabDetailStatus(CabDetailEntity cabDetailEntity);
	
}
