package com.marlabs.cab.service.domain.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlabs.cab.service.domain.manager.service.ManagerRosterService;
import com.marlabs.cab.service.persistance.manager.dao.ManagerRosterDAO;

@Service
public class ManagerRosterServiceImpl implements ManagerRosterService{
	
	@Autowired
	private ManagerRosterDAO managerRosterDAO;

}
