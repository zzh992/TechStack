package com.techstack.pms.dao.mybatis.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techstack.component.mapper.BeanMapper;
import com.techstack.component.mybatis.dao.BaseDao;
import com.techstack.pms.dao.dto.PmsRoleMenuDTO;
import com.techstack.pms.dao.facade.PmsRoleMenuDaoFacade;
import com.techstack.pms.dao.mybatis.entity.PmsRoleMenu;

@Component("pmsRoleMenuDaoFacade")
public class PmsRoleMenuDaoFacadeimpl implements PmsRoleMenuDaoFacade {
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public <Model> Model saveOrUpdate(Model model) {
		PmsRoleMenu pmsRoleMenu = BeanMapper.map(model, PmsRoleMenu.class);
		baseDao.saveOrUpdate(pmsRoleMenu);
		return (Model) BeanMapper.map(pmsRoleMenu,PmsRoleMenuDTO.class);
	}

	@Override
	public <Model> Model getById(Long id) {
		PmsRoleMenu pmsRoleMenu = baseDao.getById(PmsRoleMenu.class, id);
		return (Model) BeanMapper.map(pmsRoleMenu,PmsRoleMenuDTO.class);
	}

	@Override
	public void deleteById(Long id) {
		baseDao.deleteById(PmsRoleMenu.class, id);
	}

	@Override
	public <Model> void deleteByModel(Model model) {
		PmsRoleMenu pmsRoleMenu = BeanMapper.map(model, PmsRoleMenu.class);
		baseDao.deleteByModel(pmsRoleMenu);
	}

}
