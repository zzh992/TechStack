package com.techstack.sms2.course.entity;

import com.techstack.sms2.base.annotation.mybatis.Column;
import com.techstack.sms2.base.annotation.mybatis.Table;
import com.techstack.sms2.base.entity.BaseEntity;

/**
 * @Title: StudentCourse.java 
 * @Description: 学生-课程管理实体
 * @author zzh
 */
@Table(name="STUDENT_COURSE")
public class StudentCourse extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**	学生ID	*/
	@Column(name="STUDENT_ID")
	private Long studentId;
	
	/**	课程ID	*/
	@Column(name="COURSE_ID")
	private Long courseId;

	/**
	 * @return 学生ID
	 */
	public Long getStudentId() {
		return studentId;
	}

	/**
	 * @param 学生ID
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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
