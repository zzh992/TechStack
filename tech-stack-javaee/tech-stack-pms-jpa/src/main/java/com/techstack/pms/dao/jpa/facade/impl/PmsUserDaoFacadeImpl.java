package com.techstack.pms.dao.jpa.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.techstack.component.jpa.DynamicSpecifications;
import com.techstack.component.jpa.JpaPageUtils;
import com.techstack.component.jpa.SearchFilter;
import com.techstack.component.jpa.SearchFilter.Logic;
import com.techstack.component.jpa.SearchFilter.Operator;
import com.techstack.component.mapper.BeanMapper;
import com.techstack.pms.dao.dto.PmsRoleUserDTO;
import com.techstack.pms.dao.dto.PmsUserDTO;
import com.techstack.pms.dao.facade.PmsUserDaoFacade;
import com.techstack.pms.dao.jpa.entity.Role;
import com.techstack.pms.dao.jpa.entity.User;
import com.techstack.pms.dao.jpa.mapper.PmsRoleUserDTOMapper;
import com.techstack.pms.dao.jpa.mapper.PmsUserDTOMapper;
import com.techstack.pms.dao.jpa.repository.RoleDao;
import com.techstack.pms.dao.jpa.repository.UserDao;

@Component("pmsUserDaoFacade")
public class PmsUserDaoFacadeImpl implements PmsUserDaoFacade {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;

	@Override
	public <Model> Model saveOrUpdate(Model model) {
		PmsUserDTO pmsUserDTO = BeanMapper.map(model, PmsUserDTO.class);
		User user = PmsUserDTOMapper.toUser(pmsUserDTO);
		if(user.getId()!=null){
			User tempUser = userDao.findOne(user.getId());
			user.setRoles(tempUser.getRoles());
		}
		user = userDao.save(user);
		return (Model) PmsUserDTOMapper.toPmsUserDTO(user);
	}

	@Override
	public <Model> Model getById(Long id) {
		User user = userDao.findOne(id);
		return (Model) PmsUserDTOMapper.toPmsUserDTO(user);
	}

	@Override
	public void deleteById(Long id) {
		userDao.delete(id);
	}

	@Override
	public <Model> void deleteByModel(Model model) {
		PmsUserDTO pmsUserDTO = BeanMapper.map(model, PmsUserDTO.class);
		User user = PmsUserDTOMapper.toUser(pmsUserDTO);
		List<User> userList = userDao.findAll(DynamicSpecifications.bySearchModel(user));
		userDao.delete(userList);
	}

	@Override
	public PmsUserDTO findUserByLoginName(String loginName) {
		User user = userDao.findByLoginName(loginName);
		return PmsUserDTOMapper.toPmsUserDTO(user);
	}

	@Override
	public List<PmsUserDTO> listUserByRoleId(Long roleId) {
		Role role = roleDao.findOne(roleId);
		List<PmsUserDTO> pmsUserDTOList = new ArrayList<PmsUserDTO>();
		for(User user : role.getUsers()){
			pmsUserDTOList.add(PmsUserDTOMapper.toPmsUserDTO(user));
		}
		return pmsUserDTOList;
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
	public List<PmsRoleUserDTO> listRoleUserByRoleId(Long roleId) {
		Role role = roleDao.findOne(roleId);
		List<PmsRoleUserDTO> pmsRoleUserDTOList = new ArrayList<PmsRoleUserDTO>();
		for(User user : role.getUsers()){
			pmsRoleUserDTOList.add(PmsRoleUserDTOMapper.toPmsRoleUserDTO(role, user));
		}
		return pmsRoleUserDTOList;
	}
	
	@Override
	public Page<PmsUserDTO> listPage(int pageNum, int pageSize, Map<String, Object> paramMap) {
		List<SearchFilter> searchFilterList = new ArrayList<SearchFilter>();
		if(paramMap.get("loginName") !=null){
			SearchFilter searchFilter = new SearchFilter("loginName", Operator.LIKE, paramMap.get("loginName"), Logic.AND);
			searchFilterList.add(searchFilter);
		}
		Page<User> pageBean = userDao.findAll(JpaPageUtils.buildSpecification(searchFilterList), JpaPageUtils.buildPageRequest(pageNum, pageSize, null, null));
		Page<PmsUserDTO> page = new PageImpl<PmsUserDTO>(BeanMapper.mapList(pageBean.getContent(), PmsUserDTO.class), new PageRequest(pageNum, pageSize), pageBean.getTotalElements());
		return page;
	}

}
