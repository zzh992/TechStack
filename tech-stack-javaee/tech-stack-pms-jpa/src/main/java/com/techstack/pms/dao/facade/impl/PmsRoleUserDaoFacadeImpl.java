package com.techstack.pms.dao.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.techstack.component.mapper.BeanMapper;
import com.techstack.pms.dao.dto.PmsRoleUserDTO;
import com.techstack.pms.dao.facade.PmsRoleUserDaoFacade;
import com.techstack.pms.dao.jpa.entity.Role;
import com.techstack.pms.dao.jpa.entity.User;
import com.techstack.pms.dao.jpa.repository.RoleDao;
import com.techstack.pms.dao.jpa.repository.UserDao;

public class PmsRoleUserDaoFacadeImpl implements PmsRoleUserDaoFacade {
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserDao userDao;

	@Override
	public <Model> Model saveOrUpdate(Model model) {
		PmsRoleUserDTO pmsRoleUserDTO = BeanMapper.map(model, PmsRoleUserDTO.class);
		Role role = roleDao.findOne(pmsRoleUserDTO.getRoleId());
		User user = userDao.findOne(pmsRoleUserDTO.getUserId());
		if(!role.getUsers().contains(user)){
			role.getUsers().add(user);
			roleDao.save(role);
		}
		return (Model) pmsRoleUserDTO;
	}

	@Override
	public <Model> Model getById(Long id) {
		//PmsRoleUser pmsRoleUser = baseDao.getById(PmsRoleUser.class, id);
		//return (Model) BeanMapper.map(pmsRoleUser,PmsRoleUserDTO.class);
		return null;
	}

	@Override
	public void deleteById(Long id) {
		//baseDao.deleteById(PmsRoleUser.class, id);
	}

	@Override
	public <Model> void deleteByModel(Model model) {
		//PmsRoleUser pmsRoleUser = BeanMapper.map(model, PmsRoleUser.class);
		//baseDao.deleteByModel(pmsRoleUser);
		PmsRoleUserDTO pmsRoleUserDTO = BeanMapper.map(model, PmsRoleUserDTO.class);
		Role role = roleDao.findOne(pmsRoleUserDTO.getRoleId());
		User user = userDao.findOne(pmsRoleUserDTO.getUserId());
		if(role.getUsers().contains(user)){
			role.getUsers().remove(user);
			roleDao.save(role);
		}
	}

}
