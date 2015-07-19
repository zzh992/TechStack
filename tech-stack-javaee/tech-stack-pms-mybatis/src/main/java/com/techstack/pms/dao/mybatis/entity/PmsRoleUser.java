package com.techstack.pms.dao.mybatis.entity;

import com.techstack.component.mybatis.annotation.Column;
import com.techstack.component.mybatis.annotation.Table;


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
