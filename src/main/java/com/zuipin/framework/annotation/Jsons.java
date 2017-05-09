package com.zuipin.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @Title: Jsons
 * @Package: com.zuipin.framework.annotation
 * @author: zengxinchao  
 * @date: 2017年4月21日 下午5:24:11
 * @Description: Jsons
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Jsons {
	Json[] value();
}
