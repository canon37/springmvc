package com.zuipin.framework.aop;

import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @Title: InvokedDuration
 * @Package: com.zuipin.framework.aop
 * @author: zengxinchao  
 * @date: 2017年4月26日 下午3:07:54
 * @Description: hanlderMethod调用时间
 */
@Component
@Aspect
public class InvokedDuration {
	private static Logger log = LoggerFactory.getLogger(InvokedDuration.class);
	
	@Around(value="@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object duration(ProceedingJoinPoint joinPoint) throws Throwable{
		StopWatch watch = new StopWatch();
		watch.start();
		Object object = joinPoint.proceed();
		watch.stop();
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		StringBuffer info = new StringBuffer();
		info.append(signature.getMethod().getDeclaringClass().getName()).append("---");
		info.append("handlerMethod: ").append(signature.getMethod().getName()).append("---");
		info.append("takes: ").append(watch.getTime()).append("ms");
		log.info(info.toString());
		return object;
	}
}
