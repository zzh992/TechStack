package com.techstack.pms.dao.jpa.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techstack.component.mapper.BeanMapper;
import com.techstack.pms.dao.dto.PmsRoleActionDTO;
import com.techstack.pms.dao.facade.PmsRoleActionDaoFacade;
import com.techstack.pms.dao.jpa.entity.Action;
import com.techstack.pms.dao.jpa.entity.Role;
import com.techstack.pms.dao.jpa.repository.ActionDao;
import com.techstack.pms.dao.jpa.repository.RoleDao;

@Component("pmsRoleActionDaoFacade")
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
		if(role.getActions() == null){
			List<Action> actions = new ArrayList<Action>();
			actions.add(action);
			role.setActions(actions);
			roleDao.save(role);
		}else if(!role.getActions().contains(action)){
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
		if(pmsRoleActionDTO.getRoleId()!=null){
			Role role = roleDao.findOne(pmsRoleActionDTO.getRoleId());
			if(pmsRoleActionDTO.getActionId() == null){
				role.setActions(null);
			}else{
				Action action = actionDao.findOne(pmsRoleActionDTO.getActionId());
				if(role.getActions().contains(action)){
					role.getActions().remove(action);
				}
			}
			roleDao.save(role);
		}else if(pmsRoleActionDTO.getActionId() != null){
			Action action = actionDao.findOne(pmsRoleActionDTO.getActionId());
			for(Role role : action.getRoles()){
				if(role.getActions().contains(action)){
					role.getActions().remove(action);
				}
				roleDao.save(role);
			}
		}
	}

}
