package com.marlabs.cab.service.security.authentication.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.SessionRepositoryFilter;

import com.marlabs.cab.service.config.AccessControlFilter;
import com.marlabs.cab.service.security.authentication.entry.CabServiceUserAuthenticationEntryPoint;
import com.marlabs.cab.service.security.authentication.filter.CabServiceUserAunthenticationFilter;
import com.marlabs.cab.service.security.authentication.handler.CabServiceUserAuthenticationAccessDeniedHandler;
import com.marlabs.cab.service.security.authentication.handler.CabServiceUserAuthenticationFailureHandler;
import com.marlabs.cab.service.security.authentication.handler.CabServiceUserAuthenticationSuccessHandler;
import com.marlabs.cab.service.security.authentication.handler.CabServiceUserLogoutHandler;
import com.marlabs.cab.service.security.authentication.matcher.CabServiceRequestPathMatcher;
import com.marlabs.cab.service.security.authentication.provider.CabServiceUserAuthenticationProvider;

@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class CabServiceSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private static final String LOGIN_URI_PATH = "/mfleet/login";
	private static final String LOGOUT_URI_PATH = "/logout";
	private static final String ANT_MATCHER = "/mfleet/**";
	private static final String X_AUTH_TOKEN = "x-auth-token";
	private static final String XSRF_TOKEN = "XSRF-TOKEN";
	private static final String X_XSRF_TOKEN = "X-XSRF-TOKEN";
	
	@Autowired
	private CabServiceUserAunthenticationFilter aunthenticationFilter;
	
	@Autowired
	private CabServiceUserAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private CabServiceUserAuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	private CabServiceUserAuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	private CabServiceUserAuthenticationProvider authenticationProvider;
	
	@Autowired
	private CabServiceUserLogoutHandler userLogoutHandler;
	
	@Autowired
	private CabServiceUserAuthenticationAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private SessionRepository<? extends ExpiringSession> sessionRepository;
	
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.authenticationProvider(authenticationProvider);
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
       web.ignoring().antMatchers(HttpMethod.OPTIONS, ANT_MATCHER);
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		final SessionRepositoryFilter sessionRepositoryFilter = new SessionRepositoryFilter(sessionRepository);
        sessionRepositoryFilter.setHttpSessionStrategy(new HeaderHttpSessionStrategy());
        
        String[] patterns = new String[] {"/login" };
		
		// Only HTTP Requests 
		/*http.requiresChannel()
		      .anyRequest()
		      .requiresInsecure();*/
				
		// Only HTTPS Requests 
		http.requiresChannel()
		    .antMatchers(ANT_MATCHER)
		    .requiresSecure();

		http.cors().disable()
			.csrf()
			.requireCsrfProtectionMatcher(new CabServiceRequestPathMatcher(patterns))
			.csrfTokenRepository(getCsrfTokenRepository())
		.and()
			.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint)
			.accessDeniedHandler(accessDeniedHandler)
		.and()
			//.addFilterBefore(new AccessControlFilter(), ChannelProcessingFilter.class)
			.addFilterBefore(sessionRepositoryFilter, ChannelProcessingFilter.class)
			.addFilterBefore(new AccessControlFilter(), SessionRepositoryFilter.class)
			.addFilterBefore(aunthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			//.antMatchers(ANT_MATCHER).authenticated()
			.antMatchers("/admin/assigncab/**").hasAnyAuthority("ADMIN", "VENDOR")
			.antMatchers("/admin/**").hasAuthority("ADMIN")
			.antMatchers("/employee/**").hasAnyAuthority("EMPLOYEE", "ADMIN")
	        .anyRequest().authenticated()
		.and()
			.formLogin()
			.loginProcessingUrl(LOGIN_URI_PATH)
			.usernameParameter("username")
	        .passwordParameter("password")
			.successHandler(authenticationSuccessHandler)
			.failureHandler(authenticationFailureHandler)
		.and()
			.logout()
			.clearAuthentication(true)
			.logoutUrl(LOGOUT_URI_PATH).permitAll()
			.invalidateHttpSession(true)
		    .deleteCookies(X_AUTH_TOKEN)
		    .deleteCookies(XSRF_TOKEN)
		    .deleteCookies(X_XSRF_TOKEN)
		    .logoutSuccessHandler(userLogoutHandler)
		.and()
			.httpBasic().disable();
			//.x509().disable();
			
		//Prevent creation of session when user is not authenticated so that we have only authenticated users’ session ids stored in Redis.
		http.requestCache()
	        	.requestCache(new NullRequestCache());
			
	    http.sessionManagement()
        	//.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	    	//.sessionCreationPolicy(SessionCreationPolicy.NEVER)
        	.sessionFixation().migrateSession()
            .invalidSessionUrl(LOGIN_URI_PATH)
            .maximumSessions(1)  // allows multiple concurrent sessions for the same user
        	.maxSessionsPreventsLogin(true)
            .expiredUrl(LOGIN_URI_PATH)
            .sessionRegistry(sessionRegistry);
        	//.sessionAuthenticationStrategy(null);// Refer; https://github.com/spring-projects/spring-security/issues/3376
        
        http.headers()
        	//.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
        	.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.DENY))
        	.frameOptions().disable()
        	.xssProtection().block(true)
        .and()
        	.cacheControl()
        .and()
        	.httpStrictTransportSecurity()
        	.includeSubDomains(true).maxAgeInSeconds(31536000)
        .and()	
        	.referrerPolicy(ReferrerPolicy.SAME_ORIGIN);// ENABLE for DEV, STG & PROD
        
	}
	
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
	    return new ProviderManager(Arrays.asList(new CabServiceUserAuthenticationProvider()));
	}
	
	private CookieCsrfTokenRepository getCsrfTokenRepository() {
	    CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
	    tokenRepository.setCookiePath("/");
	    return tokenRepository;
	} 
	
}
