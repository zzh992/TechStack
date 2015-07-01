package com.techstack.sms2.course.entity;

import java.math.BigDecimal;

import com.techstack.sms2.base.annotation.mybatis.Column;
import com.techstack.sms2.base.annotation.mybatis.Table;
import com.techstack.sms2.base.entity.BaseEntity;

/**
 * @Title: Course.java 
 * @Description: 课程实体类
 * @author zzh
 */
@Table(name="COURSE")
public class Course extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**	课程名称	*/
	@Column(name="COURSE_NAME")
	private String courseName;
	
	/**	学分	*/
	@Column(name="SCORE")
	private BigDecimal score;
	
	/**	描述	*/
	@Column(name="DESCRIDE")
	private String descride;
	
	/**	课程类型	*/
	@Column(name="COURSE_TYPE")
	private Integer courseType;

	/**
	 * @return 课程名称
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param 课程名称
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * @return 学分
	 */
	public BigDecimal getScore() {
		return score;
	}

	/**
	 * @param 学分
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
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

	/**
	 * @return 课程类型
	 */
	public Integer getCourseType() {
		return courseType;
	}

	/**
	 * @param 课程类型
	 */
	public void setCourseType(Integer courseType) {
		this.courseType = courseType;
	}
	
	
	

}
