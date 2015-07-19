package com.techstack.sjs.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.techstack.sjs.entity.Paper;
import com.techstack.sjs.entity.PaperQuestion;
import com.techstack.sjs.entity.Question;
import com.techstack.sjs.page.PageBean;
import com.techstack.sjs.service.PaperService;

@Controller
public class PaperController extends BaseController{
	
	@Autowired
	private PaperService paperService;
	/*@Autowired
	private QuestionService questionService;*/
	
	@RequestMapping("/paper_paperList.action")
	public ModelAndView paperList(){
		ModelAndView mav = new ModelAndView("page/paperManage/paperList.jsp");
		ModelMap modelMap = new ModelMap();
		String paperName = getString("paperName");
		modelMap.put("paperName", paperName);
		
		PageBean pageBean = paperService.paperPage(paperName,getPageParam());
		modelMap.putAll(this.transBeanToMap(pageBean));
		mav.addAllObjects(modelMap);
		return mav;
	}
	
	@RequestMapping("/paper_paperAdd.action")
	public String paperAdd(){
		return "page/paperManage/paperAdd.jsp";
	}
	
	@RequestMapping("/paper_paperSave.action")
	public ModelAndView paperSave(){
		String paperName = getString("paperName");
		String description = getString("description");
		Long totalScore = new Long(0);
		
		Paper paper = new Paper();
		paper.setDescription(paperName);
		
		String[] categoryIdArr = getRequest().getParameterValues("categoryIdArr");
		Set<PaperQuestion> paperQuestionSet = new HashSet<PaperQuestion>();
		
		//根据知识点和题目数随机抽取题目组成试卷
		for(int i=0; i<categoryIdArr.length; i++){
			Long t = getLong("number["+i+"]");
			totalScore += getLong("number["+i+"]") * getLong("grade["+i+"]");
			List<Question> questionList = null;
			//List<Question> questionList = questionService.findQuestionByCategoryId(getLong(Long.valueOf(categoryIdArr[i])));
			for(int j=0;i<getLong("number["+i+"]");j++){
				PaperQuestion pq = new PaperQuestion();
				pq.setPaper(paper);
				pq.setQuestion(questionList.get(new Random().nextInt(questionList.size())));
				paperQuestionSet.add(pq);
			}
		}
		paper.setTotalScore(totalScore);
		paper.setPaperQuestion(paperQuestionSet);
		paperService.paperSave(paper);
		return this.operateSuccess();
	}
	
	@RequestMapping("/paper_paperModify.action")
	public ModelAndView paperModify(){
		ModelAndView mav = new ModelAndView("page/paperManage/paperModify.jsp");
		ModelMap modelMap = new ModelMap();
		
		mav.addAllObjects(modelMap);
		return mav;
	}
	
	@RequestMapping("/paper_paperUpdate.action")
	public ModelAndView paperUpdate(){
		return this.operateSuccess();
	}
	
	@RequestMapping("/paper_paperView.action")
	public ModelAndView paperView(){
		ModelAndView mav = new ModelAndView("page/paperManage/paperView.jsp");
		ModelMap modelMap = new ModelMap();
		
		mav.addAllObjects(modelMap);
		return mav;
	}
	
	@RequestMapping("/paper_deleteQuestionInPaper.action")
	@ResponseBody
	public String deleteQuestionInPaper(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Long paperId = getLong("paperId");
		Long questionId = getLong("questionId");
		resultMap.put("STATE", "SUCCESS");
		resultMap.put("MSG", "Delete Success");
		return JSON.toJSONString(resultMap);
	}
	
	@RequestMapping("/paper_paperDelete.action")
	@ResponseBody
	public String paperDelete(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("STATE", "SUCCESS");
		resultMap.put("MSG", "Delete Success");
		return JSON.toJSONString(resultMap);
	}
	
	@RequestMapping("/paper_addStudentToPaper.action")
	public ModelAndView addStudentToPaper(){
		ModelAndView mav = new ModelAndView("page/paperManage/addStudentToPaper.jsp");
		ModelMap modelMap = new ModelMap();
		
		mav.addAllObjects(modelMap);
		return mav;
	}
	
	@RequestMapping("/paper_studentToPaperSave.action")
	public ModelAndView studentToPaperSave(){
		return this.operateSuccess();
	}
}
