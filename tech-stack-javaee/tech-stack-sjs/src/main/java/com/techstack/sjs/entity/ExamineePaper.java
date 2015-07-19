package com.techstack.sjs.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ExamineePaper extends BaseEntity {

	private static final long serialVersionUID = -7675795578922524755L;
	private String status;
	private Examinee examinee;
	private Paper paper;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ManyToOne
	@JoinColumn(name = "examineeId")
	public Examinee getExaminee() {
		return examinee;
	}

	public void setExaminee(Examinee examinee) {
		this.examinee = examinee;
	}

	@ManyToOne
	@JoinColumn(name = "paperId")
	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

}
