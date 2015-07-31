package com.techstack.pms.dao.jpa.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.techstack.component.jpa.DynamicSpecifications;
import com.techstack.component.jpa.JpaPageUtils;
import com.techstack.component.jpa.SearchFilter;
import com.techstack.component.mapper.BeanMapper;
import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.dto.PmsRoleActionDTO;
import com.techstack.pms.dao.facade.PmsActionDaoFacade;
import com.techstack.pms.dao.jpa.entity.Action;
import com.techstack.pms.dao.jpa.entity.Menu;
import com.techstack.pms.dao.jpa.entity.Role;
import com.techstack.pms.dao.jpa.mapper.PmsActionDTOMapper;
import com.techstack.pms.dao.jpa.mapper.PmsRoleActionDTOMapper;
import com.techstack.pms.dao.jpa.repository.ActionDao;
import com.techstack.pms.dao.jpa.repository.RoleDao;

@Component("pmsActionDaoFacade")
public class PmsActionDaoFacadeImpl implements PmsActionDaoFacade {

	@Autowired
	private ActionDao actionDao;
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public <Model> Model saveOrUpdate(Model model) {
		PmsActionDTO pmsActionDTO = BeanMapper.map(model, PmsActionDTO.class);
		Action action = PmsActionDTOMapper.toPmsAction(pmsActionDTO);
		action = actionDao.save(action);
		return (Model) PmsActionDTOMapper.toPmsActionDTO(action);
	}

	@Override
	public <Model> Model getById(Long id) {
		Action action = actionDao.findOne(id);
		return (Model) PmsActionDTOMapper.toPmsActionDTO(action);
	}

	@Override
	public void deleteById(Long id) {
		actionDao.delete(id);
	}

	@Override
	public <Model> void deleteByModel(Model model) {
		PmsActionDTO pmsActionDTO = BeanMapper.map(model, PmsActionDTO.class);
		Action action = PmsActionDTOMapper.toPmsAction(pmsActionDTO);
		List<Action> actionList = actionDao.findAll(DynamicSpecifications.bySearchModel(action));
		actionDao.deleteInBatch(actionList);
	}

	@Override
	public List<PmsActionDTO> findActionsByIds(List<Long> ids) {
		List<Action> actionList = actionDao.findByIdIn(ids);
		List<PmsActionDTO> pmsActionDTOList = new ArrayList<PmsActionDTO>();
		for(Action action : actionList){
			pmsActionDTOList.add(PmsActionDTOMapper.toPmsActionDTO(action));
		}
		return pmsActionDTOList;
	}

	@Override
	public PmsActionDTO getActionByAction(String action) {
		Action pmsAction = actionDao.findByAction(action);
		return PmsActionDTOMapper.toPmsActionDTO(pmsAction);
	}

	@Override
	public PmsActionDTO getActionByActionName(String actionName) {
		Action pmsAction = actionDao.findByActionName(actionName);
		return PmsActionDTOMapper.toPmsActionDTO(pmsAction);
	}

	@Override
	public PmsActionDTO getActionByActionNameNotEqId(String actionName, Long id) {
		Action pmsAction = actionDao.findByActionNameAndIdNot(actionName, id);
		return PmsActionDTOMapper.toPmsActionDTO(pmsAction);
	}

	@Override
	public PmsActionDTO getActionByActionNotEqId(String action, Long id) {
		Action pmsAction = actionDao.findByActionAndIdNot(action, id);
		return PmsActionDTOMapper.toPmsActionDTO(pmsAction);
	}

	@Override
	public List<PmsActionDTO> listActionByMenuId(Long menuId) {
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
	public List<PmsRoleActionDTO> listRoleActionByRoleId(Long roleId) {
		Role role = roleDao.findOne(roleId);
		List<PmsRoleActionDTO> pmsRoleActionDTOList = new ArrayList<PmsRoleActionDTO>(); 
		for(Action action : role.getActions()){
			pmsRoleActionDTOList.add(PmsRoleActionDTOMapper.toPmsRoleActionDTO(role, action));
		}
		return pmsRoleActionDTOList;
	}

	@Override
	public List<PmsRoleActionDTO> listRoleActionByRoleIds(List<Long> roleIds) {
		List<Role> roleList = roleDao.findByIdIn(roleIds);
		List<PmsRoleActionDTO> pmsRoleActionDTOList = new ArrayList<PmsRoleActionDTO>(); 
		for(Role role : roleList){
			for(Action action : role.getActions()){
				pmsRoleActionDTOList.add(PmsRoleActionDTOMapper.toPmsRoleActionDTO(role, action));
			}
		}
		return pmsRoleActionDTOList;
	}
	
	@Override
	public Page<PmsActionDTO> listPage(int pageNum, int pageSize, Map<String, Object> paramMap) {
		List<SearchFilter> searchFilterList = new ArrayList<SearchFilter>();
		if(paramMap.get("tt") !=null){
			SearchFilter searchFilter = new SearchFilter(null, null, null, null);
			searchFilterList.add(searchFilter);
		}
		Page<Action> pageBean = actionDao.findAll(JpaPageUtils.buildSpecification(searchFilterList), JpaPageUtils.buildPageRequest(pageNum, pageSize, null, null));
		Page<PmsActionDTO> page = new PageImpl<PmsActionDTO>(BeanMapper.mapList(pageBean.getContent(), PmsActionDTO.class));
		return page;
	}

}
