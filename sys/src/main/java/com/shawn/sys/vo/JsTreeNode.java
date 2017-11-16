package com.shawn.sys.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class JsTreeNode<T> implements Serializable {
	private static final long serialVersionUID = -7887008463123899936L;

	private String id;
	private String text;
	private String icon;
	private State state = new State();
	private List<JsTreeNode<T>> children = new ArrayList<>();
	@JsonProperty("li_attr")
	private T liAttr;

	public JsTreeNode() {}
	public JsTreeNode(String id, String text) {
		this.id = id;
		this.text = text;
	}
	public JsTreeNode(String id, String text, T liAttr) {
		this(id, text);
		this.liAttr = liAttr;
	}
	public JsTreeNode(String id, String text, T liAttr, boolean selected) {
		this(id, text, liAttr);
		getState().setSelected(selected);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<JsTreeNode<T>> getChildren() {
		return children;
	}

	public void setChildren(List<JsTreeNode<T>> children) {
		this.children = children;
	}

	public T getLiAttr() {
		return liAttr;
	}

	public void setLiAttr(T liAttr) {
		this.liAttr = liAttr;
	}

	/**
	 * @ClassName: State
	 * @author liuzyhn
	 * @date 2016年3月1日 下午5:27:26
	 */
	public class State implements Serializable {
		private static final long serialVersionUID = 4349361856141022108L;

		private boolean opened=false;
		private boolean disabled=false;
		private boolean selected=false;

		public boolean isOpened() {
			return opened;
		}
		public void setOpened(boolean opened) {
			this.opened = opened;
		}
		public boolean isDisabled() {
			return disabled;
		}
		public void setDisabled(boolean disabled) {
			this.disabled = disabled;
		}
		public boolean isSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			this.selected = selected;
		}
	}
}
