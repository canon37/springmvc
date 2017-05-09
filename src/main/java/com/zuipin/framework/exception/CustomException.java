package com.zuipin.framework.exception;

import com.zuipin.framework.emuns.ErrorCode;

/**
 * 
 * @Title: CustomException
 * @Package: com.zuipin.framework.exception
 * @author: zengxinchao  
 * @date: 2017年5月3日 下午3:43:18
 * @Description: CustomException
 */
public class CustomException extends Exception{
	private static final long serialVersionUID = -197689979445010501L;
	private Integer code;
	private String message;
	
	public CustomException(){
		super();
	}
	
	public CustomException(Throwable thr){
		super(thr);
	}
	
	public CustomException(Integer code, String message){
		this.code = code;
		this.message = message;
	}
	
	public CustomException(ErrorCode errorCode){
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
