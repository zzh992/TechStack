package com.techstack.pms.dao.mybatis.entity;

import com.techstack.component.mybatis.annotation.Column;
import com.techstack.component.mybatis.annotation.Table;


/**
 * @Title: PmsRoleAction.java 
 * @Description: 角色权限关联表
 * @author zzh
 */
@Table(name="PMS_ROLE_ACTION")
public class PmsRoleAction extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**	角色ID	*/
	@Column(name="ROLE_ID")
	private Long roleId;
	
	/**	权限ID	*/
	@Column(name="ACTION_ID")
	private Long actionId;

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
	 * @return 权限ID
	 */
	public Long getActionId() {
		return actionId;
	}

	/**
	 * @param 权限ID
	 */
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	} 



}
