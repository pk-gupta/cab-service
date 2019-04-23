package com.marlabs.cab.service.domain.search.service;

import java.util.List;

import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;

public interface SearchService {
	
	public List<RequestHeaderEntity> searchText(String searchText);
	
	//public List<EmployeeView> searchViewData(String searchText);

}
