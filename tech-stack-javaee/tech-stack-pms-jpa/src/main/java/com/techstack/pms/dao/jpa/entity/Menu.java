package com.techstack.pms.dao.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "PMS_MENU")
public class Menu extends BaseEntity {
	
	private String name; 

	private String url;

	private Menu parentMenu;
	
	private String number;
	
	private Integer isLeaf;
	
	private Integer level;
	
	private String targetName;

	@Column(name="NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="URL")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@ManyToOne
	@JoinColumn(name = "PARENT_ID", nullable=true, referencedColumnName = "ID")	//TODO: 要允许parentMenu为null,但是nullable=true设置不起效
	@NotFound(action = NotFoundAction.IGNORE) //当预期的被关联的元素不在数据库(关乎关联列的错误id)时，致使Hibernate无法解决关联性问题时，Hibernate就会抛出异常，使用@ NotFound可以让Hibernate忽略这样的元素而不抛出异常。
	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}

	@Column(name="NUMBER")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name="IS_LEAF")
	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Column(name="LEVEL")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name="TARGET_NAME")
	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	
}
