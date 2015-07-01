package com.techstack.sms2.permission.entity;

import com.techstack.sms2.base.annotation.mybatis.Column;
import com.techstack.sms2.base.annotation.mybatis.Table;
import com.techstack.sms2.base.entity.BaseEntity;


/**
 * @Title: PmsRole.java 
 * @Description: 角色实体
 * @author zzh
 */
@Table(name="PMS_ROLE")
public class PmsRole extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**	角色类型，对于枚举（RoleTypeEnum）	*/
	@Column(name="ROLE_TYPE")
	private Integer roleType; 
	
	/**	角色名称	*/
	@Column(name="ROLE_NAME")
	private String roleName; 
	
	/**	描述	*/
	@Column(name="REMARK")
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
