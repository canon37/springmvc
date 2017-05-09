package com.zuipin.framework.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.zuipin.framework.annotation.Inject;

/**
 * 
 * @Title: InputAndOutputLogger
 * @Package: com.zuipin.framework.aop
 * @author: zengxinchao  
 * @date: 2017年5月4日 上午8:56:21
 * @Description: 记录handlerMethod的入参和出参
 */
@Component
@Aspect
public class InputAndOutputLogger {
	public static Logger log = LoggerFactory.getLogger(InputAndOutputLogger.class);
	
	@Around(value="@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object writeLog(ProceedingJoinPoint joinPoint) throws Throwable{
	    StringBuffer input = new StringBuffer();
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
	    Method method = methodSignature.getMethod();
	    Object[] args = joinPoint.getArgs();
	    Parameter[] parameters = method.getParameters();
	    String[] parameterNames = methodSignature.getParameterNames();
	    for (int i=0; i<parameters.length; i++) {
	    	if(parameters[i].getAnnotation(RequestBody.class) != null 
	    			|| parameters[i].getAnnotation(Inject.class) != null
	    			|| parameters[i].getAnnotation(RequestParam.class) != null){//记录@RequestBody@Inject@RequestParam注解的入参
	    		input.append(parameterNames[i]).append(":").append(JSONObject.toJSON(args[i])).append(",");
	    	}
		}
	    
	    StringBuffer info = new StringBuffer();
	    info.append("controller:").append(method.getDeclaringClass().getName());
    	info.append("--handlerMethod:").append(method.getName());
    	info.append("--requestParam:{").append(input);
    	
    	try {
    		Object result = joinPoint.proceed();
    	    info.append("}--responseValue:{").append(JSONObject.toJSON(result)).append("}");//记录出参
    	    return result;
		} finally {
			log.info(info.toString());
		}   
	}
}
