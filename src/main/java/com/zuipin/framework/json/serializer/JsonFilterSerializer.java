package com.zuipin.framework.json.serializer;

import java.util.Map;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.zuipin.framework.json.JsonFilterTuple;

/**
 * 
 * @Title: JsonFilterSerializer
 * @Package: com.zuipin.framework.json.serializer
 * @author: zengxinchao  
 * @date: 2017年4月21日 下午5:24:25
 * @Description: JsonFilterSerializer
 */
public class JsonFilterSerializer implements PropertyPreFilter{
	private Map<Class<?>, JsonFilterTuple> filter;
	
	public JsonFilterSerializer(Map<Class<?>, JsonFilterTuple> filter) {
		this.filter = filter;
	}
	
	@Override
	public boolean apply(JSONSerializer serializer, Object object, String name) {
		Class<?> clazz = object.getClass();
		JsonFilterTuple tuple = filter.get(clazz);
		if(tuple == null){
			return true;
		}
		if(!tuple.getInclude().isEmpty()){
			if(tuple.getInclude().contains(name)){
				return true;
			}else{
				return false;
			}
		}else if(!tuple.getExclude().isEmpty()){
			if(tuple.getExclude().contains(name)){
				return false;
			}else{
				return true;
			}
		}
		return true;
	}		
}
