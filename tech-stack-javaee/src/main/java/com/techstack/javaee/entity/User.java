package com.techstack.javaee.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.google.common.collect.Sets;



@Entity
@Table(name = "JAVA_USER")
public class User extends IdEntity {
	
	//@Column(name="LOGINNAME",nullable=false,length=512) @Column注解一共有10个属性，这10个属性均为可选属性;如果没有加Column，在JPA实现会自动生成默认的列名。http://www.tuicool.com/articles/3UFNbi
	private String loginName;
	
	private String name;
	
	private String password;
	
	private String salt; //如果直接对密码进行散列，那么黑客可以对通过获得这个密码散列值，然后通过查散列值字典（例如MD5密码破解网站），得到某用户的密码。http://www.2cto.com/Article/201201/117051.html
	
	private Date registerDate;
	
	// 多对多定义
	/**
	 * @JoinTable 外表关联，joinColumns本表关联的字段，inverseJoinColumns另外一张表关联的字段,有这个属性表示关联表的维护有role管理
	 * ，另外一个关联的从对象则通过mappedBy属性进行声明；http://blog.csdn.net/wangpeng047/article/details/8744063
	 * referencedColumnName :  属性referencedColumnName标注的是所关联表中的字段名,若不指定则使用的所关联表的主键字段名作为外键
	 * */
	@ManyToMany
	@JoinTable(name = "JAVA_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
	private Set<Role> roles = Sets.newHashSet();	//关联的角色可以多个，但是唯一

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	
}
