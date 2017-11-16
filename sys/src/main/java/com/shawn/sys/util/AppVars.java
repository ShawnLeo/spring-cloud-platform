package com.shawn.sys.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(AppVars.INSTNAME)
public class AppVars {


	public static final String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";

	public static final String INSTNAME = "adminAppVars";

	@Value("${page.defaultPageSize:20}")
	public int defaultPageSize;

	@Value("${page.maxPageSize:100}")
	public int maxPageSize;

	@Value("${page.displayPages:10}")
	public int displayPages;


	/**
	 * 用户重置密码后的默认密码
	 */
	@Value("${shiro.resetPassword:888888}")
	public String resetPassword;

	/**
	 * 超级管理员角色名
	 */
	@Value("${shiro.adminRole:ADMIN}")
	public String admin;

}
