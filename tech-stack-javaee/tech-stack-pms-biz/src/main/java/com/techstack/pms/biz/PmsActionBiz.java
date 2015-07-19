package com.techstack.pms.biz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.dto.PmsRoleActionDTO;
import com.techstack.pms.dao.facade.PmsActionDaoFacade;
import com.techstack.pms.dao.facade.PmsRoleActionDaoFacade;

/**
 * @Title: PmsActionBiz.java 
 * @Description: 权限点业务层
 * @author zzh
 */
@Component("pmsActionBiz")
public class PmsActionBiz {
	
	/*@Autowired
	private PmsActionDao pmsActionDao;
	@Autowired
	private PmsRoleActionDao pmsRoleActionDao;*/
	
	@Autowired
	private PmsActionDaoFacade pmsActionDaoFacade;
	@Autowired
	private PmsRoleActionDaoFacade pmsRoleActionDaoFacade;

	/**
	 * @Description: 根据Action的id字符串得到相应的权限列表
	 * @param @param ids
	 * @param @return    
	 * @return List<PmsAction>
	 */
	public List<PmsActionDTO> findActionsByIdStr(String ids) {
		//return pmsActionDao.findByIds(ids);
		List<String> idarr = Arrays.asList(ids.split(","));
		List<Long> idList = new ArrayList<Long>();
		for(String id : idarr){
			idList.add(Long.parseLong(id));
		}
		//return getBaseDao().selectList(getStatement("findActionByIds"), idarr);
		return pmsActionDaoFacade.findActionsByIds(idList);
	}

	/**
	 * @Description: 根据ID删除权限信息.
	 * @param @param id    
	 * @return void
	 */
	public void deleteById(Long id) {
		//pmsActionDao.deleteById(PmsAction.class,id);
		//getBaseDao().deleteById(PmsAction.class,id);
		pmsActionDaoFacade.deleteById(id);
	}

	/**
	 * @Description: 根据权限名称查找权限（用于判断权限名是否已存在）
	 * @param @param actionName
	 * @param @return    
	 * @return PmsAction
	 */
	public PmsActionDTO getByActionName(String actionName) {
		//return pmsActionDao.getByActionName(actionName);
		//return getBaseDao().selectOne(getStatement("getActionByActionName"), actionName);
		return pmsActionDaoFacade.getActionByActionName(actionName);
	}

	/**
	 * @Description: 根据权限查找权限记录（用于判断权限是否已存在）.
	 * @param @param action
	 * @param @return    
	 * @return PmsAction
	 */
	public PmsActionDTO getByAction(String action) {
		//return pmsActionDao.getByAction(action);
		//return getBaseDao().selectOne(getStatement("getActionByAction"), action);
		return pmsActionDaoFacade.getActionByAction(action);
	}

	/**
	 * @Description: 检查修改后的权限名是否会与其他权限名冲突.
	 * @param @param actionName
	 * @param @param id
	 * @param @return    
	 * @return PmsAction
	 */
	public PmsActionDTO getByActionNameNotEqId(String actionName, Long id) {
		//return pmsActionDao.getByActionNameNotEqId(actionName, id);
		/*Map<String, Object> param = new HashMap<String, Object>();
		param.put("actionName", actionName);
		param.put("id", id);
		return getBaseDao().selectOne(getStatement("getActionByActionNameNotEqId"), param);*/
		return pmsActionDaoFacade.getActionByActionNameNotEqId(actionName, id);
	}

	/**
	 * @Description: 检查修改后的权限是否会与其他权限冲突.
	 * @param @param action
	 * @param @param id
	 * @param @return    
	 * @return PmsAction
	 */
	public PmsActionDTO getByActionNotEqId(String action, Long id) {
		//return pmsActionDao.getByActionNotEqId(action, id);
	/*	Map<String, Object> param = new HashMap<String, Object>();
		param.put("action", action);
		param.put("id", id);
		return getBaseDao().selectOne(getStatement("getActionByActionNotEqId"), param);*/
		return pmsActionDaoFacade.getActionByActionNotEqId(action, id);
	}

