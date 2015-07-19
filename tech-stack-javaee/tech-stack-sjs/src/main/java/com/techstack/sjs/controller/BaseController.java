package com.techstack.sjs.controller;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.techstack.sjs.dwz.DwzParam;
import com.techstack.sjs.page.PageParam;

public abstract class BaseController {

	@Autowired
	private HttpServletRequest request;

	public Double getDouble(String key) {
		String value = request.getParameter(key);
		if (StringUtils.isNotBlank(value)) {
			return Double.parseDouble(value);
		}
		return null;
	}

	public Integer getInteger(String key) {
		String value = request.getParameter(key);
		if (StringUtils.isNotBlank(value)) {
			return Integer.parseInt(value);
		}
		return null;
	}

	public Long getLong(String key) {
		String value = request.getParameter(key);
		if (StringUtils.isNotBlank(value)) {
			return Long.parseLong(value);
		}
		return null;
	}

	public String getString(String key) {
		return request.getParameter(key);
	}

	/**
	 * 鏍规嵁request瀵硅薄锛岃幏鍙栭〉闈㈡彁浜よ繃鏉ョ殑DWZ妗嗘灦鐨凙jaxDone鍝嶅簲鍙傛暟鍊�
	 * 
	 * @param statusCode
	 *            鐘舵�鐮�
	 * @param message
	 *            鎿嶄綔缁撴灉鎻愮ず娑堟伅.
	 * @return DwzParam :灏佽濂界殑DwzParam瀵硅薄 .
	 */
	public DwzParam getDwzParam(String statusCode, String message) {
		// 鑾峰彇DWZ Ajax鍝嶅簲鍙傛暟鍊�骞舵瀯閫犳垚鍙傛暟瀵硅薄
		String navTabId = request.getParameter("navTabId");
		String dialogId = request.getParameter("dialogId");
		String callbackType = request.getParameter("callbackType");
		String forwardUrl = request.getParameter("forwardUrl");
		String rel = request.getParameter("rel");
		return new DwzParam(statusCode, message, navTabId, dialogId, callbackType, forwardUrl, rel, null);
	}

	/**
	 * 鍝嶅簲DWZ鐨凙jax璇锋眰.
	 * 
	 * @param statusCode
	 *            statusCode:{ok:200, error:300, timeout:301}.
	 * @param message
	 *            鎻愮ず娑堟伅.
	 */
	private ModelAndView ajaxDone(String statusCode, String message, String path) {
		DwzParam param = getDwzParam(statusCode, message);
		ModelMap modelMap = new ModelMap();
		modelMap.putAll(transBeanToMap(param));
		ModelAndView mav = new ModelAndView(path);
		mav.addAllObjects(modelMap);
		return mav;
	}

	/**
	 * 鍝嶅簲DWZ-UI鐨凙jax鎴愬姛璇锋眰锛坰tatusCode="200"锛�<br/>
	 * 璺宠浆鍒皁perateSuccess瑙嗗浘骞舵彁绀衡�鎿嶄綔鎴愬姛鈥�
	 * 
	 * @param message
	 *            鎻愮ず娑堟伅.
	 * @return operateSuccess .
	 */
	public ModelAndView operateSuccess() {
		return ajaxDone("200", "Operate Success","page/common/operateSuccess.jsp");
	}

	/**
	 * 鍝嶅簲DWZ鐨凙jax鎴愬姛璇锋眰锛坰tatusCode="200"锛�<br/>
	 * 璺宠浆鍒皁perateSuccess瑙嗗浘锛屾彁绀鸿缃殑娑堟伅鍐呭.
	 * 
	 * @param message
	 *            鎻愮ず娑堟伅.
	 * @return operateSuccess .
	 */
	public ModelAndView operateSuccess(String message) {
		return ajaxDone("200", message ,"page/common/operateSuccess.jsp");
	}

	/**
	 * 鍝嶅簲DWZ鐨刟jax澶辫触璇锋眰锛坰tatusCode="300"锛�璺宠浆鍒癮jaxDone瑙嗗浘.
	 * 
	 * @param message
	 *            鎻愮ず娑堟伅.
	 * @return ajaxDone .
	 */
	public ModelAndView operateError(String message) {
		return ajaxDone("300", message ,"page/common/operateSuccess.jsp");
	}
	
	
	/**
	 * 鑾峰彇褰撳墠椤碉紙DWZ-UI鍒嗛〉鏌ヨ鍙傛暟锛�
	 * 濡傛灉娌℃湁鍊煎垯榛樿杩斿洖1.
	 * 
	 */
	private int getPageNum() {
		// 褰撳墠椤垫暟
		String pageNumStr = request.getParameter("pageNum");
		int pageNum = 1;
		if (StringUtils.isNotBlank(pageNumStr)) {
			pageNum = Integer.valueOf(pageNumStr);
		}
		return pageNum;
	}

	/**
	 * 鑾峰彇姣忛〉璁板綍鏁帮紙DWZ-UI鍒嗛〉鏌ヨ鍙傛暟锛�
	 * 濡傛灉娌℃湁鍊煎垯榛樿杩斿洖15.
	 * 
	 */
	private int getNumPerPage() {
		String numPerPageStr = request.getParameter("numPerPage");
		int numPerPage = 20;
		if (StringUtils.isNotBlank(numPerPageStr)) {
			numPerPage = Integer.parseInt(numPerPageStr);
		}
		return numPerPage;
	}

	/**
	 * 鑾峰彇鍒嗛〉鍙傛暟锛屽寘鍚綋鍓嶉〉銆佹瘡椤佃褰曟暟.
	 * 
	 * @return PageParam .
	 */
	public PageParam getPageParam() {
		return new PageParam(getPageNum(), getNumPerPage());
	}


	// Bean --> Map : 鍒╃敤Introspector鍜孭ropertyDescriptor 灏咮ean --> Map
	public Map<String, Object> transBeanToMap(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 杩囨护class灞炴�
				if (!key.equals("class")) {
					// 寰楀埌property瀵瑰簲鐨刧etter鏂规硶
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			System.out.println("transBeanToMap Error " + e);
		}
		return map;

	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	
	
}
