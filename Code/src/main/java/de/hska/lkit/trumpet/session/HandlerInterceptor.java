package de.hska.lkit.trumpet.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface HandlerInterceptor  {
	boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler);

	void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView);

	void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex);

}
