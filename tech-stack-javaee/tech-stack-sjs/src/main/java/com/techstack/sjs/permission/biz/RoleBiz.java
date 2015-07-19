package com.techstack.sjs.permission.biz;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techstack.sjs.page.PageBean;
import com.techstack.sjs.page.PageParam;
import com.techstack.sjs.permission.dao.ActionDao;
import com.techstack.sjs.permission.dao.RoleDao;
import com.techstack.sjs.permission.entity.Action;
import com.techstack.sjs.permission.entity.Role;



@Component("userBiz")
public class RoleBiz {
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private ActionDao actionDao;

	public List<Role> listAllRole() {
		return roleDao.findAll();
	}

	public void deleteById(Long id) {
		roleDao.delete(id);
	}

	public Role getByRoleName(String roleName) {
		return roleDao.findByRoleName(roleName);
	}

	/**
	 * @Description: 查找是否存在与ID值不相同与角色名相同的角色记录（用于判断修改的角色名与其他的角色名冲突）
	 * @param @param id
	 * @param @param roleName
	 * @param @return    
	 * @return Role
	 */
	public Role findByRoleNameNotEqId(Long id, String roleName) {
		return roleDao.findByRoleNameAndIdNot(roleName, id);
	}

	/**
	 * @Description: 根据权限ID找出关联了此权限的角色.
	 * @param @param actionId
	 * @param @return    
	 * @return List<PmsRole>
	 */
	public Set<Role> listByActionId(Long actionId) {
		Action action = actionDao.findOne(actionId);
		return action.getRoles();
	}

	/**
	 * @Description: 查询并分页列出角色信息
	 * @param @param pageParam
	 * @param @param paramMap
	 * @param @return    
	 * @return PageBean
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		//return pmsRoleDao.listPage(pageParam, paramMap);
		//return pmsRoleDao.listPage(PmsRole.class, pageParam, paramMap);
		return getBaseDao().listPage(PmsRole.class, pageParam, paramMap);
	}

	public Role getById(Long id) {
		return roleDao.getOne(id);
	}

	public void create(Role role) {
		roleDao.save(role);
	}

	public void update(Role role) {
		roleDao.save(role);
	}
	
	/**
	 * @Description: 根据角色ID删除角色，并删除与用户、权限、菜单的关联关系.
	 * @param @param roleId    
	 * @return void
	 */
	public void deleteRoleById(Long roleId) {
		roleDao.delete(roleId);
	}
	
	/**
	 * @Description: 修改角色信息，并更新其关联的权限点.
	 * @param @param pmsRole    
	 * @return void
	 */
	public void updateRole(Role role) {
		roleDao.save(role);
	}
	
	/**
	 * @Description: 保存角色并关联权限.
	 * @param @param pmsRole    
	 * @return void
	 */
	public void saveRole(Role role) {
		roleDao.save(role);
	}
	
	/**
	 * @Description: 根据用户ID获得该用户的所有角色id所拼成的String，每个ID用“,”分隔
	 * @param @param operatorId
	 * @param @return    
	 * @return String
	 *//*
	public String getRoleIdsByUserId(long userId) {
		// 得到用户和角色列表
		//List<PmsRoleUser> rpList = pmsRoleUserDao.listByUserId(userId);
		List<PmsRoleUser> rpList = getBaseDao().selectList(getStatement("listRoleUserByUserId"), userId);
		// 构建StringBuffer来拼字符串
		StringBuffer roleIdsBuf = new StringBuffer("");
		for (PmsRoleUser rp : rpList) {
			roleIdsBuf.append(rp.getRoleId()).append(",");
		}
		String roleIds = roleIdsBuf.toString();
		// 截取字符串
		if (StringUtils.isNotBlank(roleIds) && roleIds.length() > 0) {
			roleIds = roleIds.substring(0, roleIds.length() - 1);
		}
		return roleIds;
	}*/
}
