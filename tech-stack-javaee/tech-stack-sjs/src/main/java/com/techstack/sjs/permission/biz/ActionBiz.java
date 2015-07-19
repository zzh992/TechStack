package com.techstack.sjs.permission.biz;

import java.util.Arrays;
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
import com.techstack.sjs.permission.entity.Menu;
import com.techstack.sjs.permission.entity.Role;

@Component("actionBiz")
public class ActionBiz {
	
	@Autowired
	private ActionDao actionDao;
	@Autowired
	private RoleDao roleDao;

	/**
	 * @Description: 根据Action的id字符串得到相应的权限列表
	 * @param @param ids
	 * @param @return    
	 * @return List<PmsAction>
	 */
	public Set<Action> findActionsByIdStr(String ids) {
		//return pmsActionDao.findByIds(ids);
		List<String> idarr = Arrays.asList(ids.split(","));
		//return getBaseDao().selectList(getStatement("findActionByIds"), idarr);
		return actionDao.findByIdIn(idarr);
	}

	/**
	 * @Description: 根据ID删除权限信息.
	 * @param @param id    
	 * @return void
	 */
	public void deleteById(Long id) {
		//pmsActionDao.deleteById(PmsAction.class,id);
		//getBaseDao().deleteById(PmsAction.class,id);
		actionDao.delete(id);
	}

	/**
	 * @Description: 根据权限名称查找权限（用于判断权限名是否已存在）
	 * @param @param actionName
	 * @param @return    
	 * @return PmsAction
	 */
	public Action getByActionName(String actionName) {
		//return pmsActionDao.getByActionName(actionName);
		//return getBaseDao().selectOne(getStatement("getActionByActionName"), actionName);
		return actionDao.findByActionName(actionName);
	}

	/**
	 * @Description: 根据权限查找权限记录（用于判断权限是否已存在）.
	 * @param @param action
	 * @param @return    
	 * @return PmsAction
	 */
	public Action getByAction(String action) {
		//return pmsActionDao.getByAction(action);
		//return getBaseDao().selectOne(getStatement("getActionByAction"), action);
		return actionDao.findByAction(action);
	}

	/**
	 * @Description: 检查修改后的权限名是否会与其他权限名冲突.
	 * @param @param actionName
	 * @param @param id
	 * @param @return    
	 * @return PmsAction
	 */
	public Action getByActionNameNotEqId(String actionName, Long id) {
		//return pmsActionDao.getByActionNameNotEqId(actionName, id);
		//Map<String, Object> param = new HashMap<String, Object>();
		//param.put("actionName", actionName);
		//param.put("id", id);
		//return getBaseDao().selectOne(getStatement("getActionByActionNameNotEqId"), param);
		return actionDao.findByActionNameAndIdNot(actionName, id);
	}

	/**
	 * @Description: 检查修改后的权限是否会与其他权限冲突.
	 * @param @param action
	 * @param @param id
	 * @param @return    
	 * @return PmsAction
	 */
	public Action getByActionNotEqId(String action, Long id) {
		//return pmsActionDao.getByActionNotEqId(action, id);
		//Map<String, Object> param = new HashMap<String, Object>();
		//param.put("action", action);
		//param.put("id", id);
		//return getBaseDao().selectOne(getStatement("getActionByActionNotEqId"), param);
		return actionDao.findByActionAndIdNot(action, id);
	}

	/**
	 * @Description:  根据菜单ID查找权限集.
	 * @param @param menuId
	 * @param @return    
	 * @return List<PmsAction>
	 */
	public Set<Action> listByMenuId(Long menuId) {
		//return pmsActionDao.listByMenuId(menuId);
		//return getBaseDao().selectList(getStatement("listActionByMenuId"), menuId);
		Menu relevantMenu = new Menu();
		relevantMenu.setId(menuId);
		return actionDao.findByRelevantMenu(relevantMenu);
	}

