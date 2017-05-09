package com.zuipin.framework.emuns;

/**
 * 
 * @Title: ErrorCode
 * @Package: com.zuipin.framework.emuns
 * @author: zengxinchao
 * @date: 2017年3月9日 上午10:04:34
 * @Description: 请在这里添加各种状态码，添加前请确认有必要添加
 * 按code顺序添加，code请保证唯一，message请用英文做必要说明
 */
public enum ErrorCode {	
	SUCCESS(0, "success"), 
	ILLEGAL_PARAM(1001, "illegal paramer"), 
	PARAM_ABSENT(1002, "necessary paramer absent"), 
	DB_EXCEPTION(1003, "database exception"), 
	EXCEPTION(1004, "system exception"), 
	ELSE(1000, "other reason");
	
	private int code;
	private String message;
	
	private ErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getCode() {
		return code;
	}
}
