package com.techstack.pms.dao.jpa.mapper;

import com.techstack.pms.dao.dto.PmsMenuDTO;
import com.techstack.pms.dao.jpa.entity.Menu;

public class PmsMenuDTOMapper {

	public static Menu toMenu(PmsMenuDTO pmsMenuDTO) {
		Menu menu = null;
		if (pmsMenuDTO != null) {
			menu = new Menu();
			menu.setId(pmsMenuDTO.getId());
			menu.setVersion(pmsMenuDTO.getVersion());
			menu.setCreateTime(pmsMenuDTO.getCreateTime());
			menu.setIsLeaf(pmsMenuDTO.getIsLeaf());
			menu.setLevel(pmsMenuDTO.getLevel());
			menu.setName(pmsMenuDTO.getName());
			menu.setNumber(pmsMenuDTO.getNumber());
			if(pmsMenuDTO.getParentId() !=null){
				Menu parentMenu = new Menu();
				parentMenu.setId(pmsMenuDTO.getParentId());
				menu.setParentMenu(parentMenu);
			}
			menu.setTargetName(pmsMenuDTO.getTargetName());
			menu.setUrl(pmsMenuDTO.getUrl());
		}
		return menu;
	}

	public static PmsMenuDTO toPmsMenuDTO(Menu menu) {
		PmsMenuDTO pmsMenuDTO = null;
		if (menu != null) {
			pmsMenuDTO = new PmsMenuDTO();
			pmsMenuDTO.setId(menu.getId());
			pmsMenuDTO.setVersion(menu.getVersion());
			pmsMenuDTO.setCreateTime(menu.getCreateTime());
			pmsMenuDTO.setIsLeaf(menu.getIsLeaf());
			pmsMenuDTO.setLevel(menu.getLevel());
			pmsMenuDTO.setName(menu.getName());
			pmsMenuDTO.setNumber(menu.getNumber());
			if(menu.getParentMenu() != null){
				pmsMenuDTO.setParentId(menu.getParentMenu().getId());
			}else{
				pmsMenuDTO.setParentId(0L);	//TODO: 目前根菜单的ParentId为0,但是jpa没法级联
			}
			pmsMenuDTO.setTargetName(menu.getTargetName());
			pmsMenuDTO.setUrl(menu.getUrl());
		}
		return pmsMenuDTO;
	}
}