	/**
	 * @Description: 查询并分页列出权限功能点.
	 * @param @param pageParam
	 * @param @param paramMap
	 * @param @return    
	 * @return PageBean
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		//return pmsActionDao.listPage(pageParam, paramMap);
		//return pmsActionDao.listPage(PmsAction.class, pageParam, paramMap);
		return getBaseDao().listPage(PmsAction.class, pageParam, paramMap);
	}

	/**
	 * @Description: 根据ID获取权限点
	 * @param @param id
	 * @param @return    
	 * @return PmsAction
	 */
	public Action getById(Long id) {
		//return pmsActionDao.getById(PmsAction.class,id);
		//return getBaseDao().getById(PmsAction.class,id);
		return actionDao.getOne(id);
	}

	/**
	 * @Description: 保存权限功能点
	 * @param @param act    
	 * @return void
	 */
	public void saveAction(Action act) {
		//pmsActionDao.saveOrUpdate(act);
		actionDao.save(act);
	}

	/**
	 * @Description: 更新权限功能点.
	 * @param @param pmsAction    
	 * @return void
	 */
	public void updateAction(Action pmsAction) {
		//pmsActionDao.saveOrUpdate(pmsAction);
		actionDao.save(pmsAction);
	}
	
	/**
	 * @Description: 根据权限ID删除权限并解除权限与角色的关联关系. 
	 * @param @param actionId    
	 * @return void
	 */
	public void deleteActionById(Long actionId) {
		//pmsActionDao.deleteById(PmsAction.class,actionId);
		//getBaseDao().deleteById(PmsAction.class,actionId);
		// 删除权限和角色关联表中的关联关系
		//pmsRoleActionDao.deleteByActionId(actionId);
		//PmsRoleAction pmsRoleAction = new PmsRoleAction();
		//pmsRoleAction.setActionId(actionId);
		//getBaseDao().deleteByModel(pmsRoleAction);
		
		
	}
	
	/**
	 * @Description: 根据角色ID统计有多少权限关联到此角色.
	 * @param @param roleId
	 * @param @return    
	 * @return int
	 */
	public int countActionByRoleId(Long roleId) {
		//List<PmsRoleAction> actionList = pmsRoleActionDao.listByRoleId(roleId);
		/*List<PmsRoleAction> actionList = getBaseDao().selectList(getStatement("listRoleActionByRoleId"), roleId);
		if (actionList == null || actionList.isEmpty()) {
			return 0;
		} else {
			return actionList.size();
		}*/
		Role role = roleDao.getOne(roleId);
		return role.getActions().size();
	}
	
	/**
	 * @Description: 根据角色ID，获取所有的功能权限ID集
	 * @param @param roleId
	 * @param @return    
	 * @return String
	 */
	public String getActionIdsByRoleId(Long roleId) {
		//List<PmsRoleAction> rmList = pmsRoleActionDao.listByRoleId(roleId);
		List<PmsRoleAction> rmList = getBaseDao().selectList(getStatement("listRoleActionByRoleId"), roleId);
		StringBuffer actionIds = new StringBuffer();
		if (rmList != null && !rmList.isEmpty()) {
			for (PmsRoleAction rm : rmList) {
				actionIds.append(rm.getActionId()).append(",");
			}
		}
		return actionIds.toString();
	}
	
	/**
	 * @Description: 根据角色ID集得到所有权限ID集
	 * @param @param roleIds
	 * @param @return    
	 * @return String
	 */
	public String getActionIdsByRoleIds(String roleIds) {
		// 得到角色－权限表中roleiId在ids中的所有关联对象
		//List<PmsRoleAction> listPmsRoleActions = pmsRoleActionDao.listByRoleIds(roleIds);
		List<String> roldIdArr = Arrays.asList(roleIds.split(","));
		List<PmsRoleAction> listPmsRoleActions = getBaseDao().selectList(getStatement("listRoleActionByRoleIds"), roldIdArr);
		// 构建StringBuffer
		StringBuffer actionIdsBuf = new StringBuffer("");
		// 拼接字符串
		for (PmsRoleAction pmsRoleAction : listPmsRoleActions) {
			actionIdsBuf.append(pmsRoleAction.getActionId()).append(",");
		}
		String actionIds = actionIdsBuf.toString();
		// 截取字符串
		if (StringUtils.isNotBlank(actionIds) && actionIds.length() > 0) {
			actionIds = actionIds.substring(0, actionIds.length() - 1); // 去掉最后一个逗号
		}
		return actionIds;
	}
}
