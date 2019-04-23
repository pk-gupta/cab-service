package com.marlabs.cab.service.persistance.common.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.persistance.common.dao.CustomQueryDAO;
import com.marlabs.cab.service.persistance.entity.OfficeBranchEntity;
import com.marlabs.cab.service.persistance.sql.mapper.CityBranchSQLMapper;
import com.marlabs.cab.service.persistance.sql.mapper.EmployeeLandmarkSQLMapper;
import com.marlabs.cab.service.persistance.sql.mapper.EmployeeProjectManagerSQLMapper;
import com.marlabs.cab.service.persistance.sql.mapper.OfficeBranchSQLMapper;
@Repository
@Transactional
public class CustomQueryDAOImpl implements CustomQueryDAO {
	
	private static Logger logger = LogManager.getLogger(CustomQueryDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<CityBranchSQLMapper> getCityBranchDetails() {
		logger.info("getCityBranchDetails() ->  Office Branch with City DAO call... ");

		Session session = null;

		List<CityBranchSQLMapper> officeBranchCityList = null;

		try {
			session = sessionFactory.openSession();
	     	String customeQuery = "SELECT oc.OFFICE_CITY_ID as officeCityId, oc.OFFICE_CITY_NAME as officeCityName, ob.OFFICE_BRANCH_CODE as officeBranchCode, ob.OFFICE_BRANCH_NAME as officeBranchName FROM OFFICE_BRANCH ob JOIN OFFICE_CITY oc ON oc.OFFICE_CITY_ID = ob.FK_OFFICE_CITY_ID ORDER BY oc.OFFICE_CITY_ID";
			
	     	SQLQuery query = session.createSQLQuery(customeQuery);
	     	
	     	query.setResultTransformer(Transformers.aliasToBean(CityBranchSQLMapper.class));
   	
	     	officeBranchCityList = query.addScalar("officeCityId", StandardBasicTypes.LONG)
									    .addScalar("officeCityName", StandardBasicTypes.STRING)
									    .addScalar("officeBranchCode", StandardBasicTypes.STRING)
									    .addScalar("officeBranchName", StandardBasicTypes.STRING)
									    .list();
	     	
	     	/*officeBranchCityList=((SQLQuery) query.setResultTransformer(Transformers.aliasToBean(CityBranchSQLMapper.class)))
	     										  .addScalar("officeCityId", StandardBasicTypes.LONG)
	     										  .addScalar("officeCityName", StandardBasicTypes.STRING)
	     										  .addScalar("officeBranchCode", StandardBasicTypes.STRING)
	     										  .addScalar("officeBranchName", StandardBasicTypes.STRING)
	     										  .list();*/
	     	
	     	logger.info("getCityBranchDetails() -> Office Branch with City DAO call: Success");
		} catch (Exception exception) {
			logger.error("getCityBranchDetails() -> ERROR: " + exception.getCause());
			logger.error(CabServiceUtil.getStackTrace(exception).toString());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return officeBranchCityList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeLandmarkSQLMapper> getEmployeeLandmarkDetails(String employeeId) {
		logger.info("getEmployeeLandmarkDetails() ->  Employee Landmark DAO call... ");

		Session session = null;

		List<EmployeeLandmarkSQLMapper> employeeLandmarkList = null;

		try {
			session = sessionFactory.openSession();
	     	String customeQuery = "SELECT el.EMPLOYEE_LANDMARK_ID as employeeLandmarkId,el.EMPLOYEE_ID as employeeId,cm.CITY_LANDMARK_NAME as employeeLandmarkName FROM EMPLOYEE_LANDMARK el JOIN CITY_LANDMARK_MASTER cm ON cm.CITY_LANDMARK_MASTER_ID = el.FK_CITY_LANDMARK_MASTER_ID WHERE el.EMPLOYEE_ID=:employeeid and el.ACTIVE='true'";
			
	     	SQLQuery query = session.createSQLQuery(customeQuery);
	     	
	    	query.setParameter("employeeid", employeeId)
	    		 .setResultTransformer(Transformers.aliasToBean(EmployeeLandmarkSQLMapper.class));
	    	
	    	employeeLandmarkList = query.addScalar("employeeLandmarkId", StandardBasicTypes.LONG)
									    .addScalar("employeeId", StandardBasicTypes.STRING)
									    .addScalar("employeeLandmarkName", StandardBasicTypes.STRING)
									    .list();
	     
	     	logger.info("getEmployeeLandmarkDetails() -> Employee Landmark DAO call: Success");
		} catch (Exception exception) {
			logger.error("getEmployeeLandmarkDetails() -> ERROR: " + exception.getCause());
			logger.error(CabServiceUtil.getStackTrace(exception).toString());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return employeeLandmarkList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeProjectManagerSQLMapper> getEmployeeProjectManager(long requestId) {
			logger.info("getEmployeeProjectManager() -> Employee Project Manager DAO Call...");

			Session session = null;
			List<EmployeeProjectManagerSQLMapper> employeeProjectManagerList = null;

			try {
				session = sessionFactory.openSession();

				String entityQuery = "SELECT emp.EmployeeId as employeeId, emp.GlobalEmpId AS empGlobalEmpId, emp.FirstName AS empFirstName, emp.MiddleName AS empMiddleName, emp.LastName AS empLastName, emp.Mobile AS empMobile, emp.OffEmailID AS empOffEmailID, mgr.FirstName AS mgrFirstName, mgr.MiddleName AS mgrMiddleName, mgr.LastName AS mgrLastName, mgr.OffEmailID AS mgrOffEmailID, mgr.EmployeeId as mgrEmployeeId, mgr.GlobalEmpId AS mgrGlobalEmpId FROM employee_details_view emp JOIN REQUEST_HEADER rq ON rq.EMPLOYEE_ID = emp.GlobalEmpId JOIN employee_details_view mgr ON mgr.GlobalEmpId = rq.MANAGER_ID WHERE rq.REQ_HEADER_ID = :requestId";

				SQLQuery query = session.createSQLQuery(entityQuery);
				
				employeeProjectManagerList = query.setParameter("requestId", requestId)
												   .setResultTransformer(Transformers.aliasToBean(EmployeeProjectManagerSQLMapper.class))
												   .list();

				logger.info("getEmployeeProjectManager() -> Employee Project Manager DAO Call: Success");
			} catch (RuntimeException exception) {
				logger.error("getEmployeeProjectManager() -> ERROR: " + exception.getCause());
			} finally {
				if (!CabServiceUtil.isNULL(session))
					session.close();
			}

			return employeeProjectManagerList;
		}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeBranchSQLMapper> getCitySpecificOfficeBranches(String officeBranchCode) {		
		logger.info("getCitySpecificOfficeBranches() -> City Specific Office Branches DAO Call...");

		Session session = null;
		
		List<OfficeBranchSQLMapper> officeBranchList = null;

		try {
			session = sessionFactory.openSession();

			String entityQuery="SELECT offbc.OFFICE_BRANCH_NAME AS officeBranchName, offbc.OFFICE_BRANCH_CODE AS officeBranchCode, offc.OFFICE_CITY_NAME AS officeCityName FROM OFFICE_BRANCH offb JOIN OFFICE_BRANCH offbc ON offbc.FK_OFFICE_CITY_ID=offb.FK_OFFICE_CITY_ID JOIN OFFICE_CITY offc ON offc.OFFICE_CITY_ID=offb.FK_OFFICE_CITY_ID WHERE offb.OFFICE_BRANCH_CODE=:officeBranchCode";
			
			SQLQuery query = session.createSQLQuery(entityQuery);

			query.setParameter("officeBranchCode", officeBranchCode);			
			
			officeBranchList=query.setResultTransformer(Transformers.aliasToBean(OfficeBranchSQLMapper.class)).list();
			
			logger.info("getCitySpecificOfficeBranches() -> City Specific Office Branches DAO Call:Success");
		} catch (RuntimeException exception) {
			logger.error("getCitySpecificOfficeBranches() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return officeBranchList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeBranchSQLMapper> getLandmarkSpecificOfficeBranches(String employeeId) {
		logger.info("getLandmarkSpecificOfficeBranches() -> Landmark Specific Office Branches DAO Call...");

		Session session = null;
		
		List<OfficeBranchSQLMapper> officeBranchList = null;

		try {
			session = sessionFactory.openSession();
			
			String entityQuery="SELECT offb.OFFICE_BRANCH_NAME AS officeBranchName, offb.OFFICE_BRANCH_CODE AS officeBranchCode, offc.OFFICE_CITY_NAME AS officeCityName FROM EMPLOYEE_LANDMARK emplmark JOIN OFFICE_BRANCH offb ON offb.FK_OFFICE_CITY_ID=emplmark.FK_OFFICE_CITY_ID JOIN OFFICE_CITY offc ON offc.OFFICE_CITY_ID=emplmark.FK_OFFICE_CITY_ID WHERE emplmark.EMPLOYEE_ID=:employeeId AND emplmark.ACTIVE='true'";
			
			SQLQuery query = session.createSQLQuery(entityQuery);

			query.setParameter("employeeId", employeeId);

			officeBranchList= query.setResultTransformer(Transformers.aliasToBean(OfficeBranchSQLMapper.class)).list();
			logger.info("getLandmarkSpecificOfficeBranches() -> Landmark Specific Office Branches DAO Call:Success");
		} catch (RuntimeException exception) {
			logger.error("getCitySpecificOfficeBranches() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return officeBranchList;
	}	
}
	