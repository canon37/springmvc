package com.zuipin.framework.editor;

import java.beans.PropertyEditorSupport;

import com.zuipin.entity.User;
/**
 * 
 * @Title: UserEditor
 * @Package: com.zuipin.framework.editor
 * @author: zengxinchao  
 * @date: 2017年5月9日 下午3:16:14
 * @Description: this is a demonstrate
 */
public class UserEditor extends PropertyEditorSupport{

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		User user = new User();
		user.setName(text);
		setValue(user);
	}
}
