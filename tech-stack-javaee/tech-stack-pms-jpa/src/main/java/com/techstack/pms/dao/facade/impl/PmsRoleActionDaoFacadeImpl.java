package com.techstack.pms.dao.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.techstack.component.mapper.BeanMapper;
import com.techstack.pms.dao.dto.PmsRoleActionDTO;
import com.techstack.pms.dao.facade.PmsRoleActionDaoFacade;
import com.techstack.pms.dao.jpa.entity.Action;
import com.techstack.pms.dao.jpa.entity.Role;
import com.techstack.pms.dao.jpa.repository.ActionDao;
import com.techstack.pms.dao.jpa.repository.RoleDao;

public class PmsRoleActionDaoFacadeImpl implements PmsRoleActionDaoFacade {
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private ActionDao actionDao;

	@Override
	public <Model> Model saveOrUpdate(Model model) {
		PmsRoleActionDTO pmsRoleActionDTO = BeanMapper.map(model, PmsRoleActionDTO.class);
		Role role = roleDao.findOne(pmsRoleActionDTO.getRoleId());
		Action action = actionDao.findOne(pmsRoleActionDTO.getActionId());
		if(!role.getActions().contains(action)){
			role.getActions().add(action);
			roleDao.save(role);
		}
		return (Model) pmsRoleActionDTO;
	}

	@Override
	public <Model> Model getById(Long id) {
		//PmsRoleActionDTO pmsRoleActionDTO = baseDao.getById(PmsRoleAction.class, id);
		//return (Model) BeanMapper.map(pmsRoleAction,PmsRoleActionDTO.class);
		return null;
	}

	@Override
	public void deleteById(Long id) {
		//baseDao.deleteById(PmsRoleAction.class, id);
	}

	@Override
	public <Model> void deleteByModel(Model model) {
		PmsRoleActionDTO pmsRoleActionDTO = BeanMapper.map(model, PmsRoleActionDTO.class);
		Role role = roleDao.findOne(pmsRoleActionDTO.getRoleId());
		Action action = actionDao.findOne(pmsRoleActionDTO.getActionId());
		if(role.getActions().contains(action)){
			role.getActions().remove(action);
			roleDao.save(role);
		}
	}

}
