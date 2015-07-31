package com.techstack.pms.dao.jpa.mapper;

import com.techstack.pms.dao.dto.PmsRoleDTO;
import com.techstack.pms.dao.jpa.entity.Role;

public class PmsRoleDTOMapper {

	public static Role toRole(PmsRoleDTO pmsRoleDTO) {
		Role role = null;
		if (pmsRoleDTO != null) {
			role = new Role();
			role.setId(pmsRoleDTO.getId());
			role.setVersion(pmsRoleDTO.getVersion());
			role.setCreateTime(pmsRoleDTO.getCreateTime());
			role.setRemark(pmsRoleDTO.getRemark());
			role.setRoleName(pmsRoleDTO.getRoleName());
			role.setRoleType(pmsRoleDTO.getRoleType());
		}
		return role;
	}

	public static PmsRoleDTO toPmsRoleDTO(Role role) {
		PmsRoleDTO pmsRoleDTO = null;
		if (role != null) {
			pmsRoleDTO = new PmsRoleDTO();
			pmsRoleDTO.setId(role.getId());
			pmsRoleDTO.setVersion(role.getVersion());
			pmsRoleDTO.setCreateTime(role.getCreateTime());
			pmsRoleDTO.setRemark(role.getRemark());
			pmsRoleDTO.setRoleName(role.getRoleName());
			pmsRoleDTO.setRoleType(role.getRoleType());
		}
		return pmsRoleDTO;
	}
}
