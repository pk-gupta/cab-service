package com.marlabs.cab.service.persistance.master.data.dao.impl;

import java.util.List;

import javax.persistence.Entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.persistance.entity.EmployeeLandmarkEntity;
import com.marlabs.cab.service.persistance.entity.OfficeBranchEntity;
import com.marlabs.cab.service.persistance.entity.OfficeCityEntity;
import com.marlabs.cab.service.persistance.entity.ReasonEntity;
import com.marlabs.cab.service.persistance.entity.ServiceAndTimeEntity;
import com.marlabs.cab.service.persistance.master.data.dao.MasterDataDAO;

@Repository
@Transactional
public class MasterDataDAOImpl implements MasterDataDAO {
	
	private static Logger logger = LogManager.getLogger(MasterDataDAOImpl.class);
		
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
    Environment environment;

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeLandmarkEntity> getLandmarks() {
		
		logger.info("getLandmarks() -> Landmark Master data DAO call...");
		
		List<?> landmarkList = getEntityList(EmployeeLandmarkEntity.class);
		
		return landmarkList == null ? null : (List<EmployeeLandmarkEntity>) landmarkList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReasonEntity> getReasons() {
		logger.info("getReasons() -> Reasons Master data DAO call...");
		
		List<?> reasonList =getEntityList(ReasonEntity.class);
		
		return reasonList == null ? null : (List<ReasonEntity>) reasonList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceAndTimeEntity> getServiceAndTime() {
		logger.info("getServiceAndTime() -> Service And Time Master data DAO call...");
		
		List<?> serviceTimeList = getEntityList(ServiceAndTimeEntity.class);
		
		return serviceTimeList == null ? null : (List<ServiceAndTimeEntity>) serviceTimeList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeBranchEntity> getOfficeBranches() {
		logger.info("getOfficeBranches() -> Office Branches Master data DAO call...");
		
		List<?> officeBranchList = getEntityList(OfficeBranchEntity.class);
		
		return officeBranchList == null ? null : (List<OfficeBranchEntity>) officeBranchList;
	}
	
	@Override
	public List<ServiceAndTimeEntity> getLogoutTime() {
		logger.info("getLogoutTime() -> Logout Time Master data DAO call...");
		
		List<?> serviceTimeList = getLoginLogoutTime(ServiceAndTimeEntity.class, "Drop");
		
		return serviceTimeList == null ? null : (List<ServiceAndTimeEntity>) serviceTimeList;
	}

	@Override
	public List<ServiceAndTimeEntity> getLoginTime() {
		logger.info("getLogoutTime() -> Login Time Master data DAO call...");
		
		List<?> serviceTimeList = getLoginLogoutTime(ServiceAndTimeEntity.class, "Pick-Up");
		
		return serviceTimeList == null ? null : (List<ServiceAndTimeEntity>) serviceTimeList;
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeCityEntity> getOfficeCities() {
		logger.info("getOfficeCities() -> Office Cities Master data DAO call...");
		
		List<?> officeCityList = getEntityList(OfficeCityEntity.class);
		
		return officeCityList == null ? null : (List<OfficeCityEntity>) officeCityList;
	}
	
	@SuppressWarnings("unchecked")
	private List<? extends Entity>  getEntityList(Class<?> entityClass){
		logger.info("getEntityList() -> Fetching Master data for "+entityClass+"...");
		
		Session session = null;
		Criteria criteria = null;
		List<? extends Entity> entityList = null;
		
		try{
			session = sessionFactory.openSession();
			String entityQuery = "FROM "+entityClass.getName()+" as entity WHERE entity.active = :isactive";
			Query queryString = session.createQuery(entityQuery);
			queryString.setString("isactive", "true");
			
			entityList = queryString.list();
			
			logger.info("getEntityList() -> Fetching Master data for "+entityClass+": Success");
		}catch(RuntimeException exception){
			logger.error("getEntityList() -> ERROR: "+ exception.getCause());
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return entityList;
	}
	
	@SuppressWarnings("unchecked")
	private List<? extends Entity>  getLoginLogoutTime(Class<?> entityClass,String serviceType){
		logger.info("getLoginLogoutTime() -> Fetching Master data for "+serviceType+"...");
		
		Session session = null;
		Criteria criteria = null;
		List<? extends Entity> entityList = null;
		
		try{
			session = sessionFactory.openSession();
			String entityQuery = "FROM "+entityClass.getName()+" as entity WHERE entity.active = :isactive AND entity.serviceType = :servicetype";
			Query queryString = session.createQuery(entityQuery);
			queryString.setString("isactive", "true");
			queryString.setString("servicetype", serviceType);
			
			entityList = queryString.list();
			
			logger.info("getLoginLogoutTime() -> Fetching Master data for "+serviceType+": Success");
		}catch(RuntimeException exception){
			logger.error("getLoginLogoutTime() -> ERROR: "+ exception.getCause());
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return entityList;
	}
}
