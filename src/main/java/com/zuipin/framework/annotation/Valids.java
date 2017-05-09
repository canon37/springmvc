package com.zuipin.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @Title: Valids
 * @Package: com.zuipin.framework.annotation
 * @author: zengxinchao  
 * @date: 2017年5月2日 上午9:30:22
 * @Description: Valids
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Valids {
	Valid[] value();
}
