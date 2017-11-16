package com.shawn.sys.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

public final class NodeType implements Serializable {
	private static final long serialVersionUID = 8273073886298408024L;

	public static final NodeType MENU = new NodeType("MENU", "菜单");
	public static final NodeType ACTION = new NodeType("ACTION", "页面资源");

	public static final NodeType ORGAN = new NodeType("ORGAN", "组织机构");
	public static final NodeType USER = new NodeType("USER", "用户");

	private String type;
	private String name;

	@JsonIgnoreProperties({ "actions", "level", "dispOrder", "style", "createTime",
		"updateTime", "intro", "flumeIds", "flumeNames", "children", "users" })
	@JsonInclude(Include.NON_NULL)
	private Object attr;

	/**
	 * @param attr 数据
	 * @param type 基本类型
	 * @return
	 */
	public static NodeType getSelfNodeType(Object attr, NodeType type) {
		NodeType self = new NodeType(type.type, type.name);
		self.setAttr(attr);
		return self;
	}
	private NodeType(String type, String name) {
		this.type = type;
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public Object getAttr() {
		return attr;
	}
	public void setAttr(Object attr) {
		this.attr = attr;
	}
}
