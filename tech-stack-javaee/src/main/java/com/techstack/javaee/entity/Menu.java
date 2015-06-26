package com.techstack.javaee.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "JAVA_MENU")
public class Menu extends IdEntity {

	private String name;
	
	private String url;
	
	@ManyToOne
	@JoinColumn(name = "PARENT_ID",referencedColumnName = "ID")
	private Menu parentMenu;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}

	
}
