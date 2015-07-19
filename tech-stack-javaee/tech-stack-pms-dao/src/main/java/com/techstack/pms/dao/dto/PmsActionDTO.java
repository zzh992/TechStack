package com.techstack.pms.dao.dto;



/**
 * @Title: PmsAction.java 
 * @Description: 权限实体类
 * @author zzh
 */
public class PmsActionDTO extends PmsBaseDTO {

	private static final long serialVersionUID = 1L;
	
	/**	权限名称	*/
	private String actionName; 
	
	/**	权限标识	*/
	private String action;
	
	/**	权限描述	*/
	private String remark;
	
	/**	关联菜单	*/
	private Long menuId;
	
	/**	关联菜单名称	*/
	private String menuName;

	/**
	 * @return 权限名称
	 */
	public String getActionName() {
		return actionName;
	}

	/**
	 * @param 权限名称
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * @return 权限标识
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param 权限标识
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return 权限描述
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param 权限描述
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return 关联菜单
	 */
	public Long getMenuId() {
		return menuId;
	}

	/**
	 * @param 关联菜单
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return 关联菜单名称
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * @param 关联菜单名称
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	

}
