package com.techstack.sms2.course.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.techstack.sms2.base.page.PageBean;
import com.techstack.sms2.base.struts.BaseAction;
import com.techstack.sms2.course.biz.CourseBiz;
import com.techstack.sms2.course.biz.MajorBiz;
import com.techstack.sms2.course.entity.Course;
import com.techstack.sms2.course.entity.Major;
import com.techstack.sms2.course.entity.MajorCourse;
import com.techstack.sms2.course.enums.CourseTypeEnum;

/**
 * @Title: MajorAction.java 
 * @Description: 专业管理ACTION
 * @author zzh
 */
public class MajorAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(MajorAction.class);

	@Autowired
	private MajorBiz majorBiz;
	
	@Autowired
	private CourseBiz courseBiz;
	
	/**
	 * @Description: 进入专业管理页面
	 * @param @return    
	 * @return String
	 */
	public String majorList(){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("majorName", getString("majorName"));
		paramMap.put("module", "majorList");
		PageBean pageBean = majorBiz.listPage(getPageParam(), paramMap, Major.class);
		this.pushData(pageBean);
		this.pushData(paramMap);
		return "majorList";
	}
	
	/**
	 * @Description: 进入专业管理新增页面
	 * @param @return    
	 * @return String
	 */
	public String majorAdd(){
		return "majorAdd";
	}
	
	/**
	 * @Description: 专业管理新增保存
	 * @param @return    
	 * @return String
	 */
	public String majorSave(){
		Major major = new Major();
		major.setMajorName(getString("majorName"));
		String needScore = getString("needScore");
		if(needScore == null){
			return operateError("所需学分不能为空");
		}
		major.setNeedScore(new BigDecimal(needScore));
		major.setDescride(getString("descride"));
		majorBiz.saveOrUpdate(major);
		log.info("==== info ==== 新增专业【"+major.getMajorName()+"】成功");
		return operateSuccess();
	}
	
	/**
	 * @Description: 专业管理修改页面
	 * @param @return    
	 * @return String
	 */
	public String majorEdit(){
		Long majorId = getLong("id");
		Major major = majorBiz.getById(Major.class, majorId);
		this.pushData(major);
		return "majorEdit";
	}
	
	/**
	 * @Description: 专业管理修改保存
	 * @param @return    
	 * @return String
	 */
	public String majorUpdate(){
		Long majorId = getLong("id");
		Major major = majorBiz.getById(Major.class, majorId);
		major.setMajorName(getString("majorName"));
		String needScore = getString("needScore");
		if(needScore == null){
			return operateError("所需学分不能为空");
		}
		major.setNeedScore(new BigDecimal(needScore));
		major.setDescride(getString("descride"));
		majorBiz.saveOrUpdate(major);
		log.info("==== info ==== 课程【"+major.getMajorName()+"】修改成功");
		return operateSuccess();
	}
	
	/**
	 * @Description: 专业管理删除
	 * @param     
	 * @return void
	 */
	public void majorDel(){
		Long majorId = getLong("id");
		PageBean pageBean = courseBiz.courseByMajorPage(getPageParam(), majorId,new HashMap<String,Object>());
		if(pageBean.getRecordList().size() == 0){
			majorBiz.deleteById(Major.class, majorId);
			getOutputMsg().put("STATE", "SUCCESS");
			getOutputMsg().put("MSG", "删除成功");
		}else{
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "该专业下有课程，不能删除");
		}
		outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}
	
	/**
	 * @Description: 专业添加专业课页面
	 * @param @return    
	 * @return String
	 */
	public String majorAddCourse(){
		Long majorId = getLong("majorId");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("module", "course");
		paramMap.put("courseType", CourseTypeEnum.MAJOR.getValue());
		PageBean pageBean = courseBiz.listPage(getPageParam(), paramMap, Course.class);
		this.pushData(pageBean);
		this.pushData(paramMap);
		this.putData("courseTypeEnumList", CourseTypeEnum.toList());
		this.putData("majorId", majorId);
		return "majorAddCourse";
	}
	
	/**
	 * @Description: 专业添加专业课保存
	 * @param     
	 * @return void
	 */
	public void majorCourseSave(){
		Long courseId = getLong("courseId");
		Long majorId = getLong("majorId");
		List<MajorCourse> majorCourseList = majorBiz.findMajorCourse(courseId, majorId);
		if(majorCourseList.size()==0){
			majorBiz.majorCourseSave(courseId, majorId);
			getOutputMsg().put("STATE", "SUCCESS");
			getOutputMsg().put("MSG", "添加成功");
		}else{
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "该专业已经存在这门专业课");
		}
		
		outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}
	
	/**
	 * @Description: 专业信息
	 * @param @return    
	 * @return String
	 */
	public String majorInfo(){
		Long majorId = getLong("id");
		Major major = majorBiz.getById(Major.class, majorId);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		PageBean pageBean = courseBiz.courseByMajorPage(getPageParam(),majorId,paramMap);
		this.pushData(pageBean);
		this.putData("courseTypeEnumList", CourseTypeEnum.toList());
		this.putData("major", major);
		return "majorInfo";
	}
	
}
