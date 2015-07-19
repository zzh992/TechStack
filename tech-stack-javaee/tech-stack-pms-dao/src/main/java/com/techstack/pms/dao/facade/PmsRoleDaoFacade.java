package com.techstack.pms.dao.facade;

import java.util.List;

import com.techstack.pms.dao.dto.PmsRoleDTO;
import com.techstack.pms.dao.dto.PmsRoleUserDTO;

public interface PmsRoleDaoFacade extends PmsBaseDaoFacade {

	public List<PmsRoleDTO> listAllRole();

	public PmsRoleDTO getRoleByRoleName(String roleName);

	public PmsRoleDTO findRoleByRoleNameNotEqId(String roleName, Long id);

	public List<PmsRoleDTO> listRoleByActionId(Long actionId);

	public List<PmsRoleUserDTO> listRoleUserByUserId(Long userId);
}
