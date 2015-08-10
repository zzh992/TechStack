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
@Table(name = "PMS_USER")
public class User extends BaseEntity {

	private String loginName;

	private String loginPwd;

	private String remark;

	private Integer type;

	private List<Role> roles = new ArrayList<Role>(); // 关联的角色可以多个，但是唯一

	// @Column(name="LOGINNAME",nullable=false,length=512)
	// @Column注解一共有10个属性，这10个属性均为可选属性;如果没有加Column，在JPA实现会自动生成默认的列名。http://www.tuicool.com/articles/3UFNbi
	@Column(name = "LOGIN_NAME")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "LOGIN_PWD")
	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "TYPE")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	// 多对多定义
	/**
	 * JoinTable 外表关联，joinColumns本表关联的字段，inverseJoinColumns另外一张表关联的字段,
	 * 有这个属性表示关联表的维护由User管理
	 * ，另外一个关联的从对象则通过mappedBy属性进行声明；http://blog.csdn.net/wangpeng047
	 * /article/details/8744063 referencedColumnName
	 * :属性referencedColumnName标注的是所关联表中的字段名,若不指定则使用的所关联表的主键字段名作为外键
	 * 
	 */
	@ManyToMany(fetch=FetchType.EAGER )
	@JoinTable(name = "PMS_ROLE_USER", joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
