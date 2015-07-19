package com.techstack.pms.dao.facade;

import java.util.List;

import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.dto.PmsMenuDTO;
import com.techstack.pms.dao.dto.PmsRoleMenuDTO;

public interface PmsMenuDaoFacade extends PmsBaseDaoFacade{

	public List<PmsMenuDTO> listMenuByParent(Long parentId);

	public List<PmsMenuDTO> listMenuByRoleIds(List<Long> roleIds);

	public List<PmsRoleMenuDTO> listRoleMenuByRoleId(Long roleId);

	public List<PmsActionDTO> listAllActionByMenuId(Long menuId);

	public List<PmsMenuDTO> listMenuByParentId(Long parentId);

	public List<PmsMenuDTO> listMenuBy(Integer isLeaf, String name,
			Long parentId);
}
