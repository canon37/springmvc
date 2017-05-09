package com.zuipin.framework.handler;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zuipin.framework.emuns.ErrorCode;
import com.zuipin.framework.exception.CustomException;
import com.zuipin.framework.exception.IllegalParamException;
import com.zuipin.framework.exception.ParamAbsentException;
import com.zuipin.framework.exception.ParamInjectException;
import com.zuipin.framework.result.Result;

/**
 * 
 * @Title: GlobalExceptionHandler
 * @Package: com.zuipin.framework.exceptionhandler
 * @author: zengxinchao  
 * @date: 2017年3月23日 下午3:25:30
 * @Description: GlobalExceptionHandler
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value=Exception.class)
	@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
	public Result<?> defaultErrorHandler(Exception e){
		log.error("", e);
		Result<Object> result = new Result<>(ErrorCode.EXCEPTION);
		result.setMessage(e.getMessage());
		return result;
	}
	
	@ExceptionHandler(value=SQLException.class)
	@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
	public Result<?> sqlErrorHandler(SQLException e){
		log.error("", e);
		Result<?> result = new Result<>(ErrorCode.DB_EXCEPTION);
		result.setMessage(e.getMessage());
		return result;
	}
	
	@ExceptionHandler(value=CustomException.class)
	public Result<?> customErrorHandler(CustomException e){
		log.error("", e);
		Result<?> result = new Result<>();
		result.setCode(e.getCode());
		result.setMessage(e.getMessage());
		return result;
	}
	
	@ExceptionHandler(value={
			IllegalParamException.class, 
			ParamAbsentException.class,
			MissingServletRequestParameterException.class,
			MethodArgumentTypeMismatchException.class,
			ParamInjectException.class
	})
	public Result<?> paramValidErrorHandler(Exception e){
		Result<?> result = new Result<>();
		if(e instanceof ParamAbsentException || e instanceof MissingServletRequestParameterException){
			result.setCode(ErrorCode.PARAM_ABSENT.getCode());
			result.setMessage(e.getMessage());
		}else if(e instanceof HttpMessageNotReadableException){
			if(e.getCause() instanceof JsonProcessingException){
				result.setCode(ErrorCode.ILLEGAL_PARAM.getCode());
				result.setMessage("json parameter parse failed,please check your json format:  " + e.getCause().getMessage());
			}else{
				result.setCode(ErrorCode.EXCEPTION.getCode());
				result.setMessage(e.getMessage());
				log.error("", e);
				return result;
			}
		}else{
			result.setCode(ErrorCode.ILLEGAL_PARAM.getCode());
			result.setMessage(e.getMessage());
		}
		log.info(result.getMessage());
		return result;
	}
}
