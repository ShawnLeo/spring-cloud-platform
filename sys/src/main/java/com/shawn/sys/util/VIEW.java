package com.shawn.sys.util;

import com.shawn.sys.controller.SysLogController;

public abstract class VIEW {

	/**
	 * @see com.shawn.sys.controller.UserController#index(org.springframework.ui.Model)
	 */
	public static final String ADMIN_USER = "admin/user-index";

	/**
	 * @see com.shawn.sys.controller.UserController#userProfile(org.springframework.ui.Model)
	 */
	public static final String ADMIN_USER_PROFILE = "admin/user-profile";

	/**
	 * @see com.shawn.sys.controller.RoleController#index(org.springframework.ui.Model)
	 */
	public static final String ADMIN_ROLE = "admin/role-index";

	/**
	 * @see com.shawn.sys.controller.RoleController#permissions(Long,
	 *      org.springframework.ui.Model)
	 */
	public static final String ADMIN_ROLE_PERMISSIONS = "admin/role-permissions";

	/**
	 * @see com.shawn.sys.controller.RoleController#target(Long,
	 *      org.springframework.ui.Model)
	 */
	public static final String ADMIN_ROLE_TARGET = "admin/role-target";

	/**
	 * @see com.shawn.sys.controller.MenuController#index(org.springframework.ui.Model)
	 */
	public static final String ADMIN_MENU = "admin/menu-index";

	/**
	 * @see com.shawn.sys.controller.OrganController#index(org.springframework.ui.Model)
	 */
	public static final String ADMIN_ORGAN = "admin/organ-index";

	/**
	 * @see SysLogController#index(org.springframework.ui.Model)
	 */
	public static final String SYSLOG_INDEX = "admin/syslog";

	/**
	 * @see com.shawn.sys.controller.WordBookController#index(org.springframework.ui.Model)
	 */
	public static final String ADMIN_WORDBOOK = "admin/wordbook-index";

	/**
	 * @see com.shawn.sys.controller.WordBookController#items(Long,
	 *      org.springframework.ui.Model)
	 */
	public static final String ADMIN_WORDBOOK_ITEMS = "admin/wordbook-items";

	/**
	 * @see com.shawn.sys.controller.WordBookController#getWordBookMapJsp()
	 */
	public static final String ADMIN_WORDBOOK_MAP = "admin/wordbookMap";

	/**
	 * @see com.shawn.sys.controller.BankController#queryBankList(org.springframework.ui.Model)
	 */
	public static final String BANK_LIST = "credit/bank";
	
	/**
     * @see com.shawn.sys.controller.IndustryController#list(com.shawn.sys.entity.Industry, org.springframework.ui.Model)
     */
    public static final String INDUSTRY_LIST = "admin/industry-list";
    
    public static final String ADMIN_CLIENT = "client/client-index";
}
