package com.zuipin.framework.exception;

/**
 * 
 * @Title: IllegalParamException
 * @Package: com.zuipin.framework.exception
 * @author: zengxinchao  
 * @date: 2017年5月4日 下午1:34:01
 * @Description: IllegalParamException
 */
public class IllegalParamException extends RuntimeException{

	private static final long serialVersionUID = 6659275682706729478L;

	public IllegalParamException(String msg){
		super("illegal param: " + msg);
	}
}
