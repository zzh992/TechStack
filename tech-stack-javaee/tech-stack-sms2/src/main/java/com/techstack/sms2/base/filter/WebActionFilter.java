package com.techstack.sms2.base.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Title: WebActionFilter.java 
 * @Description: 会话过滤Filter，对没有会话的请求重定向到登录页
 * @author zzh
 */
public class WebActionFilter implements Filter{
	private static final Log log = LogFactory.getLog(WebActionFilter.class);
	
	@Override
	public void destroy() {
		log.info("==== info ==== WebActionFilter destroy");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String uri = request.getServletPath(); // 请求路径
		if(!(uri.startsWith("/login")||uri.startsWith("/register"))&&uri.endsWith(".action")){	//请求不是登录或注册请求且是以action结尾，需要判断会话是否存在
			HttpSession session =  request.getSession();
			Map<String, Object> userInfoMap = (Map<String, Object>)session.getAttribute("userInfoMap");
			if(userInfoMap == null){	//如果没有会话，则跳转到登录界面
				response.sendRedirect(request.getContextPath() + "/login_timeoutConfirm.action");
				return;
			}
		}
		chain.doFilter(request, response);
		return;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		log.info("==== info ==== WebActionFilter init");
	}

}
