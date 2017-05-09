package com.zuipin.framework.exception;

/**
 * 
 * @Title: ParamAbsentException
 * @Package: com.zuipin.framework.exception
 * @author: zengxinchao  
 * @date: 2017年5月4日 下午1:33:54
 * @Description: ParamAbsentException
 */
public class ParamAbsentException extends RuntimeException{

	private static final long serialVersionUID = 7177686802412227432L;

	public ParamAbsentException(String msg){
		super(msg);
	}
}
