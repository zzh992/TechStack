package com.techstack.pms.dao.mybatis.facade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techstack.component.mapper.BeanMapper;
import com.techstack.component.mybatis.dao.BaseDao;
import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.dto.PmsMenuDTO;
import com.techstack.pms.dao.dto.PmsRoleMenuDTO;
import com.techstack.pms.dao.facade.PmsMenuDaoFacade;
import com.techstack.pms.dao.mybatis.entity.PmsAction;
import com.techstack.pms.dao.mybatis.entity.PmsMenu;
import com.techstack.pms.dao.mybatis.entity.PmsRoleMenu;

@Component("pmsMenuDaoFacade")
public class PmsMenuDaoFacadeImpl implements PmsMenuDaoFacade {
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public <Model> Model saveOrUpdate(Model model) {
		PmsMenu pmsMenu = BeanMapper.map(model, PmsMenu.class);
		baseDao.saveOrUpdate(pmsMenu);
		return (Model) BeanMapper.map(pmsMenu,PmsMenuDTO.class);
	}

	@Override
	public <Model> Model getById(Long id) {
		PmsMenu pmsMenu = baseDao.getById(PmsMenu.class, id);
		return (Model) BeanMapper.map(pmsMenu,PmsMenuDTO.class);
	}

	@Override
	public void deleteById(Long id) {
		baseDao.deleteById(PmsMenu.class, id);
	}

	@Override
	public <Model> void deleteByModel(Model model) {
		PmsMenu pmsMenu = BeanMapper.map(model, PmsMenu.class);
		baseDao.deleteByModel(pmsMenu);
	}

	@Override
	public List<PmsMenuDTO> listMenuByParent(Long parentId) {
		List<PmsMenu> pmsMenuList = baseDao.selectList(getStatement("listMenuByParent"), parentId);
		return BeanMapper.mapList(pmsMenuList, PmsMenuDTO.class);
	}

	@Override
	public List<PmsMenuDTO> listMenuByRoleIds(List<Long> roleIds) {
		List<PmsMenu> pmsMenuList = baseDao.selectList(getStatement("listMenuByRoleIds"), roleIds);
		return BeanMapper.mapList(pmsMenuList, PmsMenuDTO.class);
	}

	@Override
	public List<PmsRoleMenuDTO> listRoleMenuByRoleId(Long roleId) {
		List<PmsRoleMenu> menuList = baseDao.selectList(getStatement("listRoleMenuByRoleId"), roleId);
		return BeanMapper.mapList(menuList, PmsRoleMenuDTO.class);
	}

	@Override
	public List<PmsActionDTO> listAllActionByMenuId(Long menuId) {
		List<PmsAction> actionList = baseDao.selectList(getStatement("listAllActionByMenuId"), menuId);
		return BeanMapper.mapList(actionList, PmsActionDTO.class);
	}

	@Override
	public List<PmsMenuDTO> listMenuByParentId(Long parentId) {
		List<PmsMenu> pmsMenuList = baseDao.selectList(getStatement("listMenuByParentId"), parentId);
		return BeanMapper.mapList(pmsMenuList, PmsMenuDTO.class);
	}

	@Override
	public List<PmsMenuDTO> listMenuBy(Integer isLeaf, String name,Long parentId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("isLeaf", isLeaf);
		param.put("name", name);
		param.put("name", name);
		List<PmsMenu> pmsMenuList = baseDao.selectList(getStatement("listMenuBy"), param);
		return BeanMapper.mapList(pmsMenuList, PmsMenuDTO.class);
	}
	
	public String getStatement(String sqlId) {
		String name = this.getClass().getName();
		StringBuffer sb = new StringBuffer();
		sb.append(name).append(".").append(sqlId);
		String statement = sb.toString();

		return statement;
	}

}
