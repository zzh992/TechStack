package com.techstack.pms.dao.jpa.repository;

import java.util.Set;

import com.techstack.component.jpa.BaseRepository;
import com.techstack.pms.dao.jpa.entity.Menu;

public interface MenuDao extends BaseRepository<Menu, Long> {

	public Set<Menu> findByParentMenu(Menu parentMenu);

	public Set<Menu> findByNameAndIsLeaf(String name, Integer isLeaf);
}
