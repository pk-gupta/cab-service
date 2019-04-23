package com.marlabs.cab.service.persistance.employee.dao.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.exception.CabServiceTransactionException;
import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.persistance.employee.dao.EmployeeScheduleRequestDAO;
import com.marlabs.cab.service.persistance.entity.EmployeeExistingRequestView;
import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;
import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;
import com.marlabs.cab.service.persistance.vo.ProcessNewRequestVO;
import com.marlabs.cab.service.persistance.vo.ScheduleRequestVO;

@Repository
@Transactional
public class EmployeeScheduleRequestDAOImpl implements EmployeeScheduleRequestDAO {

	private static Logger logger = LogManager.getLogger(EmployeeScheduleRequestDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	Environment environment;

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeManagerDashboardEntity> scheduleRequestDashboard(String employeeId) {
		logger.info("scheduleRequestDashboard() -> Schedule Request Dashboard DAO call...");

		Session session = null;
		List<EmployeeManagerDashboardEntity> employeeManagerDashboardEntityList = null;

		try {
			session = sessionFactory.openSession();
			String entityQuery = "SELECT emdv.* FROM emp_mgr_dashboard_view emdv WHERE emdv.employeeId=:employeeId AND emdv.requestType=:requesttype AND emdv.toDate >= GETDATE()-1 AND emdv.toDate <=DATEADD(DAY, 90, GETDATE()) ORDER BY emdv.reqHeaderId DESC";
			SQLQuery query = session.createSQLQuery(entityQuery);
			
			employeeManagerDashboardEntityList=((SQLQuery) query.setParameter("employeeId", employeeId)
					                                            .setParameter("requesttype", Constants.REQUEST_TYPE_SCHEDULE))
					                                            .addEntity(EmployeeManagerDashboardEntity.class).list();
			logger.info("scheduleRequestDashboard() -> Schedule Request Dashboard DAO: Success");
		} catch (Exception exception) {
			logger.error("scheduleRequestDashboard() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return employeeManagerDashboardEntityList;
	}

	@Override
	public ScheduleRequestVO createNewScheduleRequest(String employeeId) {

		return null;
	}

	@Override
	public ProcessNewRequestVO processScheduleRequest(RequestHeaderEntity requestHeader,ScheduleRequestVO scheduleRequestVO) {
		logger.info("processScheduleRequest() -> New Schedule Request DAO call...");
		Session session = null;
		Transaction transaction = null;
		RequestHeaderEntity header = null;
		boolean flag=false;
		ProcessNewRequestVO processNewRequestVO=new ProcessNewRequestVO(); 
		//String statusMessage=getDuplicateSingleRequest(requestHeader,scheduleRequestVO);
		try {
			 /*processNewRequestVO.setStatusMessage(statusMessage);
				
			if (processNewRequestVO.getStatusMessage().equalsIgnoreCase(Constants.STATUS_SUCCESS)) { 
				processNewRequestVO.setRequestHeaderEntity(header);
				return processNewRequestVO;
					
				}*/

			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			//List<RequestDetailEntity> requestDetailList=scheduleRequestVO.getRequestDetailEntity();
			
			header = (RequestHeaderEntity) session.merge(requestHeader);
			if (requestHeader.getRequestDetailList().size() % 50 == 0) { // 50,same as the JDBC batch size flush a batch of update and release memory
				session.flush();
			}

			  transaction.commit();
			
			  /*if (!CabServiceUtil.isNULL(requestDetailList)) {
				for (RequestDetailEntity requestDetailList1 : requestDetailList) {
					for (RequestDetailEntity requestDetailList2 : header.getRequestDetailList()) {
						if (requestDetailList2.getRequestDetailId().equals(requestDetailList1.getRequestDetailId())) {
							flag = true;
							break;
						}
					}
					
					if (flag != true) {
						String entityQuery = "DELETE FROM REQUEST_DETAIL WHERE REQ_DETAIL_ID=:requestDetailId";
						SQLQuery query = session.createSQLQuery(entityQuery);
						query.setParameter("requestDetailId", requestDetailList1.getRequestDetailId()).executeUpdate();

					}
					
					flag = false;
				}
			}*/			 
				
			processNewRequestVO.setRequestHeaderEntity(header);
			logger.info("processScheduleRequest() -> New Schedule Request DAO: Success");
		} catch (Exception exception) {
			logger.error("processScheduleRequest() -> ERROR: " + exception.getCause());

		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return processNewRequestVO;
	}

	@Override
	public List<RequestHeaderEntity> editScheduleRequest(long requestId) {

		Session session = null;
		List<RequestHeaderEntity> requestheaderList = null;
		ScheduleRequestVO scheduleRequestVO = new ScheduleRequestVO();

		try {
			session = sessionFactory.openSession();
			String requestHeaderQuery = "FROM RequestHeaderEntity as header where header.requestHeaderId = :requestId";
			Query queryString = session.createQuery(requestHeaderQuery);
			queryString.setLong("requestId", requestId);
			requestheaderList = queryString.list();

			logger.info("editScheduleRequest() -> Edit Schedule Request DAO: Success");
		} catch (Exception exception) {
			logger.error("editScheduleRequest() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
		return requestheaderList;
	}

	@Override
	public String deleteScheduleRequest(long requestId) {
		logger.info("deleteScheduleRequest() -> Delete Schedule Request DAO call...");
		Session session = null;
		Transaction transaction = null;
		String status = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			RequestHeaderEntity requestHeader = session.get(RequestHeaderEntity.class, requestId);
			requestHeader.setIsDeleted("true");
			session.update(requestHeader);
			transaction.commit();
			status = "Request Deleted successfully"; // environment.getProperty("cab.service.request.delete.success");
			logger.info("deleteScheduleRequest() -> Delete Schedule Request DAO: Success");
		} catch (Exception exception) {
			logger.error("deleteScheduleRequest() -> ERROR: " + exception.getCause());
			status = "Failed to Delete Request. Please try again later. "; // environment.getProperty("cab.service.request.delete.error.fail");
																			// //
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return status;
	}

	@Override
	public String cancelScheduleRequest(long requestId, String employeeId) {
		logger.info("cancelScheduleRequest() -> Cancel Schedule Request DAO call...");
		Session session = null;
		Transaction transaction = null;
		String status = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			RequestHeaderEntity requestHeader = session.get(RequestHeaderEntity.class, requestId);
			requestHeader.setIsCancelled("true");
			requestHeader.setCancelledBy(employeeId);
			session.update(requestHeader);
			transaction.commit();
			status = "Request Cancelled successfully"; // environment.getProperty("cab.service.request.cancel.success");
			logger.info("cancelScheduleRequest() -> Cancel Schedule Request DAO: Success");
		} catch (Exception exception) {
			logger.error("cancelScheduleRequest() -> ERROR: " + exception.getCause());
			status = "Failed to Cancel Request. Please try again later. "; // environment.getProperty("cab.service.request.cancel.error.fail");
																			// //
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return status;
	}

	public String deleteRequestDetails(long headerId) {
		logger.info("deleteRequestDetails() -> Delete Schedule Request Details DAO call...");
		Session session = null;
		String status = null;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery("DELETE RequestDetailEntity WHERE requestHeader=:requestheaderid");
			query.setLong("requestheaderid", headerId);
			query.executeUpdate();
			logger.info("deleteRequestDetails() -> Delete Schedule Request Details DAO: Success");
		} catch (Exception exception) {
			logger.error("deleteRequestDetails() -> ERROR: " + exception.getCause());
			status = "Failed to Delete Request Details. Please try again later. "; // environment.getProperty("cab.service.request.delete.error.fail");
																					// //
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String cancelSelectedScheduleRequest(ScheduleRequestVO scheduleRequestVO) {

		logger.info("cancelSelectedScheduleRequest() -> Cancel Selected Schedule Request DAO call...");
		Session session = null;
		Transaction transaction = null;
		String status = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			updateRequestDetailRecords(scheduleRequestVO, session);

			transaction.commit();

			RequestHeaderEntity requestHeader = new RequestHeaderEntity();
			requestHeader.setRequestHeaderId(scheduleRequestVO.getRequestId());
			List<RequestDetailEntity> requestDetailList = session.createCriteria(RequestDetailEntity.class)
																 .add(Restrictions.eq("requestHeader", requestHeader))
																 .add(Restrictions.or(Restrictions.isNull("isCancelled"), Restrictions.eq("isCancelled", "false")))
																 .list();

			if (requestDetailList.isEmpty()) {
				transaction = session.beginTransaction();
				RequestHeaderEntity reqHeader = session.get(RequestHeaderEntity.class, scheduleRequestVO.getRequestId());
				reqHeader.setIsCancelled(Constants.STATUS_TRUE);
				reqHeader.setCancelledBy(scheduleRequestVO.getGlobalEmployeeId());
				reqHeader.setApproveStatus(Constants.REQUEST_STATUS_CANCELLED);
				reqHeader.setUpdatedBy(scheduleRequestVO.getGlobalEmployeeId());
				session.update(reqHeader);
				transaction.commit();
			}

			status = "Selected Schedule Request Cancelled successfully";
			logger.info("cancelSelectedScheduleRequest() -> Cancel Selected Schedule Request DAO: Success");
		} catch (Exception exception) {
			logger.error("cancelSelectedScheduleRequest() -> ERROR: " + exception.getCause());
			status = "Failed to Cancel Selected Schedule Request. Please try again later. ";
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.clear();
		}
		return status;
	}

	private void updateRequestDetailRecords(ScheduleRequestVO scheduleRequestVO, Session session) {
		scheduleRequestVO.getRequestDetailEntity().forEach(requestDetail -> {
			RequestDetailEntity requestDetailEntity = session.get(RequestDetailEntity.class,
					requestDetail.getRequestDetailId());

			requestDetailEntity.setCancelReason(scheduleRequestVO.getEmployeeRemark());
			requestDetailEntity.setIsCancelled(Constants.STATUS_TRUE);
			requestDetailEntity.setUpdatedBy(scheduleRequestVO.getGlobalEmployeeId());
			session.update(requestDetailEntity);
		});
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	private String getDuplicateSingleRequest(RequestHeaderEntity requestHeader,ScheduleRequestVO scheduleRequestVO){
		 Session session = null;		 
		 List<RequestHeaderEntity> requestHeaderEntityList = null;		
		 String statusMessage=Constants.STATUS_FAILED;
	
		try {
			 session = sessionFactory.openSession();
			
             String entityQuery="SELECT rh.* FROM REQUEST_HEADER rh WHERE rh.EMPLOYEE_ID=:employeId AND rh.REQUEST_TYPE=:requestType AND rh.REQ_SERVICE_TYPE=:requestServiceType";
			
			 SQLQuery query = session.createSQLQuery(entityQuery);
			 
			 requestHeaderEntityList= ((SQLQuery) query.setParameter("employeId", scheduleRequestVO.getGlobalEmployeeId())
					                                    .setParameter("requestServiceType", scheduleRequestVO.getRequestServiceType())
					                                    .setParameter("requestType", Constants.REQUEST_TYPE_SCHEDULE)) 
					                                    .addEntity(RequestHeaderEntity.class).list();
			
			 if(!requestHeaderEntityList.isEmpty()){
				
			 for(RequestHeaderEntity requestHeaderEntity:requestHeaderEntityList){
					
			 if(requestHeaderEntity.getRequestServiceType().equalsIgnoreCase(requestHeader.getRequestServiceType()) && requestHeader.getRequestServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_PICKUP_DROP) && requestHeaderEntity.getLoginTime().equals(requestHeader.getLoginTime()) && requestHeaderEntity.getLogoutTime().equals(requestHeader.getLogoutTime()) && requestHeaderEntity.getFromDate().equals(requestHeader.getFromDate()) && requestHeaderEntity.getToDate().equals(requestHeader.getToDate())){
						
					 statusMessage=Constants.STATUS_SUCCESS;break;					
			  }else if(requestHeaderEntity.getRequestServiceType().equalsIgnoreCase(requestHeader.getRequestServiceType()) && requestHeader.getRequestServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_PICKUP) && requestHeaderEntity.getLoginTime().equals(requestHeader.getLoginTime()) && requestHeaderEntity.getFromDate().equals(requestHeader.getFromDate())  && requestHeaderEntity.getToDate().equals(requestHeader.getToDate())){
						
					statusMessage=Constants.STATUS_SUCCESS;break;					
			  }else if(requestHeaderEntity.getRequestServiceType().equalsIgnoreCase(requestHeader.getRequestServiceType()) && requestHeader.getRequestServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_DROP) && requestHeaderEntity.getLogoutTime().equals(requestHeader.getLogoutTime()) && requestHeaderEntity.getFromDate().equals(requestHeader.getFromDate())  && requestHeaderEntity.getToDate().equals(requestHeader.getToDate())) {
					    	
				 	statusMessage=Constants.STATUS_SUCCESS;break;
					      }
				        }
				      }
			
              if(!requestHeaderEntityList.isEmpty() && statusMessage.equalsIgnoreCase(Constants.STATUS_FAILED)){
				
			  for(RequestHeaderEntity requestHeaderEntity:requestHeaderEntityList){
					
			  if(requestHeaderEntity.getRequestServiceType().equalsIgnoreCase(requestHeader.getRequestServiceType()) && (requestHeaderEntity.getToDate()).getDate() >= (requestHeader.getFromDate()).getDate() && requestHeader.getRequestHeaderId()!=requestHeaderEntity.getRequestHeaderId() && (requestHeaderEntity.getToDate()).getMonth() == (requestHeader.getFromDate()).getMonth()){
						
					 statusMessage=Constants.STATUS_SUCCESS;break;					
			  }else if(requestHeaderEntity.getRequestServiceType().equalsIgnoreCase(requestHeader.getRequestServiceType()) && (requestHeaderEntity.getToDate()).getDate() >= (requestHeader.getFromDate()).getDate() && requestHeader.getRequestHeaderId()!=requestHeaderEntity.getRequestHeaderId() && (requestHeaderEntity.getToDate()).getMonth() == (requestHeader.getFromDate()).getMonth()){
						
					statusMessage=Constants.STATUS_SUCCESS;	break;				
			  }else if(requestHeaderEntity.getRequestServiceType().equalsIgnoreCase(requestHeader.getRequestServiceType()) && (requestHeaderEntity.getToDate()).getDate() >= (requestHeader.getFromDate()).getDate() && requestHeader.getRequestHeaderId()!=requestHeaderEntity.getRequestHeaderId() && (requestHeaderEntity.getToDate()).getMonth() == (requestHeader.getFromDate()).getMonth()) {
					    	
				 	statusMessage=Constants.STATUS_SUCCESS;break;
			  }else{
					
					statusMessage=Constants.STATUS_FAILED;
					
				}
				
			   }
	        }
									
		} catch (RuntimeException exception) {
			logger.error("getDuplicateSingleRequest() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return statusMessage;
	}
	
	@Override
	public List<EmployeeExistingRequestView> getExistingRequests(ScheduleRequestVO scheduleRequestVO) {
		Session session = null;
		List<EmployeeExistingRequestView> existingRequests = null;
		try {
			session = sessionFactory.openSession();
			Criteria requestCriteria = session.createCriteria(EmployeeExistingRequestView.class)
											  .add(Restrictions.eq("employeeId", scheduleRequestVO.getGlobalEmployeeId()))
											  .add(Restrictions.eq("serviceType", scheduleRequestVO.getEmployeeServiceType()))
											  .add(Restrictions.between("serviceDate", scheduleRequestVO.getEmplopyeeSelectedFromDate()
						  								 , scheduleRequestVO.getEmplopyeeSelectedToDate()));
											  //.add(Restrictions.or(Restrictions.eq("loginTime", scheduleRequestVO.getEmployeeSelectedLogin()),
													//	Restrictions.eq("logoutTime", scheduleRequestVO.getEmployeeSelectedLogout())));
			
			existingRequests = requestCriteria.list();	
			
		} catch (Exception exception) {
			logger.error("getExistingRequests() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return existingRequests;
	}	
	
	@Override
	public void deleteRequestDetail(long requestDetailId) {
		logger.info("deleteRequestDetail() -> " + "Delete Request Detail DAO call...");
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			
			RequestDetailEntity requestDetail = (RequestDetailEntity ) session.createCriteria(RequestDetailEntity.class)
	                    .add(Restrictions.eq("requestDetailId", requestDetailId)).uniqueResult();
			session.delete(requestDetail);
			
			transaction.commit();

			logger.info("deleteRequestDetail() -> " + "Delete Request Detail: Success");
		} catch (Exception exception) {
			logger.error("deleteRequestDetail() -> ERROR: " + exception.getCause());
			throw new CabServiceTransactionException(exception.getMessage());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
	}

	@Override
	public RequestHeaderEntity getRequestHeader(long requestHeaderId) {
		logger.info("getRequestHeader() -> " + "Retrieve Request Header DAO call...");
		Session session = null;
		RequestHeaderEntity requestheader = null;

		try {
			session = sessionFactory.openSession();
			Criteria requestCriteria = session.createCriteria(RequestHeaderEntity.class)
					  .add(Restrictions.eq("requestHeaderId", requestHeaderId));

			Optional<Object> result = Optional.ofNullable(requestCriteria.uniqueResult());
			
			if(result.isPresent()){
				requestheader = (RequestHeaderEntity)result.get();
			}

			logger.info("getRequestHeader() -> " + "Retrieve Request Header: Success");
		} catch (Exception exception) {
			logger.error("getRequestHeader() -> ERROR: " + exception.getCause());
			throw new CabServiceTransactionException(exception.getMessage());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
		return requestheader;
	}
	
	@Override
	public List<RequestDetailEntity> getRequestDetailWithHeader(long requestHeaderId) {
		logger.info("getRequestDetailWithHeader() -> " + "Retrieve Request Detail With Request Header Id DAO call...");
		Session session = null;
		List<RequestDetailEntity> requestDetail = null;

		try {
			session = sessionFactory.openSession();
			
			RequestHeaderEntity requestHeader = new RequestHeaderEntity();
			requestHeader.setRequestHeaderId(requestHeaderId);
			
			Criteria requestCriteria = session.createCriteria(RequestDetailEntity.class)
					  .add(Restrictions.eq("requestHeader", requestHeader));
			
			requestDetail = requestCriteria.list();

			/*Optional<Object> result = Optional.ofNullable(requestCriteria.uniqueResult());
			
			if(result.isPresent()){
				requestDetail = (RequestHeaderEntity)result.get();
			}*/

			logger.info("getRequestDetailWithHeader() -> " + "Retrieve Request Detail With Request Header Id: Success");
		} catch (Exception exception) {
			logger.error("getRequestHeader() -> ERROR: " + exception.getCause());
			throw new CabServiceTransactionException(exception.getMessage());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
		return requestDetail;
	}

}


