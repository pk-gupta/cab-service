package com.marlabs.cab.service.domain.batch.roster;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

/**
 * @author SureshGowda
 */
@Configuration
public class RosterJobConfig {

	private static final String PROPERTY_EXCEL_SOURCE_FILE_PATH = "batch.roster.excel.file.path";

	@Bean
	ItemReader<RosterMapperVO> rosterReader(Environment environment) {
		PoiItemReader<RosterMapperVO> reader = new PoiItemReader<>();
		reader.setLinesToSkip(1);
		reader.setResource(new ClassPathResource(environment.getRequiredProperty(PROPERTY_EXCEL_SOURCE_FILE_PATH)));
		reader.setRowMapper(rosterBatchRowMapper());
		
		return reader;
	}

	/**
	 * If your Excel document has no header, you have to create a custom row
	 * mapper and configure it here.
	 */
	private RowMapper<RosterMapperVO> rosterBatchRowMapper() {
		return new RosterBatchRowMapper();
	}

	@Bean
	ItemProcessor<RosterMapperVO, RosterMapperVO> rosterProcessor() {
		return new RosterProcessor();
	}

	@Bean
	ItemWriter<RosterMapperVO> rosterWriter() {
		return new RosterWriter();
	}

	@Bean
	Step rosterStep(ItemReader<RosterMapperVO> rosterReader,
			ItemProcessor<RosterMapperVO, RosterMapperVO> rosterProcessor, ItemWriter<RosterMapperVO> rosterWriter,
			StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("rosterStep").<RosterMapperVO, RosterMapperVO>chunk(1).reader(rosterReader)
				.processor(rosterProcessor).writer(rosterWriter).listener(rosterListener()).build();
	}

	@Bean
	Job rosterJob(JobBuilderFactory jobBuilderFactory, @Qualifier("rosterStep") Step rosterStep) {
		return jobBuilderFactory.get("rosterJob").incrementer(new RunIdIncrementer()).flow(rosterStep).end()
				.build();
	}
	
	@Bean
	public JobExecutionListener rosterListener() {
		return new RosterJobCompletionListener();
	}
	
}
