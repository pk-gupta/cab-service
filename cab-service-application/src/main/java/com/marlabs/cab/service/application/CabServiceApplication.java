package com.marlabs.cab.service.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import com.marlabs.cab.service.security.authentication.config.CabServiceSecurityConfiguration;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages={"com.marlabs.cab.service" , "com.marlabs.cab.service.config", "com.marlabs.cab.service.common", "com.marlabs.cab.service.domain", "com.marlabs.cab.service.persistance", "com.marlabs.cab.service.security"})
@EntityScan(basePackages={"com.marlabs.cab.service.persistance"})
@EnableJpaRepositories(basePackages={"com.marlabs.cab.service.persistance"})
@EnableBatchProcessing
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800 )
//@CrossOrigin(origins= "*")
public class CabServiceApplication {
	
	private static Logger logger = LogManager.getLogger(CabServiceApplication.class);
	
    @Bean
    public WebSecurityConfigurerAdapter cabServiceSecurityConfiguration() {
        return new CabServiceSecurityConfiguration();
    }

	public static void main(String[] args) {
		logger.info("starting the app");
		SpringApplication.run(CabServiceApplication.class, args);
	}
}
