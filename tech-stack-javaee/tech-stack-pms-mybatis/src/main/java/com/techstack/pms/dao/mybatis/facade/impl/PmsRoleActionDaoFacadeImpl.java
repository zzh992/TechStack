package com.techstack.pms.dao.mybatis.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techstack.component.mapper.BeanMapper;
import com.techstack.component.mybatis.dao.BaseDao;
import com.techstack.pms.dao.dto.PmsMenuDTO;
import com.techstack.pms.dao.dto.PmsRoleActionDTO;
import com.techstack.pms.dao.facade.PmsRoleActionDaoFacade;
import com.techstack.pms.dao.mybatis.entity.PmsMenu;
import com.techstack.pms.dao.mybatis.entity.PmsRoleAction;

@Component("pmsRoleActionDaoFacade")
public class PmsRoleActionDaoFacadeImpl implements PmsRoleActionDaoFacade {
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public <Model> Model saveOrUpdate(Model model) {
		PmsRoleAction pmsRoleAction = BeanMapper.map(model, PmsRoleAction.class);
		baseDao.saveOrUpdate(pmsRoleAction);
		return (Model) BeanMapper.map(pmsRoleAction,PmsRoleActionDTO.class);
	}

	@Override
	public <Model> Model getById(Long id) {
		PmsRoleAction pmsRoleAction = baseDao.getById(PmsRoleAction.class, id);
		return (Model) BeanMapper.map(pmsRoleAction,PmsRoleActionDTO.class);
	}

	@Override
	public void deleteById(Long id) {
		baseDao.deleteById(PmsRoleAction.class, id);
	}

	@Override
	public <Model> void deleteByModel(Model model) {
		PmsRoleAction pmsRoleAction = BeanMapper.map(model, PmsRoleAction.class);
		baseDao.deleteByModel(pmsRoleAction);
	}

}
