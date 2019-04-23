package com.marlabs.cab.service.config;
/*
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;*/

public class JwtFilter {//extends GenericFilterBean {

	/*private static Logger logger = LogManager.getLogger(JwtFilter.class);

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request1 = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if ("OPTIONS".equalsIgnoreCase(request1.getMethod())) {
			response.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers",
					"content-type,access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with");
			response.setStatus(HttpServletResponse.SC_OK);
		} else {

			final HttpServletRequest request = (HttpServletRequest) req;

			logger.info("##########Entered into JwtFilter.doFilter()##########");

			String token_param = request.getParameter("access_token");

			String authHeader = request.getHeader("Authorization");

			if (authHeader == null) {
				authHeader = "Bearer " + token_param;
			}

			if (authHeader == null || !authHeader.startsWith("Bearer")) {
				throw new ServletException("Missing or invalid Authorization header.");
			}

			final String token = authHeader.substring(7); // The part after
															// "Bearer "

			try {
				final Claims claims = Jwts.parser().setSigningKey("123456".getBytes("UTF-8")).parseClaimsJws(token)
						.getBody();
				request.setAttribute("claims", claims);

				// final Claims claims1=new DefaultClaims();
				// claims1.put("contactId", token);
				// request.setAttribute("claims", claims1);

			} catch (final SignatureException e) {
				throw new ServletException("Invalid token.", e);
			}
			chain.doFilter(req, res);
			logger.info("##########Exit from JwtFilter.doFilter()##########");
		}
	}*/
}
