package com.techstack.pms.dao.dto;



/**
 * @Title: PmsRoleUser.java 
 * @Description: 角色用户关联表
 * @author zzh
 */
public class PmsRoleUserDTO extends PmsBaseDTO {

	private static final long serialVersionUID = 1L;
	
	/**	角色ID	*/
	private Long roleId;
	
	/**	用户ID	*/
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
