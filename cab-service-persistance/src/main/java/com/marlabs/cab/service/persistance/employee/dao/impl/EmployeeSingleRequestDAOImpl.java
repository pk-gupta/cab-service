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
import com.marlabs.cab.service.persistance.employee.dao.EmployeeSingleRequestDAO;
import com.marlabs.cab.service.persistance.entity.EmployeeExistingRequestView;
import com.marlabs.cab.service.persistance.entity.EmployeeManagerDashboardEntity;
import com.marlabs.cab.service.persistance.entity.RequestDetailEntity;
import com.marlabs.cab.service.persistance.entity.RequestHeaderEntity;
import com.marlabs.cab.service.persistance.vo.ProcessNewRequestVO;
import com.marlabs.cab.service.persistance.vo.SingleRequestVO;

@Repository
@Transactional
public class EmployeeSingleRequestDAOImpl implements EmployeeSingleRequestDAO {

	private static Logger logger = LogManager.getLogger(EmployeeSingleRequestDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	Environment env;

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeManagerDashboardEntity> singleRequestDashboard(String employeeId) {
		logger.info("singleRequestDashboard() -> Single Request Dashboard DAO call...");

		Session session = null;
		List<EmployeeManagerDashboardEntity> employeeManagerDashboardEntityList = null;

		try {
			session = sessionFactory.openSession();
			String entityQuery = "SELECT emdv.* FROM emp_mgr_dashboard_view emdv WHERE emdv.employeeId=:employeeId AND emdv.requestType=:requesttype AND emdv.toDate >= GETDATE()-1 AND emdv.toDate <=DATEADD(DAY, 90, GETDATE())  ORDER BY emdv.reqHeaderId DESC";
			SQLQuery query = session.createSQLQuery(entityQuery);
			
			employeeManagerDashboardEntityList=((SQLQuery) query.setParameter("employeeId", employeeId)
					                                            .setParameter("requesttype", Constants.REQUEST_TYPE_SINGLE))
					                                            .addEntity(EmployeeManagerDashboardEntity.class).list();
			logger.info("singleRequestDashboard() -> Single Request Dashboard DAO: Success");
		} catch (Exception exception) {
			logger.error("singleRequestDashboard() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return employeeManagerDashboardEntityList;
	}

	@Override
	public SingleRequestVO createNewSingleRequest(String employeeId) {

		return null;
	}

	@Override
	public ProcessNewRequestVO processSingleRequest(RequestHeaderEntity requestHeader,SingleRequestVO singleRequestVO) {
		logger.info("processSingleRequest() -> New Single Request DAO call...");
		Session session = null;
		Transaction transaction = null;
		RequestHeaderEntity header = null;
		ProcessNewRequestVO processNewRequestVO = new ProcessNewRequestVO();
		//String statusMessage = getDuplicateSingleRequest(requestHeader, singleRequestVO);

		try {
			/*processNewRequestVO.setStatusMessage(statusMessage);

			if (!processNewRequestVO.getStatusMessage().equalsIgnoreCase(Constants.STATUS_FAILED)) {
				processNewRequestVO.setRequestHeaderEntity(header);
				return processNewRequestVO;

			}*/
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			//List<RequestDetailEntity> requestDetailList = singleRequestVO.getRequestDetailEntity();

			header = (RequestHeaderEntity) session.merge(requestHeader);
			if (requestHeader.getRequestDetailList().size() % 50 == 0) { 
				session.flush();
			}

			transaction.commit();

			/*if (!CabServiceUtil.isNULL(requestDetailList)) {

				if (requestDetailList.size() > requestHeader.getRequestDetailList().size()) {
					for (RequestDetailEntity requestDetailList1 : requestDetailList) {
						if (!requestDetailList1.getRequestDetailId()
								.equals(requestHeader.getRequestDetailList().get(0).getRequestDetailId())) {
							String entityQuery = "DELETE FROM REQUEST_DETAIL WHERE REQ_DETAIL_ID=:requestDetailId";
							SQLQuery query = session.createSQLQuery(entityQuery);
							query.setParameter("requestDetailId", requestDetailList1.getRequestDetailId())
									.executeUpdate();

						}
					}
				}
			}*/

			processNewRequestVO.setRequestHeaderEntity(header);

			logger.info("processSingleRequest() -> New Single Request DAO: Success");
		} catch (Exception exception) {
			logger.error("processSingleRequest() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
		return processNewRequestVO;
	}

	@Override
	public List<RequestHeaderEntity> editSingleRequest(long requestId) {

		Session session = null;
		List<RequestHeaderEntity> requestheaderList = null;

		try {
			session = sessionFactory.openSession();
			String requestHeaderQuery = "FROM RequestHeaderEntity as header where header.requestHeaderId = :requestId";
			Query queryString = session.createQuery(requestHeaderQuery);
			queryString.setLong("requestId", requestId);
			requestheaderList = queryString.list();

			logger.info("editScheduleRequest() -> " + "Edit Schedule Request Dashboard DAO: Success");
		} catch (Exception exception) {
			logger.error("editScheduleRequest() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
		return requestheaderList;
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
	public String deleteSingleRequest(long requestId) {
		logger.info("deleteSingleRequest() -> Delete Single Request DAO call...");
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
			logger.info("deleteSingleRequest() -> Delete Single Request DAO: Success");
		} catch (Exception exception) {
			logger.error("deleteSingleRequest() -> ERROR: " + exception.getCause());
			status = "Failed to Delete Request. Please try again later. "; // environment.getProperty("cab.service.request.delete.error.fail");
																			// //
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return status;
	}

	@Override
	public String cancelSingleRequest(long requestId, String employeeId) {
		logger.info("cancelSingleRequest() -> Cancel Schedule Request DAO call...");
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
			logger.info("cancelSingleRequest() -> Cancel Schedule Request DAO: Success");
		} catch (Exception exception) {
			logger.error("cancelSingleRequest() -> ERROR: " + exception.getCause());
			status = "Failed to Cancel Request. Please try again later. "; // environment.getProperty("cab.service.request.cancel.error.fail");
																			// //
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.clear();
		}
		return status;
	}

	public String deleteRequestDetails(long headerId) {
		logger.info("deleteRequestDetails() -> Delete Single Request Details DAO call...");
		Session session = null;
		String status = null;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery("DELETE RequestDetailEntity WHERE requestHeader=:requestheaderid");
			query.setLong("requestheaderid", headerId);
			query.executeUpdate();
			logger.info("deleteRequestDetails() -> Delete Single Request Details DAO: Success");
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
	public String cancelSelectedSingleRequest(SingleRequestVO singleRequestVO) {

		logger.info("cancelSelectedSingleRequest() -> Cancel Selected Single Request DAO call...");
		Session session = null;
		Transaction transaction = null;
		String status= null;
		RequestHeaderEntity requestHeader = null;
		try {

			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			updateRequestDetailRecords(singleRequestVO, session);

			transaction.commit();

			requestHeader = new RequestHeaderEntity();
			requestHeader.setRequestHeaderId(singleRequestVO.getRequestId());
			requestHeader.setRequestServiceType(singleRequestVO.getRequestServiceType());
			List<RequestDetailEntity> requestDetailList = session.createCriteria(RequestDetailEntity.class)
					.add(Restrictions.eq("requestHeader", requestHeader))
					.add(Restrictions.or(Restrictions.isNull("isCancelled"), Restrictions.eq("isCancelled", "false")))
					.list();

			if (requestDetailList.isEmpty()) {
				transaction = session.beginTransaction();
				// RequestHeaderEntity reqHeader =
				// session.get(RequestHeaderEntity.class,
				// singleRequestVO.getRequestId());
				requestHeader = session.get(RequestHeaderEntity.class, singleRequestVO.getRequestId());
				requestHeader.setIsCancelled(Constants.STATUS_TRUE);
				requestHeader.setCancelledBy(singleRequestVO.getGlobalEmployeeId());
				requestHeader.setApproveStatus(Constants.REQUEST_STATUS_CANCELLED);
				requestHeader.setUpdatedBy(singleRequestVO.getGlobalEmployeeId());
				requestHeader.setRequestServiceType(singleRequestVO.getRequestServiceType());
				session.update(requestHeader);
				transaction.commit();
			}
            status = Constants.SINGLE_REQUEST_CANCELLED_SUCCESS;
			logger.info("cancelSelectedSingleRequest() -> Cancel Selected Single Request DAO: Success");
		} catch (Exception exception) {
			logger.error("cancelSelectedSingleRequest() -> ERROR: " + exception.getCause());
			status = Constants.SINGLE_REQUEST_CANCELLED_FAILED;
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.clear();
		}
		return status;
	}

	private void updateRequestDetailRecords(SingleRequestVO singleRequestVO, Session session) {
		singleRequestVO.getRequestDetailEntity().forEach(requestDetail -> {
			RequestDetailEntity requestDetailEntity = session.get(RequestDetailEntity.class,requestDetail.getRequestDetailId());

			requestDetailEntity.setCancelReason(singleRequestVO.getEmployeeRemark());
			requestDetailEntity.setIsCancelled(Constants.STATUS_TRUE);
			requestDetailEntity.setUpdatedBy(singleRequestVO.getGlobalEmployeeId());
			session.update(requestDetailEntity);
		});
	}

	@SuppressWarnings("unchecked")
	private String getDuplicateSingleRequest(RequestHeaderEntity requestHeader, SingleRequestVO singleRequestVO) {
		Session session = null;

		List<RequestHeaderEntity> requestHeaderEntityList = null;

		String statusMessage = Constants.STATUS_FAILED;

		try {
			session = sessionFactory.openSession();

			String entityQuery = "SELECT rh.* FROM REQUEST_HEADER rh WHERE rh.EMPLOYEE_ID=:employeId AND rh.FROM_DATE=:serviceDate AND rh.REQ_SERVICE_TYPE=:requestServiceType AND rh.REQUEST_TYPE=:requestType";

			SQLQuery query = session.createSQLQuery(entityQuery);

			query.setParameter("serviceDate", singleRequestVO.getServiceDate());

			query.setParameter("employeId", singleRequestVO.getGlobalEmployeeId());

			query.setParameter("requestServiceType", singleRequestVO.getEmployeeServiceType());

			query.setParameter("requestType", Constants.REQUEST_TYPE_SINGLE);

			requestHeaderEntityList = query.addEntity(RequestHeaderEntity.class).list();

			if (!requestHeaderEntityList.isEmpty()) {

			for (RequestHeaderEntity requestHeaderEntity : requestHeaderEntityList) {

			if (requestHeaderEntity.getRequestServiceType().equalsIgnoreCase(requestHeader.getRequestServiceType())&& requestHeader.getRequestServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_PICKUP_DROP)&& requestHeaderEntity.getLoginTime().equals(requestHeader.getLoginTime())&& requestHeaderEntity.getLogoutTime().equals(requestHeader.getLogoutTime())&& requestHeaderEntity.getFromDate().equals(requestHeader.getFromDate())) {

				statusMessage = Constants.STATUS_SUCCESS;break;
			} else if (requestHeaderEntity.getRequestServiceType().equalsIgnoreCase(requestHeader.getRequestServiceType())&& requestHeader.getRequestServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_PICKUP)&& requestHeaderEntity.getLoginTime().equals(requestHeader.getLoginTime())&& requestHeaderEntity.getFromDate().equals(requestHeader.getFromDate())) {

			 	statusMessage = Constants.STATUS_SUCCESS;break;
			} else if (requestHeaderEntity.getRequestServiceType().equalsIgnoreCase(requestHeader.getRequestServiceType())&& requestHeader.getRequestServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_DROP)&& requestHeaderEntity.getLogoutTime().equals(requestHeader.getLogoutTime())&& requestHeaderEntity.getFromDate().equals(requestHeader.getFromDate())) {

			   statusMessage = Constants.STATUS_SUCCESS;break;
					}
				}
			}

			if (!requestHeaderEntityList.isEmpty() && statusMessage.equalsIgnoreCase(Constants.STATUS_FAILED) && requestHeader.getRequestHeaderId() == 0) {

			for (RequestHeaderEntity requestHeaderEntity : requestHeaderEntityList) {

			if (requestHeaderEntity.getRequestServiceType().equalsIgnoreCase(requestHeader.getRequestServiceType())	&& requestHeader.getRequestServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_PICKUP_DROP)&& Float.parseFloat(requestHeader.getLoginTime())- Float.parseFloat(requestHeaderEntity.getLoginTime()) >= 6 && Float.parseFloat(requestHeader.getLogoutTime())- Float.parseFloat(requestHeaderEntity.getLogoutTime()) >= 4 && requestHeaderEntity.getFromDate().equals(requestHeader.getFromDate())) {

				statusMessage =Constants.STATUS_FAILED;break;					

			} else if (requestHeaderEntity.getRequestServiceType().equalsIgnoreCase(requestHeader.getRequestServiceType())&& requestHeader.getRequestServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_PICKUP)&& Float.parseFloat(requestHeader.getLoginTime())	- Float.parseFloat(requestHeaderEntity.getLoginTime()) >= 6	&& requestHeaderEntity.getFromDate().equals(requestHeader.getFromDate())) {

				statusMessage = Constants.STATUS_FAILED;break;						
			} else if (requestHeaderEntity.getRequestServiceType().equalsIgnoreCase(requestHeader.getRequestServiceType())&& requestHeader.getRequestServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_DROP) && Float.parseFloat(requestHeader.getLogoutTime())	- Float.parseFloat(requestHeaderEntity.getLogoutTime()) >= 4 && requestHeaderEntity.getFromDate().equals(requestHeader.getFromDate())) {

				statusMessage = Constants.STATUS_FAILED;break;						
			} else {

				statusMessage = Constants.STATUS_SUCCESS;
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
	public List<EmployeeExistingRequestView> getExistingRequests(SingleRequestVO singleRequestVO) {
		Session session = null;
		List<EmployeeExistingRequestView> existingRequests = null;
		try {
			session = sessionFactory.openSession();
			Criteria requestCriteria = session.createCriteria(EmployeeExistingRequestView.class)
											  .add(Restrictions.eq("employeeId", singleRequestVO.getGlobalEmployeeId()))
											  .add(Restrictions.eq("serviceType", singleRequestVO.getEmployeeServiceType()))
											  .add(Restrictions.eq("serviceDate", singleRequestVO.getServiceDate()));
											  //.add(Restrictions.or(Restrictions.eq("loginTime", singleRequestVO.getEmployeeSelectedLogin()),
														//Restrictions.eq("logoutTime", singleRequestVO.getEmployeeSelectedLogout())));
			
			existingRequests = requestCriteria.list();	
			
		} catch (Exception exception) {
			logger.error("getExistingRequests() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return existingRequests;
	}	
	
	}