	/**
	 * @Description:  根据菜单ID查找权限集.
	 * @param @param menuId
	 * @param @return    
	 * @return List<PmsAction>
	 */
	public List<PmsActionDTO> listByMenuId(Long menuId) {
		//return pmsActionDao.listByMenuId(menuId);
		//return getBaseDao().selectList(getStatement("listActionByMenuId"), menuId);
		return pmsActionDaoFacade.listActionByMenuId(menuId);
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
	public PmsActionDTO getById(Long id) {
		//return pmsActionDao.getById(PmsAction.class,id);
		//return getBaseDao().getById(PmsAction.class,id);
		return pmsActionDaoFacade.getById(id);
	}

	/**
	 * @Description: 保存权限功能点
	 * @param @param act    
	 * @return void
	 */
	public void saveAction(PmsActionDTO act) {
		//pmsActionDao.saveOrUpdate(act);
		//getBaseDao().saveOrUpdate(act);
		pmsActionDaoFacade.saveOrUpdate(act);
	}

	/**
	 * @Description: 更新权限功能点.
	 * @param @param pmsAction    
	 * @return void
	 */
	public void updateAction(PmsActionDTO pmsAction) {
		//pmsActionDao.saveOrUpdate(pmsAction);
		//getBaseDao().saveOrUpdate(pmsAction);
		pmsActionDaoFacade.saveOrUpdate(pmsAction);
	}
	
	/**
	 * @Description: 根据权限ID删除权限并解除权限与角色的关联关系. 
	 * @param @param actionId    
	 * @return void
	 */
	public void deleteActionById(Long actionId) {
		//pmsActionDao.deleteById(PmsAction.class,actionId);
		//getBaseDao().deleteById(PmsAction.class,actionId);
		pmsActionDaoFacade.deleteById(actionId);
		// 删除权限和角色关联表中的关联关系
		//pmsRoleActionDao.deleteByActionId(actionId);
		//PmsRoleAction pmsRoleAction = new PmsRoleAction();
		//pmsRoleAction.setActionId(actionId);
		//getBaseDao().deleteByModel(pmsRoleAction);
		PmsRoleActionDTO pmsRoleActionDTO = new PmsRoleActionDTO();
		pmsRoleActionDTO.setActionId(actionId);
		pmsRoleActionDaoFacade.deleteByModel(pmsRoleActionDTO);
	}
	
	/**
	 * @Description: 根据角色ID统计有多少权限关联到此角色.
	 * @param @param roleId
	 * @param @return    
	 * @return int
	 */
	public int countActionByRoleId(Long roleId) {
		//List<PmsRoleAction> actionList = pmsRoleActionDao.listByRoleId(roleId);
		//List<PmsRoleAction> actionList = getBaseDao().selectList(getStatement("listRoleActionByRoleId"), roleId);
		List<PmsRoleActionDTO> actionList = pmsActionDaoFacade.listRoleActionByRoleId(roleId);
		if (actionList == null || actionList.isEmpty()) {
			return 0;
		} else {
			return actionList.size();
		}
	}
	
	/**
	 * @Description: 根据角色ID，获取所有的功能权限ID集
	 * @param @param roleId
	 * @param @return    
	 * @return String
	 */
	public String getActionIdsByRoleId(Long roleId) {
		//List<PmsRoleAction> rmList = pmsRoleActionDao.listByRoleId(roleId);
		//List<PmsRoleAction> rmList = getBaseDao().selectList(getStatement("listRoleActionByRoleId"), roleId);
		List<PmsRoleActionDTO> rmList = pmsActionDaoFacade.listRoleActionByRoleId(roleId);
		StringBuffer actionIds = new StringBuffer();
		if (rmList != null && !rmList.isEmpty()) {
			for (PmsRoleActionDTO rm : rmList) {
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
		List<Long> roldIdList = new ArrayList<Long>();
		for(String roleId : roldIdArr){
			roldIdList.add(Long.parseLong(roleId));
		}
		//List<PmsRoleAction> listPmsRoleActions = getBaseDao().selectList(getStatement("listRoleActionByRoleIds"), roldIdArr);
		List<PmsRoleActionDTO> listPmsRoleActions = pmsActionDaoFacade.listRoleActionByRoleIds(roldIdList);
		// 构建StringBuffer
		StringBuffer actionIdsBuf = new StringBuffer("");
		// 拼接字符串
		for (PmsRoleActionDTO pmsRoleAction : listPmsRoleActions) {
			actionIdsBuf.append(pmsRoleAction.getActionId()).append(",");
		}
		String actionIds = actionIdsBuf.toString();
		// 截取字符串
		if (!StringUtils.isEmpty(actionIds)) {
			actionIds = actionIds.substring(0, actionIds.length() - 1); // 去掉最后一个逗号
		}
		return actionIds;
	}

}
