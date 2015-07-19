package com.techstack.sjs.permission.dao;

import java.util.List;
import java.util.Set;

import com.techstack.sjs.base.repository.BaseRepository;
import com.techstack.sjs.permission.entity.Action;
import com.techstack.sjs.permission.entity.Menu;

public interface ActionDao extends BaseRepository<Action, Long> {
	public Set<Action> findByRelevantMenu(Menu relevantMenu);
	public Set<Action> findByIdIn(List<String> idList);
	public Action findByActionName(String actionName);
	public Action findByAction(String action);
	public Action findByActionNameAndIdNot(String actionName, Long id);
	public Action findByActionAndIdNot(String action, Long id);
}
