package com.techstack.pms.dao.mybatis.facade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.techstack.component.mapper.BeanMapper;
import com.techstack.component.mybatis.dao.BaseDao;
import com.techstack.component.mybatis.page.PageParam;
import com.techstack.pms.dao.dto.PmsRoleUserDTO;
import com.techstack.pms.dao.dto.PmsUserDTO;
import com.techstack.pms.dao.facade.PmsUserDaoFacade;
import com.techstack.pms.dao.mybatis.entity.PmsRoleUser;
import com.techstack.pms.dao.mybatis.entity.PmsUser;

@Component("pmsUserDaoFacade")
public class PmsUserDaoFacadeImpl implements PmsUserDaoFacade {
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public <Model> Model saveOrUpdate(Model model) {
		PmsUser pmsUser = BeanMapper.map(model, PmsUser.class);
		baseDao.saveOrUpdate(pmsUser);
		return (Model) BeanMapper.map(pmsUser,PmsUserDTO.class);
	}

	@Override
	public <Model> Model getById(Long id) {
		PmsUser pmsUser = baseDao.getById(PmsUser.class, id);
		return (Model) BeanMapper.map(pmsUser,PmsUserDTO.class);
	}

	@Override
	public void deleteById(Long id) {
		baseDao.deleteById(PmsUser.class, id);
	}

	@Override
	public <Model> void deleteByModel(Model model) {
		PmsUser pmsUser = BeanMapper.map(model, PmsUser.class);
		baseDao.deleteByModel(pmsUser);
	}

	@Override
	public PmsUserDTO findUserByLoginName(String loginName) {
		PmsUser pmsUser = baseDao.selectOne(getStatement("findUserByLoginName"), loginName);
		return BeanMapper.map(pmsUser,PmsUserDTO.class);
	}

	@Override
	public List<PmsUserDTO> listUserByRoleId(Long roleId) {
		List<PmsUser> pmsUserList = baseDao.selectList(getStatement("listUserByRoleId"), roleId);
		return BeanMapper.mapList(pmsUserList, PmsUserDTO.class);
	}

	@Override
	public List<PmsRoleUserDTO> listRoleUserByUserId(Long userId) {
		List<PmsRoleUser> pmsRoleUserList = baseDao.selectList(getStatement("listRoleUserByUserId"), userId);
		return BeanMapper.mapList(pmsRoleUserList, PmsRoleUserDTO.class);
	}

	@Override
	public List<PmsRoleUserDTO> listRoleUserByRoleId(Long roleId) {
		List<PmsRoleUser> pmsRoleUserList = baseDao.selectList(getStatement("listRoleUserByRoleId"), roleId);
		return BeanMapper.mapList(pmsRoleUserList, PmsRoleUserDTO.class);
	}
	
	public String getStatement(String sqlId) {
		String name = this.getClass().getName();
		StringBuffer sb = new StringBuffer();
		sb.append(name).append(".").append(sqlId);
		String statement = sb.toString();

		return statement;
	}

	@Override
	public Page<PmsUserDTO> listPage(int pageNum, int pageSize, Map<String, Object> paramMap) {
		PageParam pageParam = new PageParam(pageNum, pageSize);
		Page pageBean = baseDao.listPage(PmsUser.class, pageParam, paramMap);
		Page<PmsUserDTO> page = new PageImpl<PmsUserDTO>(BeanMapper.mapList(pageBean.getContent(), PmsUserDTO.class), new PageRequest(pageNum, pageSize), pageBean.getTotalElements());
		return page;
	}

}
