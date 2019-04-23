package com.marlabs.cab.service.security.authentication.repository;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import redis.clients.jedis.Jedis;

/* IMPORTANT NOTE:  This Class is NOT USED in the Application
 * 
Ref:  Google: spring security redis csrf token repository
	  https://github.com/bassmake/CsrfRestExample
	  https://stackoverflow.com/questions/38160916/csrf-token-in-redis-spring-session
	  https://sdqali.in/blog/2016/07/20/csrf-protection-with-spring-security-and-angular-js/
	  https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#csrf
	  https://medium.com/spektrakel-blog/angular2-and-spring-a-friend-in-security-need-is-a-friend-against-csrf-indeed-9f83eaa9ca2e
	  https://stackoverflow.com/questions/39976202/csrf-with-spring-and-angular-2
	  https://www.future-processing.pl/blog/exploring-spring-boot-and-spring-security-custom-token-based-authentication-of-rest-services-with-spring-security-and-pinch-of-spring-java-configuration-and-spring-integration-testing/
	  https://github.com/ehirsch/spring-angular2
	  https://github.com/sdqali/csrf-demo
	  https://stackoverflow.com/questions/43349948/angular-2-spring-security-csrf-tokenhttps://spring.io/blog/2015/01/20/the-resource-server-angular-js-and-spring-security-part-iii
*/
@Component
public class RedisCsrfTokenRepository implements CsrfTokenRepository  {
	
	private final Logger log = LogManager.getLogger(RedisCsrfTokenRepository.class);

	public static final String XSRF_PARAMETER_NAME = "_csrf";

    public static final String XSRF_HEADER_NAME = "X-XSRF-TOKEN";

    private final Jedis tokenRepository = new Jedis("localhost", 6379);

    public RedisCsrfTokenRepository() {
        log.info("Creating {}", RedisCsrfTokenRepository.class.getSimpleName());
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        return new DefaultCsrfToken(XSRF_HEADER_NAME, XSRF_PARAMETER_NAME, createNewToken());
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        String key = getKey(request);
        if (key == null)
            return;

        if (token == null) {
            tokenRepository.del(key.getBytes());
        } else {
            tokenRepository.set(key.getBytes(), SerializationUtils.serialize(token));
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String key = getKey(request);
        if (key != null) {
            byte[] tokenString = tokenRepository.get(key.getBytes());
            if (tokenString != null) {
                return (CsrfToken) SerializationUtils.deserialize(tokenString);
            }
        }
        return null;
    }

    private String getKey(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private String createNewToken() {
        return UUID.randomUUID().toString();
    }

}
