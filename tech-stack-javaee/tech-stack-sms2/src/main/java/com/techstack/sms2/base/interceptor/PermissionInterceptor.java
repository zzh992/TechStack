package com.techstack.sms2.base.interceptor;

import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.techstack.sms2.base.annotation.permission.Permission;

/**
 * @Title: PermissionInterceptor.java 
 * @Description: 权限拦截器
 * @author zzh
 */
public class PermissionInterceptor  implements MethodInterceptor{
	
	private static final Log log = LogFactory.getLog(PermissionInterceptor.class);

	@SuppressWarnings("unchecked")
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// 判断该方法是否加了@Permission注解
		if (invocation.getMethod().isAnnotationPresent(Permission.class)) {
			log.info("=== invoke PermissionInterceptor");
			// 得到方法上的Permission注解
			final Permission pm = invocation.getMethod().getAnnotation(Permission.class);
			
			// 获取被注解方法中的request参数，要求方法中一定要有HttpServletRequest参数
			Map<String, Object> userInfoMap = (Map<String, Object>) ActionContext.getContext().getSession().get("userInfoMap");
			if(userInfoMap == null){
				return "permissionError"; // 跳转到错误提示页面.
			}
			final List<String> permissions = (List<String>)userInfoMap.get("pmsAction");
			if(permissions == null){
				// 没有此功能点权限
				log.info("==== info ==== 您没有执行此操作的权限:" + pm.value());
				return "permissionError"; // 跳转到错误提示页面.
			}
			if (permissions.contains(pm.value())){ // 拥有此功能点权限
				// 执行被拦截的方法，如果此方法不调用，则被拦截的方法不会被执行
				log.info("==== info ==== 拥有这个权限:" + pm.value());
				return invocation.proceed();
			}
			// 没有此功能点权限
			log.info("==== info ==== 您没有执行此操作的权限:" + pm.value());
			return "permissionError"; // 跳转到错误提示页面.
		}
		// 没加@Permission注解的方法可直接执行
		// 执行被拦截的方法，如果此方法不调用，则被拦截的方法不会被执行
		return invocation.proceed();
	}

}
