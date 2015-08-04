package com.techstack.pms.springmvc.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.techstack.component.dwz.DwzUtils;
import com.techstack.component.mapper.BeanMapper;
import com.techstack.component.shiro.ShiroUser;
import com.techstack.component.springmvc.SpringMVCBaseController;
import com.techstack.pms.biz.PmsActionBiz;
import com.techstack.pms.biz.PmsMenuBiz;
import com.techstack.pms.biz.PmsRoleBiz;
import com.techstack.pms.biz.PmsUserBiz;
import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.dto.PmsMenuDTO;
import com.techstack.pms.dao.dto.PmsRoleDTO;
import com.techstack.pms.dao.mybatis.entity.PmsUser;
import com.techstack.pms.enums.RoleTypeEnum;
import com.techstack.pms.enums.UserTypeEnum;

@Controller
@RequestMapping("/pmsRole_")
public class PmsRoleController extends SpringMVCBaseController{

	private static Log log = LogFactory.getLog(PmsRoleController.class);
	
	@Autowired
	private PmsActionBiz pmsActionBiz;
	@Autowired
	private PmsRoleBiz pmsRoleBiz;
	@Autowired
	private PmsUserBiz pmsUserBiz;
	@Autowired
	private PmsMenuBiz pmsMenuBiz;
	
	
	/**
	 * @Description: 获取角色列表
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:role:view")
	@RequestMapping("pmsRoleList.action")
	public ModelAndView pmsRoleList() {
		try {
			ModelAndView mav = new ModelAndView("page/pms/pmsRole/pmsRoleList.jsp");
			ModelMap modelMap = new ModelMap();
			Map<String, Object> paramMap = new HashMap<String, Object>(); // 业务条件查询参数
			paramMap.put("roleName", getString("roleName")); // 角色名称（模糊查询）
			paramMap.put("module", "pmsRole");
			Page<PmsRoleDTO> pageBean = pmsRoleBiz.listPage(DwzUtils.getPageNum(getHttpRequest()), DwzUtils.getNumPerPage(getHttpRequest()), paramMap);

			ShiroUser user = this.getCurrentUser();
			modelMap.putAll(BeanMapper.map(user, Map.class));
			modelMap.putAll(BeanMapper.map(pageBean, Map.class));
			modelMap.putAll(BeanMapper.map(paramMap, Map.class));
			//this.pushData(user);
			//this.pushData(pageBean);
			// 回显查询条件值
			//this.pushData(paramMap);		
			
			modelMap.put("RoleTypeEnumList", RoleTypeEnum.toList());
			modelMap.put("RoleTypeEnum", RoleTypeEnum.toMap());
			modelMap.put("UserTypeEnum", UserTypeEnum.toMap());
			mav.addAllObjects(modelMap);
			return mav;
		} catch (Exception e) {
			log.error("==== error ==== 查询角色失败：", e);
			return DwzUtils.operateErrorInSpringMVC("获取数据失败", getHttpRequest());
		}
	}

	/**
	 * @Description: 转到添加角色页面 .
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:role:add")
	@RequestMapping("pmsRoleAdd.action")
	public ModelAndView pmsRoleAdd() {
		try {
			ModelAndView mav = new ModelAndView("page/pms/pmsRole/pmsRoleAdd.jsp");
			return mav;
		} catch (Exception e) {
			log.error("==== error ==== 进入角色添加页面失败", e);
			return DwzUtils.operateErrorInSpringMVC("获取数据失败", getHttpRequest());
		}
	}

	/**
	 * @Description: 保存新添加的一个角色 
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:role:add")
	@RequestMapping("pmsRoleSave.action")
	public ModelAndView pmsRoleSave() {
		try {
			String roleName = getString("roleName");
			PmsRoleDTO roleCheck = pmsRoleBiz.getByRoleName(roleName);
			if (roleCheck != null) {
				return DwzUtils.operateErrorInSpringMVC("角色名【" + roleName + "】已存在", getHttpRequest());
			}

			// 保存基本角色信息
			PmsRoleDTO pmsRole = new PmsRoleDTO();
			pmsRole.setRoleType(RoleTypeEnum.USER.getValue()); // 角色类型（1:超级管理员角色，0:普通用户角色）
			pmsRole.setRoleName(roleName);
			pmsRole.setRemark(getString("desc"));
			pmsRole.setCreateTime(new Date());

			// 表单数据校验
			String validateMsg = validatePmsRole(pmsRole);
			if (StringUtils.isNotBlank(validateMsg)) {
				return DwzUtils.operateErrorInSpringMVC(validateMsg, getHttpRequest()); // 返回错误信息
			}

			pmsRoleBiz.saveRole(pmsRole);
			log.info("==== info ==== 添加角色【"+roleName+"】成功");
			return DwzUtils.operateErrorInSpringMVC("操作成功", getHttpRequest());
		} catch (Exception e) {
			log.error("==== error ==== 添加角色失败：", e);
			return DwzUtils.operateErrorInSpringMVC("保存数据失败", getHttpRequest());
		}
	}

	/**
	 * @Description: 校验角色表单数据
	 * @param @param pmsRole
	 * @param @return    
	 * @return String
	 */
	private String validatePmsRole(PmsRoleDTO pmsRole) {
		String msg = ""; // 用于存放校验提示信息的变量
		String roleName = pmsRole.getRoleName(); // 角色名称
		String desc = pmsRole.getRemark(); // 描述
		// 角色名称 actionName
		msg += DwzUtils.lengthValidate("角色名称", roleName, true, 3, 90);
		// 描述 desc
		msg += DwzUtils.lengthValidate("描述", desc, true, 3, 300);
		return msg;
	}

