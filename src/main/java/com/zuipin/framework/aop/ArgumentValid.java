package com.zuipin.framework.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.zuipin.framework.annotation.Valid;
import com.zuipin.framework.annotation.Valids;
import com.zuipin.framework.exception.IllegalParamException;
import com.zuipin.framework.exception.ParamAbsentException;
import com.zuipin.util.ClassUtil;
import com.zuipin.util.StringUtil;

/**
 * 
 * @Title: ArgumentValid
 * @Package: com.zuipin.framework.aop
 * @author: zengxinchao  
 * @date: 2017年3月31日 下午1:34:58
 * @Description: ArgumentValid
 */
@Component
@Aspect
public class ArgumentValid {
	@Around(value="@annotation(com.zuipin.framework.annotation.Valids)")
	public Object valid(ProceedingJoinPoint joinPoint) throws Throwable{
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
	    Method method = methodSignature.getMethod();
	    String[] parameterNames = methodSignature.getParameterNames();
	    Object[] args = joinPoint.getArgs();
	    Map<String, Object> parameters = new HashMap<>();
	    for (int i=0; i<args.length; i++) {
	    	parameters.put(parameterNames[i], args[i]);
		}
	    
	    Valid[] valids = method.getAnnotation(Valids.class).value();
	    for (Valid valid : valids) {
	    	boolean validRequired = false;//是否要验证必填
	    	String[] nodes = valid.name().split("\\.");//以点号拆分
	    	Object root = parameters.get(nodes[0]);//获得入参
	    	List<Object> targets = new ArrayList<>();//以下会进行层级遍历，得到valid.name对应的最终参数 集合
    		targets.add(root);
	    	for (int i=0; i<nodes.length; i++) {//遍历节点下所有参数，包括数组
	    		List<Object> tempTarget = new ArrayList<>();//用于中转    		
	    		for (Object target : targets) {
	    			Object node = null;
					if(i == 0){//根结点
						node = target;
					}else{
						Method nodeGetter = target.getClass().getDeclaredMethod(ClassUtil.getter(nodes[i]));
						node = nodeGetter.invoke(target);//获取当前节点
					}
					
					Object[] tempNode = {};//用于中转
					if(node != null){
						if(ClassUtil.isSupportArray(node.getClass())){
				    		if(node.getClass().isArray()){//基础数组
				    			tempNode = tempNode.getClass().cast(node);
				    		}else{//List或Set
				    			tempNode = tempNode.getClass().cast(node.getClass().getDeclaredMethod("toArray").invoke(node));
				    		}
				    	}else{//正常对象
			    			tempNode = new Object[]{node};
			    		}
					}else if(i == nodes.length - 1){//未节点，并且是null  则验证非空
						validRequired = true;
					}
					tempTarget.addAll(Arrays.asList(tempNode));
				}
	    		targets.clear();
	    		targets.addAll(tempTarget);
				tempTarget.clear();
			}
	    	if(validRequired){
	    		valid(valid, null, null);
	    	}
	    	if(targets.size() > 0){
	    		Class<?> type = targets.get(0).getClass();
	    		for (Object target : targets) {
					valid(valid, target, type);
				}
	    	}
		}
	    return joinPoint.proceed();
	}
	
	private void valid(Valid annotation, Object target, Class<?> type) throws ParamAbsentException, IllegalParamException{
		if(annotation.required()){
			if(target == null){
				throw new ParamAbsentException(String.format("required param '%s' is absent", annotation.name()));
			}
		}
		
		if(!annotation.required() && target == null){
			return;
		}
		
		String parameter = target.toString();
		if(type == String.class){	
			if(annotation.length() > 0 && annotation.length() != parameter.length()){
				throw new IllegalParamException(String.format("%s's length must be %s", annotation.name(), annotation.length()));
			}
			if(annotation.maxLength() > 0 && parameter.length() > annotation.maxLength()){
				throw new IllegalParamException(String.format("%s's length must be less than %s", annotation.name(), annotation.maxLength()));
			}
			if(annotation.minLength() > 0 && parameter.length() < annotation.minLength()){
				throw new IllegalParamException(String.format("%s's length must be greater than %s", annotation.name(), annotation.minLength()));
			}
			if(StringUtil.isNotBlank(annotation.regex())){
				Pattern pattern = Pattern.compile(annotation.regex());
				if(!pattern.matcher(parameter).matches()){
					throw new IllegalParamException(String.format("'%s' does not matches the pattern", annotation.name()));
				}
			}
		}else if(ClassUtil.isPrimitiveNumber(type)){			
			Double num = Double.parseDouble(parameter);
			if(annotation.maxValue() != Double.MAX_VALUE && num > annotation.maxValue()){
				throw new IllegalParamException(String.format("'%s' must be less than %s", annotation.name(), StringUtil.nf.format(annotation.maxValue())));
			}
			if(annotation.minValue() != Double.MIN_VALUE && num < annotation.minValue()){
				throw new IllegalParamException(String.format("'%s' must be greater than %s", annotation.name(), StringUtil.nf.format(annotation.minValue())));
			}
		}
	
	}
}
