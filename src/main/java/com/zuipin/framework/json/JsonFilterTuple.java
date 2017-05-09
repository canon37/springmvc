package com.zuipin.framework.json;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @Title: JsonFilterTuple
 * @Package: com.zuipin.framework.json.util
 * @author: zengxinchao  
 * @date: 2017年4月25日 上午9:43:09
 * @Description: JsonFilterTuple
 */
public class JsonFilterTuple {
	private Set<String> include = new HashSet<>();
	
	private Set<String> exclude = new HashSet<>();
	
	public JsonFilterTuple(){
		
	}

	public Set<String> getInclude() {
		return include;
	}

	public void setInclude(Set<String> include) {
		this.include = include;
	}

	public Set<String> getExclude() {
		return exclude;
	}

	public void setExclude(Set<String> exclude) {
		this.exclude = exclude;
	}
}
