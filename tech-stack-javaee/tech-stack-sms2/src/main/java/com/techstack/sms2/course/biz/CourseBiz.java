package com.techstack.sms2.course.biz;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.techstack.sms2.base.page.PageBean;
import com.techstack.sms2.base.page.PageParam;
import com.techstack.sms2.course.entity.Course;
import com.techstack.sms2.course.entity.StudentCourse;

/**
 * @Title: CourseBiz.java 
 * @Description: 课程业务类
 * @author zzh
 */
@Service("courseBiz")
public class CourseBiz extends BaseBiz{
	
	/**
	 * @Description: 判断课程是否被选择
	 * @param @param courseId
	 * @param @return    
	 * @return boolean
	 */
	public boolean judgeCourseSelect(Long courseId){
		StudentCourse sc = new StudentCourse();
		sc.setCourseId(courseId);
		List<StudentCourse> scList = getBaseDao().getByModel(sc);
		if(scList.size() == 0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * @Description: 查看学生是否选了某课程，没有返回NULL，有则返回课程实体类
	 * @param @param courseId
	 * @param @param stuId
	 * @param @return    
	 * @return Course
	 */
	public Course findCourseByCosIdAndStuId(Long courseId,Long stuId){
		StudentCourse sc = new StudentCourse();
		sc.setCourseId(courseId);
		sc.setStudentId(stuId);
		List<StudentCourse> scList = getBaseDao().getByModel(sc);
		if(scList.size() == 0){
			return null;
		}else{
			return getBaseDao().getById(Course.class, courseId);
		}
	}
	
	/**
	 * @Description: 根据专业来查找下面的专业课
	 * @param @param pageParam
	 * @param @param majorId
	 * @param @return    
	 * @return PageBean
	 */
	public PageBean courseByMajorPage(PageParam pageParam,Long majorId,Map<String,Object> paramMap){
		paramMap.put("majorId", majorId);
		return getBaseDao().listPage(getStatement("courseByMajorPage"), pageParam, paramMap);
	}
	
	/**
	 * @Description: 查询学生可以选择的课程
	 * @param @param pageParam
	 * @param @param majorId
	 * @param @param paramMap
	 * @param @return    
	 * @return PageBean
	 */
	public PageBean courseCanSelectByMajorPage(PageParam pageParam,Long majorId,Map<String,Object> paramMap){
		paramMap.put("majorId", majorId);
		return getBaseDao().listPage(getStatement("courseCanSelectByMajorPage"), pageParam, paramMap);
	}
	
	/**
	 * @Description: 查询学生选择的课程
	 * @param @param pageParam
	 * @param @param studentId
	 * @param @return    
	 * @return PageBean
	 */
	public PageBean courseByStudentPage(PageParam pageParam,Long studentId,Map<String,Object> paramMap){
		paramMap.put("studentId", studentId);
		return getBaseDao().listPage(getStatement("courseByStudentPage"), pageParam, paramMap);
	}
	
}
