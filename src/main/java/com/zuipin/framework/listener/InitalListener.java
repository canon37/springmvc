package com.zuipin.framework.listener;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static com.zuipin.framework.handler.ResponseBodyHandler.RESPONSE_VALUE_FILTER_MAP;

/**
 * 
 * @Title: InitalListener
 * @Package: com.zuipin.framework.listener
 * @author: zengxinchao  
 * @date: 2017年4月25日 上午10:15:15
 * @Description: InitalListener
 */
public class InitalListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		
		context.setAttribute(RESPONSE_VALUE_FILTER_MAP, new HashMap<String, Object>());	
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
