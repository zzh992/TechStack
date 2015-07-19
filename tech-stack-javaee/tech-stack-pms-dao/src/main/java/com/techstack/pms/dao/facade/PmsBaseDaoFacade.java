package com.techstack.pms.dao.facade;

public interface PmsBaseDaoFacade {
	
	public <Model> Model saveOrUpdate(Model model);
	
	public <Model> Model getById(Long id);
	
	public void deleteById(Long id);
	
	public <Model> void deleteByModel(Model model);
}
