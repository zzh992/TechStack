package com.techstack.sjs.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
@Entity
public class LoginLog extends BaseEntity {

	private static final long serialVersionUID = -5506621756441916293L;

	private String userName;
	private String role;
	private Long failLoginNum;
	private Date time;
	private User user;
	
	@OneToOne
	@JoinColumn(name = "userId")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Long getFailLoginNum() {
		return failLoginNum;
	}
	public void setFailLoginNum(Long failLoginNum) {
		this.failLoginNum = failLoginNum;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	
}
