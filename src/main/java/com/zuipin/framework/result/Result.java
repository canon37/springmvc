package com.zuipin.framework.result;

import com.zuipin.framework.emuns.ErrorCode;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Title: Result
 * @Package: com.zuipin.framework.result
 * @author: zengxinchao
 * @date: 2017年3月9日 上午10:04:11
 * @Description: Result
 */
public class Result<T> {	
	/** 错误编码 */
	@ApiModelProperty(example = "1001")
	private Integer		code;
	
	/** 错误信息*/
	@ApiModelProperty(example = "illegal param")
	private String		message;
	
	/** 数据 */
	private T			data;
	
	public Result(ErrorCode errorCode, T data) {
		super();
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.data = data;
	}
	
	public Result(ErrorCode errorCode) {
		super();
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
	}
	
	public Result() {
		super();
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
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
