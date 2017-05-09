package com.zuipin.framework.quartz.factory;

import javax.annotation.Resource;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;
/**
 * 
 * @Title: JobFactory
 * @Package: com.zuipin.framework.quartz.factory
 * @author: zengxinchao  
 * @date: 2017年5月4日 下午4:04:52
 * @Description: JobFactory
 */
@Component
public class JobFactory extends AdaptableJobFactory{
	@Resource
	private AutowireCapableBeanFactory capableBeanFactory;
	
	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		Object jobInstance = super.createJobInstance(bundle);
		capableBeanFactory.autowireBean(jobInstance);
		return jobInstance;
	}
}
