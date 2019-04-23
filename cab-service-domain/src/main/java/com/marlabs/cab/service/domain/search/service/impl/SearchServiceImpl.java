package com.marlabs.cab.service.domain.search.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlabs.cab.service.domain.parallel.activity.service.ParallelActivityService;
import com.marlabs.cab.service.domain.search.service.SearchService;
import com.marlabs.cab.service.persistance.entity.EmployeeView;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	ParallelActivityService parallelActivityService;

	@Override
	public List<RequestHeaderEntity> searchText(String searchText) {

		//TO DO
		List<RequestHeaderEntity> localDataSearchResults = parallelActivityService.searchInLocalData(searchText);
		
		List<EmployeeView> viewSearchResults = parallelActivityService.searchInEmployeeView(searchText);
		
		return null;
	}

}
