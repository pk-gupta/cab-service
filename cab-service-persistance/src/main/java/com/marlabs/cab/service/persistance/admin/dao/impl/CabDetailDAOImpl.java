package com.marlabs.cab.service.persistance.admin.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.persistance.admin.dao.CabDetailDAO;
import com.marlabs.cab.service.persistance.entity.CabDetailEntity;
import com.marlabs.cab.service.persistance.entity.CabOwnerEntity;
import com.marlabs.cab.service.persistance.entity.OfficeCityEntity;
import com.marlabs.cab.service.persistance.sql.mapper.EmployeeRequestSQLMapper;
import com.marlabs.cab.service.persistance.vo.CabDetailVO;

@Repository
@Transactional
public class CabDetailDAOImpl implements CabDetailDAO{

	private static Logger logger = LogManager.getLogger(CabDetailDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<CabOwnerEntity> cabDetailDashboard() {
		logger.info("cabDetailDashboard() -> Cab Detail Dashboard DAO call...");
		
		Session session = null;
		List<CabOwnerEntity> cabOwnerEntityList = null;
		
		try{
			session = sessionFactory.openSession();
			String cabOwnerEntity = "FROM CabOwnerEntity WHERE  active=:active";
			Query queryString = session.createQuery(cabOwnerEntity);
			queryString.setString("active", "true");
			cabOwnerEntityList = queryString.list();
			logger.info("cabDetailDashboard() -> Cab Detail Dashboard DAO: Success");
		}catch(Exception exception){
			logger.error("cabDetailDashboard() -> ERROR: "+ exception.getCause());
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return cabOwnerEntityList;
	}

	@Override
	public String addUpdateCabOwner(CabOwnerEntity cabOwnerEntity) {
		logger.info("addUpdateCabOwner() -> New Cab Owner DAO call...");
		Session session = null;
		Transaction transaction = null;
		String status = null;
		try{
			
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
		    session.saveOrUpdate(cabOwnerEntity);
		    transaction.commit();
			status = Constants.STATUS_SUCCESS;
			logger.info("addUpdateCabOwner() -> New Cab Owner DAO: Success");
		}catch(Exception exception){
			logger.error("addUpdateCabOwner() -> ERROR: "+ exception.getCause());
			status = Constants.STATUS_FAILED;
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CabOwnerEntity editCabOwner(Long cabOwnerId) {
		logger.info("editCabOwner() -> Edit Cab Owner DAO call...");
		
		Session session = null;
		CabOwnerEntity cabOwnerEntity = null;
		
		try{
			session = sessionFactory.openSession();
			Criteria ownerQuery = session.createCriteria(CabOwnerEntity.class);
			ownerQuery.add(Restrictions.eq("cabOwnerId", cabOwnerId));
			cabOwnerEntity = (CabOwnerEntity) ownerQuery.uniqueResult();
			
			logger.info("editCabOwner() -> Edit Cab Owner DAO: Success");
		}catch(Exception exception){
			logger.error("editCabOwner() -> ERROR: "+ exception.getCause());
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return cabOwnerEntity;	
	}

	@Override
	public String addUpdateCabDetail(CabDetailEntity cabDetailEntity) {
		logger.info("addUpdateCabDetail() -> New Cab Detail DAO call...");
		Session session = null;
		Transaction transaction = null;
		String status = null;
		
		try{
			
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(cabDetailEntity);
			transaction.commit();
			status = Constants.STATUS_SUCCESS;
			logger.info("addUpdateCabDetail() -> New Cab Detail DAO: Success");
		}catch(Exception exception){
			logger.error("addUpdateCabDetail() -> ERROR: "+ exception.getCause());
			status = Constants.STATUS_FAILED;
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CabDetailEntity editCabDetail(Long cabDetailId) {
		logger.info("editCabDetail() -> Edit Cab Detail DAO call...");
		
		Session session = null;
		CabDetailEntity cabDetailEntity = null;
		//Set officeCity = new HashSet();
		
		try{
			session = sessionFactory.openSession();
			
			Criteria detailQuery = session.createCriteria(CabDetailEntity.class);
			detailQuery.add(Restrictions.eq("cabDetailId", cabDetailId));
			cabDetailEntity = (CabDetailEntity) detailQuery.uniqueResult();
			
			logger.info("editCabDetail() -> Edit Cab Detail DAO: Success");
		}catch(Exception exception){
			logger.error("editCabDetail() -> ERROR: "+ exception.getCause());
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return cabDetailEntity;	
	}

	@Override
	public String updateCabOwnerStatus(CabOwnerEntity cabOwnerEntity) {
		logger.info("updateCabOwnerStatus() -> Cab Owner status update DAO call...");
		Session session = null;
		Transaction transaction = null;
		String status = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(cabOwnerEntity);
			transaction.commit();
			status = Constants.STATUS_SUCCESS;
			logger.info("updateCabOwnerStatus() -> Cab Owner status update DAO: Success");
		} catch (Exception exception) {
			logger.error("updateCabOwnerStatus() -> ERROR: " + exception.getCause());
			status = Constants.STATUS_FAILED;
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return status;
	}

	@Override
	public String updateCabDetailStatus(CabDetailEntity cabDetailEntity) {
		logger.info("updateCabDetailStatus() -> Cab Detail status update DAO call...");
		Session session = null;
		Transaction transaction = null;
		String status = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(cabDetailEntity);
			transaction.commit();
			status = Constants.STATUS_SUCCESS;
			logger.info("updateCabDetailStatus() -> Cab Detail status update DAO: Success");
		} catch (Exception exception) {
			logger.error("updateCabDetailStatus() -> ERROR: " + exception.getCause());
			status = Constants.STATUS_FAILED;
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return status;
	}

}
