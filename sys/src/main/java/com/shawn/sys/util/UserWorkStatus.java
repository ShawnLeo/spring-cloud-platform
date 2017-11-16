/**
 * @Title: UserWorkStatus.java
 * @Package com.shawn.sys.util
 * @author liuzyhn
 * @date 2016年3月10日 下午9:31:58
 * @version V1.0
 */
package com.shawn.sys.util;

/**
 * @ClassName: UserWorkStatus
 * @Description: 用户工作状态
 */
public enum UserWorkStatus {

	/** 
	 * 离职
	 */
	FORMER("-1", "离职"),
	/** 
	 * 正常
	 */
	NORMAL("0", "正常"),
	/** 
	 * 休假
	 */
	VACATION("2", "休假");

	private String code;
	private String text;
	private UserWorkStatus(String code, String text) {
		this.code = code;
		this.text = text;
	}
	/**
	 * @return 获取 code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @return 获取 text
	 */
	public String getText() {
		return text;
	}
}
