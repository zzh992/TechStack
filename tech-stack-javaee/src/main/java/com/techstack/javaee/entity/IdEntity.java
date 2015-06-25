package com.techstack.javaee.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * Oracle需要每个Entity独立定义id的SEQUCENCE时，不继承于本类而改为实现一个Idable的接口。
 * 
 */

//JPA 基类的标识
@MappedSuperclass
public abstract class IdEntity {
	protected Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//TABLE：使用一个特定的数据库表格来保存主键。 SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。 IDENTITY：主键由数据库自动生成（主要是自动增长型） AUTO：主键由程序控制
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
