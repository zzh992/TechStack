package com.techstack.pms.dao.facade;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.dto.PmsRoleActionDTO;

public interface PmsActionDaoFacade extends PmsBaseDaoFacade{

	public List<PmsActionDTO> findActionsByIds(List<Long> ids);

	public PmsActionDTO getActionByAction(String action);

	public PmsActionDTO getActionByActionName(String actionName);

	public PmsActionDTO getActionByActionNameNotEqId(String actionName, Long id);

	public PmsActionDTO getActionByActionNotEqId(String action, Long id);

	public List<PmsActionDTO> listActionByMenuId(Long menuId);

	public List<PmsRoleActionDTO> listRoleActionByRoleId(Long roleId);

	public List<PmsRoleActionDTO> listRoleActionByRoleIds(List<Long> roleIds);

	public Page<PmsActionDTO> listPage(int pageNum, int pageSize, Map<String, Object> paramMap);
}
