package com.techstack.sjs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserLoginController {
	
	@RequestMapping("/login_login.action")
	public String login(){
		return "page/index.jsp";
	}
}
