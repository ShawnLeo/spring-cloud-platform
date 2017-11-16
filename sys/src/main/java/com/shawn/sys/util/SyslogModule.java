package com.shawn.sys.util;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SyslogModule {

	LOGIN("用户登录"),

	SYSTEM("系统管理");

	private String text;

	private SyslogModule(String text) {
		this.text = text;
	}

	public String getName() {
		return name();
	}

	public String getText() {
		return text;
	}

}
