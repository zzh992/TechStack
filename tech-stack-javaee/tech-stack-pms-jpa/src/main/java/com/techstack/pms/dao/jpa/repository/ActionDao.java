package com.techstack.pms.dao.jpa.repository;

import java.util.List;

import com.techstack.component.jpa.BaseRepository;
import com.techstack.pms.dao.jpa.entity.Action;
import com.techstack.pms.dao.jpa.entity.Menu;

public interface ActionDao extends BaseRepository<Action, Long> {
	
	public List<Action> findByRelevantMenu(Menu relevantMenu);

	public List<Action> findByIdIn(List<Long> idList);

	public Action findByActionName(String actionName);

	public Action findByAction(String action);

	public Action findByActionNameAndIdNot(String actionName, Long id);

	public Action findByActionAndIdNot(String action, Long id);
}
