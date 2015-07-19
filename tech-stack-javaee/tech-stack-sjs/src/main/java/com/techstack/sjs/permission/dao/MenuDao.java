package com.techstack.sjs.permission.dao;

import java.util.Set;

import com.techstack.sjs.base.repository.BaseRepository;
import com.techstack.sjs.permission.entity.Menu;

public interface MenuDao extends BaseRepository<Menu, Long> {
	public Set<Menu> findByParentMenu(Menu parentMenu);
	public Set<Menu> findByNameAndIsLeaf(String name, Integer isLeaf);
}
