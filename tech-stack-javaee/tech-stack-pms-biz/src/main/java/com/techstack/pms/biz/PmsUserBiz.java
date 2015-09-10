package com.techstack.pms.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.techstack.pms.dao.dto.PmsRoleUserDTO;
import com.techstack.pms.dao.dto.PmsUserDTO;
import com.techstack.pms.dao.facade.PmsRoleUserDaoFacade;
import com.techstack.pms.dao.facade.PmsUserDaoFacade;

/**
 * @Title: PmsUserBiz.java 
 * @Description: 用户业务层
 * @author zzh
 */
@Component("pmsUserBiz")
public class PmsUserBiz {

	@Autowired
	private PmsUserDaoFacade pmsUserDaoFacade;
	@Autowired
	private PmsRoleUserDaoFacade pmsRoleUserDaoFacade;

	/**
	 * @Description: 根据登录名取得用户对象
	 * @param @param loginName
	 * @param @return    
	 * @return PmsUser
	 */
	public PmsUserDTO findUserByLoginName(String loginName) {
		return pmsUserDaoFacade.findUserByLoginName(loginName);
	}

	/**
	 * @Description: 根据ID删除一个用户，同时删除与该用户关联的角色关联信息. type="1"的超级管理员不能删除.
	 * @param @param userId    
	 * @return void
	 */
	public void deleteUserById(long userId) {
		PmsUserDTO pmsUser = pmsUserDaoFacade.getById(userId);
		if (pmsUser != null) {
			if ("1".equals(pmsUser.getType())) {
				throw new RuntimeException("【" + pmsUser.getLoginName() + "】为超级管理员，不能删除！");
			}
			// 删除原来的角色与用户关联
			PmsRoleUserDTO pmsRoleUser = new PmsRoleUserDTO();
			pmsRoleUser.setUserId(userId);
			pmsRoleUserDaoFacade.deleteByModel(pmsRoleUser);
			pmsUserDaoFacade.deleteById(pmsUser.getId());
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
		return pmsUserDaoFacade.listUserByRoleId(roleId);
	}
	
	/**
	 * @Description: 更新用户信息.
	 * @param @param user    
	 * @return void
	 */
	public void update(PmsUserDTO user) {
		pmsUserDaoFacade.saveOrUpdate(user);
	}
	
	/**
	 * @Description: 根据用户ID更新用户密码.
	 * @param @param userId
	 * @param @param newPwd
	 * @param @param isTrue    
	 * @return void
	 */
	public void updateUserPwd(Long userId, String newPwd, Integer isTrue) {
		PmsUserDTO pmsUser = pmsUserDaoFacade.getById(userId);
		if(pmsUser!=null){
			pmsUser.setLoginPwd(newPwd);
		}
		pmsUserDaoFacade.saveOrUpdate(pmsUser);
	}

	/**
	 * @Description: 根据ID获取用户信息
	 * @param @param userId
	 * @param @return    
	 * @return PmsUser
	 */
	public PmsUserDTO getById(Long userId) {
		return pmsUserDaoFacade.getById(userId);
	}

	/**
	 * @Description: 查询并分页列出用户信息.
	 * @param @param pageParam
	 * @param @param paramMap
	 * @param @return    
	 * @return PageBean
	 */
	public Page<PmsUserDTO> listPage(int pageNum, int pageSize, Map<String, Object> paramMap) {
		return pmsUserDaoFacade.listPage(pageNum, pageSize, paramMap);
	}

	/**
	 * @Description: 保存用户信息及其关联的角色.
	 * @param @param pmsUser
	 * @param @param roleUserStr    
	 * @return void
	 */
	public void saveUser(PmsUserDTO pmsUser, List<Long> roleIds) {
		// 保存用户信息
		pmsUser = pmsUserDaoFacade.saveOrUpdate(pmsUser);
		
		// 保存角色关联信息
		if (pmsUser!=null && roleIds!=null && !roleIds.isEmpty()) {
			saveOrUpdateRoleUser(pmsUser.getId(), roleIds);
		}
	}
	
	/**
	 * @Description: 保存用户和角色之间的关联关系
	 * @param @param userId
	 * @param @param roleIdsStr    
	 * @return void
	 */
	private void saveOrUpdateRoleUser(Long userId, List<Long> roleIds) {
		// 删除原来的角色与用户关联
		List<PmsRoleUserDTO> listPmsRoleUsers = pmsUserDaoFacade.listRoleUserByUserId(userId);
		List<Long> originRoleIds = new ArrayList<Long>();
		for (PmsRoleUserDTO pmsRoleUser : listPmsRoleUsers) {
			originRoleIds.add(pmsRoleUser.getRoleId());
		}
		List<Long> newAddRoleIds = removeAll(roleIds, originRoleIds);
		for(Long newRoleId : newAddRoleIds){
			PmsRoleUserDTO pmsRoleUser = new PmsRoleUserDTO();
			pmsRoleUser.setUserId(userId);
			pmsRoleUser.setRoleId(newRoleId);
			pmsRoleUserDaoFacade.saveOrUpdate(pmsRoleUser);
		}
		List<Long> removeRoleIds = removeAll(originRoleIds, roleIds);
		for(Long removeRoleId : removeRoleIds){
			PmsRoleUserDTO pmsRoleUser = new PmsRoleUserDTO();
			pmsRoleUser.setRoleId(removeRoleId);
			pmsRoleUser.setUserId(userId);
			pmsRoleUserDaoFacade.deleteByModel(pmsRoleUser);
		}
		/*Map<Long, PmsRoleUserDTO> delMap = new HashMap<Long, PmsRoleUserDTO>();
		for (PmsRoleUserDTO pmsRoleUser : listPmsRoleUsers) {
			delMap.put(pmsRoleUser.getRoleId(), pmsRoleUser);
		}
		if (roleIds!=null && !roleIds.isEmpty()) {
			// 创建新的关联
			//String[] roleIds = roleIds.split(",");
			for (Long roleId : roleIds) {
				//long roleId = Long.parseLong(roleIds[i]);
				if (delMap.get(roleId) == null) {
					PmsRoleUserDTO pmsRoleUser = new PmsRoleUserDTO();
					pmsRoleUser.setUserId(userId);
					pmsRoleUser.setRoleId(roleId);
					pmsRoleUserDaoFacade.saveOrUpdate(pmsRoleUser);
				} else {
					delMap.remove(roleId);
				}
			}
		}

		Iterator<Long> iterator = delMap.keySet().iterator();
		while (iterator.hasNext()) {
			long roleId = iterator.next();
			PmsRoleUserDTO pmsRoleUser = new PmsRoleUserDTO();
			pmsRoleUser.setRoleId(roleId);
			pmsRoleUser.setUserId(userId);
			pmsRoleUserDaoFacade.deleteByModel(pmsRoleUser);
		}*/
	}
	
	/**
	 * roleIds - originRoleIds的差集
	 * @param roleIds
	 * @param originRoleIds
	 * @return
	 */
	private List<Long> removeAll(List<Long> roleIds, List<Long> originRoleIds){
		List<Long> result = new ArrayList<Long>();
		for(Long roleId : roleIds){
			if(!originRoleIds.contains(roleId)){
				result.add(roleId);
			}
		}
		return result;
	}

	
	/**
	 * @Description: 修改用户信息及其关联的角色
	 * @param @param pmsUser
	 * @param @param roleUserStr    
	 * @return void
	 */
	public void updateUser(PmsUserDTO pmsUser, List<Long> roleIds) {
		pmsUserDaoFacade.saveOrUpdate(pmsUser);
		// 更新角色信息
		if(pmsUser != null){
			saveOrUpdateRoleUser(pmsUser.getId(), roleIds);
		}
	}
	
	/**
	 * @Description: 根据角色ID统计有多少个用户关联到此角色.
	 * @param @param roleId
	 * @param @return    
	 * @return int
	 */
	public int countUserByRoleId(Long roleId) {
		List<PmsRoleUserDTO> userList = pmsUserDaoFacade.listRoleUserByRoleId(roleId);
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
	public List<PmsRoleUserDTO> listRoleUserByUserId(long userId) {
		return pmsUserDaoFacade.listRoleUserByUserId(userId);
	}

}