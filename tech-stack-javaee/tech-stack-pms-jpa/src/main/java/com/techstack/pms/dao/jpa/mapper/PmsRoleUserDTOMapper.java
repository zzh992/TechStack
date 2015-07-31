package com.techstack.pms.dao.jpa.mapper;

import com.techstack.pms.dao.dto.PmsRoleUserDTO;
import com.techstack.pms.dao.jpa.entity.Role;
import com.techstack.pms.dao.jpa.entity.User;

public class PmsRoleUserDTOMapper {

	public static PmsRoleUserDTO toPmsRoleUserDTO(Role role, User user) {
		PmsRoleUserDTO pmsRoleUserDTO = null;
		if (role != null && user != null) {
			pmsRoleUserDTO = new PmsRoleUserDTO();
			pmsRoleUserDTO.setRoleId(role.getId());
			pmsRoleUserDTO.setUserId(user.getId());
		}
		return pmsRoleUserDTO;
	}
}
