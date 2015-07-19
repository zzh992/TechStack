package com.techstack.sjs.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class User extends BaseEntity {

	private static final long serialVersionUID = 6608424063616720491L;

	private String userName;
	private String loginName;
	private String password;
	private String role;
	private Examinee examinee;
    private LoginLog loginlog;
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@OneToOne(mappedBy = "user")
	public Examinee getExaminee() {
		return examinee;
	}

	public void setExaminee(Examinee examinee) {
		this.examinee = examinee;
	}
	@OneToOne(mappedBy = "user")
	public LoginLog getLoginlog() {
		return loginlog;
	}

	public void setLoginlog(LoginLog loginlog) {
		this.loginlog = loginlog;
	}

}
