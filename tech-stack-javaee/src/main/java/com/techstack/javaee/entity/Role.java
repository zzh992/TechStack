package com.techstack.javaee.entity;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "JAVA_ROLE")
public class Role extends IdEntity {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
