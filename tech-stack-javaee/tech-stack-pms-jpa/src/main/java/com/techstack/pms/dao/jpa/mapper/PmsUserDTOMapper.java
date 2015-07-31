package com.techstack.pms.dao.jpa.mapper;

import com.techstack.pms.dao.dto.PmsUserDTO;
import com.techstack.pms.dao.jpa.entity.User;

public class PmsUserDTOMapper {

	public static User toUser(PmsUserDTO pmsUserDTO) {
		User user = null;
		if (pmsUserDTO != null) {
			user = new User();
			user.setId(pmsUserDTO.getId());
			user.setVersion(pmsUserDTO.getVersion());
			user.setCreateTime(pmsUserDTO.getCreateTime());
			user.setLoginName(pmsUserDTO.getLoginName());
			user.setLoginPwd(pmsUserDTO.getLoginPwd());
			user.setRemark(pmsUserDTO.getRemark());
			user.setType(pmsUserDTO.getType());
		}
		return user;
	}

	public static PmsUserDTO toPmsUserDTO(User user) {
		PmsUserDTO pmsUserDTO = null;
		if (user != null) {
			pmsUserDTO = new PmsUserDTO();
			pmsUserDTO.setId(user.getId());
			pmsUserDTO.setVersion(user.getVersion());
			pmsUserDTO.setCreateTime(user.getCreateTime());
			pmsUserDTO.setLoginName(user.getLoginName());
			pmsUserDTO.setLoginPwd(user.getLoginPwd());
			pmsUserDTO.setRemark(user.getRemark());
			pmsUserDTO.setType(user.getType());
		}
		return pmsUserDTO;
	}
}
