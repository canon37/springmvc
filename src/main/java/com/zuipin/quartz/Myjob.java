package com.zuipin.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class Myjob extends QuartzJobBean{
	private Logger log = LoggerFactory.getLogger(Myjob.class);
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//do your job here
		log.info("123");
	}

}
