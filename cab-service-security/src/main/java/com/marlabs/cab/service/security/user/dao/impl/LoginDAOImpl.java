package com.marlabs.cab.service.security.user.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.persistance.entity.EmployeeView;
import com.marlabs.cab.service.persistance.entity.OtherUserEntity;
import com.marlabs.cab.service.persistance.entity.UserAdditionalDetailsView;
import com.marlabs.cab.service.security.user.dao.LoginDAO;
import com.marlabs.cab.service.security.user.vo.LoginVO;

@Repository
@Transactional
public class LoginDAOImpl implements LoginDAO {
	
	private static Logger logger = LogManager.getLogger(LoginDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Object employeeLogin(LoginVO loginVO) {
		logger.info("LoginDAOImpl -- employeeLogin() -> Login DAO Call : Employee Login...");
		
		Session session = null;
		
		Object employeeDetails = null;
		
		try{
			session = sessionFactory.openSession();
			
			employeeDetails = session.createCriteria(EmployeeView.class)
								     .add(Restrictions.eq("offEmailID", loginVO.getUserEmail()))
								     .uniqueResult();
			
			logger.info("LoginDAOImpl -- employeeLogin() -> Login DAO Call: Success");
		}catch(Exception exception){
			logger.error("LoginDAOImpl -- employeeLogin() -> ERROR: "+ exception.getCause());
			logger.error(exception.getStackTrace());
			employeeDetails = Constants.SERVER_ERROR;
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return employeeDetails;
		
	}
	
	@Override
	public Object vendorLogin(LoginVO loginVO) {
		logger.info("LoginDAOImpl -- vendorLogin() -> Login DAO Call : Vendor Login...");
		
		Session session = null;
		
		Object otherUserEntity = null;
		
		try{
			session = sessionFactory.openSession();
			otherUserEntity = session.createCriteria(OtherUserEntity.class)
				     			  	 .add(Restrictions.eq("otherUserId", loginVO.getUserId()))
				     			  	 //.add(Restrictions.eq("userPassword", loginVO.getUserPassword()))
				     			  	 //.add(Restrictions.or(Restrictions.eq("userEmail", loginVO.getUserEmail())))
				     			  	 .uniqueResult();
			
			logger.info("LoginDAOImpl -- vendorLogin() -> Login DAO Call : Vendor Login -- Success");
		}catch(Exception exception){
			logger.error("LoginDAOImpl -- vendorLogin() -> ERROR: "+ exception.getCause());
			logger.error(exception.getStackTrace());
			otherUserEntity = Constants.SERVER_ERROR;
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return otherUserEntity;
	}
	
	@Override
	public Object userAdditionalDetails(String userId) {
		logger.info("LoginDAOImpl -- userAdditionalDetails() -> Login DAO Call : User Addtional Details...");
		
		Session session = null;
		
		Object otherDetails = null;
		
		try{
			session = sessionFactory.openSession();
			otherDetails = session.createCriteria(UserAdditionalDetailsView.class)
				     			  .add(Restrictions.eq("userId", userId))
				     			  .uniqueResult();
			
			logger.info("LoginDAOImpl -- userAdditionalDetails() -> User Addtional Details: Success");
		}catch(Exception exception){
			logger.error("LoginDAOImpl -- userAdditionalDetails() -> ERROR: "+ exception.getCause());
			logger.error(exception.getStackTrace());
			otherDetails = Constants.SERVER_ERROR;
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return otherDetails;
	}

}
