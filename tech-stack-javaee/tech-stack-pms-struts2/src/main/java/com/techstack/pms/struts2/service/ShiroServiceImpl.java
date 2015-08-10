package com.techstack.pms.struts2.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techstack.component.shiro.ShiroService;
import com.techstack.component.shiro.ShiroUser;
import com.techstack.pms.biz.PmsActionBiz;
import com.techstack.pms.biz.PmsRoleBiz;
import com.techstack.pms.biz.PmsUserBiz;
import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.dto.PmsRoleDTO;
import com.techstack.pms.dao.dto.PmsRoleUserDTO;
import com.techstack.pms.dao.dto.PmsUserDTO;

@Component("shiroService")
public class ShiroServiceImpl implements ShiroService {

	@Autowired
	private PmsUserBiz pmsUserBiz;
	@Autowired
	private PmsRoleBiz pmsRoleBiz;
	@Autowired
	private PmsActionBiz pmsActionBiz;
	
	@Override
	public ShiroUser findShiroUserByUsername(String username) {
		PmsUserDTO pmsUser = pmsUserBiz.findUserByLoginName(username);
		ShiroUser shiroUser = new ShiroUser();
		shiroUser.setId(pmsUser.getId());
		shiroUser.setName(pmsUser.getLoginName());
		shiroUser.setPassword(pmsUser.getLoginPwd());
		shiroUser.setUsername(pmsUser.getLoginName());
		return shiroUser;
	}

	@Override
	public Set<String> findRolesByByUsername(String username) {
		PmsUserDTO pmsUser = pmsUserBiz.findUserByLoginName(username);
		Set<String> roles = new HashSet<String>();
		List<PmsRoleUserDTO> roleUserList = pmsUserBiz.listRoleUserByUserId(pmsUser.getId());
		for(PmsRoleUserDTO roleUser : roleUserList){
			PmsRoleDTO role = pmsRoleBiz.getById(roleUser.getRoleId());
			roles.add(role.getRoleName());
		}
		return roles;
	}

	@Override
	public Set<String> findPermissionsByUsername(String username) {
		PmsUserDTO pmsUser = pmsUserBiz.findUserByLoginName(username);
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
		
		Set<String> actionList = new HashSet<String>();
		for (PmsActionDTO pmsAction : pmsActionList) {
			actionList.add(pmsAction.getAction());
		}
		return actionList;
	}

}
