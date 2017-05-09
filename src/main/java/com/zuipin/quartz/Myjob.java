package com.zuipin.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class Myjob extends QuartzJobBean{
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//do your job here
	}

}
