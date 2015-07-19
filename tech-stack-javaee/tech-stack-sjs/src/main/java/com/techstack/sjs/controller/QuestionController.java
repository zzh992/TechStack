package com.techstack.sjs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.techstack.sjs.entity.Category;
import com.techstack.sjs.entity.Choice;
import com.techstack.sjs.entity.Question;
import com.techstack.sjs.page.PageBean;
import com.techstack.sjs.service.QuestionService;

@Controller
public class QuestionController extends BaseController {

	@Autowired
	private QuestionService questionService;

	@RequestMapping("/question_question.action")
	public ModelAndView questionList() {
		ModelAndView mv = new ModelAndView("page/questionManage/question.jsp");
		ModelMap map = new ModelMap();
		PageBean pb = questionService.listQuestionPage(getLong("categoryId"), getPageParam());
		List<Category> category = new ArrayList<Category>();

		Category c1 = new Category();
		c1.setId(new Long(1));
		c1.setCategoryName("Java");

		Category c2 = new Category();
		c2.setId(new Long(2));
		c2.setCategoryName("DB");

		category.add(c1);
		category.add(c2);

		map.put("categoryList", category);
		map.put("categoryId", getLong("categoryId"));

		Set<Choice> choice = new LinkedHashSet<Choice>();
		Choice cho1 = new Choice();
		cho1.setChoice("A");
		cho1.setDescription("aaa");
		choice.add(cho1);

		Choice cho2 = new Choice();
		cho2.setChoice("B");
		cho2.setDescription("bbb");
		choice.add(cho2);

		List<Question> question = new ArrayList<Question>();
		Question q1 = new Question();
		q1.setId(new Long(1));
		q1.setCategory(c1);
		q1.setDescription("hello1");
		q1.setchoice(choice);
		q1.setAnswer("A");

		Question q2 = new Question();
		q2.setId(new Long(2));
		q2.setCategory(c2);
		q2.setDescription("hello2");
		q2.setchoice(choice);
		q2.setAnswer("B");

		question.add(q1);
		question.add(q2);

		map.put("questionList", question);

		map.putAll(transBeanToMap(pb));
		mv.addAllObjects(map);

		return mv;
	}

	@RequestMapping("/question_questionAdd.action")
	public String questionAdd() {
		return "page/questionManage/questionAdd.jsp";
	}

	@RequestMapping("/question_questionModify.action")
	public String questionModify() {
		return "page/questionManage/questionModify.jsp";
	}

	@RequestMapping("/question_questionView.action")
	public ModelAndView questionView() {
		ModelAndView mv = new ModelAndView("page/questionManage/questionView.jsp");
		ModelMap map = new ModelMap();
		Long questionId = getLong("id");
		Question question = questionService.viewQuestion(questionId);
		map.put("question", question);
		mv.addAllObjects(map);
		return mv;
	}

	@RequestMapping("/question_save.action")
	public String questionSave(ModelMap question) {
		return "page/questionManage/questionSave.jsp";
	}

	@RequestMapping("/question_questionDel.action")
	@ResponseBody
	public String questionDelete() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("STATE", "SUCCESS");
		resultMap.put("MSG", "Delete Success");
		return JSON.toJSONString(resultMap);
	}
}
