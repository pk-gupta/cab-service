package com.marlabs.cab.service.web.common.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RosterController {

	private static Logger logger = LogManager.getLogger(RosterController.class);
	
	@Autowired
	private Job rosterJob;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
    Environment environment;
	
	@RequestMapping(value = "/employee/uploadRoster", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> processRoster(@RequestParam("uploadedRoster") MultipartFile uploadedRoster) throws Exception {

		logger.info("processRoster() -> STARTED ROSTER BATCH PROCESS...");
		
		byte[] uploadedRosterBytes = uploadedRoster.getBytes();
		ClassLoader classLoader = getClass().getClassLoader();
		
		File rosterDataFile = new File(classLoader.getResource(environment.getProperty("batch.roster.excel.file.path")).getFile());
		String rosterDataFilePath = rosterDataFile.getAbsolutePath();
		Files.write(Paths.get(rosterDataFilePath), uploadedRosterBytes, StandardOpenOption.WRITE);
		
		JobParameters jobParameters = new JobParametersBuilder().addLong("currentTime", System.currentTimeMillis())
				.toJobParameters();
		JobExecution jobExecution = jobLauncher.run(rosterJob, jobParameters);
		
		// TO DO: Handle Exceptions & Validate Column Value messages
		
		logger.info("processRoster() -> COMPLETED ROSTER BATCH PROCESS");
		
		return new ResponseEntity<>(jobExecution.getStatus().getBatchStatus().toString(), HttpStatus.OK);
	}
}
