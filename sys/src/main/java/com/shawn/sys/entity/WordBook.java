/**
 * @Title: WordBook.java
 * @Package com.shawn.sys.entity
 * @author liuzyhn
 * @date 2016年2月24日 上午11:12:38
 * @version V1.0
 */
package com.shawn.sys.entity;

import com.shawn.sys.dialect.AbstractWordBook;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName: WordBook
 */
//@Entity
//@Table(name = Tables.WORDBOOK)
public class WordBook extends AbstractWordBook implements Serializable {
	/*private static final long serialVersionUID = -9200890730000950189L;

	*//** 字典值 *//*
	private String code;
	*//** 名称 *//*
	private String name;
	*//** 字典描述 *//*
	private String description;
	*//** 层级 *//*
	@Column(name = Columns.WORDBOOK_LEVEL)
	private Integer level = 1;
	*//** 字典排序 *//*
	private Integer dispOrder = 9999;

	*//** 字典子项 *//*
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent",fetch = FetchType.EAGER)
	@OrderBy("dispOrder")
	private List<WordBook> childrens = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId")
	@JsonIgnore
	private WordBook parent;

	@Transient
	private Long parentId;

	@Transient
	private String parentCode;

	*//**
	 * @return 获取 code 字典值
	 *//*
	public String getCode() {
		return code;
	}
	*//**
	 * @param  code 字典值
	 *//*
	public void setCode(String code) {
		this.code = code;
	}
	*//**
	 * @return 获取 name 名称
	 *//*
	public String getName() {
		return name;
	}
	*//**
	 * @param  name 名称
	 *//*
	public void setName(String name) {
		this.name = name;
	}
	*//**
	 * @return 获取 description 字典描述
	 *//*
	public String getDescription() {
		return description;
	}
	*//**
	 * @param  description 字典描述
	 *//*
	public void setDescription(String description) {
		this.description = description;
	}
	*//**
	 * @return 获取 level 层级
	 *//*
	public Integer getLevel() {
		return level;
	}
	*//**
	 * @param  level 层级
	 *//*
	public void setLevel(Integer level) {
		this.level = level;
	}
	*//**
	 * @return 获取 dispOrder 字典排序
	 *//*
	public Integer getDispOrder() {
		return dispOrder;
	}
	*//**
	 * @param  dispOrder 字典排序
	 *//*
	public void setDispOrder(Integer dispOrder) {
		this.dispOrder = dispOrder;
	}
	*//**
	 * @return 获取 childrens 字典子项
	 *//*
	public List<WordBook> getChildrens() {
		return childrens;
	}
	*//**
	 * @param  childrens 字典子项
	 *//*
	public void setChildrens(List<WordBook> childrens) {
		this.childrens = childrens;
	}
	*//**
	 * @return 获取 parent
	 *//*
	public WordBook getParent() {
		return parent;
	}
	*//**
	 * @param  parent
	 *//*
	public void setParent(WordBook parent) {
		this.parent = parent;
	}
	*//**
	 * @return 获取 parentId
	 *//*
	public Long getParentId() {
		if(parentId == null) {
			if(parent != null) {
				parentId = parent.getId();
			}
		}
		return parentId;
	}
	*//**
	 * @param  parentId
	 *//*
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	*//**
	 * @return 获取 parentCode 父级Code
	 *//*
	public String getParentCode() {
		if(parentCode == null) {
			if(parent != null) {
				parentCode = parent.getCode();
			}
		}
		return parentCode;
	}
	*//**
	 * @param  parentCode
	 *//*
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}*/
}
