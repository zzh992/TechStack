package com.techstack.pms.springmvc.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.techstack.component.shiro.ShiroUser;
import com.techstack.component.springmvc.SpringMVCBaseController;
import com.techstack.pms.biz.PmsActionBiz;
import com.techstack.pms.biz.PmsMenuBiz;
import com.techstack.pms.biz.PmsRoleBiz;
import com.techstack.pms.biz.PmsUserBiz;
import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.dto.PmsUserDTO;

@Controller
@RequestMapping("/login_")
public class PmsLoginController extends SpringMVCBaseController{

	private static final Log log = LogFactory.getLog(PmsLoginController.class);

	@Autowired
	private PmsActionBiz pmsActionBiz;
	@Autowired
	private PmsRoleBiz pmsRoleBiz;
	@Autowired
	private PmsUserBiz pmsUserBiz;
	@Autowired
	private PmsMenuBiz pmsMenuBiz;

	/**
	 * 进入登录页面.
	 * @return
	 */
	@RequestMapping("loginPage.action")
	public ModelAndView loginPage() {
		ModelAndView mav = new ModelAndView("login.jsp");
		ModelMap modelMap = new ModelMap();
		Object error = getHttpRequest().getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if(error != null){
			modelMap.put("loginInfo", "Login fail, please check the username or password");
		}
		mav.addAllObjects(modelMap);
		return mav;
	}

	/**
	 * 登录验证Action
	 * 
	 * @return
	 * @throws Exception
	 */
	/*public String userLogin() {
		try {
			String loginName = getString("loginName");

			if (StringUtils.isBlank(loginName)) {
				this.putData("loginNameMsg", "用户名不能为空");
				return "input";
			}

			this.putData("loginName", loginName);
			PmsUser user = pmsUserBiz.findUserByLoginName(loginName);
			if (user == null) {
				this.putData("loginNameMsg", "用户名不存在");
				return "input";
			}

			String pwd = getString("loginPwd");
			if (StringUtils.isBlank(pwd)) {
				this.putData("loginPwdMsg", "密码不能为空");
				return "input";
			}

			// 加密明文密码
			// 验证密码
			if (user.getLoginPwd().equals(DigestUtils.sha1Hex(pwd))) {// 密码正确
				// 用户信息，包括登录信息和权限
				Map<String, Object> userInfoMap = new HashMap<String, Object>();
				userInfoMap.put("pmsUser", user);
				userInfoMap.put("pmsAction", getActions(user));

				getSessionMap().put("userInfoMap", userInfoMap);
				this.putData("loginName", loginName);
				
				try {
					this.putData("tree", buildUserPermissionMenu(user));
					pmsUserBiz.update(user);

				} catch (Exception e) {
					log.error("==== error ==== 登录出现异常",e);
					return "input";
				}
				log.info("==== info ==== 用户"+loginName+"登录系统");
				return "main";

			} else {
				// 密码错误
				log.warn("== wrongPassword");
				String msg = "密码错误";

				pmsUserBiz.update(user);
				this.putData("loginPwdMsg", msg);
				return "input";
			}

		} catch (RuntimeException e) {
			log.error("==== error ==== 登录出现异常：", e);
			this.putData("errorMsg", "登录出错");
			return "input";
		} catch (Exception e) {
			log.error("==== error ==== 登录出现异常：", e);
			this.putData("errorMsg", "登录出错");
			return "input";
		}
	}*/
	@RequestMapping("mainpage.action")
	public ModelAndView mainpage(){
		ModelAndView mav = new ModelAndView("page/index.jsp");
		ModelMap modelMap = new ModelMap();
		// 用户信息，包括登录信息和权限
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Map<String, Object> userInfoMap = new HashMap<String, Object>();
		PmsUserDTO user = pmsUserBiz.findUserByLoginName(shiroUser.getUsername());
		userInfoMap.put("pmsUser", user);
		userInfoMap.put("pmsAction", getActions(user));

//		getSessionMap().put("userInfoMap", userInfoMap);
		modelMap.put("loginName", shiroUser.getUsername());
		
		try {
			modelMap.put("tree", buildUserPermissionMenu(user));
			pmsUserBiz.update(user);

		} catch (Exception e) {
			log.error("==== error ==== 登录出现异常",e);
			mav.setViewName("login.jsp");
			mav.addAllObjects(modelMap);
			return mav;
		}
		log.info("==== info ==== 用户"+shiroUser.getUsername()+"登录系统");
		mav.addAllObjects(modelMap);
		return mav;
	}

	/**
	 * 跳转到退出确认页面.
	 * 
	 * @return LogOutConfirm.
	 */
	@RequestMapping("logoutConfirm.action")
	public String logoutConfirm() {
		return "page/logoutConfirm.jsp";
	}

	/**
	 * 退出登录
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("logout.action")
	public String logout() throws Exception {
		//getSessionMap().clear();
		return "login.jsp";
	}

	/**
	 * 跳转到登录超时确认页面.
	 * 
	 * @return LogOutConfirm.
	 * @throws Exception
	 */
	@RequestMapping("timeoutConfirm.action")
	public String timeoutConfirm() throws Exception {
		return "page/timeoutConfirm.jsp";
	}

	/**
	 * 获取用户的权限标识字符串，以逗号分隔
	 * @param pmsUser
	 * @return
	 */
	private List<String> getActions(PmsUserDTO pmsUser) {
		// 根据用户ID得到该用户的所有角色拼成的字符串
		String roleIds = pmsRoleBiz.getRoleIdsByUserId(pmsUser.getId());
		// 根据角色ID字符串得到该用户的所有权限拼成的字符串
		String actionIds = "";
		if (StringUtils.isNotBlank(roleIds)) {
			actionIds = pmsActionBiz.getActionIdsByRoleIds(roleIds);
		}
		// 根据权限ID字符串得到权限列表
		List<PmsActionDTO> pmsActionList = new ArrayList<PmsActionDTO>();
		if (!"".equals(actionIds)) {
			pmsActionList = pmsActionBiz.findActionsByIdStr(actionIds);
		}
		
		List<String> actionList = new ArrayList<String>();
		for (PmsActionDTO pmsAction : pmsActionList) {
			actionList.add(pmsAction.getAction());
		}
		log.info("==== info ==== 用户"+pmsUser.getLoginName()+"有"+actionList.size()+"个权限点");
		return actionList;
	}

	/**
	 * 获取用户的菜单权限
	 * @param pmsUser
	 * @return
	 * @throws Exception
	 */
	private String buildUserPermissionMenu(PmsUserDTO pmsUser){
		// 根据用户ID得到该用户的所有角色拼成的字符串
		String roleIds = pmsRoleBiz.getRoleIdsByUserId(pmsUser.getId());
		if (StringUtils.isBlank(roleIds)) {
			log.info("==== info ==== 用户[" + pmsUser.getLoginName() + "]没有配置对应的权限角色");
			throw new RuntimeException("该帐号已被取消所有系统权限");
		}
		// 根据操作员拥有的角色ID,构建管理后台的树形权限功能菜单
		return pmsMenuBiz.buildPermissionTree(roleIds);
	}

}
