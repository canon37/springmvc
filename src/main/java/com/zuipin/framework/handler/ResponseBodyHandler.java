package com.zuipin.framework.handler;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zuipin.framework.annotation.Json;
import com.zuipin.framework.annotation.Jsons;
import com.zuipin.framework.json.serializer.JsonFilterSerializer;
import com.zuipin.framework.json.JsonFilterTuple;

/**
 * 
 * @Title: ResponseBodyHandler
 * @Package: com.zuipin.framework.handler
 * @author: zengxinchao  
 * @date: 2017年4月21日 下午5:24:05
 * @Description: ResponseBodyHandler
 */
@Component
public class ResponseBodyHandler implements HandlerMethodReturnValueHandler{
	public static final String RESPONSE_VALUE_FILTER_MAP = "response_value_filter_map";
	
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return returnType.hasMethodAnnotation(Json.class) || returnType.hasMethodAnnotation(Jsons.class);
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
		mavContainer.setRequestHandled(true);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		ServletContext contex = request.getServletContext();
		Map<Class<?>, JsonFilterTuple> filter = getJsonFilterMap(contex, request.getRequestURI());
		
		if(filter == null){
			Annotation[] annotations = returnType.getMethodAnnotations();
			filter = new HashMap<>();
			for (Annotation annotation : annotations) {
				if(annotation instanceof Jsons){
					Jsons jsons = (Jsons)annotation;
					for (Json json : jsons.value()) {
						cacheDetail(filter, json);
					}
				}else if(annotation instanceof Json){
					Json json = (Json)annotation;
					cacheDetail(filter, json);
				}
			}
			getMap(contex).put(request.getRequestURI(), filter);
		}
		
		JsonFilterSerializer jsonFilterSerializer = new JsonFilterSerializer(filter);
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		String result = JSON.toJSONString(returnValue, jsonFilterSerializer, SerializerFeature.WriteMapNullValue);
		response.getWriter().write(result);
	}
	
	/**
	 * 
	 *@author: zengxinchao  
	 *@date: 2017年4月25日 下午1:35:19
	 *@param filter
	 *@param json
	 *@Description: cacheDetail
	 */
	private void cacheDetail(Map<Class<?>, JsonFilterTuple> filter, Json json){
		JsonFilterTuple tuple = filter.get(json.type());
		if(tuple == null){
			tuple = new JsonFilterTuple();
			filter.put(json.type(), tuple);
		}
		if(json.include().length() > 0){
			tuple.getInclude().addAll(Arrays.asList(json.include().split(",")));
		}else if(json.exclude().length() > 0){
			tuple.getExclude().addAll(Arrays.asList(json.exclude().split(",")));
		}
	}
	
	/**
	 * 
	 *@author: zengxinchao  
	 *@date: 2017年4月25日 下午1:34:51
	 *@param contex
	 *@param key
	 *@return
	 *@Description: 获取json filter map
	 */
	@SuppressWarnings("unchecked")
	private Map<Class<?>, JsonFilterTuple> getJsonFilterMap(ServletContext contex, String key){
		Object filter = getMap(contex).get(key);
		if(filter == null){
			return null;
		}else{
			return (Map<Class<?>, JsonFilterTuple>) filter;
		}
	}
	
	/**
	 * 
	 *@author: zengxinchao  
	 *@date: 2017年4月25日 下午4:33:54
	 *@param contex
	 *@return
	 *@Description: 获取缓存的filter map
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getMap(ServletContext contex){
		return (Map<String, Object>)contex.getAttribute(RESPONSE_VALUE_FILTER_MAP);
	}
}
