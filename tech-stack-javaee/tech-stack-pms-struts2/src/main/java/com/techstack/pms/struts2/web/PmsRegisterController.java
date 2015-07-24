package com.techstack.pms.struts2.web;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.techstack.sms2.base.struts.BaseAction;
import com.techstack.sms2.permission.biz.PmsUserBiz;
import com.techstack.sms2.permission.entity.PmsRoleUser;
import com.techstack.sms2.permission.entity.PmsUser;
import com.techstack.sms2.permission.enums.UserTypeEnum;

/**
 * @Title: PmsRegisterAction.java 
 * @Description: 用户注册ACTION
 * @author zzh
 */
public class PmsRegisterController extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(PmsRegisterController.class);
	
	@Autowired
	private PmsUserBiz pmsUserBiz;
	
	/**
	 * @Description: 进入注册页面
	 * @param @return    
	 * @return String
	 */
	public String register(){
		return "register";
	}
	
	/**
	 * @Description: 用户注册保存
	 * @param @return    
	 * @return String
	 */
	public String userSave(){
		String loginName = getString("loginName");
		PmsUser user  = pmsUserBiz.findUserByLoginName(loginName);
		if(user!=null){
			this.putData("loginNameMsg", "用户名已被注册");
			log.info("==== info ==== 用户【"+loginName+"】已被注册");
			return "register";
		}
		user = new PmsUser();
		user.setLoginName(loginName);
		String loginPwd =DigestUtils.sha1Hex(getString("loginPwd"));
		user.setLoginPwd(loginPwd);
		user.setType(UserTypeEnum.USER.getValue());
		pmsUserBiz.create(user);
		PmsRoleUser studentUser = new PmsRoleUser();
		studentUser.setUserId(user.getId());
		studentUser.setRoleId(4L);
		pmsUserBiz.getBaseDao().saveOrUpdate(studentUser);
		log.info("==== info ==== 用户【"+loginName+"】注册成功");
		return "login";
	}
}
