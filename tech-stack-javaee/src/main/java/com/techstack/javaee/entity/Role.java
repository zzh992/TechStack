package com.techstack.javaee.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.google.common.collect.Sets;


@Entity
@Table(name = "JAVA_ROLE")
public class Role extends IdEntity {

	private String name;

	
	/**
	 * mappedBy: 定义类之间的双向关系。如果类之间是单向关系，不需要提供定义，如果类和类之间形成双向关系，我们就需要使用这个属性进行定义，
	 * 否则可能引起数据一致性的问题。该属性的值是主体对象的属性名而不是表的列名
	 * 
	 */
	@ManyToMany(mappedBy = "roles")
	private Set<User> users = Sets.newHashSet();
	
	@ManyToMany
	@JoinTable(name = "JAVA_ROLE_MENU", joinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "MENU_ID", referencedColumnName = "ID") })
	private Set<Menu> menus = Sets.newHashSet();
	
	@ManyToMany
	@JoinTable(name = "JAVA_ROLE_ACTION", joinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "ACTION_ID", referencedColumnName = "ID") })
	private Set<Action> actions = Sets.newHashSet();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	public Set<Action> getActions() {
		return actions;
	}

	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}
	
	
}
