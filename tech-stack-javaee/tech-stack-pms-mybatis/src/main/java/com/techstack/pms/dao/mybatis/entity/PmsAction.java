package com.techstack.pms.dao.mybatis.entity;

import com.techstack.component.mybatis.annotation.Column;
import com.techstack.component.mybatis.annotation.Table;


/**
 * @Title: PmsAction.java 
 * @Description: 权限实体类
 * @author zzh
 */
@Table(name="PMS_ACTION")
public class PmsAction extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**	权限名称	*/
	@Column(name="ACTION_NAME")
	private String actionName; 
	
	/**	权限标识	*/
	@Column(name="ACTION")
	private String action;
	
	/**	权限描述	*/
	@Column(name="REMARK")
	private String remark;
	
	/**	关联菜单	*/
	@Column(name="MENU_ID")
	private Long menuId;
	
	/**	关联菜单名称	*/
	@Column(name="MENU_NAME")
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
