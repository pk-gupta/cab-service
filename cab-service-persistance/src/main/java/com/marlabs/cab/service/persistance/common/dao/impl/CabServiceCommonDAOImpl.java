package com.marlabs.cab.service.persistance.common.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marlabs.cab.service.common.util.CabServiceUtil;
import com.marlabs.cab.service.persistance.common.dao.CabServiceCommonDAO;
import com.marlabs.cab.service.persistance.entity.CabDetailEntity;
import com.marlabs.cab.service.persistance.entity.CityLandmarkMasterEntity;
import com.marlabs.cab.service.persistance.entity.EmployeeProjectAllocationView;
import com.marlabs.cab.service.persistance.entity.EmployeeView;
import com.marlabs.cab.service.persistance.entity.OfficeBranchEntity;
import com.marlabs.cab.service.persistance.entity.OfficeCityEntity;
import com.marlabs.cab.service.persistance.entity.TripHeaderEntity;
import com.marlabs.cab.service.persistance.sql.mapper.EmployeeRequestSQLMapper;
import com.marlabs.cab.service.persistance.sql.mapper.PendingRequestSQLMapper;
import com.marlabs.cab.service.persistance.vo.SingleRequestVO;

@Repository
@Transactional
public class CabServiceCommonDAOImpl implements CabServiceCommonDAO {

