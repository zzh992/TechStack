package com.techstack.pms.dao.facade;

import java.util.List;

import com.techstack.pms.dao.dto.PmsRoleUserDTO;
import com.techstack.pms.dao.dto.PmsUserDTO;

public interface PmsUserDaoFacade extends PmsBaseDaoFacade {

	public PmsUserDTO findUserByLoginName(String loginName);

	public List<PmsUserDTO> listUserByRoleId(Long roleId);

	public List<PmsRoleUserDTO> listRoleUserByUserId(Long userId);

	public List<PmsRoleUserDTO> listRoleUserByRoleId(Long roleId);
}
