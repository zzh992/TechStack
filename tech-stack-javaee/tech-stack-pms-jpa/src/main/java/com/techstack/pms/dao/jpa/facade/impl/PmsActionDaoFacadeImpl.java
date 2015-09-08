package com.techstack.pms.dao.jpa.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
		//actionDao.deleteInBatch(actionList);
		//actionDao.flush();
		if(actionList != null){
			actionDao.delete(actionList);
		}
	}

	@Override
	public List<PmsActionDTO> findActionsByIds(List<Long> ids) {
		List<Action> actionList = actionDao.findByIdIn(ids);
		List<PmsActionDTO> pmsActionDTOList = new ArrayList<PmsActionDTO>();
		if(actionList!=null && !actionList.isEmpty()){
			for(Action action : actionList){
				pmsActionDTOList.add(PmsActionDTOMapper.toPmsActionDTO(action));
			}
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
		if(actionList!=null && actionList.isEmpty()){
			for(Action action : actionList){
				pmsActionDTOList.add(PmsActionDTOMapper.toPmsActionDTO(action));
			}
		}
		return pmsActionDTOList;
	}

	@Override
	public List<PmsRoleActionDTO> listRoleActionByRoleId(Long roleId) {
		Role role = roleDao.findOne(roleId);
		List<PmsRoleActionDTO> pmsRoleActionDTOList = new ArrayList<PmsRoleActionDTO>(); 
		if(role!=null && role.getActions()!=null){
			for(Action action : role.getActions()){
				pmsRoleActionDTOList.add(PmsRoleActionDTOMapper.toPmsRoleActionDTO(role, action));
			}
		}
		return pmsRoleActionDTOList;
	}

	@Override
	public List<PmsRoleActionDTO> listRoleActionByRoleIds(List<Long> roleIds) {
		List<Role> roleList = roleDao.findByIdIn(roleIds);
		List<PmsRoleActionDTO> pmsRoleActionDTOList = new ArrayList<PmsRoleActionDTO>(); 
		if(roleList!=null && !roleList.isEmpty()){
			for(Role role : roleList){
				if(role.getRoleType() == 1){
					List<Action> actions = actionDao.findAll();
					if(actions!=null && !actions.isEmpty()){
						for(Action action  : actions){
							pmsRoleActionDTOList.add(PmsRoleActionDTOMapper.toPmsRoleActionDTO(role, action));
						}
					}
				}
				if(role.getActions()!=null && !role.getActions().isEmpty()){
					for(Action action : role.getActions()){
						pmsRoleActionDTOList.add(PmsRoleActionDTOMapper.toPmsRoleActionDTO(role, action));
					}
				}
			}
		}
		return pmsRoleActionDTOList;
	}
	
	@Override
	public Page<PmsActionDTO> listPage(int pageNum, int pageSize, Map<String, Object> paramMap) {
		List<SearchFilter> searchFilterList = new ArrayList<SearchFilter>();
		if(paramMap.get("actionName") !=null && !StringUtils.isEmpty(paramMap.get("actionName").toString())){
			SearchFilter searchFilter = new SearchFilter("actionName", Operator.LIKE, paramMap.get("actionName"), Logic.AND);
			searchFilterList.add(searchFilter);
		}
		if(paramMap.get("action") !=null && !StringUtils.isEmpty(paramMap.get("action").toString())){
			SearchFilter searchFilter = new SearchFilter("action", Operator.EQ, paramMap.get("action"), Logic.AND);
			searchFilterList.add(searchFilter);
		}
		Page<Action> pageBean = actionDao.findAll(JpaPageUtils.buildSpecification(searchFilterList), JpaPageUtils.buildPageRequest(pageNum, pageSize, null, null));
		Page<PmsActionDTO> page = new PageImpl<PmsActionDTO>(BeanMapper.mapList(pageBean.getContent(), PmsActionDTO.class), new PageRequest(pageNum, pageSize), pageBean.getTotalElements());
		return page;
	}

}
