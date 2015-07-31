package com.techstack.pms.dao.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PMS_ACTION")
public class Action extends BaseEntity {

	private String actionName; 

	/** Permission identification */
	private String action;
	
	private String remark;
	
	private List<Role> roles = new ArrayList<Role>();

	private Menu relevantMenu;
	
	private String menuName;

	@Column(name="ACTION_NAME")
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	@Column(name="ACTION")
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Column(name="REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToOne
	@JoinColumn(name = "MENU_ID", referencedColumnName = "ID")
	public Menu getRelevantMenu() {
		return relevantMenu;
	}

	public void setRelevantMenu(Menu relevantMenu) {
		this.relevantMenu = relevantMenu;
	}

	@Column(name="MENU_NAME")
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@ManyToMany(mappedBy = "actions")
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