	private static Logger logger = LogManager.getLogger(CabServiceCommonDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private static final String TITLE_EDGE_NGRAM_INDEX = "edgeNGramTitle";
	private static final String TITLE_NGRAM_INDEX = "nGramTitle";
	private static final String TITLE_EDGE_NGRAM_INDEX1 = "edgeNGramTitleTrip";
	private static final String TITLE_NGRAM_INDEX1 = "nGramTitleTrip";
	private static final String TITLE_EDGE_NGRAM_INDEX_EMPLOYEE = "edgeNGramTitleEmployee";
	private static final String TITLE_NGRAM_INDEX_EMPLOYEE = "nGramTitleEmployee";
			
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeView> autoCompleteSearch(String searchText) {
		
		logger.info("autoCompleteSearch() -> Started Auto Search DAO call...");
		
		Session session = null;
		FullTextSession fullTextSession = null;
		List<EmployeeView> listEmployees = null;

		try {
			session = sessionFactory.openSession();
			fullTextSession = Search.getFullTextSession(session);
			
			fullTextSession.createIndexer(EmployeeView.class).transactionTimeout(2)// true by default, highly recommended
			 												 .batchSizeToLoadObjects(10)
															 .optimizeAfterPurge( true ) // true is default, saves some disk space
														     .optimizeOnFinish( true ) // true by defaultstartAndWait();
														     .purgeAllOnStart(true);

			QueryBuilder queryBuilder =  fullTextSession.getSearchFactory()
													    .buildQueryBuilder()
													    .forEntity(EmployeeView.class)
													    .get();

			org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword()
													                 .fuzzy()
													                 .withPrefixLength(5)
													                 .boostedTo(10)
													                 .onField(TITLE_NGRAM_INDEX_EMPLOYEE).andField(TITLE_EDGE_NGRAM_INDEX_EMPLOYEE)
													                 .matching(searchText.toLowerCase())
													                 .createQuery();
			
			/*org.apache.lucene.search.Query luceneQuery = queryBuilder.phrase()
														 			 .withSlop(0)
														 			 .onField(TITLE_NGRAM_INDEX_EMPLOYEE)
																	 .andField(TITLE_EDGE_NGRAM_INDEX_EMPLOYEE)
																	 .sentence(searchText.toLowerCase()).createQuery();*/

			listEmployees = fullTextSession.createFullTextQuery(luceneQuery, EmployeeView.class)
										    .setMaxResults(10)
										    .list();
			
			logger.info("autoCompleteSearch() -> Auto Search DAO : Success");
			
		} catch (Exception exception) {
			logger.error("autoCompleteSearch() -> ERROR: " + exception.getCause());
		} finally {
			 if (!CabServiceUtil.isNULL(fullTextSession))
				fullTextSession.close();
		}
		
		return listEmployees;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CabDetailEntity> autoCompleteSearchCab(String searchText) {
		
		logger.info("autoCompleteSearchCab() -> Started Auto Search Cab DAO call...");
		
		Session session = null;
		FullTextSession fullTextSession = null;
		List<CabDetailEntity> listCabDetails = null;

		try {
			session = sessionFactory.openSession();
			fullTextSession = Search.getFullTextSession(session);
			
			fullTextSession.createIndexer(CabDetailEntity.class).transactionTimeout(2) // true by default, highly recommended
																.batchSizeToLoadObjects(10)
																.optimizeAfterPurge( true ) // true is default, saves some disk space
															    .optimizeOnFinish( true ) // true by defaultstartAndWait();
															    .purgeAllOnStart(true);
			
			QueryBuilder queryBuilder =  fullTextSession.getSearchFactory()
													    .buildQueryBuilder()
													    .forEntity(CabDetailEntity.class)
													    .get();
  
			org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword()
													                 .fuzzy()
													                 .withPrefixLength(5)
													                 .boostedTo(10)
													                 .onField(TITLE_NGRAM_INDEX).andField(TITLE_EDGE_NGRAM_INDEX)
													                 .matching(searchText.toLowerCase())
													                 .createQuery();
			

			Criteria query = session.createCriteria(CabDetailEntity.class)
				   					.add(Restrictions.le("startDate", new Date()))
				   					.add(Restrictions.eq("active", "true"))
				   					.add(Restrictions.ge("insuranceDate", new Date()));

			
			listCabDetails = fullTextSession.createFullTextQuery(luceneQuery, CabDetailEntity.class)
											.setCriteriaQuery(query)
										    .setMaxResults(10)
										    .list();
			
			logger.info("autoCompleteSearchCab() -> Auto Search Cab DAO : Success");
			
		} catch (Exception exception) {
			logger.error("autoCompleteSearchCab() -> ERROR: " + exception.getCause());
		} finally {
			/*if (!CabServiceUtil.isNULL(session))
				session.close();*/
			if (!CabServiceUtil.isNULL(fullTextSession))
				fullTextSession.close();
		}
		
		return listCabDetails;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripHeaderEntity> autoCompleteSearchTripNumber(Long tripHeaderId) {
		logger.info("autoCompleteSearchTripNumber() -> Started Auto Search Trip Number DAO call...");
		
		Session session = null;
		FullTextSession fullTextSession = null;
		List<TripHeaderEntity> tripHeaderList = null;

		try {
			session = sessionFactory.openSession();
			fullTextSession = Search.getFullTextSession(session);
			
			fullTextSession.createIndexer(TripHeaderEntity.class).transactionTimeout(2) // true by default, highly recommended
															     .optimizeAfterPurge(true) // true is default, saves some disk space
															     .optimizeOnFinish(true) // true by defaultstartAndWait();
															     .purgeAllOnStart(true);
			
			QueryBuilder queryBuilder =  fullTextSession.getSearchFactory()
													    .buildQueryBuilder()
													    .forEntity(TripHeaderEntity.class)
													    .get();

			org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword()
													                 .fuzzy()
													                 .withPrefixLength(5)
													                 .boostedTo(10)
													                 .onField(TITLE_NGRAM_INDEX1).andField(TITLE_EDGE_NGRAM_INDEX1)
													                 .matching(tripHeaderId.toString())
													                 .createQuery();
			
			tripHeaderList = fullTextSession.createFullTextQuery(luceneQuery, TripHeaderEntity.class)
										    .setMaxResults(10)
										    .list();
			
			logger.info("autoCompleteSearchTripNumber() -> Auto Search Trip Number DAO : Success");
			
		} catch (Exception exception) {
			logger.error("autoCompleteSearchTripNumber() -> ERROR: " + exception.getCause());
		} finally {
			/*if (!CabServiceUtil.isNULL(session))
				session.close();*/
			if (!CabServiceUtil.isNULL(fullTextSession))
				fullTextSession.close();
		}
		
		return tripHeaderList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CityLandmarkMasterEntity> getCityLandmarks(Long officeCityId) {
		logger.info("getCityLandmarks() ->  Started City Landmark DAO call...");
		Session session = null;
		List<CityLandmarkMasterEntity> cityLandmarkMasterEntityList = null;

		try {
			session = sessionFactory.openSession();
			OfficeCityEntity officeCity = new OfficeCityEntity();
			officeCity.setOfficeCityId(officeCityId);
			
			cityLandmarkMasterEntityList = session.createCriteria(CityLandmarkMasterEntity.class)
												  .add(Restrictions.eq("officeCity", officeCity))
											      .list();
			logger.info(" getCityLandmarks() -> " + "City Landmark DAO: Success");
		} catch (Exception exception) {
			logger.error("getCityLandmarks() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
		return cityLandmarkMasterEntityList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityLandmarkMasterEntity> getBranchLandmarks(String officeBranchCode) {
		logger.info("getBranchLandmarks() ->  Started Branch Landmarks DAO call...");
		Session session = null;
		List<CityLandmarkMasterEntity> cityLandmarkMasterEntityList = null;

		List<OfficeBranchEntity> officeBranchEntityList = null;
		Long officeCityId = 2l;

		try {
			session = sessionFactory.openSession();
			
			String officeBranchQuery = "FROM OfficeBranchEntity WHERE officeBranchcode=:officeBranchCode";
			Query queryOfficeBranch = session.createQuery(officeBranchQuery);

			queryOfficeBranch.setParameter("officeBranchCode", officeBranchCode);

			officeBranchEntityList = queryOfficeBranch.list();
			for (OfficeBranchEntity officeBranchEntity : officeBranchEntityList) {

				officeCityId = officeBranchEntity.getOfficeCity().getOfficeCityId();

			}

			OfficeCityEntity officeCity=new OfficeCityEntity();
			officeCity.setOfficeCityId(officeCityId);
			cityLandmarkMasterEntityList = session.createCriteria(CityLandmarkMasterEntity.class)
												  .add(Restrictions.eq("officeCity", officeCity))
											      .list();

			logger.info(" getBranchLandmarks() -> " + "Branch Landmarks DAO: Success");
		} catch (Exception exception) {
			logger.error("getBranchLandmarks() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
		return cityLandmarkMasterEntityList;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeRequestSQLMapper> requestDetails(Long requestHeaderId) {
		logger.info("requestDetails() -> Request Details DAO call......");
		
		Session session = null;
		
		List<EmployeeRequestSQLMapper> employeeRequestList = null;
		
		try{
			session = sessionFactory.openSession();
            			
			String entityQuery="SELECT rd.REQ_DETAIL_ID AS requestDetailId,rd.SERVICE_TYPE AS serviceType, rd.SERVICE_DATE AS serviceDate, rd.IS_CANCELLED AS isCancelled, rd.CANCEL_REASON AS cancelReason, rd.CREATED_BY AS createdBy, rd.UPDATED_BY AS updatedBy, rd.UPDATE_DATE AS updateDate, cd.REGISTRATION_NO AS registrationNo FROM REQUEST_DETAIL rd LEFT JOIN TRIP_DETAIL td ON td.FK_REQ_DETAIL_ID=rd.REQ_DETAIL_ID LEFT JOIN TRIP_HEADER th ON th.TRIP_HEADER_ID=td.FK_TRIP_HEADER_ID LEFT JOIN CAB_DETAIL cd ON cd.CAB_DETAIL_ID=th.FK_CAB_DETAIL_ID WHERE rd.FK_REQ_HEADER_ID=:requestHeaderId ORDER BY rd.SERVICE_DATE";
			
			SQLQuery query = session.createSQLQuery(entityQuery);

			query.setParameter("requestHeaderId", requestHeaderId);
			
			employeeRequestList=query.setResultTransformer(Transformers.aliasToBean(EmployeeRequestSQLMapper.class)).list();	
			
			logger.info("requestDetails() -> Request Details DAO call: Success");
		}catch(Exception exception){
			logger.error("requestDetails() -> ERROR: "+ exception.getCause());
			logger.error(exception.getStackTrace());
		}finally{
			if(!CabServiceUtil.isNULL(session))
				session.close();
		}
		
		return employeeRequestList;
	}
	
	@Override
	public EmployeeView getProjectApprover(SingleRequestVO singleRequestVO) {
		
		logger.info("getProjectApprover()->  Started Project Approver DAO call...");
         Session session = null;
         EmployeeView employeeView = null;

        EmployeeProjectAllocationView employeeProjectAllocationView = null;
     
      try {
         session = sessionFactory.openSession();
         String globalEmpId=singleRequestVO.getGlobalEmployeeId();
         String employeeSelectedProjectId=singleRequestVO.getEmployeeSelectedProjectId();
         
         String employeeProjectAllocationQuery = "FROM  EmployeeProjectAllocationView WHERE globalEmpId=:employeeId and projectID=:employeeSelectedProjectId";
         
         Query query = session.createQuery(employeeProjectAllocationQuery);

         query .setParameter("employeeId", globalEmpId);
         
         query.setParameter("employeeSelectedProjectId",employeeSelectedProjectId);

         employeeProjectAllocationView =(EmployeeProjectAllocationView) query.uniqueResult();
         
         globalEmpId = employeeProjectAllocationView.getManagerID();

           String employeeViewQuery = "FROM EmployeeView WHERE globalEmpId=:employeeId";
           
           Query query1 = session.createQuery(employeeViewQuery);

           query1.setParameter("employeeId", globalEmpId);
        
           employeeView = (EmployeeView) query1.uniqueResult();

         logger.info("getProjectApprover()-> " + "Project Approver DAO: Success");
         } catch (Exception exception) {
          logger.error("getProjectApprover() -> ERROR: " + exception.getCause());
         } finally {
          if (!CabServiceUtil.isNULL(session))
          session.close();
               }
                 return employeeView;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PendingRequestSQLMapper> restrictMultipleCreateRequest(String employeeId) {
		logger.info("restrictMultipleCreateRequest() -> Restrict Multiple Create Request DAO Call...");

		Session session = null;
		
		List<PendingRequestSQLMapper> pendingSQLMapperEntity = null;		

		try {
			session = sessionFactory.openSession();
			
			String entityQuery="SELECT elclm.CITY_LANDMARK_NAME as landmark, COALESCE(rh.pendingApprCount, 0) AS pendingApprCount, COALESCE(ta.noShowCount, 0) AS noShowCount FROM ( SELECT el.EMPLOYEE_ID, clm.CITY_LANDMARK_NAME FROM EMPLOYEE_LANDMARK el JOIN CITY_LANDMARK_MASTER clm ON clm.CITY_LANDMARK_MASTER_ID = el.FK_CITY_LANDMARK_MASTER_ID AND el.ACTIVE = 'true' ) elclm LEFT JOIN ( SELECT ta.EMPLOYEE_ID, count(*) as noShowCount FROM TRIP_AVAILED ta JOIN TRIP_HEADER th ON th.TRIP_HEADER_ID = ta.FK_TRIP_HEADER_ID WHERE ta.IS_TRIP_AVAILED = 'false' AND th.TRIP_DATE >= GETDATE()-1 AND th.TRIP_DATE <=DATEADD(DAY, 90, GETDATE()) GROUP BY EMPLOYEE_ID, IS_TRIP_AVAILED ) ta ON ta.EMPLOYEE_ID = elclm.EMPLOYEE_ID LEFT JOIN ( SELECT count(*) AS pendingApprCount, EMPLOYEE_ID FROM REQUEST_HEADER WHERE APPROVE_STATUS = 'Pending' GROUP BY EMPLOYEE_ID ) rh ON rh.EMPLOYEE_ID = elclm.EMPLOYEE_ID WHERE elclm.EMPLOYEE_ID = :employeeId";
			
			SQLQuery query = session.createSQLQuery(entityQuery);

			query.setParameter("employeeId", employeeId);

			pendingSQLMapperEntity=query.setResultTransformer(Transformers.aliasToBean(PendingRequestSQLMapper.class)).list();
			
			if(pendingSQLMapperEntity.isEmpty()){
				
				PendingRequestSQLMapper pendingRequestSQLMapper= new PendingRequestSQLMapper();
				
				pendingRequestSQLMapper.setNoShowCount(0);
				
				pendingRequestSQLMapper.setPendingApprCount(0);
				
				pendingSQLMapperEntity.add(pendingRequestSQLMapper);			
						
			}
			
		   logger.info("restrictMultipleCreateRequest() -> Restrict Multiple Create Request DAO Call:Success");
		} catch (RuntimeException exception) {
			logger.error("Restrict Multiple Create Request() -> ERROR: " + exception.getCause());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}

		return pendingSQLMapperEntity;
	}	

}