	/**
	 * @Description:  转到角色修改页面 .
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:role:edit")
	@RequestMapping("pmsRoleEdit.action")
	public ModelAndView pmsRoleEdit() {
		try {
			ModelAndView mav = new ModelAndView("page/pms/pmsRole/pmsRoleEdit.jsp");
			ModelMap modelMap = new ModelMap();
			Long roleId = getLong("roleId");
			PmsRoleDTO pmsRole = pmsRoleBiz.getById(roleId);
			if (pmsRole == null) {
				return DwzUtils.operateErrorInSpringMVC("获取数据失败", getHttpRequest());
			}

			// 普通用户没有修改超级管理员角色的权限
			if (UserTypeEnum.USER.getValue().equals(this.getCurrentUser().getType()) 
			 && RoleTypeEnum.ADMIN.getValue().equals(pmsRole.getRoleType())) {
				return DwzUtils.operateErrorInSpringMVC("你没有修改超级管理员角色的权限", getHttpRequest());
			}

			//this.pushData(pmsRole);
			modelMap.putAll(BeanMapper.map(pmsRole, Map.class));
			mav.addAllObjects(modelMap);
			return mav;
		} catch (Exception e) {
			log.error("==== error ==== 进入修改角色页面失败：", e);
			return DwzUtils.operateErrorInSpringMVC("获取数据失败", getHttpRequest());
		}
	}

	/**
	 * @Description: 保存修改后的角色信息
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:role:edit")
	@RequestMapping("pmsRoleUpdate.action")
	public ModelAndView pmsRoleUpdate() {
		try {
			Long id = getLong("id");

			PmsRoleDTO pmsRole = pmsRoleBiz.getById(id);
			if (pmsRole == null) {
				return DwzUtils.operateErrorInSpringMVC("无法获取要修改的数据", getHttpRequest());
			}

			// 普通用户没有修改超级管理员角色的权限
			if (UserTypeEnum.USER.getValue().equals(this.getCurrentUser().getType()) 
			 && RoleTypeEnum.ADMIN.getValue().equals(pmsRole.getRoleType())) {
				return DwzUtils.operateErrorInSpringMVC("你没有修改超级管理员角色的权限", getHttpRequest());
			}

			String roleName = getString("roleName");
			PmsRoleDTO roleCheck = pmsRoleBiz.findByRoleNameNotEqId(id, roleName);
			if (roleCheck != null) {
				return DwzUtils.operateErrorInSpringMVC("角色名【" + roleName + "】已存在", getHttpRequest());
			}

			pmsRole.setRoleName(roleName);
			pmsRole.setRemark(getString("remark"));

			// 表单数据校验
			String validateMsg = validatePmsRole(pmsRole);
			if (StringUtils.isNotBlank(validateMsg)) {
				return DwzUtils.operateErrorInSpringMVC(validateMsg, getHttpRequest()); // 返回错误信息
			}

			pmsRoleBiz.updateRole(pmsRole);
			log.info("==== info ==== 修改角色【"+roleName+"】成功");
			return DwzUtils.operateErrorInSpringMVC("操作成功", getHttpRequest());
		} catch (Exception e) {
			log.error("==== error ==== 修改角色失败", e);
			return DwzUtils.operateErrorInSpringMVC("保存失败", getHttpRequest());
		}
	}

	/**
	 * @Description: 删除一个角色
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:role:delete")
	@RequestMapping("pmsRoleDel.action")
	public ModelAndView pmsRoleDel() {
		try {
			Long roleId = getLong("roleId");

			PmsRoleDTO role = pmsRoleBiz.getById(roleId);
			if (role == null) {
				return DwzUtils.operateErrorInSpringMVC("无法获取要删除的角色", getHttpRequest());
			}
			if (RoleTypeEnum.ADMIN.getValue().equals(role.getRoleType())) {
				return DwzUtils.operateErrorInSpringMVC("超级管理员角色不可删除", getHttpRequest());
			}

			String msg = "";
			// 判断是否有用户关联到此角色
			int userCount = pmsUserBiz.countUserByRoleId(roleId);
			if (userCount > 0) {
				msg += "【" + userCount + "】个用户";
			}
			// 判断是否有权限关联到此角色 
			// int actionCount = pmsActionBiz.countActionByRoleId(roleId);
			// if (actionCount > 0){
			// msg += "【"+actionCount+"】个权限";
			// }
			// // 判断是否有菜单关联到此角色
			// int menuCount = pmsMenuBiz.countMenuByRoleId(roleId);
			// if (menuCount > 0){
			// msg += "【"+menuCount+"】个菜单";
			// }

			if (StringUtils.isNotBlank(msg)) {
				msg += "关联到此角色，要先解除所有关联后才能删除!";
				return DwzUtils.operateErrorInSpringMVC("有" + msg, getHttpRequest());
			}
			
			pmsRoleBiz.deleteRoleById(roleId);
			log.info("==== info ==== 删除角色成功");
			return DwzUtils.operateErrorInSpringMVC("操作成功", getHttpRequest());
		} catch (Exception e) {
			log.error("==== error ==== 删除角色失败", e);
			return DwzUtils.operateErrorInSpringMVC("删除失败", getHttpRequest());
		}
	}

	/**
	 * @Description: 分配权限UI
	 * @param @return    
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	//@Permission("pms:role:edit")
	@RequestMapping("assignPermissionUI.action")
	public String assignPermissionUI() {
		ModelAndView mav = new ModelAndView("page/pms/pmsRole/assignPermissionUI.jsp");
		ModelMap modelMap = new ModelMap();
		Long roleId = getLong("roleId");
		PmsRoleDTO role = pmsRoleBiz.getById(roleId);
		if (role == null) {
			return DwzUtils.operateErrorInStruts2("无法获取角色信息");
		}
		// 普通用户没有修改超级管理员角色的权限
		if (UserTypeEnum.USER.getValue().equals(this.getCurrentUser().getType()) 
		 && RoleTypeEnum.ADMIN.getValue().equals(role.getRoleType())) {
			return DwzUtils.operateErrorInStruts2("你没有修改超级管理员角色的权限");
		}

		String menuIds = "";
		String actionIds = "";
		try {
			menuIds = pmsMenuBiz.getMenuIdsByRoleId(roleId); // 根据角色查找角色对应的菜单ID集
			actionIds = pmsActionBiz.getActionIdsByRoleId(roleId); // 根据角色查找角色对应的功能权限ID集
		} catch (Exception e) {
			log.error("==== error ==== 根据角色ID，找不到对应的菜单、权限", e);
		}

		// 前面加个逗号，方便接下来的处理
		menuIds = "," + menuIds;
		actionIds = "," + actionIds;

		modelMap.put("menuActionTree", pmsMenuBiz.buildMenuActionTree(menuIds, actionIds));

		// 查询角色对应的用户
		List<PmsUser> userList = (List<PmsUser>) pmsUserBiz.listUserByRoleId(roleId);
		modelMap.put("userList", userList);

		modelMap.put("roleId", roleId);
		mav.addAllObjects(modelMap);
		return "assignPermissionUI";
	}

	/**
	 * @Description: 分配角色权限
	 * @param     
	 * @return void
	 */
	//@Permission("pms:role:edit")
	@RequestMapping("assignPermission.action")
	public Map<String, Object> assignPermission() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Long roleId = getLong("roleId");

