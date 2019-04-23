package com.marlabs.cab.service.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;

//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class AccessControlFilter implements Filter {
	
	private final Logger log = LogManager.getLogger(AccessControlFilter.class);

	@Autowired
	RedisOperationsSessionRepository redisOperationsSessionRepository;
	
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		log.info("doFilter() -> Starting Point from Access Control Filter...");
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        String clientOrigin = request.getHeader("origin");
        response.addHeader("Access-Control-Allow-Origin", clientOrigin);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Headers, Accept, X-XSRF-TOKEN, XSRF-TOKEN, X-Requested-By, Content-Type, Origin, Authorization, X-Requested-With, x-auth-token, OPTIONS");
        response.addHeader("Access-Control-Expose-Headers", "x-auth-token, XSRF-TOKEN");
        
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        }else if(!isMethodAllowed(request)) {
        	log.error("doFilter() -> Requested METHOD NOT ALLOWED...!!!");
        	response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }else {
        	filterChain.doFilter(request, response);
        }
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {}
	
	private boolean isMethodAllowed(HttpServletRequest request){
		if(!"POST".equalsIgnoreCase(request.getMethod())
				&& !"GET".equalsIgnoreCase(request.getMethod())){
			return false;
		}
		
		return true;
	}
}
