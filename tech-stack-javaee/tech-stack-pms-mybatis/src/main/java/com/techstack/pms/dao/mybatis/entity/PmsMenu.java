package com.techstack.pms.dao.mybatis.entity;

import com.techstack.component.mybatis.annotation.Column;
import com.techstack.component.mybatis.annotation.Table;


/**
 * @Title: PmsMenu.java 
 * @Description: 系统菜单
 * @author zzh
 */
@Table(name="PMS_MENU")
public class PmsMenu extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	/**	菜单名称	*/
	@Column(name="NAME")
	private String name; 
	
	/**	 菜单地址URL	*/
	@Column(name="URL")
	private String url;
	
	/**	 父菜单	*/
	@Column(name="PARENT_ID")
	private Long parentId; 
	
	/**	菜单编号	*/
	@Column(name="NUMBER")
	private String number;
	
	/**	是否叶子节点，对于枚举（NodeTypeEnum）	*/
	@Column(name="IS_LEAF")
	private Integer isLeaf;
	
	/**	级别	*/
	@Column(name="LEVEL")
	private Integer level;
	
	/**	用于刷新页面的配置	*/
	@Column(name="TARGET_NAME")
	private String targetName;

	/**
	 * @return 菜单名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param 菜单名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 菜单地址URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param 菜单地址URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}


	/**
	 * @return  父菜单
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param  父菜单
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return 菜单编号
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param 菜单编号
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	

	/**
	 * @return 是否叶子节点，对于枚举（NodeTypeEnum）
	 */
	public Integer getIsLeaf() {
		return isLeaf;
	}

	/**
	 * @param 是否叶子节点，对于枚举（NodeTypeEnum）
	 */
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	/**
	 * @return 级别
	 */
	public Integer getLevel() {
		return level;
	} 

	/**
	 * @param 级别
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}


	/**
	 * @return 用于刷新页面的配置
	 */
	public String getTargetName() {
		return targetName;
	}

	/**
	 * @param 用于刷新页面的配置
	 */
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	
	
	


}
