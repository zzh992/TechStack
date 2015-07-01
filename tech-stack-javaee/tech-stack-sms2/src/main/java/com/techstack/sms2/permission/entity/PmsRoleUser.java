package com.techstack.sms2.permission.entity;

import com.techstack.sms2.base.annotation.mybatis.Column;
import com.techstack.sms2.base.annotation.mybatis.Table;
import com.techstack.sms2.base.entity.BaseEntity;


/**
 * @Title: PmsRoleUser.java 
 * @Description: 角色用户关联表
 * @author zzh
 */
@Table(name="PMS_ROLE_USER")
public class PmsRoleUser extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**	角色ID	*/
	@Column(name="ROLE_ID")
	private Long roleId;
	
	/**	用户ID	*/
	@Column(name="USER_ID")
	private Long userId;

	/**
	 * @return 角色ID
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param 角色ID
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return 用户ID
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param 用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}



}
