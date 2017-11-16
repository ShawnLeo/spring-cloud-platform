/**
 * @Title: UserFlag.java
 * @Package com.shawn.sys.util
 * @author liuzyhn
 * @date 2016年3月10日 下午8:15:41
 * @version V1.0
 */
package com.shawn.sys.util;

/**
 * @ClassName: UserFlag
 * @Description: 有效性标识{-1注销,0有效}
 */
public enum UserFlag {
	/** 
	 * 有效
	 */
	VALID("0", "有效"),
	/** 
	 * 无效
	 */
	INVALID("-1", "无效");

	private String flag;
	private String text;

	UserFlag(String flag, String text) {
		this.flag = flag;
		this.text = text;
	}
	/**
	 * @return 获取 flag
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * @return 获取 text
	 */
	public String getText() {
		return text;
	}
}
