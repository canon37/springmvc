package com.zuipin.framework.exception;

/**
 * 
 * @Title: ParamInjectException
 * @Package: com.zuipin.framework.exception
 * @author: zengxinchao  
 * @date: 2017年4月26日 下午2:47:18
 * @Description: ParamInjectException
 */
public class ParamInjectException extends Exception{
	private static final long serialVersionUID = -2073655108403204819L;

	public ParamInjectException(Throwable throwable, String argument){
		super(String.format("parameter inject failed: '%s' ,input format error or other reason", argument), throwable);
	}
}
