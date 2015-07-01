package com.techstack.sms2.permission.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.techstack.sms2.base.page.PageBean;
import com.techstack.sms2.base.page.PageParam;
import com.techstack.sms2.course.biz.BaseBiz;
import com.techstack.sms2.permission.entity.PmsRole;
import com.techstack.sms2.permission.entity.PmsRoleAction;
import com.techstack.sms2.permission.entity.PmsRoleMenu;
import com.techstack.sms2.permission.entity.PmsRoleUser;

/**
 * @Title: PmsRoleBiz.java 
 * @Description: 角色业务层
 * @author zzh
 */
@Component("pmsRoleBiz")
public class PmsRoleBiz extends BaseBiz{

	/*@Autowired
	private PmsRoleDao pmsRoleDao;
	@Autowired
	private PmsRoleActionDao pmsRoleActionDao;
	@Autowired
	private PmsRoleUserDao pmsRoleUserDao;
	@Autowired
	private PmsRoleMenuDao pmsRoleMenuDao;*/


	/**
	 * @Description: 获取所有角色列表，以供添加用户时选择
	 * @param @return    
	 * @return List<PmsRole>
	 */
	public List<PmsRole> listAllRole() {
		//return pmsRoleDao.listAll();
		return getBaseDao().selectList(getStatement("listAllRole"), new HashMap<String,Object>());
	}

	/**
	 * @Description: 根据角色ID删除角色 .
	 * @param @param id    
	 * @return void
	 */
	public void deleteById(Long id) {
		//pmsRoleDao.deleteById(PmsRole.class,id);
		getBaseDao().deleteById(PmsRole.class,id);
	}

	/**
	 * @Description: 根据角色名称获取角色记录（用于判断角色名是否已存在）
	 * @param @param roleName
	 * @param @return    
	 * @return PmsRole
	 */
	public PmsRole getByRoleName(String roleName) {
		//return pmsRoleDao.getByRoleName(roleName);
		return getBaseDao().selectOne(getStatement("getRoleByRoleName"), roleName);
	}

	/**
	 * @Description: 查找是否存在与ID值不相同与角色名相同的角色记录（用于判断修改的角色名与其他的角色名冲突）
	 * @param @param id
	 * @param @param roleName
	 * @param @return    
	 * @return PmsRole
	 */
	public PmsRole findByRoleNameNotEqId(Long id, String roleName) {
		//return pmsRoleDao.findByRoleNameNotEqId(id, roleName);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("roleName", roleName);
		return getBaseDao().selectOne(getStatement("findRoleByRoleNameNotEqId"), param);
		
	}

	/**
	 * @Description: 根据权限ID找出关联了此权限的角色.
	 * @param @param actionId
	 * @param @return    
	 * @return List<PmsRole>
	 */
	public List<PmsRole> listByActionId(Long actionId) {
		//return pmsRoleDao.listByActionId(actionId);
		return getBaseDao().selectList(getStatement("listRoleByActionId"), actionId);
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

	/**
	 * @Description: 根据ID获取角色.
	 * @param @param id
	 * @param @return    
	 * @return PmsRole
	 */
	public PmsRole getById(Long id) {
		//return pmsRoleDao.getById(PmsRole.class,id);
		return getBaseDao().getById(PmsRole.class,id);
	}

	/**
	 * @Description: 保存角色
	 * @param @param pmsRole    
	 * @return void
	 */
	public void create(PmsRole pmsRole) {
		//pmsRoleDao.saveOrUpdate(pmsRole);
		getBaseDao().saveOrUpdate(pmsRole);
	}

	/**
	 * @Description: 更新角色
	 * @param @param pmsRole    
	 * @return void
	 */
	public void update(PmsRole pmsRole) {
		//pmsRoleDao.saveOrUpdate(pmsRole);
		getBaseDao().saveOrUpdate(pmsRole);
	}
	
	/**
	 * @Description: 根据角色ID删除角色，并删除与用户、权限、菜单的关联关系.
	 * @param @param roleId    
	 * @return void
	 */
	public void deleteRoleById(Long roleId) {
		// 删除角色权限关联表中的数据
		//pmsRoleActionDao.deleteByRoleId(roleId);
		PmsRoleAction pmsRoleAction = new PmsRoleAction();
		pmsRoleAction.setRoleId(roleId);
		getBaseDao().deleteByModel(pmsRoleAction);
		
		// 删除角色菜单关联表中的数据
		//pmsRoleMenuDao.deleteByRoleId(roleId);
		PmsRoleMenu pmsRoleMenu = new PmsRoleMenu();
		pmsRoleMenu.setRoleId(roleId);
		getBaseDao().deleteByModel(pmsRoleMenu);
		
		// 删除角色用户关联表中的数据
		//pmsRoleUserDao.deleteByRoleId(roleId);
		PmsRoleUser pmsRoleUser = new PmsRoleUser();
		pmsRoleUser.setRoleId(roleId);
		getBaseDao().deleteByModel(pmsRoleUser);
		
		// 最后删除角色信息
		//pmsRoleDao.deleteById(PmsRole.class,roleId);
		getBaseDao().deleteById(PmsRole.class,roleId);
		
	}
	
	/**
	 * @Description: 修改角色信息，并更新其关联的权限点.
	 * @param @param pmsRole    
	 * @return void
	 */
	public void updateRole(PmsRole pmsRole) {
		//pmsRoleDao.saveOrUpdate(pmsRole);
		getBaseDao().saveOrUpdate(pmsRole);
	}
	
	/**
	 * @Description: 保存角色并关联权限.
	 * @param @param pmsRole    
	 * @return void
	 */
	public void saveRole(PmsRole pmsRole) {
		//pmsRoleDao.saveOrUpdate(pmsRole);
		getBaseDao().saveOrUpdate(pmsRole);
	}
	
	/**
	 * @Description: 根据用户ID获得该用户的所有角色id所拼成的String，每个ID用“,”分隔
	 * @param @param operatorId
	 * @param @return    
	 * @return String
	 */
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
	}


}
