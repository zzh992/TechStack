package com.techstack.sms2.course.entity;

import java.math.BigDecimal;

import com.techstack.sms2.base.annotation.mybatis.Column;
import com.techstack.sms2.base.annotation.mybatis.Table;
import com.techstack.sms2.base.entity.BaseEntity;

/**
 * @Title: Major.java 
 * @Description: 专业实体类
 * @author zzh
 */
@Table(name="MAJOR")
public class Major extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**	专业名称	*/
	@Column(name="MAJOR_NAME")
	private String majorName;
	
	/**	所需学分	*/
	@Column(name="NEED_SCORE")
	private BigDecimal needScore;
	
	/**	描述	*/
	@Column(name="DESCRIDE")
	private String descride;

	/**
	 * @return 专业名称
	 */
	public String getMajorName() {
		return majorName;
	}

	/**
	 * @param 专业名称
	 */
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	
	/**
	 * @return 所需学分
	 */
	public BigDecimal getNeedScore() {
		return needScore;
	}

	/**
	 * @param 所需学分
	 */
	public void setNeedScore(BigDecimal needScore) {
		this.needScore = needScore;
	}

	/**
	 * @return 描述
	 */
	public String getDescride() {
		return descride;
	}

	/**
	 * @param 描述
	 */
	public void setDescride(String descride) {
		this.descride = descride;
	} 
	
	

}
