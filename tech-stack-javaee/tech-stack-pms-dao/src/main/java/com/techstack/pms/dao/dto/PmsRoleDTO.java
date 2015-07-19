package com.techstack.pms.dao.dto;



/**
 * @Title: PmsRole.java 
 * @Description: 角色实体
 * @author zzh
 */
public class PmsRoleDTO extends PmsBaseDTO {

	private static final long serialVersionUID = 1L;
	
	/**	角色类型，对于枚举（RoleTypeEnum）	*/
	private Integer roleType; 
	
	/**	角色名称	*/
	private String roleName; 
	
	/**	描述	*/
	private String remark;

	

	/**
	 * @return 角色类型，对于枚举（RoleTypeEnum）
	 */
	public Integer getRoleType() {
		return roleType;
	}

	/**
	 * @param 角色类型，对于枚举（RoleTypeEnum）
	 */
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return 角色名称
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param 角色名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	

}
