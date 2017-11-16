/**
 * @Title: OrganuserNode.java
 * @Package com.shawn.sys.dto
 */
package com.shawn.sys.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: OrganuserNode
 * @Description: zTree 组织机构－用户树节点
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class OrganuserNode implements Serializable {
	private static final long serialVersionUID = -7640499796108069975L;

	private Long id;
	private Long pId;
	private String name;
	private Integer level;
	private boolean open = true;
	private boolean checked = false;
	private List<OrganuserNode> children = new ArrayList<OrganuserNode>();

	//自定义属性
	private String ctype;

	public OrganuserNode() {}
	public OrganuserNode(Long id, Long pId, String name, String ctype) {
		this.id = id;
		this.pId = pId;
		this.name = name;
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
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
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
	public List<OrganuserNode> getChildren() {
		return children;
	}
	public void setChildren(List<OrganuserNode> children) {
		this.children = children;
	}
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
}
