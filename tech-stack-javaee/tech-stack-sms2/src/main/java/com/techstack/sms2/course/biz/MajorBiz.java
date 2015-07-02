package com.techstack.sms2.course.biz;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techstack.sms2.course.entity.Major;
import com.techstack.sms2.course.entity.MajorCourse;

/**
 * @Title: MajorBiz.java 
 * @Description: 专业业务类
 * @author zzh
 */
@Service("majorBiz")
public class MajorBiz extends BaseBiz{
	
	/**
	 * @Description: 查询所有专业
	 * @param @return    
	 * @return List<Major>
	 */
	public List<Major> findAllMajor(){
		return getBaseDao().getByModel(new Major());
	}
	
	/**
	 * @Description: 查看专业下是否有某门课程
	 * @param @param courseId
	 * @param @param majorId
	 * @param @return    
	 * @return List<MajorCourse>
	 */
	public List<MajorCourse> findMajorCourse(Long courseId,Long majorId){
		MajorCourse majorCourse = new MajorCourse();
		majorCourse.setCourseId(courseId);
		majorCourse.setMajorId(majorId);
		return getBaseDao().getByModel(majorCourse);
	}
	
	/**
	 * @Description: 添加课程到专业下
	 * @param @param courseId
	 * @param @param majorId    
	 * @return void
	 */
	public void majorCourseSave(Long courseId,Long majorId){
		MajorCourse majorCourse = new MajorCourse();
		majorCourse.setCourseId(courseId);
		majorCourse.setMajorId(majorId);
		getBaseDao().saveOrUpdate(majorCourse);
	}
	
}
