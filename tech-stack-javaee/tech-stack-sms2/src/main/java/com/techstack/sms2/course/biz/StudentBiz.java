package com.techstack.sms2.course.biz;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.techstack.sms2.course.entity.Student;
import com.techstack.sms2.course.entity.StudentCourse;

/**
 * @Title: StudentBiz.java 
 * @Description: 学生业务类
 * @author zzh
 */
@Service("studentBiz")
public class StudentBiz extends BaseBiz{
	
	/**
	 * @Description: 根据UserId查找学生，可判断学生用户是否有验证
	 * @param @param userId
	 * @param @return    
	 * @return Student
	 */
	public Student findStudentByUserId(Long userId){
		return getBaseDao().selectOne(getStatement("findStudentByUserId"), userId);
	}
	
	/**
	 * @Description: 学生选择一门课程
	 * @param @param stuId
	 * @param @param courseId    
	 * @return void
	 */
	public void selectCourse(Long stuId, Long courseId){
		StudentCourse sc = new StudentCourse();
		sc.setStudentId(stuId);
		sc.setCourseId(courseId);
		getBaseDao().saveOrUpdate(sc);
	}
	
	/**
	 * @Description: 汇总学生选择的专业课的总学分
	 * @param @param stuId
	 * @param @return    
	 * @return BigDecimal
	 */
	public BigDecimal getScoreSumForStudent(Long stuId){
		BigDecimal sum = getBaseDao().selectOne(getStatement("getScoreSumForStudent"), stuId);
		return sum;
	}
}
