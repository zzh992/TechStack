package com.techstack.sjs.service;

import java.util.List;

import com.techstack.sjs.entity.Category;
import com.techstack.sjs.page.PageBean;
import com.techstack.sjs.page.PageParam;

public interface CategoryService {
	public abstract boolean categorySave(Category category);

	/**
	 * Category list page which can find by categoryName
	 * 
	 * @param pageParam
	 * @return
	 */
	public abstract PageBean categoryPage(String categoryName,
			PageParam pageParam);

	public Category findCategoryById(Long id);
	
	public abstract boolean deleteCategoryById(Long id);
	
	public List<Category> getAllCategory();
}
