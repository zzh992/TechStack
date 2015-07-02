package com.techstack.sms2.course.entity;

import com.techstack.sms2.base.annotation.mybatis.Column;
import com.techstack.sms2.base.annotation.mybatis.Table;
import com.techstack.sms2.base.entity.BaseEntity;

/**
 * @Title: MajorCourse.java 
 * @Description: 专业-课程关联实体
 * @author zzh
 */
@Table(name="MAJOR_COURSE")
public class MajorCourse extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	/**	专业ID	*/
	@Column(name="MAJOR_ID")
	private Long majorId;
	
	/**	课程ID	*/
	@Column(name="COURSE_ID")
	private Long courseId;

	/**
	 * @return 专业ID
	 */
	public Long getMajorId() {
		return majorId;
	}

	/**
	 * @param 专业ID
	 */
	public void setMajorId(Long majorId) {
		this.majorId = majorId;
	}

	/**
	 * @return 课程ID
	 */
	public Long getCourseId() {
		return courseId;
	}

	/**
	 * @param 课程ID
	 */
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	
	
}
