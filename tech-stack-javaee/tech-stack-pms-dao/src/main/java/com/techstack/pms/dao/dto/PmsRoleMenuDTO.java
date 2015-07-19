package com.techstack.pms.dao.dto;



/**
 * @Title: PmsRoleMenu.java 
 * @Description: 角色菜单关联
 * @author zzh
 */
public class PmsRoleMenuDTO extends PmsBaseDTO {

	private static final long serialVersionUID = 1L;
	
	/**	角色ID	*/
	private Long roleId; 
	
	/**	 菜单ID	*/
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
