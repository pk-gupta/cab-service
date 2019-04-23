package com.marlabs.cab.service.persistance.search.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marlabs.cab.service.persistance.entity.EmployeeView;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;
import com.marlabs.cab.service.persistance.search.dao.SearchDAO;

@Repository
@Transactional
public class SearchDAOImpl implements SearchDAO {

	@Override
	public List<RequestHeaderEntity> searchLocalData(String searchText) {
		
		//TO DO
		
		return null;
	}

	@Override
	public List<EmployeeView> searchViewData(String searchText) {
		
		// TO DO
		
		return null;
	}

}
