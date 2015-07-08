package com.techstack.sms2.permission.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.techstack.sms2.base.annotation.permission.Permission;
import com.techstack.sms2.base.page.PageBean;
import com.techstack.sms2.base.struts.BaseAction;
import com.techstack.sms2.permission.biz.PmsRoleBiz;
import com.techstack.sms2.permission.biz.PmsUserBiz;
import com.techstack.sms2.permission.entity.PmsRole;
import com.techstack.sms2.permission.entity.PmsRoleUser;
import com.techstack.sms2.permission.entity.PmsUser;
import com.techstack.sms2.permission.enums.RoleTypeEnum;
import com.techstack.sms2.permission.enums.UserStatusEnum;
import com.techstack.sms2.permission.enums.UserTypeEnum;

/**
 * @Title: PmsUserAction.java 
 * @Description: 用户ACTION
 * @author zzh
 */
public class PmsUserAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private static Log log = LogFactory.getLog(PmsUserAction.class);

	@Autowired
	private PmsRoleBiz pmsRoleBiz;
	@Autowired
	private PmsUserBiz pmsUserBiz;
	
	
	/**
	 * @Description: 分页列出用户信息，并可按登录名获姓名进行查询.
	 * @param @return    
	 * @return String
	 */
	@Permission("pms:user:view")
	public String pmsUserList() {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>(); // 业务条件查询参数
			paramMap.put("loginName", getString("loginName")); // 用户登录名（精确查询）

			PageBean pageBean = pmsUserBiz.listPage(getPageParam(), paramMap);
			this.pushData(pageBean);
			//PmsUser pmsUser = getLoginedUser();// 获取当前登录用户对象
			//this.putData("currLoginName", pmsUser.getLoginName());
			// 回显查询条件值
			this.pushData(paramMap);
			
			this.putData("UserStatusEnumList", UserStatusEnum.values());
			this.putData("UserStatusEnum", UserStatusEnum.toMap());
			this.putData("UserTypeEnumList", UserTypeEnum.values());
			this.putData("UserTypeEnum", UserTypeEnum.toMap());
			
			return "pmsUserList";
		} catch (Exception e) {
			log.error("==== error ==== 查询用户失败：", e);
			return operateError("获取数据失败");
		}
	}

	/**
	 * @Description: 查看用户详情.
	 * @param @return    
	 * @return String
	 */
	@Permission("pms:user:view")
	public String pmsUserView() {
		try {
			Long userId = getLong("id");
			PmsUser pmsUser = pmsUserBiz.getById(userId);
			if (pmsUser == null) {
				return operateError("无法获取要查看的数据");
			}

			// 普通用户没有查看超级管理员的权限
			// if ("0".equals(this.getLoginedUser().getType()) &&
			// "1".equals(pmsUser.getType())) {
			// return operateError("权限不足");
			// }

			this.pushData(pmsUser);
			// 准备角色列表
			this.putData("rolesList", pmsRoleBiz.listAllRole());
			
			// 准备该用户拥有的角色ID字符串
			List<PmsRoleUser> lisPmsRoleUsers = pmsUserBiz.listRoleUserByUserId(userId);
			StringBuffer owenedRoleIdBuffer = new StringBuffer("");
			for (PmsRoleUser pmsRoleUser : lisPmsRoleUsers) {
				owenedRoleIdBuffer.append(pmsRoleUser.getRoleId());
				owenedRoleIdBuffer.append(",");
			}
			String owenedRoleIds = owenedRoleIdBuffer.toString();
			if (StringUtils.isNotBlank(owenedRoleIds) && owenedRoleIds.length() > 0) {
				owenedRoleIds = owenedRoleIds.substring(0, owenedRoleIds.length() - 1);
			}
			this.putData("owenedRoleIds", owenedRoleIds);
			return "pmsUserView";
		} catch (Exception e) {
			log.error("==== error ==== 查看用户详情失败：", e);
			return operateError("获取数据失败");
		}
	}

	/**
	 * @Description: 转到添加用户页面 .
	 * @param @return    
	 * @return String
	 */
	@Permission("pms:user:add")
	public String pmsUserAdd() {
		try {
			
			this.putData("rolesList", pmsRoleBiz.listAllRole());
			this.putData("UserStatusEnumList", UserStatusEnum.values());
			this.putData("RoleTypeEnum", RoleTypeEnum.toMap());
			return "pmsUserAdd";
		} catch (Exception e) {
			log.error("==== error ==== 进入添加用户页面失败：", e);
			return operateError("获取角色列表数据失败");
		}
	}

	/**
	 * @Description: 保存一个用户
	 * @param @return    
	 * @return String
	 */
	@Permission("pms:user:add")
	public String pmsUserSave() {
		try {
			String loginPwd = getString("loginPwdss"); // 初始登录密码

			String loginName = getString("loginNamess");

			PmsUser pmsUser = new PmsUser();
			pmsUser.setLoginName(loginName); // 登录名
			pmsUser.setLoginPwd(loginPwd);
			pmsUser.setRemark(getString("desc")); // 描述
			pmsUser.setType(UserTypeEnum.USER.getValue()); // 类型（ "0":'普通用户',"1":'超级管理员'），只能添加普通用户

			String roleUserStr = getRoleUserStr();

			// 表单数据校验
			String validateMsg = validatePmsUser(pmsUser, roleUserStr);

			// if (!loginPwdFormat(loginPwd)) {
			// return operateError("登录密码必须由字母、数字、特殊符号组成");
			// }

			if (StringUtils.isNotBlank(validateMsg)) {
				return operateError(validateMsg); // 返回错误信息
			}

			// 校验用户登录名是否已存在
			PmsUser loginNameCheck = pmsUserBiz.findUserByLoginName(loginName);
			if (loginNameCheck != null) {
				return operateError("登录名【" + loginName + "】已存在");
			}

			pmsUser.setLoginPwd(DigestUtils.sha1Hex(loginPwd)); // 存存前对密码进行加密

			pmsUserBiz.saveUser(pmsUser, roleUserStr);
			log.info("==== info ==== 用户【"+loginName+"】保存成功");
			return operateSuccess();
		} catch (Exception e) {
			log.error("==== error ==== 保存用户信息失败：", e);
			return operateError("保存用户信息失败");
		}
	}


	/**
	 * @Description: 验证输入的邮箱格式是否符合
	 * @param @param email
	 * @param @return    
	 * @return boolean
	 *//*
	public static boolean emailFormat(String email) {
		// boolean tag = true;
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		boolean result = Pattern.matches(check, email);
		return result;
	}

	*//**
	 * @Description: 验证输入的密码格式是否符合
	 * @param @param loginPwd
	 * @param @return    
	 * @return boolean
	 *//*
	public static boolean loginPwdFormat(String loginPwd) {
		return loginPwd.matches(".*?[^a-zA-Z\\d]+.*?") && loginPwd.matches(".*?[a-zA-Z]+.*?") && loginPwd.matches(".*?[\\d]+.*?");
	}

	*//**
	 * @Description: 验证输入的用户姓名格式是否符合
	 * @param @param realName
	 * @param @return    
	 * @return boolean
	 *//*
	public static boolean realNameFormat(String realName) {
		return realName.matches("[^\\x00-\\xff]+");
	}*/

	/**
	 * @Description: 校验Pms用户表单数据.
	 * @param @param user
	 * @param @param roleUserStr
	 * @param @return    
	 * @return String
	 */
	private String validatePmsUser(PmsUser user, String roleUserStr) {
		String msg = ""; // 用于存放校验提示信息的变量
		msg += lengthValidate("登录名", user.getLoginName(), true, 3, 50);

		/*
		 * String specialChar = "`!@#$%^&*()_+\\/"; if
		 * (user.getLoginName().contains(specialChar)) { msg +=
		 * "登录名不能包含特殊字符，"; }
		 */

//		if (!emailFormat(user.getLoginName())) {
//			msg += "账户名格式必须为邮箱地址！";
//		}
		
		/*// 登录密码
		String loginPwd = user.getLoginPwd();
		String loginPwdMsg = lengthValidate("登录密码", loginPwd, true, 6, 50);
		
		 * if (StringUtils.isBlank(loginPwdMsg) &&
		 * !ValidateUtils.isAlphanumeric(loginPwd)) { loginPwdMsg +=
		 * "登录密码应为字母或数字组成，"; }
		 
		msg += loginPwdMsg;*/


		/*// 状态
		Integer status = user.getStatus();
		if (status == null) {
			msg += "请选择状态，";
		} else if (status.intValue() < 100 || status.intValue() > 101) {
			msg += "状态值不正确，";
		}*/

		//msg += lengthValidate("描述", user.getRemark(), true, 3, 100);

		// 新增用户的权限不能为空，为空没意义
		if (StringUtils.isBlank(roleUserStr) && user.getId() == null) {
			msg += "用户关联的角色不能为空";
		}
		return msg;
	}

	/**
	 * @Description: 删除用户
	 * @param @return    
	 * @return String
	 */
	@Permission("pms:user:delete")
	public String pmsUserDel() {
		long id = getLong("id");
		pmsUserBiz.deleteUserById(id);
		return this.operateSuccess("操作成功");
	}

	/**
	 * @Description: 转到修改用户界面
	 * @param @return    
	 * @return String
	 */
	@Permission("pms:user:edit")
	public String pmsUserEdit() {
		try {
			Long id = getLong("id");
			PmsUser pmsUser = pmsUserBiz.getById(id);
			if (pmsUser == null) {
				return operateError("无法获取要修改的数据");
			}

			// 普通用户没有修改超级管理员的权限
			if (UserTypeEnum.USER.getValue().equals(this.getLoginedUser().getType()) 
			 && UserTypeEnum.ADMIN.getValue().equals(pmsUser.getType())) {
				return operateError("权限不足");
			}

			this.pushData(pmsUser);
			// 准备角色列表
			this.putData("rolesList", pmsRoleBiz.listAllRole());
			
			// 准备该用户拥有的角色ID字符串
			List<PmsRoleUser> lisPmsRoleUsers = pmsUserBiz.listRoleUserByUserId(id);
			StringBuffer owenedRoleIdBuffer = new StringBuffer("");
			for (PmsRoleUser pmsRoleUser : lisPmsRoleUsers) {
				owenedRoleIdBuffer.append(pmsRoleUser.getRoleId());
				owenedRoleIdBuffer.append(",");
			}
			String owenedRoleIds = owenedRoleIdBuffer.toString();
			if (StringUtils.isNotBlank(owenedRoleIds) && owenedRoleIds.length() > 0) {
				owenedRoleIds = owenedRoleIds.substring(0, owenedRoleIds.length() - 1);
			}
			this.putData("owenedRoleIds", owenedRoleIds);
			
			this.putData("UserStatusEnum", UserStatusEnum.toMap());
			this.putData("UserTypeEnum", UserTypeEnum.toMap());
			this.putData("RoleTypeEnum", RoleTypeEnum.toMap());
			
			return "pmsUserEdit";
		} catch (Exception e) {
			log.error("==== error ==== 进入用户修改页面失败：", e);
			return operateError("获取修改数据失败");
		}
	}

	/**
	 * @Description: 保存修改后的用户信息
	 * @param @return    
	 * @return String
	 */
	@Permission("pms:user:edit")
	public String pmsUserUpdate() {
		try {
			Long id = getLong("id");

			PmsUser pmsUser = pmsUserBiz.getById(id);
			if (pmsUser == null) {
				return operateError("无法获取要修改的用户信息");
			}

			// 普通用户没有修改超级管理员的权限
			if (UserTypeEnum.USER.getValue() == this.getLoginedUser().getType() && UserTypeEnum.ADMIN.getValue()==pmsUser.getType()) {
				return operateError("权限不足");
			}

			pmsUser.setRemark(getString("remark"));
			// 修改时不能修状态
			// pmsUser.setStatus(getInteger("status"));

			String roleUserStr = getRoleUserStr();
			String newStr = "";
			StringBuffer oldRoleNameBuffer = new StringBuffer();
			// 查询用户原有的角色
			List<PmsRoleUser> list = pmsUserBiz.listRoleUserByUserId(id);
			for (PmsRoleUser ro : list) {
				if (newStr == null || "".equals(newStr) ) {
					newStr += ro.getRoleId();
				} else {
					newStr += "," + ro.getRoleId();
				}
				PmsRole role = pmsRoleBiz.getById(ro.getRoleId());
				oldRoleNameBuffer.append(role.getRoleName()).append(",");
			}

			// StringBuffer newRoleNameBuffer = new StringBuffer();
			// String[] newRoleIdList = roleUserStr.split(",");
			// for (String roleId : newRoleIdList) {
			// PmsRole role = pmsRoleBiz.getById(Long.valueOf(roleId));
			// newRoleNameBuffer.append(role.getRoleName()).append(",");
			// }

			// 表单数据校验
			String validateMsg = validatePmsUser(pmsUser, roleUserStr);
			if (StringUtils.isNotBlank(validateMsg)) {
				return operateError(validateMsg); // 返回错误信息
			}

			pmsUserBiz.updateUser(pmsUser, roleUserStr);
			log.info("==== info ==== 修改用户【"+pmsUser.getLoginName()+"】成功");
			return operateSuccess();
		} catch (Exception e) {
			log.error("==== error ==== 修改用户失败", e);
			return operateError("更新用户信息失败");
		}
	}


	/**
	 * @Description: 重置用户的密码（注意：不是修改当前登录用户自己的密码） .
	 * @param @return    
	 * @return String
	 */
	@Permission("pms:user:edit")
	public String pmsUserResetPwd() {
		PmsUser user = pmsUserBiz.getById(getLong("id"));
		if (user == null) {
			return operateError("无法获取要重置的信息");
		}

		// 普通用户没有修改超级管理员的权限
		if (UserTypeEnum.USER.getValue() == this.getLoginedUser().getType() && UserTypeEnum.ADMIN.getValue() == user.getType()) {
			return operateError("你没有修改超级管理员的权限");
		}

		this.putData("userId", user.getId());
		this.pushData(user);

		return "pmsUserResetPwd";
	}

	/**
	 * @Description: 重置用户密码.
	 * @param @return    
	 * @return String
	 */
	@Permission("pms:user:edit")
	public String resetUserPwd() {
		try {
			Long userId = getLong("userId");
			PmsUser user = pmsUserBiz.getById(userId);
			if (user == null) {
				return operateError("无法获取要重置密码的用户信息");
			}

			// 普通用户没有修改超级管理员的权限
			if ("0".equals(this.getLoginedUser().getType()) && "1".equals(user.getType())) {
				return operateError("你没有修改超级管理员的权限");
			}

			String newPwd = getString("newPwd");
			String newPwd2 = getString("newPwd2");

			/*if (!loginPwdFormat(newPwd)) {
				return operateError("登录密码必须由字母、数字、特殊符号组成");
			}*/

			String validateMsg = validatePassword(newPwd, newPwd2);
			if (StringUtils.isNotBlank(validateMsg)) {
				return operateError(validateMsg); // 返回错误信息
			}
			
			pmsUserBiz.updateUserPwd(userId, DigestUtils.sha1Hex(newPwd), 101);

			return operateSuccess();
		} catch (Exception e) {
			log.error("==== error ==== 重置用户密码失败：", e);
			return operateError("密码重置出错:" + e.getMessage());
		}
	}

	/**
	 * @Description: 进入重置当前登录用户自己的密码的页面.
	 * @param @return    
	 * @return String
	 */
	public String pmsUserChangeOwnPwd() {
		return "pmsUserChangeOwnPwd";
	}

	/**
	 * @Description: 重置当前登录用户自己的密码.
	 * @param @return    
	 * @return String
	 */
	public String userChangeOwnPwd() {
		try {

			PmsUser user = this.getLoginedUser();
			if (user == null) {
				return operateError("无法从会话中获取用户信息");
			}

			// 判断旧密码是否正确
			String oldPwd = getString("oldPwd");
			if (StringUtils.isBlank(oldPwd)) {
				return operateError("请输入旧密码");
			}
			// 旧密码要判空，否则sha1Hex会出错
			if (!user.getLoginPwd().equals(DigestUtils.sha1Hex(oldPwd))) {
				return operateError("旧密码不正确");
			}

			// 校验新密码
			String newPwd = getString("newPwd");
			if (oldPwd.equals(newPwd)) {
				return operateError("新密码不能与旧密码相同");
			}

			/*if (!loginPwdFormat(newPwd)) {
				return operateError("登录密码必须由字母、数字、特殊符号组成");
			}*/

			String newPwd2 = getString("newPwd2");
			String validateMsg = validatePassword(newPwd, newPwd2);
			if (StringUtils.isNotBlank(validateMsg)) {
				return operateError(validateMsg); // 返回错误信息
			}

			// 更新密码
			pmsUserBiz.updateUserPwd(user.getId(), DigestUtils.sha1Hex(newPwd), 100);

			// 修改密码成功后要清空session，以强制重新登录
			// getSessionMap().remove(ConstantSession.OPERATOR_SESSION_KEY);
			// getSessionMap().remove(ConstantSession.ACTIONS_SESSION_KEY);
			// getSessionMap().clear();


			return operateSuccess("密码修改成功，请重新登录!");
		} catch (Exception e) {
			log.error("==== error ==== 用户重置自己的密码失败：", e);
			return operateError("修改密码出错:" + e.getMessage());
		}
	}

	/**
	 * @Description: 当前登录的用户查看自己帐号的详细信息.
	 * @param @return    
	 * @return String
	 */
	public String pmsUserViewOwnInfo() {
		try {

			PmsUser pmsUser = this.getLoginedUser();
			if (pmsUser == null) {
				return operateError("无法从会话中获取用户信息");
			}

			PmsUser user = pmsUserBiz.getById(pmsUser.getId());
			if (user == null) {
				return operateError("无法获取用户信息");
			}

			this.pushData(user);

			return "pmsUserViewOwnInfo";
		} catch (Exception e) {
			log.error("==== error ==== 查看自己的用户信息失败", e);
			return operateError("无法获取要修改的用户信息失败");
		}
	}

	/**
	 * @Description: 得到角色和用户关联的ID字符串
	 * @param @return
	 * @param @throws Exception    
	 * @return String
	 */
	private String getRoleUserStr() throws Exception {
		String roleStr = getString("selectVal");
		if (StringUtils.isNotBlank(roleStr) && roleStr.length() > 0) {
			roleStr = roleStr.substring(0, roleStr.length() - 1);
		}
		return roleStr;
	}

	/**
	 * @Description: 验证重置密码
	 * @param @param newPwd
	 * @param @param newPwd2
	 * @param @return    
	 * @return String
	 */
	private String validatePassword(String newPwd, String newPwd2) {
		String msg = ""; // 用于存放校验提示信息的变量
		if (StringUtils.isBlank(newPwd)) {
			msg += "新密码不能为空，";
		}/* else if (newPwd.length() < 6) {
			msg += "新密码不能少于6位长度，";
		}*/

		if (!newPwd.equals(newPwd2)) {
			msg += "两次输入的密码不一致";
		}
		return msg;
	}
}