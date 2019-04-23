package com.marlabs.cab.service.persistance.manager.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marlabs.cab.service.persistance.manager.dao.ManagerRosterDAO;

@Repository
@Transactional
public class ManagerRosterDAOImpl implements ManagerRosterDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
}
