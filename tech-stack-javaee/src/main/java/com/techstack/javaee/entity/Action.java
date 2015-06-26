package com.techstack.javaee.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "JAVA_ACTION")
public class Action extends IdEntity {
	
	private String actionName; 
	
	/**    权限标识    */
	private String action;
	
	@OneToOne
	@JoinColumn(name = "MENU_ID",referencedColumnName = "ID")
	private Menu relevantMenu;

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Menu getRelevantMenu() {
		return relevantMenu;
	}

	public void setRelevantMenu(Menu relevantMenu) {
		this.relevantMenu = relevantMenu;
	}

}
