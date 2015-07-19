package com.techstack.pms.dao.mybatis.entity;

import com.techstack.component.mybatis.annotation.Column;
import com.techstack.component.mybatis.annotation.Table;


/**
 * @Title: PmsUser.java 
 * @Description: 用户实体
 * @author zzh
 */
@Table(name="PMS_USER")
public class PmsUser extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**	登录名	*/
	@Column(name="LOGIN_NAME")
	private String loginName;
	
	/**	登录密码	*/
	@Column(name="LOGIN_PWD")
	private String loginPwd; 
	
	/**	描述	*/
	@Column(name="REMARK")
	private String remark; 
	
	/**	操作员类型，对应枚举（UserTypeEnum），超级管理员由系统初始化时添加，不能删除	*/
	@Column(name="TYPE")
	private Integer type;
	

	/**
	 * @return 登录名
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param 登录名
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return 登录密码
	 */
	public String getLoginPwd() {
		return loginPwd;
	}

	/**
	 * @param 登录密码
	 */
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	/**
	 * @return 描述
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param 描述
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return  操作员类型，对应枚举（UserTypeEnum），超级管理员由系统初始化时添加，不能删除
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param  操作员类型，对应枚举（UserTypeEnum），超级管理员由系统初始化时添加，不能删除
	 */
	public void setType(Integer type) {
		this.type = type;
	}





}
