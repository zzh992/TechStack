package com.techstack.pms.dao.jpa.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techstack.component.mapper.BeanMapper;
import com.techstack.pms.dao.dto.PmsRoleUserDTO;
import com.techstack.pms.dao.facade.PmsRoleUserDaoFacade;
import com.techstack.pms.dao.jpa.entity.Role;
import com.techstack.pms.dao.jpa.entity.User;
import com.techstack.pms.dao.jpa.repository.RoleDao;
import com.techstack.pms.dao.jpa.repository.UserDao;

@Component("pmsRoleUserDaoFacade")
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
		if(!user.getRoles().contains(role)){
			user.getRoles().add(role);
			userDao.save(user);
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
		if(pmsRoleUserDTO.getUserId() != null){
			User user = userDao.findOne(pmsRoleUserDTO.getUserId());
			if(pmsRoleUserDTO.getRoleId()!=null){
				Role role = roleDao.findOne(pmsRoleUserDTO.getRoleId());
				if(user.getRoles().contains(role)){
					user.getRoles().remove(role);
					
				}
			}else{
				user.setRoles(null);
			}
			userDao.save(user);
		}else if(pmsRoleUserDTO.getRoleId() != null){
			Role role = roleDao.findOne(pmsRoleUserDTO.getRoleId());
			for(User user : role.getUsers()){
				if(user.getRoles().contains(role)){
					user.getRoles().remove(role);
				}
				userDao.save(user);
			}
		}
	}

}
