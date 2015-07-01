package com.techstack.sms2.course.action;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.techstack.sms2.base.page.PageBean;
import com.techstack.sms2.base.struts.BaseAction;
import com.techstack.sms2.course.biz.CourseBiz;
import com.techstack.sms2.course.biz.MajorBiz;
import com.techstack.sms2.course.biz.StudentBiz;
import com.techstack.sms2.course.entity.Course;
import com.techstack.sms2.course.entity.Major;
import com.techstack.sms2.course.entity.Student;
import com.techstack.sms2.course.enums.CourseTypeEnum;
import com.techstack.sms2.course.enums.PublicStatusEnum;
import com.techstack.sms2.course.enums.SexTypeEnum;

/**
 * @Title: StudentAction.java 
 * @Description: 学生ACTION
 * @author zzh
 */
public class StudentAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(StudentAction.class);
	
	@Autowired 
	private StudentBiz studentBiz;
	
	@Autowired  
	private MajorBiz majorBiz;
	
	@Autowired
	private CourseBiz courseBiz;

	/**
	 * @Description: 学生信息管理-学生信息
	 * @param @return    
	 * @return String
	 */
	public String studentInfo(){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("studentNo", getString("studentNo"));
		paramMap.put("name", getString("name"));
		paramMap.put("majorName", getString("majorName"));
		paramMap.put("module", "studentInfo");
		PageBean pageBean = studentBiz.listPage(this.getPageParam(), paramMap, Student.class);
		this.pushData(pageBean);
		this.pushData(paramMap);
		this.putData("sexTypeEnumList", SexTypeEnum.toList());
		return "studentInfo";
	}
	
	/**
	 * @Description: 课程选择（学生）-我的信息
	 * @param @return    
	 * @return String
	 */
	public String myStudentInfo(){
		Long userId = getLoginedUser().getId();
		Student stu = studentBiz.findStudentByUserId(userId);
		this.pushData(stu);
		this.putData("sexTypeEnumList", SexTypeEnum.toList());
		this.putData("majorList", majorBiz.findAllMajor());
		return "myStudentInfo";
	}
	
	/**
	 * @Description: 学生信息验证
	 * @param @return    
	 * @return String
	 */
	public String myStudentInfoAuth(){
		Long userId = getLoginedUser().getId();
		Student stu = studentBiz.findStudentByUserId(userId);
		if(stu == null){
			stu = new Student();
		}
		stu.setStudentNo(getString("studentNo"));
		stu.setName(getString("name"));
		stu.setSex(getInteger("sex"));
		stu.setAge(getInteger("age"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			stu.setBirthday(sdf.parse(getString("birthday")));
		} catch (Exception e) {
			log.error("==== error ==== 出生日期有误", e);
			return operateError("出生日期有误");
		}
		stu.setInSchoolYear(getString("inSchoolYear"));
		stu.setMajorId(getLong("majorId"));
		Major major = majorBiz.getById(Major.class, getLong("majorId"));
		stu.setMajorName(major.getMajorName());
		stu.setUserId(getLoginedUser().getId());
		studentBiz.saveOrUpdate(stu);
		this.pushData(stu);
		this.putData("sexTypeEnumList", SexTypeEnum.toList());
		this.putData("majorList", majorBiz.findAllMajor());
		log.info("==== info ==== 用户【"+getLoginedUser().getLoginName()+"】验证通过，学生名字为【"+stu.getName()+"】");
		return "myStudentInfo";
	}
	
	/**
	 * @Description: 课程选择信息
	 * @param @return    
	 * @return String
	 */
	public String courseSelectInfo(){
		Long userId = getLoginedUser().getId();
		Student stu = studentBiz.findStudentByUserId(userId);
		if(stu != null){	//已经通过学生信息认证
			
			if(stu.getIsEndSelect() == PublicStatusEnum.YES.getValue()){	//判断学生是否已结束选课
				return operateErrorAndCloseTab("您已结束选课");
			}
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("courseName", getString("courseName"));
			paramMap.put("courseType", getInteger("courseType"));
			PageBean pageBean = courseBiz.courseCanSelectByMajorPage(getPageParam(), stu.getMajorId(),paramMap);
			this.pushData(pageBean);
			this.pushData(paramMap);
			this.putData("courseTypeEnumList", CourseTypeEnum.toList());
			return "courseSelectInfo";
		}else{	
			//return operateError("请先进行学生信息认证");
			return operateErrorAndCloseTab("请先进行学生信息认证");
		}
		
	}
	
	/**
	 * @Description: 选择课程
	 * @param     
	 * @return void
	 */
	public void courseSelect(){
		Long courseId = getLong("courseId");
		Long stuId = studentBiz.findStudentByUserId(getLoginedUser().getId()).getId();
		Course course = courseBiz.findCourseByCosIdAndStuId(courseId, stuId);
		if(course != null){
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "您已经选了这门课");
		}else{
			studentBiz.selectCourse(stuId, courseId);
			getOutputMsg().put("STATE", "SUCCESS");
			getOutputMsg().put("MSG", "选课成功");
			log.info("==== info ==== 用户【"+getLoginedUser().getLoginName()+"】选课成功");
		}
		outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}
	
	/**
	 * @Description: 我的课程信息
	 * @param @return    
	 * @return String
	 */
	public String myCourseInfo(){
		Long userId = getLoginedUser().getId();
		Student stu = studentBiz.findStudentByUserId(userId);
		if(stu == null){
			//return operateError("请先进行学生信息认证");
			return operateErrorAndCloseTab("请先进行学生信息认证");
		}
		Major major = majorBiz.getById(Major.class, stu.getMajorId());
		Map<String,Object> paramMap = new HashMap<String,Object>();
		PageBean pageBean = courseBiz.courseByStudentPage(getPageParam(), stu.getId(),paramMap);
		this.pushData(pageBean);
		this.putData("courseTypeEnumList", CourseTypeEnum.toList());
		this.putData("publicStatusEnum", PublicStatusEnum.toMap());
		this.putData("student", stu);
		this.putData("major", major);
		return "myCourseInfo";
	}
	
	/**
	 * @Description: 结束选课
	 * @param     
	 * @return void
	 */
	public void myCourseEnd(){
		Long userId = getLoginedUser().getId();
		Student stu = studentBiz.findStudentByUserId(userId);
		if(stu != null){
			//判断所选专业课的学分总和是否大于专业必须学分
			Major major = majorBiz.getById(Major.class, stu.getMajorId());
			BigDecimal sum = studentBiz.getScoreSumForStudent(stu.getId());
			if(sum == null||sum.doubleValue()<major.getNeedScore().doubleValue()){
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "您的专业课学分还不够，请继续选课");
			}else{
				stu.setIsEndSelect(PublicStatusEnum.YES.getValue());
				studentBiz.saveOrUpdate(stu);
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", "选课结束");
				
				log.info("==== info ==== 用户【"+getLoginedUser().getLoginName()+"】选课结束");
			}
		}else{
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "请先进行学生信息认证");
		}
		outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}
	
}
