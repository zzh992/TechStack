package com.techstack.sjs.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Examinee extends BaseEntity {

	private static final long serialVersionUID = -3774826040479991051L;
	private String jobTitle;
	private String department;
	private String certificate;
	private String gender;
	private String entryTime;
	private User user;
	private Set<ExamineePaper> examineePaper;

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	@OneToOne
	@JoinColumn(name = "userId")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToMany(mappedBy = "examinee")
	public Set<ExamineePaper> getExamineePaper() {
		return examineePaper;
	}

	public void setExamineePaper(Set<ExamineePaper> examineePaper) {
		this.examineePaper = examineePaper;
	}

}
