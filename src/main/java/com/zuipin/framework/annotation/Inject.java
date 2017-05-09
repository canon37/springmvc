package com.zuipin.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @Title: Inject
 * @Package: com.zuipin.framework.annotation
 * @author: zengxinchao  
 * @date: 2017年3月24日 上午8:49:32
 * @Description: request参数自动注入对象，对应入参属性名需要以"对象名_属性名"命名，支持多级对象
 * 最终值类型支持String,Long,Integer,Double,Float,Boolean,Short不支持基本数据类型
 */
@Documented
@Target(ElementType.PARAMETER)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {
	
}
