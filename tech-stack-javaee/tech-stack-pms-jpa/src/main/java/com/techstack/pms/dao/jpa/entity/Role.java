package com.techstack.pms.dao.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PMS_ROLE")
public class Role extends BaseEntity {

	private String roleName; 
	
	private Integer roleType; 
	
	private String remark;

	private List<User> users = new ArrayList<User>();

	private List<Menu> menus = new ArrayList<Menu>();

	private List<Action> actions = new ArrayList<Action>();

	@Column(name="ROLE_NAME")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name="ROLE_TYPE")
	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	@Column(name="REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * mappedBy: 定义类之间的双向关系。如果类之间是单向关系，不需要提供定义，如果类和类之间形成双向关系，我们就需要使用这个属性进行定义，
	 * 否则可能引起数据一致性的问题。该属性的值是主体对象的属性名而不是表的列名
	 * 
	 */
	@ManyToMany(mappedBy = "roles", fetch=FetchType.LAZY)
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "PMS_ROLE_MENU", joinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "MENU_ID", referencedColumnName = "ID") })
	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "PMS_ROLE_ACTION", joinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "ACTION_ID", referencedColumnName = "ID") })
	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	

	
}
