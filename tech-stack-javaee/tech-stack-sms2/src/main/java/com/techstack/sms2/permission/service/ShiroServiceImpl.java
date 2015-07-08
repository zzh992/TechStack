package com.techstack.sms2.permission.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techstack.component.shiro.ShiroService;
import com.techstack.component.shiro.ShiroUser;
import com.techstack.sms2.permission.biz.PmsUserBiz;
import com.techstack.sms2.permission.entity.PmsUser;

@Component("shiroService")
public class ShiroServiceImpl implements ShiroService {

	@Autowired
	private PmsUserBiz pmsUserBiz;
	
	@Override
	public ShiroUser findShiroUserByUsername(String username) {
		PmsUser pmsUser = pmsUserBiz.findUserByLoginName(username);
		ShiroUser shiroUser = new ShiroUser();
		shiroUser.setId(pmsUser.getId());
		shiroUser.setName(pmsUser.getLoginName());
		shiroUser.setPassword(pmsUser.getLoginPwd());
		shiroUser.setUsername(pmsUser.getLoginName());
		return shiroUser;
	}

}
