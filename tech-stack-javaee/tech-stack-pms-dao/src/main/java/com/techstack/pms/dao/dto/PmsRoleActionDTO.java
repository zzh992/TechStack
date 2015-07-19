package com.techstack.pms.dao.dto;



/**
 * @Title: PmsRoleAction.java 
 * @Description: 角色权限关联表
 * @author zzh
 */
public class PmsRoleActionDTO extends PmsBaseDTO {

	private static final long serialVersionUID = 1L;
	
	/**	角色ID	*/
	private Long roleId;
	
	/**	权限ID	*/
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
