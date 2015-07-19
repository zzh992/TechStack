package com.techstack.pms.dao.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

import com.techstack.component.mybatis.annotation.Column;

/**
 * @Title: BaseEntity.java 
 * @Description: 基础实体类：包含基础属性
 * @author zzh
 */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer version = 0;
	/**
	 * 创建时间
	 */
	protected Date createTime = new Date();
	
	@Column(name="ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="VERSION")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name="CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
