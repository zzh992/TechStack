package com.techstack.sjs.service;

import java.util.List;

import com.techstack.sjs.entity.Question;
import com.techstack.sjs.page.PageBean;
import com.techstack.sjs.page.PageParam;


public interface QuestionService {
	
	public boolean addQuestion(Question question);
	
	public boolean editQuestion(Question question);
	
	public boolean deleteQuestion(Question question);
	
	public Question viewQuestion(Long id);
	
	public List<Question> getAllQuestion();
	
	public PageBean listQuestionPage(Long categoryId,PageParam pageParam);

}
