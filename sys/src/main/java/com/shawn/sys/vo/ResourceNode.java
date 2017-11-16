package com.shawn.sys.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * zTree 资源树节点
 * 
 * @author Shawn
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ResourceNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -232213799219110344L;

	private Long id;
	private Long pId;
	private String name;
	private Integer level;
	private boolean open = true;
	private boolean checked = false;
	private List<ResourceNode> children = new ArrayList<ResourceNode>();

	// 自定义属性
	private String curl;// 资源地址
	private String ctype;

	public ResourceNode() {// 构建资源树
	}

	public ResourceNode(Long id, Long pId, String name, String curl, String ctype, boolean checked) {
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.curl = curl;
		this.ctype = ctype;
		this.checked = checked;
	}

	public String getCurl() {
		return curl;
	}

	public void setCurl(String curl) {
		this.curl = curl;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<ResourceNode> getChildren() {
		return children;
	}

	public void setChildren(List<ResourceNode> children) {
		this.children = children;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}
