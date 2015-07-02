package com.techstack.sms2.permission.biz;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.techstack.sms2.base.page.PageBean;
import com.techstack.sms2.base.page.PageParam;
import com.techstack.sms2.course.biz.BaseBiz;
import com.techstack.sms2.permission.entity.PmsRoleUser;
import com.techstack.sms2.permission.entity.PmsUser;

/**
 * @Title: PmsUserBiz.java 
 * @Description: 用户业务层
 * @author zzh
 */
@Service("pmsUserBiz")
public class PmsUserBiz extends BaseBiz{

/*	@Autowired
	private PmsUserDao pmsUserDao;

	@Autowired
	private PmsRoleUserDao pmsRoleUserDao;*/

	/**
	 * @Description: 根据登录名取得用户对象
	 * @param @param loginName
	 * @param @return    
	 * @return PmsUser
	 */
	public PmsUser findUserByLoginName(String loginName) {
		//return pmsUserDao.findByLoginName(loginName);
		return getBaseDao().selectOne(getStatement("findUserByLoginName"), loginName);
	}

	/**
	 * @Description: 根据ID删除一个用户，同时删除与该用户关联的角色关联信息. type="1"的超级管理员不能删除.
	 * @param @param userId    
	 * @return void
	 */
	public void deleteUserById(long userId) {
		//PmsUser pmsUser = pmsUserDao.getById(PmsUser.class,userId);
		PmsUser pmsUser = getBaseDao().getById(PmsUser.class,userId);
		if (pmsUser != null) {
			if ("1".equals(pmsUser.getType())) {
				throw new RuntimeException("【" + pmsUser.getLoginName() + "】为超级管理员，不能删除！");
			}
			//pmsUserDao.deleteById(PmsUser.class,pmsUser.getId());
			getBaseDao().deleteById(PmsUser.class,pmsUser.getId());
			
			// 删除原来的角色与用户关联
			//pmsRoleUserDao.deleteByUserId(userId);
			PmsRoleUser pmsRoleUser = new PmsRoleUser();
			pmsRoleUser.setUserId(userId);
			getBaseDao().deleteByModel(pmsRoleUser);
		}
	}

	/**
	 * @Description: 根据角色ID查询用户
	 * @param @param roleId
	 * @param @return    
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List listUserByRoleId(long roleId) {
		//return pmsUserDao.listByRoleId(roleId);
		return getBaseDao().selectList(getStatement("listUserByRoleId"), roleId);
	}
	
	/**
	 * @Description: 更新用户信息.
	 * @param @param user    
	 * @return void
	 */
	public void update(PmsUser user) {
		//pmsUserDao.saveOrUpdate(user);
		getBaseDao().saveOrUpdate(user);
	}
	
	/**
	 * @Description: 根据用户ID更新用户密码.
	 * @param @param userId
	 * @param @param newPwd
	 * @param @param isTrue    
	 * @return void
	 */
	public void updateUserPwd(Long userId, String newPwd, Integer isTrue) {
		//PmsUser pmsUser = pmsUserDao.getById(PmsUser.class,userId);
		PmsUser pmsUser = getBaseDao().getById(PmsUser.class,userId);
		pmsUser.setLoginPwd(newPwd);
		//pmsUserDao.saveOrUpdate(pmsUser);
		getBaseDao().saveOrUpdate(pmsUser);
	}

	/**
	 * @Description: 根据ID获取用户信息
	 * @param @param userId
	 * @param @return    
	 * @return PmsUser
	 */
	public PmsUser getById(Long userId) {
		//return pmsUserDao.getById(PmsUser.class,userId);
		return getBaseDao().getById(PmsUser.class,userId);
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
	public void create(PmsUser pmsUser) {
		//pmsUserDao.saveOrUpdate(pmsUser);
		getBaseDao().saveOrUpdate(pmsUser);
	}
	
	/**
	 * @Description: 保存用户信息及其关联的角色.
	 * @param @param pmsUser
	 * @param @param roleUserStr    
	 * @return void
	 */
	public void saveUser(PmsUser pmsUser, String roleUserStr) {
		// 保存用户信息
		//pmsUserDao.saveOrUpdate(pmsUser);
		getBaseDao().saveOrUpdate(pmsUser);
		
		// 保存角色关联信息
		if (StringUtils.isNotBlank(roleUserStr) && roleUserStr.length() > 0) {
			saveOrUpdateRoleUser(pmsUser.getId(), roleUserStr);
		}
	}
	
	/**
	 * @Description: 保存用户和角色之间的关联关系
	 * @param @param userId
	 * @param @param roleIdsStr    
	 * @return void
	 */
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

	
	/**
	 * @Description: 修改用户信息及其关联的角色
	 * @param @param pmsUser
	 * @param @param roleUserStr    
	 * @return void
	 */
	public void updateUser(PmsUser pmsUser, String roleUserStr) {
		//pmsUserDao.saveOrUpdate(pmsUser);
		getBaseDao().saveOrUpdate(pmsUser);
		// 更新角色信息
		saveOrUpdateRoleUser(pmsUser.getId(), roleUserStr);
	}
	
	/**
	 * @Description: 根据角色ID统计有多少个用户关联到此角色.
	 * @param @param roleId
	 * @param @return    
	 * @return int
	 */
	public int countUserByRoleId(Long roleId) {
		//List<PmsRoleUser> userList = pmsRoleUserDao.listByRoleId(roleId);
		List<PmsRoleUser> userList = getBaseDao().selectList(getStatement("listRoleUserByRoleId"), roleId);
		if (userList == null || userList.isEmpty()) {
			return 0;
		} else {
			return userList.size();
		}
	}
	
	/**
	 * @Description:  根据用户ID获得所有用户－角色关联列表
	 * @param @param userId
	 * @param @return    
	 * @return List<PmsRoleUser>
	 */
	public List<PmsRoleUser> listRoleUserByUserId(long userId) {
		//return pmsRoleUserDao.listByUserId(userId);
		return getBaseDao().selectList(getStatement("listRoleUserByUserId"), userId);
	}

}