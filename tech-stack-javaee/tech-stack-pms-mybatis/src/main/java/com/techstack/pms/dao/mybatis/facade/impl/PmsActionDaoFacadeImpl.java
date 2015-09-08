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
import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.dto.PmsRoleActionDTO;
import com.techstack.pms.dao.facade.PmsActionDaoFacade;
import com.techstack.pms.dao.mybatis.entity.PmsAction;
import com.techstack.pms.dao.mybatis.entity.PmsRoleAction;

@Component("pmsActionDaoFacade")
public class PmsActionDaoFacadeImpl implements PmsActionDaoFacade {

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public <Model> Model saveOrUpdate(Model model) {
		PmsAction pmsAction = BeanMapper.map(model, PmsAction.class);
		baseDao.saveOrUpdate(pmsAction);
		return (Model) BeanMapper.map(pmsAction,PmsActionDTO.class);
	}

	@Override
	public <Model> Model getById(Long id) {
		PmsAction pmsAction = baseDao.getById(PmsAction.class, id);
		return (Model) BeanMapper.map(pmsAction,PmsActionDTO.class);
	}

	@Override
	public void deleteById(Long id) {
		baseDao.deleteById(PmsAction.class, id);
	}

	@Override
	public <Model> void deleteByModel(Model model) {
		PmsAction pmsAction = BeanMapper.map(model, PmsAction.class);
		baseDao.deleteByModel(pmsAction);
	}

	@Override
	public List<PmsActionDTO> findActionsByIds(List<Long> ids) {
		List<PmsAction> pmsActionList = baseDao.selectList(getStatement("findActionByIds"), ids);
		return BeanMapper.mapList(pmsActionList, PmsActionDTO.class);
	}

	@Override
	public PmsActionDTO getActionByAction(String action) {
		PmsAction pmsAction = baseDao.selectOne(getStatement("getActionByAction"), action);
		return BeanMapper.map(pmsAction, PmsActionDTO.class);
	}

	@Override
	public PmsActionDTO getActionByActionName(String actionName) {
		PmsAction pmsAction = baseDao.selectOne(getStatement("getActionByActionName"), actionName);
		return BeanMapper.map(pmsAction, PmsActionDTO.class);
	}

	@Override
	public PmsActionDTO getActionByActionNameNotEqId(String actionName, Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("actionName", actionName);
		param.put("id", id);
		PmsAction pmsAction = baseDao.selectOne(getStatement("getActionByActionNameNotEqId"), param);
		return BeanMapper.map(pmsAction, PmsActionDTO.class);
	}

	@Override
	public PmsActionDTO getActionByActionNotEqId(String action, Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("action", action);
		param.put("id", id);
		PmsAction pmsAction = baseDao.selectOne(getStatement("getActionByActionNotEqId"), param);
		return BeanMapper.map(pmsAction, PmsActionDTO.class);
	}

	@Override
	public List<PmsActionDTO> listActionByMenuId(Long menuId) {
		List<PmsAction> pmsActionList = baseDao.selectList(getStatement("listActionByMenuId"), menuId);
		return BeanMapper.mapList(pmsActionList, PmsActionDTO.class);
	}

	@Override
	public List<PmsRoleActionDTO> listRoleActionByRoleId(Long roleId) {
		List<PmsRoleAction> actionList = baseDao.selectList(getStatement("listRoleActionByRoleId"), roleId);
		return BeanMapper.mapList(actionList, PmsRoleActionDTO.class);
	}

	@Override
	public List<PmsRoleActionDTO> listRoleActionByRoleIds(List<Long> roleIds) {
		List<PmsRoleAction> listPmsRoleActions = baseDao.selectList(getStatement("listRoleActionByRoleIds"), roleIds);
		return BeanMapper.mapList(listPmsRoleActions, PmsRoleActionDTO.class);
	}
	
	public String getStatement(String sqlId) {
		String name = this.getClass().getName();
		StringBuffer sb = new StringBuffer();
		sb.append(name).append(".").append(sqlId);
		String statement = sb.toString();

		return statement;
	}

	@Override
	public Page<PmsActionDTO> listPage(int pageNum, int pageSize, Map<String, Object> paramMap) {
		PageParam pageParam = new PageParam(pageNum, pageSize);
		Page pageBean = baseDao.listPage(PmsAction.class, pageParam, paramMap);
		Page<PmsActionDTO> page = new PageImpl<PmsActionDTO>(BeanMapper.mapList(pageBean.getContent(), PmsActionDTO.class), new PageRequest(pageNum, pageSize), pageBean.getTotalElements());
		return page;
	}

}
