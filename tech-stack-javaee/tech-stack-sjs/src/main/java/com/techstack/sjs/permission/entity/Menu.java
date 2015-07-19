package com.techstack.sjs.permission.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.techstack.sjs.base.entity.IdEntity;

@Entity
@Table(name = "PMS_MENU")
public class Menu extends IdEntity {
	
	@Column(name="NAME")
	private String name; 

	@Column(name="URL")
	private String url;

	@ManyToOne
	@JoinColumn(name = "PARENT_ID", referencedColumnName = "ID")
	private Menu parentMenu;
	
	@Column(name="NUMBER")
	private String number;
	
	@Column(name="IS_LEAF")
	private Integer isLeaf;
	
	@Column(name="LEVEL")
	private Integer level;
	
	@Column(name="TARGET_NAME")
	private String targetName;

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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	
}
