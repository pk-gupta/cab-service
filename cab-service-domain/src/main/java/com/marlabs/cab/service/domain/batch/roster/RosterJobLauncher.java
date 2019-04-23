package com.marlabs.cab.service.domain.batch.roster;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * @author SureshGowda
 */
@Component
public class RosterJobLauncher {

	private static Logger logger = LogManager.getLogger(RosterJobLauncher.class);

	private final Job job;

	private final JobLauncher jobLauncher;

	@Autowired
	private PoiItemReader<RosterMapperVO> rosterReader;

	@Autowired
	RosterJobLauncher(@Qualifier("rosterJob") Job job, JobLauncher jobLauncher) {
		this.job = job;
		this.jobLauncher = jobLauncher;
	}

	void launchRosterBatchJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException {

		logger.info("launchRosterBatchJob() -> Started Roster Job Launcher...");

		rosterReader.setResource(new ClassPathResource(""));
		jobLauncher.run(job, newExecution());

		logger.info("launchRosterBatchJob() -> Completed Roster Job Launcher");
	}

	private JobParameters newExecution() {
		Map<String, JobParameter> parameters = new HashMap<>();

		JobParameter parameter = new JobParameter(new Date());
		parameters.put("currentTime", parameter);

		return new JobParameters(parameters);
	}
}
