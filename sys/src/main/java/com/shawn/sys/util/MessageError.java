package com.shawn.sys.util;

/**
 * 报文错误码
 *
 */
public enum MessageError {

	SYSTEM_ERROR("内部错误"),

	PARAM_ERROR("参数错误"), ;

	private String msg;

	private MessageError(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

}
