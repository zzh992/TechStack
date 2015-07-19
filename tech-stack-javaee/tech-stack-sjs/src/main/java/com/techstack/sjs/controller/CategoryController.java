package com.techstack.sjs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.techstack.sjs.page.PageBean;
import com.techstack.sjs.service.CategoryService;

@Controller
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/category_categoryList.action")
	public ModelAndView categoryList() {
		ModelAndView mav = new ModelAndView("page/categoryManage/categoryList.jsp");
		ModelMap modelMap = new ModelMap();
		String categoryName = getString("categoryName");
		modelMap.put("categoryName", categoryName);
		
		PageBean pageBean = categoryService.categoryPage(categoryName, getPageParam());
		modelMap.putAll(this.transBeanToMap(pageBean));
		mav.addAllObjects(modelMap);
		return mav;
	}

	@RequestMapping("/category_categoryAdd.action")
	public String categoryAdd() {
		return "page/categoryManage/categoryAdd.jsp";
	}

	@RequestMapping("/category_categorySave.action")
	public ModelAndView categorySave() {
		return this.operateSuccess();
	}

	@RequestMapping("/category_categoryModify.action")
	public String categoryModify() {
		return "page/categoryManage/categoryModify.jsp";
	}
    
	@RequestMapping("/category_categoryUpdate.action")
	public ModelAndView categoryUpdate(){
		return null;
		
	}
	
	
	
}
