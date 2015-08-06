package com.techstack.pms.dao.mybatis.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techstack.component.mapper.BeanMapper;
import com.techstack.component.mybatis.dao.BaseDao;
import com.techstack.pms.dao.dto.PmsRoleUserDTO;
import com.techstack.pms.dao.facade.PmsRoleUserDaoFacade;
import com.techstack.pms.dao.mybatis.entity.PmsRoleUser;

@Component("pmsRoleUserDaoFacade")
public class PmsRoleUserDaoFacadeImpl implements PmsRoleUserDaoFacade {
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public <Model> Model saveOrUpdate(Model model) {
		PmsRoleUser pmsRoleUser = BeanMapper.map(model, PmsRoleUser.class);
		baseDao.saveOrUpdate(pmsRoleUser);
		return (Model) BeanMapper.map(pmsRoleUser,PmsRoleUserDTO.class);
	}

	@Override
	public <Model> Model getById(Long id) {
		PmsRoleUser pmsRoleUser = baseDao.getById(PmsRoleUser.class, id);
		return (Model) BeanMapper.map(pmsRoleUser,PmsRoleUserDTO.class);
	}

	@Override
	public void deleteById(Long id) {
		baseDao.deleteById(PmsRoleUser.class, id);
	}

	@Override
	public <Model> void deleteByModel(Model model) {
		PmsRoleUser pmsRoleUser = BeanMapper.map(model, PmsRoleUser.class);
		baseDao.deleteByModel(pmsRoleUser);
	}

}
