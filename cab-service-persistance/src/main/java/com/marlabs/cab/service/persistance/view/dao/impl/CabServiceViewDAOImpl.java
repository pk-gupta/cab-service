package com.marlabs.cab.service.persistance.view.dao.impl;

import java.util.List;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.persistance.entity.EmployeeView;
import com.marlabs.cab.service.persistance.entity.CityLandmarkMasterEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeLandmarkEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeProjectAllocationView;
import com.marlabs.cab.service.persistance.view.dao.CabServiceViewDAO;
import com.marlabs.cab.service.persistance.vo.CityLandmarkMasterVO;


@Repository
@Transactional
public class CabServiceViewDAOImpl implements CabServiceViewDAO {
	
	private static Logger logger = LogManager.getLogger(CabServiceViewDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<EmployeeView> getEmployeeView(String globalEmpId) {
		logger.info("getEmployeeView() -> Cab Service View DAO call...");
		
		Session session = null;
		List<EmployeeView> employeeList = null;
		
		try{
			session = sessionFactory.openSession();
		
			String requestHeaderQuery = "FROM  EmployeeView WHERE globalEmpId = :globalEmpid";
			Query queryString = session.createQuery(requestHeaderQuery);
			
			queryString.setString("globalEmpid", globalEmpId);
			employeeList=queryString.list();
			
			logger.info("getEmployeeView() -> Cab Service View  DAO: Success");
		}catch(Exception exception){
			logger.error("getEmployeeView() -> ERROR: "+ exception.getCause());
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return employeeList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeProjectAllocationView> getEmployeeProjectList(String globalEmpId) {
		logger.info("getEmployeeProjectList() -> Cab Service View DAO call...");
		
		Session session = null;
		List<EmployeeProjectAllocationView> emplyoeeProjectList = null;
		
		try{
			session = sessionFactory.openSession();
		
			String requestHeaderQuery = " FROM EmployeeProjectAllocationView WHERE globalEmpId = :globalEmpid AND projAllocationEndDate IS NULL ORDER BY employeeProjectName";
			Query queryString = session.createQuery(requestHeaderQuery);
			queryString.setString("globalEmpid", globalEmpId);
			emplyoeeProjectList=queryString.list();
			
			logger.info("getEmployeeProjectList() -> Cab Service View  DAO: Success");
		}catch(Exception exception){
			logger.error("getEmployeeProjectList() -> ERROR: "+ exception.getCause());
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return emplyoeeProjectList;
	}
	
	@Override
	public List<CityLandmarkMasterEntity> getEmployeeLandmarkData(String employeeId) {
		logger.info("getEmployeeLandmarkData()->  Started Employee Landmark Data DAO call...");
        Session session = null;
        List<EmployeeLandmarkEntity> employeeLandmarkList = null;

       List<CityLandmarkMasterEntity> cityLandmarkMasterList = null;
       Long cityLandmarkMasterId =0l;

     try {
        session = sessionFactory.openSession();
      
        String employeeLandmarkQuery = "FROM  EmployeeLandmarkEntity WHERE employeeId=:employeeId";
        Query query = session.createQuery(employeeLandmarkQuery);

        query .setParameter("employeeId", employeeId);
       
        employeeLandmarkList = query.list();

        for (EmployeeLandmarkEntity employeeLandmark :  employeeLandmarkList) {

        	cityLandmarkMasterId = employeeLandmark.getCityLandmarkMaster().getCityLandmarkMasterId();

     }

          String cityLandmarkMasterQuery = "FROM CityLandmarkMasterEntity WHERE cityLandmarkMasterId=:cityLandmarkMasterId";
          Query query1 = session.createQuery(cityLandmarkMasterQuery);

          query1.setParameter("cityLandmarkMasterId", cityLandmarkMasterId);
       
          cityLandmarkMasterList = query1.list();

        logger.info("getProjectApprover()-> " + "Project Approver DAO: Success");
        } catch (Exception exception) {
         logger.error("getProjectApprover() -> ERROR: " + exception.getCause());
        } finally {
         if (!CabServiceUtil.isNULL(session))
         session.close();
              }
                return cityLandmarkMasterList;
	}
	
	
	
	

}
