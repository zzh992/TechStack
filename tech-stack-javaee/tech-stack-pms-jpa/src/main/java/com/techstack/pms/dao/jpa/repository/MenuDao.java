package com.techstack.pms.dao.jpa.repository;

import java.util.List;

import com.techstack.component.jpa.BaseRepository;
import com.techstack.pms.dao.jpa.entity.Menu;

public interface MenuDao extends BaseRepository<Menu, Long> {

	public List<Menu> findByParentMenu(Menu parentMenu);

	public List<Menu> findByNameAndIsLeaf(String name, Integer isLeaf);
}
