package com.techstack.sms2.base.taglib;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;

import com.opensymphony.xwork2.ActionContext;

/**
 * @Title: PermissionTaglib.java 
 * @Description: 自定义权限权标签 
 * @author zzh
 */
@SuppressWarnings("serial")
public class PermissionTaglib extends BodyTagSupport {
	/**	权限值	*/
	private String value; 

	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		if (StringUtils.isNotBlank(value)){
			Map<String, Object> userInfoMap = (Map<String, Object>) ActionContext.getContext().getSession().get("userInfoMap");
			if(userInfoMap == null){
				return SKIP_BODY;
			}
			final List<String> permissions = (List<String>)userInfoMap.get("pmsAction");
			if (permissions.contains(value.trim())){ // 拥有此功能点权限
				return EVAL_BODY_INCLUDE;
			}
		}
		return SKIP_BODY;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	} 
}
