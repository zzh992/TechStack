package com.techstack.pms.dao.jpa.mapper;

import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.jpa.entity.Action;
import com.techstack.pms.dao.jpa.entity.Menu;

public class PmsActionDTOMapper {

	public static Action toPmsAction(PmsActionDTO pmsActionDTO) {
		Action action = null;
		if (pmsActionDTO != null) {
			action = new Action();
			action.setId(pmsActionDTO.getId());
			action.setVersion(pmsActionDTO.getVersion());
			action.setCreateTime(pmsActionDTO.getCreateTime());
			action.setAction(pmsActionDTO.getAction());
			action.setActionName(pmsActionDTO.getActionName());
			action.setMenuName(pmsActionDTO.getMenuName());
			action.setRemark(pmsActionDTO.getRemark());
			if(pmsActionDTO.getMenuId() != null){
				Menu relevantMenu = new Menu();
				relevantMenu.setId(pmsActionDTO.getMenuId());
				action.setRelevantMenu(relevantMenu);
			}
		}
		return action;
	}

	public static PmsActionDTO toPmsActionDTO(Action action) {
		PmsActionDTO pmsActionDTO = null;
		if (action != null) {
			pmsActionDTO = new PmsActionDTO();
			pmsActionDTO.setId(action.getId());
			pmsActionDTO.setVersion(action.getVersion());
			pmsActionDTO.setCreateTime(action.getCreateTime());
			pmsActionDTO.setAction(action.getAction());
			pmsActionDTO.setActionName(action.getActionName());
			pmsActionDTO.setMenuName(action.getMenuName());
			pmsActionDTO.setRemark(action.getRemark());
			if(action.getRelevantMenu()!=null){
				pmsActionDTO.setMenuId(action.getRelevantMenu().getId());
			}
		}
		return pmsActionDTO;
	}
}
