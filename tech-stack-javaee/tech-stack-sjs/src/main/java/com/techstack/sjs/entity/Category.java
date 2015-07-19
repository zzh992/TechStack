package com.techstack.sjs.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Category extends BaseEntity {

	private static final long serialVersionUID = -8969971869595554597L;

	private String categoryName;
	private Set<Question> question;

	private String description;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@OneToMany(mappedBy = "category")
	public Set<Question> getQuestion() {
		return question;
	}

	public void setQuestion(Set<Question> question) {
		this.question = question;
	}

	public void setDescription(String description) {
		this.description = description;

	}
}