			PmsRoleDTO role = pmsRoleBiz.getById(roleId);
			if (role == null) {
				resultMap.put("MSG", "无法获取角色信息");
				return resultMap;
			}
			// 普通用户没有修改超级管理员角色的权限
			if (UserTypeEnum.USER.getValue().equals(this.getCurrentUser().getType()) 
					 && RoleTypeEnum.ADMIN.getValue().equals(role.getRoleType())) {
				resultMap.put("MSG", "你没有修改超级管理员角色的权限");
				return resultMap;
			}
			
			String menuIds = getString("menuIds");
			
			if (StringUtils.isNotBlank(menuIds)) {
				// 去除js错误选择导致的 undefined
				menuIds = menuIds.replaceAll("undefined,", "");
			}
			
			String actionIds = getString("actionIds");
			
			if (StringUtils.isNotBlank(actionIds)) {
				// 去除js错误选择导致的 undefined
				actionIds = actionIds.replaceAll("undefined,", "");
			}
			// 分配菜单权限，功能权限
			pmsMenuBiz.assignPermission(roleId, menuIds, actionIds);

			// String menuNameBuffer = theMenusIdsChangeNames(menuIds); // 查询菜单的

			// String actionNameBuffer = theActionIdsChangeNames(actionIds);
			resultMap.put("STATE", "SUCCESS");
		} catch (Exception e) {
			log.error("分配权限出现错误!", e);
			resultMap.put("STATE", "FAIL");
			resultMap.put("MSG", "分配权限出现错误。" + e.getMessage());
		}
		return resultMap;
	}

	/**
	 * @Description: 把权限的ID转换成NAME
	 * @param @param actionIds
	 * @param @return    
	 * @return String
	 */
	@SuppressWarnings("unused")
	private String theActionIdsChangeNames(String actionIds) {
		if (StringUtils.isEmpty(actionIds))
			return null;
		StringBuffer actionBuffer = new StringBuffer();
		int actionNum = actionIds.indexOf(",");
		if (actionNum <= 0) {
			PmsActionDTO action = pmsActionBiz.getById(Long.valueOf(actionIds));
			actionBuffer.append(action.getActionName());
		} else {
			String[] actionArray = actionIds.split(",");
			for (int i = 0; i < actionArray.length; i++) {
				PmsActionDTO action = pmsActionBiz.getById(Long.valueOf(actionArray[i]));
				if (i == actionArray.length - 1) {
					actionBuffer.append(action.getActionName());
				} else {
					actionBuffer.append(action.getActionName()).append(",");
				}
			}
		}
		return actionBuffer.toString();
	}

	/**
	 * @Description: 把菜单的ID转换成name
	 * @param @param menuIds
	 * @param @return    
	 * @return String
	 */
	@SuppressWarnings("unused")
	private String theMenusIdsChangeNames(String menuIds) {
		if (StringUtils.isEmpty(menuIds))
			return null;
		StringBuffer menuBuffer = new StringBuffer(); // 追加菜单的名称
		int menuNum = menuIds.indexOf(",");
		if (menuNum <= 0) {
			PmsMenuDTO menu = pmsMenuBiz.getById(Long.valueOf(menuIds));
			menuBuffer.append(menu.getName());
		} else {
			String[] menuArray = menuIds.split(",");
			for (int i = 0; i < menuArray.length; i++) {
				PmsMenuDTO menu = pmsMenuBiz.getById(Long.valueOf(menuArray[i]));
				if (i == menuArray.length - 1) {
					menuBuffer.append(menu.getName());
				} else {
					menuBuffer.append(menu.getName()).append(",");
				}
			}
		}
		return menuBuffer.toString();
	}
}