package com.techstack.pms.dao.dto;

import java.io.Serializable;
import java.util.Date;


public abstract class PmsBaseDTO implements Serializable{

	private static final long serialVersionUID = 4365371070062440538L;
	
	private Long id;
	
	private Integer version = 0;

	protected Date createTime = new Date();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
