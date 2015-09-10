package com.techstack.pms.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

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
	public List<PmsActionDTO> findActionsByIds(List<Long> ids) {
		/*List<String> idarr = Arrays.asList(ids.split(","));
		List<Long> idList = new ArrayList<Long>();
		for(String id : idarr){
			idList.add(Long.parseLong(id));
		}*/
		return pmsActionDaoFacade.findActionsByIds(ids);
	}

	/**
	 * @Description: 根据ID删除权限信息.
	 * @param @param id    
	 * @return void
	 */
	public void deleteById(Long id) {
		pmsActionDaoFacade.deleteById(id);
	}

	/**
	 * @Description: 根据权限名称查找权限（用于判断权限名是否已存在）
	 * @param @param actionName
	 * @param @return    
	 * @return PmsAction
	 */
	public PmsActionDTO getByActionName(String actionName) {
		return pmsActionDaoFacade.getActionByActionName(actionName);
	}

	/**
	 * @Description: 根据权限查找权限记录（用于判断权限是否已存在）.
	 * @param @param action
	 * @param @return    
	 * @return PmsAction
	 */
	public PmsActionDTO getByAction(String action) {
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
		return pmsActionDaoFacade.getActionByActionNotEqId(action, id);
	}

	/**
	 * @Description:  根据菜单ID查找权限集.
	 * @param @param menuId
	 * @param @return    
	 * @return List<PmsAction>
	 */
	public List<PmsActionDTO> listByMenuId(Long menuId) {
		return pmsActionDaoFacade.listActionByMenuId(menuId);
	}

	/**
	 * @Description: 查询并分页列出权限功能点.
	 * @param @param pageParam
	 * @param @param paramMap
	 * @param @return    
	 * @return PageBean
	 */
	public Page<PmsActionDTO> listPage(int pageNum, int pageSize, Map<String, Object> paramMap) {
		return pmsActionDaoFacade.listPage(pageNum, pageSize, paramMap);
	}

	/**
	 * @Description: 根据ID获取权限点
	 * @param @param id
	 * @param @return    
	 * @return PmsAction
	 */
	public PmsActionDTO getById(Long id) {
		return pmsActionDaoFacade.getById(id);
	}

	/**
	 * @Description: 保存权限功能点
	 * @param @param act    
	 * @return void
	 */
	public void saveAction(PmsActionDTO act) {
		pmsActionDaoFacade.saveOrUpdate(act);
	}

	/**
	 * @Description: 更新权限功能点.
	 * @param @param pmsAction    
	 * @return void
	 */
	public void updateAction(PmsActionDTO pmsAction) {
		pmsActionDaoFacade.saveOrUpdate(pmsAction);
	}
	
	/**
	 * @Description: 根据权限ID删除权限并解除权限与角色的关联关系. 
	 * @param @param actionId    
	 * @return void
	 */
	public void deleteActionById(Long actionId) {
		PmsRoleActionDTO pmsRoleActionDTO = new PmsRoleActionDTO();
		pmsRoleActionDTO.setActionId(actionId);
		pmsRoleActionDaoFacade.deleteByModel(pmsRoleActionDTO);
		
		pmsActionDaoFacade.deleteById(actionId);
	}
	
	/**
	 * @Description: 根据角色ID，获取所有的功能权限ID集
	 * @param @param roleId
	 * @param @return    
	 * @return String
	 */
	public List<Long> getActionIdsByRoleId(Long roleId) {
		List<PmsRoleActionDTO> rmList = pmsActionDaoFacade.listRoleActionByRoleId(roleId);
		List<Long> actionIds = new ArrayList<Long>();
		if (rmList != null && !rmList.isEmpty()) {
			for (PmsRoleActionDTO rm : rmList) {
				actionIds.add(rm.getActionId());
			}
		}
		return actionIds;
		/*StringBuffer actionIds = new StringBuffer();
		if (rmList != null && !rmList.isEmpty()) {
			for (PmsRoleActionDTO rm : rmList) {
				actionIds.append(rm.getActionId()).append(",");
			}
		}
		return actionIds.toString();*/
	}
	
	/**
	 * @Description: 根据角色ID集得到所有权限ID集
	 * @param @param roleIds
	 * @param @return    
	 * @return String
	 */
	public List<Long> getActionIdsByRoleIds(List<Long> roleIds) {
		// 得到角色－权限表中roleiId在ids中的所有关联对象
		/*List<String> roldIdArr = Arrays.asList(roleIds.split(","));
		List<Long> roldIdList = new ArrayList<Long>();
		for(String roleId : roldIdArr){
			roldIdList.add(Long.parseLong(roleId));
		}*/
		List<PmsRoleActionDTO> listPmsRoleActions = pmsActionDaoFacade.listRoleActionByRoleIds(roleIds);
		List<Long> actionIds = new ArrayList<Long>();
		for (PmsRoleActionDTO pmsRoleAction : listPmsRoleActions) {
			actionIds.add(pmsRoleAction.getActionId());
		}
		return actionIds;
		/*StringBuffer actionIdsBuf = new StringBuffer("");
		for (PmsRoleActionDTO pmsRoleAction : listPmsRoleActions) {
			actionIdsBuf.append(pmsRoleAction.getActionId()).append(",");
		}
		String actionIds = actionIdsBuf.toString();
		if (!StringUtils.isEmpty(actionIds)) {
			actionIds = actionIds.substring(0, actionIds.length() - 1); // 去掉最后一个逗号
		}
		return actionIds;*/
	}

}
