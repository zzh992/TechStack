package com.techstack.sms2.base.annotation.mybatis;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: Table.java 
 * @Description:自定义mybatis注解：在实体类进行表注解
 * @author zzh
 */
@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
	public abstract String name();
}
