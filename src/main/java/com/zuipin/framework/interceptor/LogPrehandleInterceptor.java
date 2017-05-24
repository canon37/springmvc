package com.zuipin.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @Title: LogPrehandleInterceptor
 * @Package: com.zuipin.framework.interceptor
 * @author: zengxinchao  
 * @date: 2017年5月24日 下午4:03:33
 * @Description: LogPrehandleInterceptor
 */
@Component
public class LogPrehandleInterceptor implements HandlerInterceptor{
	public static String REQUEST_ID = "X-Request-ID";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		MDC.put("requestId", request.getHeader(REQUEST_ID));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
