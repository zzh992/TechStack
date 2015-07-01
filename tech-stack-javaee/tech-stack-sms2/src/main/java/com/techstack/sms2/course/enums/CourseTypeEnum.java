package com.techstack.sms2.course.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: CourseTypeEnum.java 
 * @Description: 课程类型枚举： 1-专业课，2-公选课
 * @author zzh
 */
public enum CourseTypeEnum {
	/** 专业课 */
	MAJOR("专业课", 1),
	/** 公选课 */
	SELECTIVE("公选课", 2);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private Integer value;

	private CourseTypeEnum(String desc, Integer value) {
		this.desc = desc;
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static CourseTypeEnum getEnum(Integer value) {
		CourseTypeEnum resultEnum = null;
		CourseTypeEnum[] enumAry = CourseTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		CourseTypeEnum[] ary = CourseTypeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		CourseTypeEnum[] ary = CourseTypeEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", String.valueOf(ary[num].getValue()));
			map.put("desc", ary[num].getDesc());
			enumMap.put(key,map);
		}
		return enumMap;
	}

}
