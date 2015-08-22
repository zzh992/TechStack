package com.techstack.pms.dao.jpa.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techstack.component.mapper.BeanMapper;
import com.techstack.pms.dao.dto.PmsRoleMenuDTO;
import com.techstack.pms.dao.facade.PmsRoleMenuDaoFacade;
import com.techstack.pms.dao.jpa.entity.Menu;
import com.techstack.pms.dao.jpa.entity.Role;
import com.techstack.pms.dao.jpa.repository.MenuDao;
import com.techstack.pms.dao.jpa.repository.RoleDao;

@Component("pmsRoleMenuDaoFacade")
public class PmsRoleMenuDaoFacadeimpl implements PmsRoleMenuDaoFacade {
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;

	@Override
	public <Model> Model saveOrUpdate(Model model) {
		PmsRoleMenuDTO pmsRoleMenuDTO = BeanMapper.map(model, PmsRoleMenuDTO.class);
		Role role = roleDao.findOne(pmsRoleMenuDTO.getRoleId());
		Menu menu = menuDao.findOne(pmsRoleMenuDTO.getMenuId());
		if(role.getMenus() == null){
			List<Menu> menus = new ArrayList<Menu>();
			menus.add(menu);
			role.setMenus(menus);
			roleDao.save(role);
		}else if(!role.getMenus().contains(menu)){
			role.getMenus().add(menu);
			roleDao.save(role);
		}
		return (Model) pmsRoleMenuDTO;
	}

	@Override
	public <Model> Model getById(Long id) {
		//PmsRoleMenu pmsRoleMenu = baseDao.getById(PmsRoleMenu.class, id);
		//return (Model) BeanMapper.map(pmsRoleMenu,PmsRoleMenuDTO.class);
		return null;
	}

	@Override
	public void deleteById(Long id) {
		//baseDao.deleteById(PmsRoleMenu.class, id);
	}

	@Override
	public <Model> void deleteByModel(Model model) {
		//PmsRoleMenu pmsRoleMenu = BeanMapper.map(model, PmsRoleMenu.class);
		//baseDao.deleteByModel(pmsRoleMenu);
		PmsRoleMenuDTO pmsRoleMenuDTO = BeanMapper.map(model, PmsRoleMenuDTO.class);
		Role role = roleDao.findOne(pmsRoleMenuDTO.getRoleId());
		if(pmsRoleMenuDTO.getMenuId() == null){
			role.setMenus(null);
		}else{
			Menu menu = menuDao.findOne(pmsRoleMenuDTO.getMenuId());
			if(role.getMenus().contains(menu)){
				role.getMenus().remove(menu);
			}
		}
		roleDao.save(role);
	}

}
