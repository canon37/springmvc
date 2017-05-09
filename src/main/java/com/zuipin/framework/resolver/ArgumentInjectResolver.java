package com.zuipin.framework.resolver;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Iterator;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.zuipin.framework.annotation.Inject;
import com.zuipin.framework.exception.ParamInjectException;
import com.zuipin.util.ClassUtil;

/**
 * 
 * @Title: ArgumentInjectResolver
 * @Package: com.zuipin.framework.resolver
 * @author: zengxinchao  
 * @date: 2017年3月23日 下午6:04:44
 * @Description: ArgumentInjectResolver
 */
@Component
public class ArgumentInjectResolver implements HandlerMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {		
		return parameter.hasParameterAnnotation(Inject.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		
		String parameterName = parameter.getParameterName();
		Object instance = parameter.getParameterType().newInstance();
		Iterator<String> parameterNames = webRequest.getParameterNames();
		String element = "";//当前注入参数名
		try {
			while (parameterNames.hasNext()) {
				element = parameterNames.next();
				if (element.indexOf(".") > 0) {
					Object node = instance;
					Class<?> clazz = parameter.getParameterType();
					String[] nodes = element.split("\\.");//以点号拆分
					if (nodes[0].equals(parameterName)) {//判断注入参数首节点是否和参数名匹配
						for (int i = 1; i < nodes.length; i++) {
							Field field = clazz.getDeclaredField(nodes[i]);
							Method nodeGetter = clazz.getMethod(ClassUtil.getter(field.getName()));
							Method nodeSetter = clazz.getMethod(ClassUtil.setter(field.getName()), field.getType());

							if (isPrimitive(field.getType())) {//基本类型 直接构造数据
								String value = webRequest.getParameter(element);
								nodeSetter.invoke(node, field.getType().getConstructor(String.class).newInstance(value));
							} else if (field.getType().isEnum()) {//枚举
								String value = webRequest.getParameter(element);
								Object param = field.getType().getDeclaredMethod("valueOf", String.class).invoke(null, value);
								nodeSetter.invoke(node, field.getType().cast(param));
							} else {//正常对象 调用默认构造方法
								if (nodeGetter.invoke(node) == null) {
									nodeSetter.invoke(node, field.getType().newInstance());
								}
								clazz = field.getType();
								node = nodeGetter.invoke(node);
							}
						}
					}
				}
			} 
		} catch (Exception e) {
			if(e.getCause() != null){
				throw new ParamInjectException(e.getCause(), element);
			}else{
				throw new ParamInjectException(e, element);
			}
		}
		return instance;
	}
	
	private boolean isPrimitive(Class<?> clazz){
		if(clazz == String.class ||
				clazz == Integer.class ||
				clazz == Long.class ||
				clazz == Double.class ||
				clazz == Boolean.class ||
				clazz == Float.class ||
				clazz == Short.class ||
				clazz == BigDecimal.class){
			return true;
		}else{
			return false;
		}
	}

}
