package com.techstack.pms.dao.mybatis.entity;

import com.techstack.component.mybatis.annotation.Column;
import com.techstack.component.mybatis.annotation.Table;


/**
 * @Title: PmsRoleMenu.java 
 * @Description: 角色菜单关联
 * @author zzh
 */
@Table(name="PMS_ROLE_MENU")
public class PmsRoleMenu extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**	角色ID	*/
	@Column(name="ROLE_ID")
	private Long roleId; 
	
	/**	 菜单ID	*/
	@Column(name="MENU_ID")
	private Long menuId;

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
	 * @return  菜单ID
	 */
	public Long getMenuId() {
		return menuId;
	}

	/**
	 * @param  菜单ID
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	} 


}
