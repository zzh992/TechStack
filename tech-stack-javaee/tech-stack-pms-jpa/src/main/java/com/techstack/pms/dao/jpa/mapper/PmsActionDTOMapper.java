package com.techstack.pms.dao.jpa.mapper;

import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.jpa.entity.Action;
import com.techstack.pms.dao.jpa.entity.Menu;

public class PmsActionDTOMapper {
	
	public static Action toPmsAction(PmsActionDTO pmsActionDTO){
		Action action = new Action();
		action.setId(pmsActionDTO.getId());
		action.setVersion(pmsActionDTO.getVersion());
		action.setCreateTime(pmsActionDTO.getCreateTime());
		action.setAction(pmsActionDTO.getAction());
		action.setActionName(pmsActionDTO.getActionName());
		action.setMenuName(pmsActionDTO.getMenuName());
		action.setRemark(pmsActionDTO.getRemark());
		Menu relevantMenu = new Menu();
		relevantMenu.setId(pmsActionDTO.getMenuId());
		action.setRelevantMenu(relevantMenu);
		return action;
	}
	
	public static PmsActionDTO toPmsActionDTO(Action action){
		PmsActionDTO pmsActionDTO = new PmsActionDTO();
		pmsActionDTO.setId(action.getId());
		pmsActionDTO.setVersion(action.getVersion());
		pmsActionDTO.setCreateTime(action.getCreateTime());
		pmsActionDTO.setAction(action.getAction());
		pmsActionDTO.setActionName(action.getActionName());
		pmsActionDTO.setMenuName(action.getMenuName());
		pmsActionDTO.setRemark(action.getRemark());
		pmsActionDTO.setMenuId(action.getRelevantMenu().getId());
		return pmsActionDTO;
	}
}
