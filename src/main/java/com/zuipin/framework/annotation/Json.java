package com.zuipin.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @Title: Json
 * @Package: com.zuipin.framework.annotation
 * @author: zengxinchao  
 * @date: 2017年4月21日 下午5:24:18
 * @Description: Json 控制返参的字段
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Jsons.class)
public @interface Json {
	Class<?> type();
	String include() default "";
	String exclude() default "";
}
