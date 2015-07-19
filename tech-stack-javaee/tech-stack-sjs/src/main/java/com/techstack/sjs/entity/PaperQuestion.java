package com.techstack.sjs.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;

@Entity
public class PaperQuestion extends BaseEntity {

	private static final long serialVersionUID = -3800734303954713784L;
	private Long score;
	private Paper paper;
	private Question question;

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	@ManyToOne
	@JoinColumn(name = "paperId")
	@Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	@ManyToOne
	@JoinColumn(name = "questionId")
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}
