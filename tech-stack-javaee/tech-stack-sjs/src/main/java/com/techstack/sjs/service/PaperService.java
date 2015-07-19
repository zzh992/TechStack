package com.techstack.sjs.service;

import com.techstack.sjs.entity.Paper;
import com.techstack.sjs.page.PageBean;
import com.techstack.sjs.page.PageParam;

public interface PaperService {
	
	public abstract boolean paperSave(Paper paper);
	
	/**
	 * exam paper list page which can find by paperName
	 * @param pageParam
	 * @return
	 */
	public abstract PageBean paperPage(String paperName,PageParam pageParam);
	
	
}
