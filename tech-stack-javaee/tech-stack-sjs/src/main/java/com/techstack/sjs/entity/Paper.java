package com.techstack.sjs.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

@Entity
public class Paper extends BaseEntity {

	private static final long serialVersionUID = 3522574677177568157L;
	private Long totalScore;
	private Long questionNumber;
	private Set<ExamineePaper> examineePaper;
	private Set<PaperQuestion> paperQuestion;
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Long totalScore) {
		this.totalScore = totalScore;
	}

	public Long getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(Long questionNumber) {
		this.questionNumber = questionNumber;
	}

	@OneToMany(mappedBy = "paper", fetch = FetchType.EAGER)
	public Set<ExamineePaper> getExamineePaper() {
		return examineePaper;
	}

	public void setExamineePaper(Set<ExamineePaper> examineePaper) {
		this.examineePaper = examineePaper;
	}

	@OneToMany(mappedBy = "paper", fetch = FetchType.EAGER)
	@Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	public Set<PaperQuestion> getPaperQuestion() {
		return paperQuestion;
	}

	public void setPaperQuestion(Set<PaperQuestion> paperQuestion) {
		this.paperQuestion = paperQuestion;
	}

}
