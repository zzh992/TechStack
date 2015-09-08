package com.techstack.pms.dao.mybatis.facade.impl;

import java.util.HashMap;
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
import com.techstack.pms.dao.dto.PmsRoleDTO;
import com.techstack.pms.dao.dto.PmsRoleUserDTO;
import com.techstack.pms.dao.facade.PmsRoleDaoFacade;
import com.techstack.pms.dao.mybatis.entity.PmsRole;
import com.techstack.pms.dao.mybatis.entity.PmsRoleUser;

@Component("pmsRoleDaoFacade")
public class PmsRoleDaoFacadeImpl implements PmsRoleDaoFacade {
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public <Model> Model saveOrUpdate(Model model) {
		PmsRole pmsRole = BeanMapper.map(model, PmsRole.class);
		baseDao.saveOrUpdate(pmsRole);
		return (Model) BeanMapper.map(pmsRole,PmsRoleDTO.class);
	}

	@Override
	public <Model> Model getById(Long id) {
		PmsRole pmsRole = baseDao.getById(PmsRole.class, id);
		return (Model) BeanMapper.map(pmsRole,PmsRoleDTO.class);
	}

	@Override
	public void deleteById(Long id) {
		baseDao.deleteById(PmsRole.class, id);
	}

	@Override
	public <Model> void deleteByModel(Model model) {
		PmsRole pmsRole = BeanMapper.map(model, PmsRole.class);
		baseDao.deleteByModel(pmsRole);
	}

	@Override
	public List<PmsRoleDTO> listAllRole() {
		List<PmsRole> pmsRoleList = baseDao.selectList(getStatement("listAllRole"), new HashMap<String,Object>());
		return BeanMapper.mapList(pmsRoleList, PmsRoleDTO.class);
	}

	@Override
	public PmsRoleDTO getRoleByRoleName(String roleName) {
		PmsRole pmsRole = baseDao.selectOne(getStatement("getRoleByRoleName"), roleName);
		return BeanMapper.map(pmsRole, PmsRoleDTO.class);
	}

	@Override
	public PmsRoleDTO findRoleByRoleNameNotEqId(String roleName, Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("roleName", roleName);
		PmsRole pmsRole = baseDao.selectOne(getStatement("findRoleByRoleNameNotEqId"), param);
		return BeanMapper.map(pmsRole, PmsRoleDTO.class);
	}

	@Override
	public List<PmsRoleDTO> listRoleByActionId(Long actionId) {
		List<PmsRole> pmsRoleList = baseDao.selectList(getStatement("listRoleByActionId"), actionId);
		return BeanMapper.mapList(pmsRoleList, PmsRoleDTO.class);
	}

	@Override
	public List<PmsRoleUserDTO> listRoleUserByUserId(Long userId) {
		List<PmsRoleUser> rpList = baseDao.selectList(getStatement("listRoleUserByUserId"), userId);
		return BeanMapper.mapList(rpList, PmsRoleUserDTO.class);
	}
	
	public String getStatement(String sqlId) {
		String name = this.getClass().getName();
		StringBuffer sb = new StringBuffer();
		sb.append(name).append(".").append(sqlId);
		String statement = sb.toString();

		return statement;
	}

	@Override
	public Page<PmsRoleDTO> listPage(int pageNum, int pageSize, Map<String, Object> paramMap) {
		PageParam pageParam = new PageParam(pageNum, pageSize);
		Page pageBean = baseDao.listPage(PmsRole.class, pageParam, paramMap);
		Page<PmsRoleDTO> page = new PageImpl<PmsRoleDTO>(BeanMapper.mapList(pageBean.getContent(), PmsRoleDTO.class), new PageRequest(pageNum, pageSize), pageBean.getTotalElements());
		return page;
	}

}
