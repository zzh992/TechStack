package com.techstack.sjs.permission.biz;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techstack.sjs.page.PageBean;
import com.techstack.sjs.page.PageParam;
import com.techstack.sjs.permission.dao.RoleDao;
import com.techstack.sjs.permission.dao.UserDao;
import com.techstack.sjs.permission.entity.Role;
import com.techstack.sjs.permission.entity.User;

@Component("userBiz")
public class UserBiz {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;

	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}

	public void deleteUserById(long userId) {
		User user = userDao.getOne(userId);
		if (user != null) {
			if ("1".equals(user.getType())) {
				throw new RuntimeException("【" + user.getLoginName()+ "】为超级管理员，不能删除！");
			}
			userDao.delete(user);
		}
	}
	
	/**
	 * @Description: 根据角色ID查询用户
	 * @param @param roleId
	 * @param @return    
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public Set listUserByRoleId(long roleId) {
		Role role = roleDao.getOne(roleId);
		return role.getUsers();
	}
	
	/**
	 * @Description: 更新用户信息.
	 * @param @param user    
	 * @return void
	 */
	public void update(User user) {
		userDao.save(user);
	}
	
	/**
	 * @Description: 根据用户ID更新用户密码.
	 * @param @param userId
	 * @param @param newPwd
	 * @param @param isTrue    
	 * @return void
	 */
	public void updateUserPwd(Long userId, String newPwd, Integer isTrue) {
		User user = userDao.getOne(userId);
		user.setLoginPwd(newPwd);
		userDao.save(user);
	}

	/**
	 * @Description: 根据ID获取用户信息
	 * @param @param userId
	 * @param @return    
	 * @return PmsUser
	 */
	public User getById(Long userId) {
		return userDao.getOne(userId);
	}

	/**
	 * @Description: 查询并分页列出用户信息.
	 * @param @param pageParam
	 * @param @param paramMap
	 * @param @return    
	 * @return PageBean
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		//return pmsUserDao.listPage(pageParam, paramMap);
		//return pmsUserDao.listPage(PmsUser.class, pageParam, paramMap);
		paramMap.put("module", "pmsUser");
		return getBaseDao().listPage(PmsUser.class, pageParam, paramMap);
	}

	/**
	 * @Description: 保存用户信息.
	 * @param @param pmsUser    
	 * @return void
	 */
	public void create(User user) {
		userDao.save(user);
	}
	
	/**
	 * @Description: 保存用户信息及其关联的角色.
	 * @param @param pmsUser
	 * @param @param roleUserStr    
	 * @return void
	 *//*
	public void saveUser(PmsUser pmsUser, String roleUserStr) {
		// 保存用户信息
		//pmsUserDao.saveOrUpdate(pmsUser);
		getBaseDao().saveOrUpdate(pmsUser);
		
		// 保存角色关联信息
		if (StringUtils.isNotBlank(roleUserStr) && roleUserStr.length() > 0) {
			saveOrUpdateRoleUser(pmsUser.getId(), roleUserStr);
		}
	}
	
	*//**
	 * @Description: 保存用户和角色之间的关联关系
	 * @param @param userId
	 * @param @param roleIdsStr    
	 * @return void
	 *//*
	private void saveOrUpdateRoleUser(long userId, String roleIdsStr) {
		// 删除原来的角色与用户关联
		//List<PmsRoleUser> listPmsRoleUsers = pmsRoleUserDao.listByUserId(userId);
		List<PmsRoleUser> listPmsRoleUsers = getBaseDao().selectList(getStatement("listRoleUserByUserId"), userId);
		Map<Long, PmsRoleUser> delMap = new HashMap<Long, PmsRoleUser>();
		for (PmsRoleUser pmsRoleUser : listPmsRoleUsers) {
			delMap.put(pmsRoleUser.getRoleId(), pmsRoleUser);
		}
		if (StringUtils.isNotBlank(roleIdsStr)) {
			// 创建新的关联
			String[] roleIds = roleIdsStr.split(",");
			for (int i = 0; i < roleIds.length; i++) {
				long roleId = Long.parseLong(roleIds[i]);
				if (delMap.get(roleId) == null) {
					PmsRoleUser pmsRoleUser = new PmsRoleUser();
					pmsRoleUser.setUserId(userId);
					pmsRoleUser.setRoleId(roleId);
					//pmsRoleUserDao.saveOrUpdate(pmsRoleUser);
					getBaseDao().saveOrUpdate(pmsRoleUser);
				} else {
					delMap.remove(roleId);
				}
			}
		}

		Iterator<Long> iterator = delMap.keySet().iterator();
		while (iterator.hasNext()) {
			long roleId = iterator.next();
			//pmsRoleUserDao.deleteByRoleIdAndUserId(roleId, userId);
			PmsRoleUser pmsRoleUser = new PmsRoleUser();
			pmsRoleUser.setRoleId(roleId);
			pmsRoleUser.setUserId(userId);
			getBaseDao().deleteByModel(pmsRoleUser);
		}
	}

	
	*//**
	 * @Description: 修改用户信息及其关联的角色
	 * @param @param pmsUser
	 * @param @param roleUserStr    
	 * @return void
	 *//*
	public void updateUser(PmsUser pmsUser, String roleUserStr) {
		//pmsUserDao.saveOrUpdate(pmsUser);
		getBaseDao().saveOrUpdate(pmsUser);
		// 更新角色信息
		saveOrUpdateRoleUser(pmsUser.getId(), roleUserStr);
	}*/
	
	/**
	 * @Description: 根据角色ID统计有多少个用户关联到此角色.
	 * @param @param roleId
	 * @param @return    
	 * @return int
	 */
	public int countUserByRoleId(Long roleId) {
		
		Role role = roleDao.getOne(roleId);
		if (role.getUsers() == null || role.getUsers().isEmpty()) {
			return 0;
		} else {
			return role.getUsers().size();
		}
	}
	
	/**
	 * @Description:  根据用户ID获得所有用户－角色关联列表
	 * @param @param userId
	 * @param @return    
	 * @return List<PmsRoleUser>
	 *//*
	public List<PmsRoleUser> listRoleUserByUserId(long userId) {
		//return pmsRoleUserDao.listByUserId(userId);
		return getBaseDao().selectList(getStatement("listRoleUserByUserId"), userId);
	}*/
	
}
