package com.techstack.pms.dao.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.techstack.component.mapper.BeanMapper;
import com.techstack.component.mybatis.page.PageBean;
import com.techstack.component.mybatis.page.PageParam;
import com.techstack.pms.dao.dto.PmsRoleDTO;
import com.techstack.pms.dao.dto.PmsRoleUserDTO;
import com.techstack.pms.dao.facade.PmsRoleDaoFacade;
import com.techstack.pms.dao.jpa.entity.Action;
import com.techstack.pms.dao.jpa.entity.Role;
import com.techstack.pms.dao.jpa.entity.User;
import com.techstack.pms.dao.jpa.mapper.PmsRoleDTOMapper;
import com.techstack.pms.dao.jpa.mapper.PmsRoleUserDTOMapper;
import com.techstack.pms.dao.jpa.repository.ActionDao;
import com.techstack.pms.dao.jpa.repository.RoleDao;
import com.techstack.pms.dao.jpa.repository.UserDao;

public class PmsRoleDaoFacadeImpl implements PmsRoleDaoFacade {
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private ActionDao actionDao;
	@Autowired
	private UserDao userDao;

	@Override
	public <Model> Model saveOrUpdate(Model model) {
		PmsRoleDTO pmsRoleDTO = BeanMapper.map(model, PmsRoleDTO.class);
		Role role = PmsRoleDTOMapper.toRole(pmsRoleDTO);
		role = roleDao.save(role);
		return (Model) PmsRoleDTOMapper.toPmsRoleDTO(role);
	}

	@Override
	public <Model> Model getById(Long id) {
		Role role = roleDao.findOne(id);
		return (Model) PmsRoleDTOMapper.toPmsRoleDTO(role);
	}

	@Override
	public void deleteById(Long id) {
		roleDao.delete(id);
	}

	@Override
	public <Model> void deleteByModel(Model model) {
		PmsRoleDTO pmsRoleDTO = BeanMapper.map(model, PmsRoleDTO.class);
		Role role = PmsRoleDTOMapper.toRole(pmsRoleDTO);
		roleDao.delete(role);
	}

	@Override
	public List<PmsRoleDTO> listAllRole() {
		List<Role> roleList = roleDao.findAll();
		List<PmsRoleDTO> pmsRoleDTOList = new ArrayList<PmsRoleDTO>();
		for(Role role : roleList){
			pmsRoleDTOList.add(BeanMapper.map(role, PmsRoleDTO.class));
		}
		return pmsRoleDTOList;
	}

	@Override
	public PmsRoleDTO getRoleByRoleName(String roleName) {
		Role role = roleDao.findByRoleName(roleName);
		return BeanMapper.map(role, PmsRoleDTO.class);
	}

	@Override
	public PmsRoleDTO findRoleByRoleNameNotEqId(String roleName, Long id) {
		Role role = roleDao.findByRoleNameAndIdNot(roleName, id);
		return BeanMapper.map(role, PmsRoleDTO.class);
	}

	@Override
	public List<PmsRoleDTO> listRoleByActionId(Long actionId) {
		Action action = actionDao.findOne(actionId);
		List<PmsRoleDTO> pmsRoleDTOList = new ArrayList<PmsRoleDTO>();
		for(Role role : action.getRoles()){
			pmsRoleDTOList.add(BeanMapper.map(role, PmsRoleDTO.class));
		}
		return pmsRoleDTOList;
	}

	@Override
	public List<PmsRoleUserDTO> listRoleUserByUserId(Long userId) {
		User user = userDao.findOne(userId);
		List<PmsRoleUserDTO> pmsRoleUserDTOList = new ArrayList<PmsRoleUserDTO>();
		for(Role role : user.getRoles()){
			pmsRoleUserDTOList.add(PmsRoleUserDTOMapper.toPmsRoleUserDTO(role, user));
		}
		return pmsRoleUserDTOList;
	}
	
	@Override
	public Page<PmsRoleDTO> listPage(Pageable pageable, Map<String, Object> paramMap) {
		PageParam pageParam = new PageParam(pageable.getPageNumber(), pageable.getPageSize());
		PageBean pageBean = baseDao.listPage(PmsRole.class, pageParam, paramMap);
		Page<PmsRoleDTO> page = new PageImpl<PmsRoleDTO>(BeanMapper.mapList(pageBean.getRecordList(), PmsRoleDTO.class));
		return page;
	}

}
