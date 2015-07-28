package com.techstack.pms.dao.facade;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.techstack.pms.dao.dto.PmsRoleUserDTO;
import com.techstack.pms.dao.dto.PmsUserDTO;

public interface PmsUserDaoFacade extends PmsBaseDaoFacade {

	public PmsUserDTO findUserByLoginName(String loginName);

	public List<PmsUserDTO> listUserByRoleId(Long roleId);

	public List<PmsRoleUserDTO> listRoleUserByUserId(Long userId);

	public List<PmsRoleUserDTO> listRoleUserByRoleId(Long roleId);

	public Page<PmsUserDTO> listPage(int pageNum, int pageSize, Map<String, Object> paramMap);
}
