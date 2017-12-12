package de.hska.lkit.trumpet.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SimpleInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = Logger.getLogger(SimpleInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("Inside the prehandle");
		return false;
	}
}
