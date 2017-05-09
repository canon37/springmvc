package com.zuipin.conf;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.zuipin.framework.handler.ResponseBodyHandler;
import com.zuipin.framework.listener.InitalListener;
import com.zuipin.framework.resolver.ArgumentInjectResolver;

/**
 * 
 * @Title: WebMvcConfig
 * @Package: com.zuipin.conf
 * @author: zengxinchao  
 * @date: 2017年5月4日 下午3:51:42
 * @Description: WebMvcConfig
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	@Resource
	private ArgumentInjectResolver argumentResolver;
	
	@Resource
	private ResponseBodyHandler responseBodyHandler;
	
	/**
	 * 参数解析
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(argumentResolver);
	}
	/**
	 * 返参解析
	 */
	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers){
		returnValueHandlers.add(responseBodyHandler);
	}
	/**
	 * 
	 *@author: zengxinchao  
	 *@date: 2017年5月4日 下午3:52:40
	 *@return
	 *@Description: 加载listener
	 */
	@Bean
	public InitalListener initServletContex(){
		return new InitalListener();
	}
	/**
	 * 
	 *@author: zengxinchao  
	 *@date: 2017年5月9日 下午3:33:40
	 *@return
	 *@Description: jsp
	 */
	@Bean
	InternalResourceViewResolver internalResourceViewResolver () {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
