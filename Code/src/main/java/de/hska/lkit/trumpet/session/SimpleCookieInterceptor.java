package de.hska.lkit.trumpet.session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class SimpleCookieInterceptor implements HandlerInterceptor {
	@Autowired
	private StringRedisTemplate template;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		System.out.println("intercepted");
		Cookie[] cookies = req.getCookies();
		if (!ObjectUtils.isEmpty(cookies))
			for (Cookie cookie : cookies)
				if (cookie.getName().equals("auth")) {
					String auth = cookie.getValue();
					if (auth != null) {
						String uid = template.opsForValue().get("auth:" + auth + ":uid");
						if (uid != null) {
							String name = (String) template.boundHashOps("uid:" + uid + ":user").get("name");
							SimpleSecurity.setUser(name, uid);
						}
					}
				}
		return true;
	}
	// clean up SimpleSession State in the end (skipped here)

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}