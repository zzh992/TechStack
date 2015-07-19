package com.techstack.sjs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techstack.sjs.entity.User;

@Controller
public class ExamineeController extends BaseController {
	
	@RequestMapping("/getAllExam.action")
	@ResponseBody
	public User getAllExamPaper(){
		
		User user=new User();
		user.setId(new Long(2));
		user.setLoginName("roger");
		user.setPassword("1234");
		return user;
	}

}
