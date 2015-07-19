package com.techstack.sjs.dao;

import java.util.List;

import com.techstack.sjs.entity.BaseEntity;
import com.techstack.sjs.entity.User;
import com.techstack.sjs.page.PageBean;
import com.techstack.sjs.page.PageParam;

@SuppressWarnings("hiding")
public interface BaseDao<T extends BaseEntity> {

	public boolean saveOrUpdate(T t);

	public boolean deleteById(Class<T> clazz, Long id);

	public <T> T findById(Class<T> clazz, Long id);
	
	public List<T> findByEntity(T t);
	
	/**
	 * @param t can find by example
	 * @param pageParam
	 * @return
	 */
	public PageBean listPage(T t,PageParam pageParam);

}
