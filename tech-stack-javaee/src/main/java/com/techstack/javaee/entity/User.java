package com.techstack.javaee.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "JAVA_USER")
public class User extends IdEntity {
	
	//@Column(name="LOGINNAME",nullable=false,length=512) @Column注解一共有10个属性，这10个属性均为可选属性;如果没有加Column，在JPA实现会自动生成默认的列名。http://www.tuicool.com/articles/3UFNbi
	private String loginName;
	
	private String name;
	
	private String password;
	
	private String salt; //如果直接对密码进行散列，那么黑客可以对通过获得这个密码散列值，然后通过查散列值字典（例如MD5密码破解网站），得到某用户的密码。http://www.2cto.com/Article/201201/117051.html
	
	private Date registerDate;

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
	

}
