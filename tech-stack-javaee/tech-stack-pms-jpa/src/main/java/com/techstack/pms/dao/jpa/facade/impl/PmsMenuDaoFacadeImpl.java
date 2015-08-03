package com.techstack.pms.dao.jpa.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techstack.component.jpa.DynamicSpecifications;
import com.techstack.component.mapper.BeanMapper;
import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.dto.PmsMenuDTO;
import com.techstack.pms.dao.dto.PmsRoleMenuDTO;
import com.techstack.pms.dao.facade.PmsMenuDaoFacade;
import com.techstack.pms.dao.jpa.entity.Action;
import com.techstack.pms.dao.jpa.entity.Menu;
import com.techstack.pms.dao.jpa.entity.Role;
import com.techstack.pms.dao.jpa.mapper.PmsActionDTOMapper;
import com.techstack.pms.dao.jpa.mapper.PmsMenuDTOMapper;
import com.techstack.pms.dao.jpa.mapper.PmsRoleMenuDTOMapper;
import com.techstack.pms.dao.jpa.repository.ActionDao;
import com.techstack.pms.dao.jpa.repository.MenuDao;
import com.techstack.pms.dao.jpa.repository.RoleDao;

@Component("pmsMenuDaoFacade")
public class PmsMenuDaoFacadeImpl implements PmsMenuDaoFacade {
	
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private ActionDao actionDao;

	@Override
	public <Model> Model saveOrUpdate(Model model) {
		PmsMenuDTO pmsMenuDTO = BeanMapper.map(model, PmsMenuDTO.class);
		Menu menu = PmsMenuDTOMapper.toMenu(pmsMenuDTO);
		menu = menuDao.save(menu);
		return (Model) PmsMenuDTOMapper.toPmsMenuDTO(menu);
	}

	@Override
	public <Model> Model getById(Long id) {
		Menu menu = menuDao.findOne(id);
		return (Model) PmsMenuDTOMapper.toPmsMenuDTO(menu);
	}

	@Override
	public void deleteById(Long id) {
		menuDao.delete(id);
	}

	@Override
	public <Model> void deleteByModel(Model model) {
		PmsMenuDTO pmsMenuDTO = BeanMapper.map(model, PmsMenuDTO.class);
		Menu menu = PmsMenuDTOMapper.toMenu(pmsMenuDTO);
		List<Menu> menuList = menuDao.findAll(DynamicSpecifications.bySearchModel(menu));
		menuDao.delete(menuList);
	}

	@Override
	public List<PmsMenuDTO> listMenuByParent(Long parentId) {
		Menu parentMenu = new Menu();
		parentMenu.setId(parentId);
		List<Menu> menuList = menuDao.findByParentMenu(parentMenu);
		List<PmsMenuDTO> pmsMenuDTOList = new ArrayList<PmsMenuDTO>();
		for(Menu menu : menuList){
			pmsMenuDTOList.add(PmsMenuDTOMapper.toPmsMenuDTO(menu));
		}
		return pmsMenuDTOList;
	}

	@Override
	public List<PmsMenuDTO> listMenuByRoleIds(List<Long> roleIds) {
		List<Role> roleList = roleDao.findByIdIn(roleIds);
		List<PmsMenuDTO> pmsMenuDTOList = new ArrayList<PmsMenuDTO>();
		for(Role role : roleList){
			for(Menu menu : role.getMenus()){
				pmsMenuDTOList.add(PmsMenuDTOMapper.toPmsMenuDTO(menu));
			}
		}
		return pmsMenuDTOList;
	}

	@Override
	public List<PmsRoleMenuDTO> listRoleMenuByRoleId(Long roleId) {
		Role role = roleDao.findOne(roleId);
		List<PmsRoleMenuDTO> pmsRoleMenuDTOList = new ArrayList<PmsRoleMenuDTO>();
		for(Menu menu : role.getMenus()){
			pmsRoleMenuDTOList.add(PmsRoleMenuDTOMapper.toPmsRoleMenuDTO(role, menu));
		}
		return pmsRoleMenuDTOList;
	}

	@Override
	public List<PmsActionDTO> listAllActionByMenuId(Long menuId) {
		Menu relevantMenu = new Menu();
		relevantMenu.setId(menuId);
		List<Action> actionList = actionDao.findByRelevantMenu(relevantMenu);
		List<PmsActionDTO> pmsActionDTOList = new ArrayList<PmsActionDTO>();
		for(Action action : actionList){
			pmsActionDTOList.add(PmsActionDTOMapper.toPmsActionDTO(action));
		}
		return pmsActionDTOList;
	}

	@Override
	public List<PmsMenuDTO> listMenuByParentId(Long parentId) {
		Menu parentMenu = new Menu();
		parentMenu.setId(parentId);
		List<Menu> menuList = menuDao.findByParentMenu(parentMenu);
		List<PmsMenuDTO> pmsMenuDTOList = new ArrayList<PmsMenuDTO>();
		for(Menu menu : menuList){
			pmsMenuDTOList.add(PmsMenuDTOMapper.toPmsMenuDTO(menu));
		}
		return pmsMenuDTOList;
	}

	@Override
	public List<PmsMenuDTO> listMenuBy(Integer isLeaf, String name,Long parentId) {
		List<Menu> menuList = menuDao.findByNameAndIsLeaf(name, isLeaf);
		List<PmsMenuDTO> pmsMenuDTOList = new ArrayList<PmsMenuDTO>();
		for(Menu menu : menuList){
			pmsMenuDTOList.add(PmsMenuDTOMapper.toPmsMenuDTO(menu));
		}
		return pmsMenuDTOList;
	}
	

}
