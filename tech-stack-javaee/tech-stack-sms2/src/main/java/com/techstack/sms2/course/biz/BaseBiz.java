package com.techstack.sms2.course.biz;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.techstack.sms2.base.dao.BaseDao;
import com.techstack.sms2.base.page.PageBean;
import com.techstack.sms2.base.page.PageParam;

/**
 * @Title: BaseBiz.java 
 * @Description: 业务层基础类
 * @author zzh
 */
public class BaseBiz {
	
	@Autowired
	private BaseDao baseDao;
	
	public <Model> void saveOrUpdate(Model model){
		baseDao.saveOrUpdate(model);
	}
	
	public <Model>Model getById(Class<Model> modelClass,Long id){
		return baseDao.getById(modelClass, id);
	}
	
	public <Model> void deleteById(Class<Model> modelClass, Long id) {
		baseDao.deleteById(modelClass, id);
	}
	
	/**
	 * @Description: 分页查询
	 * @param @param pageParam
	 * @param @param paramMap 通过传入module查找对应条件语句块，可查看BaseMapper.xml配置
	 * @param @param modelClass
	 * @param @return    
	 * @return PageBean
	 */
	public <Model> PageBean listPage(PageParam pageParam, Map<String, Object> paramMap,Class<Model> modelClass) {
		return baseDao.listPage(modelClass, pageParam, paramMap);
	}
	
	/**
	 * @Description: 获取Mapper命名空间
	 * @param @param sqlId
	 * @param @return    
	 * @return String
	 */
	public String getStatement(String sqlId) {
		String name = this.getClass().getName();
		StringBuffer sb = new StringBuffer();
		sb.append(name).append(".").append(sqlId);
		String statement = sb.toString();

		return statement;
	}

	public BaseDao getBaseDao() {
		return baseDao;
	}
	
}
