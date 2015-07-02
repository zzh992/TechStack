package com.techstack.sms2.course.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.techstack.sms2.base.page.PageBean;
import com.techstack.sms2.base.struts.BaseAction;
import com.techstack.sms2.course.biz.CourseBiz;
import com.techstack.sms2.course.entity.Course;
import com.techstack.sms2.course.enums.CourseTypeEnum;

/**
 * @Title: CourseAction.java 
 * @Description: 课程管理ACTION
 * @author zzh
 */
public class CourseAction extends BaseAction{

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(CourseAction.class);
	
	@Autowired
	private CourseBiz courseBiz;
	
	/**
	 * @Description: 进入课程管理页面
	 * @param @return    
	 * @return String
	 */
	public String courseList(){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("courseName", getString("courseName"));
		paramMap.put("courseType", getInteger("courseType"));
		paramMap.put("module", "courseList");
		PageBean pageBean = courseBiz.listPage(getPageParam(), paramMap, Course.class);
		this.pushData(pageBean);
		this.pushData(paramMap);
		this.putData("courseTypeEnumList", CourseTypeEnum.toList());
		return "courseList";
	}
	
	/**
	 * @Description: 进入课程管理新增页面
	 * @param @return    
	 * @return String
	 */
	public String courseAdd(){
		this.putData("courseTypeEnumList", CourseTypeEnum.toList());
		return "courseAdd";
	}
	
	/**
	 * @Description: 课程管理新增保存
	 * @param @return    
	 * @return String
	 */
	public String courseSave(){
		Course course = new Course();
		course.setCourseName(getString("courseName"));
		course.setCourseType(getInteger("courseType"));
		course.setDescride(getString("descride"));
		String score = getString("score");
		if(score == null){
			return operateError("学分不能为空");
		}
		course.setScore(new BigDecimal(score));
		courseBiz.saveOrUpdate(course);
		log.info("==== info ==== 新增课程【"+course.getCourseName()+"】成功");
		return operateSuccess();
	}
	
	/**
	 * @Description: 进入课程管理修改页面
	 * @param @return    
	 * @return String
	 */
	public String courseEdit(){
		Long courseId = getLong("id");
		Course course = courseBiz.getById(Course.class, courseId);
		this.pushData(course);
		this.putData("courseTypeEnumList", CourseTypeEnum.toList());
		return "courseEdit";
	}
	
	/**
	 * @Description: 课程管理修改保存
	 * @param @return    
	 * @return String
	 */
	public String courseUpdate(){
		Long courseId = getLong("id");
		Course course = courseBiz.getById(Course.class, courseId);
		course.setCourseName(getString("courseName"));
		course.setCourseType(getInteger("courseType"));
		course.setDescride(getString("descride"));
		String score = getString("score");
		if(score == null){
			return operateError("学分不能为空");
		}
		course.setScore(new BigDecimal(score));
		courseBiz.saveOrUpdate(course);
		log.info("==== info ==== 课程【"+course.getCourseName()+"】修改成功");
		return operateSuccess();
	}
	
	/**
	 * @Description: 课程管理删除
	 * @param     
	 * @return void
	 */
	public void courseDel(){
		Long courseId = getLong("id");
		if(!courseBiz.judgeCourseSelect(courseId)){
			courseBiz.deleteById(Course.class, courseId);
			getOutputMsg().put("STATE", "SUCCESS");
			getOutputMsg().put("MSG", "删除成功");
		}else{
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "该课程已有人选择，不能删除");
		}
		outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}
}
