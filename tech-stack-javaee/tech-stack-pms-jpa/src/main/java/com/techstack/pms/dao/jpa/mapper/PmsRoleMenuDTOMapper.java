package com.techstack.pms.dao.jpa.mapper;

import com.techstack.pms.dao.dto.PmsRoleMenuDTO;
import com.techstack.pms.dao.jpa.entity.Menu;
import com.techstack.pms.dao.jpa.entity.Role;

public class PmsRoleMenuDTOMapper {

	public static PmsRoleMenuDTO toPmsRoleMenuDTO(Role role, Menu menu) {
		PmsRoleMenuDTO pmsRoleMenuDTO = null;
		if (role!=null && menu != null) {
			pmsRoleMenuDTO = new PmsRoleMenuDTO();
			pmsRoleMenuDTO.setMenuId(menu.getId());
			pmsRoleMenuDTO.setRoleId(role.getId());
		}
		return pmsRoleMenuDTO;
	}
}
