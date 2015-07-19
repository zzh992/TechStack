package com.techstack.sjs.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Question extends BaseEntity {

	private static final long serialVersionUID = 2193920911822384460L;
	private String answer;
	private Set<PaperQuestion> paperQuestion;
	private Category category;
	private Set<Choice> choice;
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@OneToMany(mappedBy = "question")
	public Set<PaperQuestion> getPaperQuestion() {
		return paperQuestion;
	}

	public void setPaperQuestion(Set<PaperQuestion> paperQuestion) {
		this.paperQuestion = paperQuestion;
	}

	@ManyToOne
	@JoinColumn(name = "categoryId")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@OneToMany(mappedBy = "question")
	public Set<Choice> getchoice() {
		return choice;
	}

	public void setchoice(Set<Choice> choice) {
		this.choice = choice;
	}

}
