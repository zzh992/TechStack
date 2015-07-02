package com.techstack.sms2.course.entity;

import java.util.Date;

import com.techstack.sms2.base.annotation.mybatis.Column;
import com.techstack.sms2.base.annotation.mybatis.Table;
import com.techstack.sms2.base.entity.BaseEntity;

/**
 * @Title: Student.java 
 * @Description: 学生实体
 * @author zzh
 */
@Table(name="STUDENT")
public class Student extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**	学号	*/
	@Column(name="STUDENT_NO")
	private String studentNo;
	
	/**	姓名	*/
	@Column(name="NAME")
	private String name;
	
	/**	性别	*/
	@Column(name="SEX")
	private Integer sex;
	
	/**	年龄	*/
	@Column(name="AGE")
	private Integer age;
	
	/**	出生日期	*/
	@Column(name="BIRTHDAY")
	private Date birthday;
	
	/**	入学年份	*/
	@Column(name="IN_SCHOOL_YEAR")
	private String inSchoolYear;
	
	/**	专业ID	*/
	@Column(name="MAJOR_ID")
	private Long majorId;
	
	/**	专业名称	*/
	@Column(name="MAJOR_NAME")
	private String majorName;
	
	/**	用户ID	*/
	@Column(name="USER_ID")
	private Long userId;
	
	/**	是否结束选课（PublicStatusEnum）*/
	@Column(name="IS_END_SELECT")
	private Integer isEndSelect;

	/**
	 * @return 学号
	 */
	public String getStudentNo() {
		return studentNo;
	}

	/**
	 * @param 学号
	 */
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	/**
	 * @return 姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param 姓名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 性别
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * @param 性别
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	/**
	 * @return 年龄
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param 年龄
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * @return 出生日期
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param 出生日期
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return 入学年份
	 */
	public String getInSchoolYear() {
		return inSchoolYear;
	}

	/**
	 * @param 入学年份
	 */
	public void setInSchoolYear(String inSchoolYear) {
		this.inSchoolYear = inSchoolYear;
	}

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
	 * @return 用户ID
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param 用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

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
	 * @return 是否结束选课（PublicStatusEnum）
	 */
	public Integer getIsEndSelect() {
		return isEndSelect;
	}

	/**
	 * @param 是否结束选课（PublicStatusEnum）
	 */
	public void setIsEndSelect(Integer isEndSelect) {
		this.isEndSelect = isEndSelect;
	}
	
	
	

}
