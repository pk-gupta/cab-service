package com.marlabs.cab.service.persistance.search.dao;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.EmployeeView;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;

public interface SearchDAO {
	
	public List<RequestHeaderEntity> searchLocalData(String searchText);
	
	public List<EmployeeView> searchViewData(String searchText);

}
