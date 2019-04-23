package com.marlabs.cab.service.domain.batch.roster;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class RosterJobCompletionListener extends JobExecutionListenerSupport {

	private static Logger logger = LogManager.getLogger(RosterJobCompletionListener.class);
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			logger.info("BATCH JOB COMPLETED SUCCESSFULLY");
		}
	}

}
