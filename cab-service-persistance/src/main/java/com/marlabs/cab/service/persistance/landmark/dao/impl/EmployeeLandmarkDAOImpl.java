package com.marlabs.cab.service.persistance.landmark.dao.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.marlabs.cab.service.common.exception.CabServiceTransactionException;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.persistance.entity.CityLandmarkMasterEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeLandmardDashboardEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeLandmarkEntity;
import com.marlabs.cab.service.persistance.entity.OfficeCityEntity;
import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;
import com.marlabs.cab.service.persistance.landmark.dao.EmployeeLandmarkDAO;
import com.marlabs.cab.service.persistance.sql.mapper.ActiveEmployeeLandmarkSQLMapper;
import com.marlabs.cab.service.persistance.vo.EmployeeLandmarkVO;

@Repository
@Transactional
public class EmployeeLandmarkDAOImpl implements EmployeeLandmarkDAO {

	private static Logger logger = LogManager.getLogger(EmployeeLandmarkDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public EmployeeLandmarkEntity processEmployeeLandmarkRequest(EmployeeLandmarkEntity employeeLandmark) {
		logger.info("processEmployeeLandmarkRequest() -> New Employee Landmark Request DAO call...");
		Session session = null;
		Transaction transaction = null;
		EmployeeLandmarkEntity employeeLandmarkEntity = null;
		List<ActiveEmployeeLandmarkSQLMapper> activeEmployeeLandmarkList = null;	

		try {
			session = sessionFactory.openSession();
            String entityQuery="SELECT el.activeLandmarkCount AS activeLandmarkCount, rh.recordAvailableCount AS requestAvailableCount, el.EMPLOYEE_LANDMARK_ID AS employeeLandmarkId FROM ( SELECT count(*) AS activeLandmarkCount, EMPLOYEE_ID,EMPLOYEE_LANDMARK_ID FROM EMPLOYEE_LANDMARK WHERE ACTIVE='true' GROUP BY EMPLOYEE_ID,EMPLOYEE_LANDMARK_ID ) el LEFT JOIN ( SELECT EMPLOYEE_ID, count(*) as recordAvailableCount FROM REQUEST_HEADER WHERE (TO_DATE >= GETDATE () AND APPROVE_STATUS='Approved') OR (TO_DATE >= GETDATE () AND APPROVE_STATUS='Pending') GROUP BY EMPLOYEE_ID, APPROVE_STATUS ) rh ON rh.EMPLOYEE_ID = el.EMPLOYEE_ID WHERE el.EMPLOYEE_ID =:employeeId";
			
			SQLQuery query = session.createSQLQuery(entityQuery);
			query.setParameter("employeeId", employeeLandmark.getEmployeeId());
			activeEmployeeLandmarkList=query.setResultTransformer(Transformers.aliasToBean(ActiveEmployeeLandmarkSQLMapper.class)).list();				
		if(!activeEmployeeLandmarkList.isEmpty()){
		if(!CabServiceUtil.isNULL(activeEmployeeLandmarkList.get(0).getRequestAvailableCount())){
		    employeeLandmarkEntity=new EmployeeLandmarkEntity();
		    employeeLandmarkEntity.setEmployeeLandmarkId(activeEmployeeLandmarkList.get(0).getEmployeeLandmarkId().longValue());
		    return employeeLandmarkEntity;				
		}else if(!CabServiceUtil.isNULL(activeEmployeeLandmarkList.get(0).getActiveLandmarkCount())){
			transaction = session.beginTransaction();
			EmployeeLandmarkEntity empLandmark = session.get(EmployeeLandmarkEntity.class,activeEmployeeLandmarkList.get(0).getEmployeeLandmarkId().longValue());
			empLandmark.setActive("false");
			empLandmark.setIsDeleted("true");
			session.update(empLandmark);
			transaction.commit();	
			}
			   }						
			transaction = session.beginTransaction();
			employeeLandmark.setActive("true");
			employeeLandmark.setIsDeleted("false");
			employeeLandmarkEntity = (EmployeeLandmarkEntity) session.merge(employeeLandmark);
			transaction.commit();
			logger.info("processEmployeeLandmarkRequest -> New Employee Landmark Request DAO: Success");
		} catch (Exception exception) {
			logger.error("processEmployeeLandmarkRequest -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
		return employeeLandmarkEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RequestDetailEntity> updateEmployeeLandmarkStatus(EmployeeLandmarkVO employeelandmarkVO) {
		logger.info("updateEmployeeLandmarkStatus() -> Status Update Employee Landmark DAO call...");
		Session session = null;
		Transaction transaction = null;
		List<RequestDetailEntity> requestDetailEntityList = null;
		EmployeeLandmarkEntity employeeLandmarkEntity = null;
		
		try {
			session = sessionFactory.openSession();
			
			String entityQuery = "SELECT rd.* FROM REQUEST_HEADER rh JOIN REQUEST_DETAIL rd ON rd.FK_REQ_HEADER_ID=rh.REQ_HEADER_ID WHERE rd.SERVICE_DATE >= GETDATE () AND rd.IS_CANCELLED='false' AND rh.APPROVE_STATUS!='Draft' AND rh.EMPLOYEE_ID=:employeeId ORDER BY rd.SERVICE_DATE";
			
			SQLQuery query = session.createSQLQuery(entityQuery);
			query.setParameter("employeeId", employeelandmarkVO.getEmployeeId());
			
			requestDetailEntityList = query.addEntity(RequestDetailEntity.class)
										   .list();
			
			if (requestDetailEntityList.isEmpty()) {
				transaction = session.beginTransaction();
				
				employeeLandmarkEntity = session.get(EmployeeLandmarkEntity.class, employeelandmarkVO.getEmployeeLandmarkId());
				employeeLandmarkEntity.setActive(employeelandmarkVO.getActive());
				session.update(employeeLandmarkEntity);
				
				transaction.commit();
			}
			
			logger.info("updateEmployeeLandmarkStatus() -> Status Update Employee Landmark DAO: Success");
		} catch (Exception exception) {
			logger.error("updateEmployeeLandmarkStatus() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return requestDetailEntityList;
	}

	@Override
	public String updateEmployeeLandmarkRequest(EmployeeLandmarkVO employeelandmarkVO) {
		logger.info("updateEmployeeLandmarkRequest() -> Update Employee Landmark Request DAO call...");
		Session session = null;
		Transaction transaction = null;
		String status = null;
		CityLandmarkMasterEntity cityLandmarkMaster=null;
		OfficeCityEntity officeCityEntity=null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			
			cityLandmarkMaster=new CityLandmarkMasterEntity();
			cityLandmarkMaster.setCityLandmarkMasterId(employeelandmarkVO.getCityLandmarkMasterId());
			
			officeCityEntity=new OfficeCityEntity();
			officeCityEntity.setOfficeCityId(employeelandmarkVO.getOfficeCityId());
			
			EmployeeLandmarkEntity employeeLandmarkEntity = session.get(EmployeeLandmarkEntity.class,
					employeelandmarkVO.getEmployeeLandmarkId());
			//employeeLandmarkEntity.setCityLandmarkMaster(cityLandmarkMaster);
			//employeeLandmarkEntity.setOfficeCityId(employeelandmarkVO.getOfficeCityId());

			session.update(employeeLandmarkEntity);
			transaction.commit();
			status = Constants.REQUEST_STATUS_SUCCESS;
			logger.info("update Employee Landmark Request() -> Update Employee Landmark Request DAO: Success");
		} catch (Exception exception) {
			logger.error("updateEmployeeLandmarkRequest() -> ERROR: " + exception.getCause());
			status = Constants.REQUEST_STATUS_FAILED; // //
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeLandmardDashboardEntity> employeeLandmarkRequestDashboard() {
		logger.info("employeeLandmarkRequestDashboard() -> Employee Landmark Request Dashboard DAO call...");

		Session session = null;
		List<EmployeeLandmardDashboardEntity> employeeLandmarkEntityList = null;

		try {
			session = sessionFactory.openSession();
			String landmarkEntityrQuery = "FROM EmployeeLandmardDashboardEntity WHERE  active=:status";
			Query queryString = session.createQuery(landmarkEntityrQuery);
			queryString.setString("status","true");
			employeeLandmarkEntityList = queryString.list();
			logger.info("employeeLandmarkRequestDashboard() -> Employee Landmark Request Dashboard DAO: Success");
		} catch (Exception exception) {
			logger.error("employeeLandmarkRequestDashboard() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return employeeLandmarkEntityList;
	}

	@Override
	public String deleteEmployeeLandmark(long employeeLandmarkId) {
		logger.info("deleteEmployeeLandmark() -> Delete Employee Landmark DAO call...");
		Session session = null;
		Transaction transaction = null;
		String status = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			EmployeeLandmarkEntity employeeLandmarkEntity = session.get(EmployeeLandmarkEntity.class,
					employeeLandmarkId);
			employeeLandmarkEntity.setIsDeleted("true");
			session.update(employeeLandmarkEntity);
			transaction.commit();
			status = Constants.REQUEST_STATUS_SUCCESS;
			logger.info("deleteEmployeeLandmark() -> Delete Employee Landmark DAO: Success");
		} catch (Exception exception) {
			logger.error("deleteEmployeeLandmark() -> ERROR: " + exception.getCause());
			status = Constants.REQUEST_STATUS_FAILED;
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return status;
	}

	@Override
	public boolean isEmployeeLandmarkExists(String employeeId) {
		logger.info("isEmployeeLandmarkExists() -> Checking for Employee Landmark Exists : DAO call...");
		Session session = null;
		boolean isLandmarkExists = false;
		Object employeeLandmark = null;
		try {
			session = sessionFactory.openSession();
			employeeLandmark = session.createCriteria(EmployeeLandmarkEntity.class)
								      .add(Restrictions.eq("employeeId",employeeId))
								      .add(Restrictions.eq("active","true"))
								      .add(Restrictions.eq("isDeleted","false"))
								      .uniqueResult();
			
			if(Optional.ofNullable(employeeLandmark).isPresent()){
				isLandmarkExists = true;			
			}
			
			
			logger.info("isEmployeeLandmarkExists() -> Checking for Employee Landmark Exists : Success");
		} catch (Exception exception) {
			logger.error("isEmployeeLandmarkExists() -> ERROR: " + exception.getCause());
			throw new CabServiceTransactionException("Server Error : " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return isLandmarkExists;
	}
	
}
