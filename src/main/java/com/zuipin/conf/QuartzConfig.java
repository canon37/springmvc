package com.zuipin.conf;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.zuipin.framework.quartz.factory.JobFactory;
import com.zuipin.quartz.Myjob;
import com.zuipin.util.QuartzUtil;

/**
 */
@Configuration
public class QuartzConfig {
	@Value("${spring.datasource.driver-class-name}")
    private String driver;
	
	@Value("${spring.datasource.url}")
    private String url;
	
	@Value("${spring.datasource.username}")
    private String username;
	
	@Value("${spring.datasource.password}")
    private String password;
	
	@Resource
	private JobFactory jobFactory;
	
	private Properties quartzProperties(){
        Properties prop = new Properties();
        prop.put("quartz.scheduler.instanceName", "ServerScheduler");
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        prop.put("org.quartz.scheduler.skipUpdateCheck", "true");
        prop.put("org.quartz.scheduler.jmx.export", "true");

        prop.put("org.quartz.scheduler.jobFactory.class", "org.quartz.simpl.SimpleJobFactory");
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        prop.put("org.quartz.jobStore.dataSource", "quartzDataSource");
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        prop.put("org.quartz.jobStore.isClustered", "true");
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "5");

        prop.put("org.quartz.dataSource.quartzDataSource.driver", driver);
        prop.put("org.quartz.dataSource.quartzDataSource.URL", url);
        prop.put("org.quartz.dataSource.quartzDataSource.user", username);
        prop.put("org.quartz.dataSource.quartzDataSource.password", password);
        prop.put("org.quartz.dataSource.quartzDataSource.maxConnections", "10");
		return prop;
	}
	
	@Bean  
    public JobDetailFactoryBean myJob1() {  
        return QuartzUtil.createJobDetail(Myjob.class, "mygroup", "myjob");  
    }  

    @Bean(name = "myJobTrigger1")  
    public CronTriggerFactoryBean dialogStatusJobTrigger1(@Qualifier("myJob1") JobDetail jobDetail) {  
        return QuartzUtil.dialogStatusTrigger(jobDetail, "0/3 * * * * ?");  
    }
	
    /** this is a demonstrate*/
	@Bean  
    public JobDetailFactoryBean myJob() {  
        return QuartzUtil.createJobDetail(Myjob.class, "mygroup", "myjob");  
    }  

    @Bean(name = "myJobTrigger")  
    public CronTriggerFactoryBean dialogStatusJobTrigger(@Qualifier("myJob") JobDetail jobDetail) {  
        return QuartzUtil.dialogStatusTrigger(jobDetail, "0/10 * * * * ?");  
    }
    /** this is a demonstrate*/
    
	@Bean  
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("myJobTrigger") Trigger cronJobTrigger) throws IOException {  
        SchedulerFactoryBean factory = new SchedulerFactoryBean();  
        factory.setJobFactory(jobFactory);
        factory.setOverwriteExistingJobs(true);  
        factory.setStartupDelay(10);
        factory.setQuartzProperties(quartzProperties());
        factory.setAutoStartup(true);
        factory.setApplicationContextSchedulerContextKey("applicationContext");
        //factory.setTriggers(cronJobTrigger);
        return factory;  
    }
}
