package com.marlabs.cab.service.domain.batch.roster;

import java.util.List;

import javax.persistence.ParameterMode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.marlabs.cab.service.common.util.CabServiceUtil;

public class RosterWriter implements ItemWriter<RosterMapperVO> {

	private static Logger logger = LogManager.getLogger(RosterWriter.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void write(List<? extends RosterMapperVO> rosterMapperList) throws Exception {

		logger.info("write() -> Started Roster Data to Database");

		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			ProcedureCall procedure = session.createStoredProcedureCall("RosterCabServiceProc");
			
			/*if(!Optional.ofNullable(items.get(0).getEmployeeId()).isPresent()){
				return;
			}*/
			
			rosterMapperList.forEach(rosterVO -> {
				procedure.registerParameter(1, String.class, ParameterMode.IN).bindValue(rosterVO.getEmployeeId());
				procedure.registerParameter(2, String.class, ParameterMode.IN).bindValue(rosterVO.getEmployeePhone());
				procedure.registerParameter(3, String.class, ParameterMode.IN).bindValue(rosterVO.getManagerId());
				procedure.registerParameter(4, String.class, ParameterMode.IN).bindValue(rosterVO.getProjectId());
				procedure.registerParameter(5, String.class, ParameterMode.IN).bindValue(rosterVO.getFromDate());
				procedure.registerParameter(6, String.class, ParameterMode.IN).bindValue(rosterVO.getToDate());
				procedure.registerParameter(7, String.class, ParameterMode.IN).bindValue(rosterVO.getRequestServiceType());
				procedure.registerParameter(8, String.class, ParameterMode.IN).bindValue(rosterVO.getRequestType());
				procedure.registerParameter(9, String.class, ParameterMode.IN).bindValue(rosterVO.getOficeBranch());
				procedure.registerParameter(10, String.class, ParameterMode.IN).bindValue(rosterVO.getWeekOff1());
				procedure.registerParameter(11, String.class, ParameterMode.IN).bindValue(rosterVO.getWeekOff2());
				procedure.registerParameter(12, String.class, ParameterMode.IN).bindValue(rosterVO.getWeekOff3());
				procedure.registerParameter(13, String.class, ParameterMode.IN).bindValue(rosterVO.getNoOffWeekOffs());
				procedure.registerParameter(14, String.class, ParameterMode.IN).bindValue(rosterVO.getLandMark());
				procedure.registerParameter(15, String.class, ParameterMode.IN).bindValue(rosterVO.getLoginTime());
				procedure.registerParameter(16, String.class, ParameterMode.IN).bindValue(rosterVO.getLogOutTime());
				procedure.registerParameter(17, String.class, ParameterMode.IN).bindValue(rosterVO.getReason());
				procedure.registerParameter(18, String.class, ParameterMode.IN).bindValue(rosterVO.getManagerRemark());
				procedure.registerParameter(19, String.class, ParameterMode.OUT);
			});
		
			ProcedureOutputs procedureResult = procedure.getOutputs();

			logger.info("write() -> Started Roster Data to Database -- " + procedureResult.getOutputParameterValue(19));

			session.getTransaction().commit();

		} catch (Exception exception) {
			logger.error("write() -> ERROR: " + exception.getCause());
			logger.error("Stack Trace: " + exception.getStackTrace());
		} finally {
			if (!CabServiceUtil.isNULL(session))
				session.close();
		}
	}

}
