package com.techstack.pms.dao.jpa.mapper;

import com.techstack.pms.dao.dto.PmsRoleActionDTO;
import com.techstack.pms.dao.jpa.entity.Action;
import com.techstack.pms.dao.jpa.entity.Role;

public class PmsRoleActionDTOMapper {

	public static PmsRoleActionDTO toPmsRoleActionDTO(Role role, Action action) {
		PmsRoleActionDTO pmsRoleActionDTO = null;
		if (role != null && action != null) {
			pmsRoleActionDTO = new PmsRoleActionDTO();
			pmsRoleActionDTO.setRoleId(role.getId());
			pmsRoleActionDTO.setActionId(action.getId());
		}
		return pmsRoleActionDTO;
	}
}
