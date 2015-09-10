package com.techstack.pms.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.techstack.pms.dao.dto.PmsRoleActionDTO;
import com.techstack.pms.dao.dto.PmsRoleDTO;
import com.techstack.pms.dao.dto.PmsRoleMenuDTO;
import com.techstack.pms.dao.dto.PmsRoleUserDTO;
import com.techstack.pms.dao.facade.PmsRoleActionDaoFacade;
import com.techstack.pms.dao.facade.PmsRoleDaoFacade;
import com.techstack.pms.dao.facade.PmsRoleMenuDaoFacade;
import com.techstack.pms.dao.facade.PmsRoleUserDaoFacade;

/**
 * @Title: PmsRoleBiz.java 
 * @Description: 角色业务层
 * @author zzh
 */
@Component("pmsRoleBiz")
public class PmsRoleBiz {

	@Autowired
	private PmsRoleDaoFacade pmsRoleDaoFacade;
	@Autowired
	private PmsRoleActionDaoFacade pmsRoleActionDaoFacade;
	@Autowired
	private PmsRoleUserDaoFacade pmsRoleUserDaoFacade;
	@Autowired
	private PmsRoleMenuDaoFacade pmsRoleMenuDaoFacade;


	/**
	 * @Description: 获取所有角色列表，以供添加用户时选择
	 * @param @return    
	 * @return List<PmsRole>
	 */
	public List<PmsRoleDTO> listAllRole() {
		return pmsRoleDaoFacade.listAllRole();
	}

	/**
	 * @Description: 根据角色ID删除角色 .
	 * @param @param id    
	 * @return void
	 */
	public void deleteById(Long id) {
		pmsRoleDaoFacade.deleteById(id);
	}

	/**
	 * @Description: 根据角色名称获取角色记录（用于判断角色名是否已存在）
	 * @param @param roleName
	 * @param @return    
	 * @return PmsRole
	 */
	public PmsRoleDTO getByRoleName(String roleName) {
		return pmsRoleDaoFacade.getRoleByRoleName(roleName);
	}

	/**
	 * @Description: 查找是否存在与ID值不相同与角色名相同的角色记录（用于判断修改的角色名与其他的角色名冲突）
	 * @param @param id
	 * @param @param roleName
	 * @param @return    
	 * @return PmsRole
	 */
	public PmsRoleDTO findByRoleNameNotEqId(Long id, String roleName) {
		return pmsRoleDaoFacade.findRoleByRoleNameNotEqId(roleName, id);
	}

	/**
	 * @Description: 根据权限ID找出关联了此权限的角色.
	 * @param @param actionId
	 * @param @return    
	 * @return List<PmsRole>
	 */
	public List<PmsRoleDTO> listByActionId(Long actionId) {
		return pmsRoleDaoFacade.listRoleByActionId(actionId);
	}

	/**
	 * @Description: 查询并分页列出角色信息
	 * @param @param pageParam
	 * @param @param paramMap
	 * @param @return    
	 * @return PageBean
	 */
	public Page<PmsRoleDTO> listPage(int pageNum, int pageSize, Map<String, Object> paramMap) {
		return pmsRoleDaoFacade.listPage(pageNum, pageSize, paramMap);
	}

	/**
	 * @Description: 根据ID获取角色.
	 * @param @param id
	 * @param @return    
	 * @return PmsRole
	 */
	public PmsRoleDTO getById(Long id) {
		return pmsRoleDaoFacade.getById(id);
	}

	/**
	 * @Description: 根据角色ID删除角色，并删除与用户、权限、菜单的关联关系.
	 * @param @param roleId    
	 * @return void
	 */
	public void deleteRoleById(Long roleId) {
		// 删除角色权限关联表中的数据
		PmsRoleActionDTO pmsRoleAction = new PmsRoleActionDTO();
		pmsRoleAction.setRoleId(roleId);
		pmsRoleActionDaoFacade.deleteByModel(pmsRoleAction);
		
		// 删除角色菜单关联表中的数据
		PmsRoleMenuDTO pmsRoleMenu = new PmsRoleMenuDTO();
		pmsRoleMenu.setRoleId(roleId);
		pmsRoleMenuDaoFacade.deleteByModel(pmsRoleMenu);
		
		// 删除角色用户关联表中的数据
		PmsRoleUserDTO pmsRoleUser = new PmsRoleUserDTO();
		pmsRoleUser.setRoleId(roleId);
		pmsRoleUserDaoFacade.deleteByModel(pmsRoleUser);
		
		// 最后删除角色信息
		pmsRoleDaoFacade.deleteById(roleId);
		
	}
	
	/**
	 * @Description: 修改角色信息，并更新其关联的权限点.
	 * @param @param pmsRole    
	 * @return void
	 */
	public void updateRole(PmsRoleDTO pmsRole) {
		pmsRoleDaoFacade.saveOrUpdate(pmsRole);
	}
	
	/**
	 * @Description: 保存角色并关联权限.
	 * @param @param pmsRole    
	 * @return void
	 */
	public void saveRole(PmsRoleDTO pmsRole) {
		pmsRoleDaoFacade.saveOrUpdate(pmsRole);
	}
	
	/**
	 * @Description: 根据用户ID获得该用户的所有角色id
	 * @param @param operatorId
	 * @param @return    
	 * @return String
	 */
	public List<Long> getRoleIdsByUserId(long userId) {
		// 得到用户和角色列表
		List<PmsRoleUserDTO> rpList = pmsRoleDaoFacade.listRoleUserByUserId(userId);
		List<Long> roleIds = new ArrayList<Long>();
		for (PmsRoleUserDTO rp : rpList) {
			roleIds.add(rp.getRoleId());
		}
		return roleIds;
		/*StringBuffer roleIdsBuf = new StringBuffer("");
		for (PmsRoleUserDTO rp : rpList) {
			roleIdsBuf.append(rp.getRoleId()).append(",");
		}
		String roleIds = roleIdsBuf.toString();
		// 截取字符串
		if (!StringUtils.isEmpty(roleIds)) {
			roleIds = roleIds.substring(0, roleIds.length() - 1);
		}
		return roleIds;*/
	}


}
