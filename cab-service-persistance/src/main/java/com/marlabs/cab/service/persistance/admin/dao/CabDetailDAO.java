package com.marlabs.cab.service.persistance.admin.dao;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.CabDetailEntity;
import com.marlabs.cab.service.persistance.entity.CabOwnerEntity;

public interface CabDetailDAO {

public List<CabOwnerEntity> cabDetailDashboard();
	
	public String addUpdateCabOwner(CabOwnerEntity cabOwnerEntity);
	
	public CabOwnerEntity editCabOwner(Long cabOwnerId);
	
	public String addUpdateCabDetail(CabDetailEntity cabDetailEntity);
	
	public CabDetailEntity editCabDetail(Long cabDetailId);
	
	public String updateCabOwnerStatus(CabOwnerEntity cabOwnerEntity);
	
	public String updateCabDetailStatus(CabDetailEntity cabDetailEntity);
}
