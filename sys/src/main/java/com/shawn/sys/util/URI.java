package com.shawn.sys.util;

import com.shawn.sys.controller.SysLogController;
import com.shawn.sys.vo.PageContext;

public abstract class URI {
    public static final String ID = "id";
	private static final String COMMON = "/common";

	/**
	 * @see URI#INDEX_HOME
	 */
	public static final String INDEX_BASE = "/";

	/**
	 * @see com.shawn.sys.controller.IndexController#index(org.springframework.ui.Model)
	 */
	public static final String INDEX_HOME = "/index";

	/**
	 * @see com.shawn.sys.controller.LoginController#login()
	 */
	public static final String LOGIN_HOME = "/login";

	/**
	 * @see com.shawn.sys.controller.LoginController#fail(org.springframework.ui.Model)
	 */
	public static final String LOGIN_FAIL = LOGIN_HOME;

	/**
	 * @see com.shawn.sys.controller.UserController#index(org.springframework.ui.Model)
	 */
	public static final String USER_HOME = "/user/index";

	/**
	 * @see com.shawn.sys.controller.UserController#page(Integer,
	 *      Integer, com.shawn.sys.entity.User,
	 *      org.springframework.ui.Model)
	 */
	public static final String USER_PAGE = "/user/page";

	/**
	 * @see com.shawn.sys.controller.UserController#userIsExists(String,
	 *      String)
	 */
	public static final String USER_ISEXISTS = "/user/isExists/{type}";

	/**
	 * @see com.shawn.sys.controller.UserController#save(com.shawn.sys.entity.User)
	 */
	public static final String USER_SAVE = "/user/save";

	/**
	 * @see com.shawn.sys.controller.UserController#update(com.shawn.sys.entity.User)
	 */
	public static final String USER_UPDATE = "/user/update";

	/**
	 * @see com.shawn.sys.controller.UserController#getUser(Long)
	 */
	public static final String USER_GET = "/user/get";

	/**
	 * @see com.shawn.sys.controller.UserController#userPassReset(Long)
	 */
	public static final String USER_PASSRESET = "/user/pass/reset";

	/**
	 * @see com.shawn.sys.controller.UserController#userPassEdit(String,
	 *      String)
	 */
	public static final String USER_PASSEDIT = COMMON + "/user/pass/edit";

	/**
	 * @see com.shawn.sys.controller.UserController#userProfile(org.springframework.ui.Model)
	 */
	public static final String USER_PROFILE = COMMON + "/user/profile";

	/**
	 * @see com.shawn.sys.controller.UserController#updateUserProfile(com.shawn.sys.entity.User)
	 */
	public static final String USER_PROFILE_UPDATE = COMMON + "/user/profile/update";

	/**
	 * @see com.shawn.sys.controller.RoleController#index(org.springframework.ui.Model)
	 */
	public static final String ROLE_HOME = "/role/index";

	/**
	 * @see com.shawn.sys.controller.RoleController#page(Integer,
	 *      Integer, com.shawn.sys.entity.Role,
	 *      org.springframework.ui.Model)
	 */
	public static final String ROLE_PAGE = "/role/page/{pageNumber}";

	/**
	 * @see com.shawn.sys.controller.RoleController#isExists(com.shawn.sys.entity.Role)
	 */
	public static final String ROLE_ISEXISTS = "/role/isExists";

	/**
	 * @see com.shawn.sys.controller.RoleController#add(com.shawn.sys.entity.Role)
	 */
	public static final String ROLE_ADD = "/role/add";

	/**
	 * @see com.shawn.sys.controller.RoleController#delete(com.shawn.sys.entity.Role)
	 */
	public static final String ROLE_DELETE = "/role/delete";

	/**
	 * @see com.shawn.sys.controller.RoleController#load(Long)
	 */
	public static final String ROLE_LOAD = "/role/load/{id}";

	/**
	 * @see com.shawn.sys.controller.RoleController#update(com.shawn.sys.entity.Role)
	 */
	public static final String ROLE_UPDATE = "/role/update";

	/**
	 * @see com.shawn.sys.controller.RoleController#permissions(Long,
	 *      org.springframework.ui.Model)
	 */
	public static final String ROLE_PERMISSIONS_P = "/role/permissions/{id}";

	/**
	 * @see com.shawn.sys.controller.RoleController#permissions(com.shawn.sys.dto.RoleManage)
	 */
	public static final String ROLE_PERMISSIONS = "/role/permissions";

	/**
	 * @see com.shawn.sys.controller.RoleController#target(Long,
	 *      org.springframework.ui.Model)
	 */
	public static final String ROLE_TARGET_P = "/role/target/{id}";

	/**
	 * @see com.shawn.sys.controller.RoleController#target(com.shawn.sys.dto.RoleManage)
	 */
	public static final String ROLE_TARGET = "/role/target";
	/**
	 * @see com.shawn.sys.controller.RoleController#list()
	 */
	public static final String ROLE_LIST = "/role/list";

	/**
	 * @see com.shawn.sys.controller.OrganController#index(org.springframework.ui.Model)
	 */
	public static final String ORGAN_HOME = "/organ/index";

	/**
	 * @see com.shawn.sys.controller.OrganController#tree()
	 */
	public static final String ORGAN_TREE = "/organ/tree";

	/**
	 * @see com.shawn.sys.controller.OrganController#isExists(com.shawn.sys.entity.Organ)
	 */
	public static final String ORGAN_ISEXISTS = "/organ/isExists";

	/**
	 * @see com.shawn.sys.controller.OrganController#load(Long)
	 */
	public static final String ORGAN_LOAD = "/organ/load/{id}";

	/**
	 * @see com.shawn.sys.controller.OrganController#update(com.shawn.sys.entity.Organ)
	 */
	public static final String ORGAN_UPDATE = "/organ/update";

	/**
	 * @see com.shawn.sys.controller.OrganController#add(com.shawn.sys.entity.Organ)
	 */
	public static final String ORGAN_ADD = "/organ/add";

	/**
	 * @see com.shawn.sys.controller.OrganController#delete(Long)
	 */
	public static final String ORGAN_DEL = "/organ/delete/{id}";

	/**
	 * @see com.shawn.sys.controller.OrganController#organJsTree()
	 */
	public static final String ORGAN_JSTREE = "/organ/jstree";
	/**
	 * @see com.shawn.sys.controller.OrganController#page(Integer, Integer, com.shawn.sys.entity.Organ, org.springframework.ui.Model)
	 */
	public static final String ORG_PAGE = "/organ/page/{pageNumber}";
	/**
	 * @see com.shawn.sys.controller.OrganController#exportExcel(com.shawn.sys.entity.Organ)
	 */
	public static final String ORGAN_EXPORT_EXCEL = "/admin/organ/export/excel";

	/**
	 * @see com.shawn.sys.controller.MenuController#index(org.springframework.ui.Model)
	 */
	public static final String MENU_HOME = "/menu/index";

	/**
	 * @see com.shawn.sys.controller.MenuController#tree()
	 */
	public static final String MENU_TREE = "/menu/tree";

	/**
	 * @see com.shawn.sys.controller.MenuController#load(Long)
	 */
	public static final String MENU_LOAD = "/menu/load/{id}";

	/**
	 * @see com.shawn.sys.controller.MenuController#update(com.shawn.sys.entity.Menu)
	 */
	public static final String MENU_UPDATE = "/menu/update";

	/**
	 * @see com.shawn.sys.controller.MenuController#add(com.shawn.sys.entity.Menu)
	 */
	public static final String MENU_ADD = "/menu/add";

	/**
	 * @see com.shawn.sys.controller.MenuController#delete(Long)
	 */
	public static final String MENU_DELETE = "/menu/delete/{id}";

	/**
	 * @see com.shawn.sys.controller.MenuController#addAction(com.shawn.sys.entity.Action)
	 */
	public static final String MENU_ACTION_ADD = "/menu/action/add";

	/**
	 * @see com.shawn.sys.controller.MenuController#updateAction(com.shawn.sys.entity.Action)
	 */
	public static final String MENU_ACTION_UPDATE = "/menu/action/update";

	/**
	 * @see com.shawn.sys.controller.MenuController#delAction(Long)
	 */
	public static final String MENU_ACTION_DELETE = "/menu/action/delete/{id}";

	/**
	 * @see com.shawn.sys.controller.MenuController#menuJsTree()
	 */
	public static final String MENU_JSTREE = "/menu/jstree";

	/**
	 * @see SysLogController#index(org.springframework.ui.Model)
	 */
	public static final String SYSLOG_INDEX = "/syslog";

	/**
	 * @see SysLogController#listPage(Integer,
	 *      Integer, com.shawn.sys.dto.SyslogCriteria,
	 *      org.springframework.ui.Model)
	 */
	public static final String SYSLOG_LISTPAGE = "/syslog/page";

	public static final String SYSLOG_ADD = "/syslog/add";

	/**
	 * @see com.shawn.sys.controller.WordBookController#getWordBookMap()
	 */
	public static final String WORDBOOK_MAP = COMMON + "/wordbook/map";

	/**
	 * @see com.shawn.sys.controller.WordBookController#index(org.springframework.ui.Model)
	 */
	public static final String WORDBOOK_HOME = "/wordbook/index";

	/**
	 * @see com.shawn.sys.controller.WordBookController#page(Integer,
	 *      Integer, com.shawn.sys.entity.WordBook,
	 *      org.springframework.ui.Model)
	 */
	public static final String WORDBOOK_PAGE = "/wordbook/page/{pageNumber}";

	/**
	 * @see com.shawn.sys.controller.WordBookController#isExists(com.shawn.sys.entity.WordBook)
	 */
	public static final String WORDBOOK_ISEXISTS = "/wordbook/isExists";

	/**
	 * @see com.shawn.sys.controller.WordBookController#load(Long)
	 */
	public static final String WORDBOOK_LOAD = "/wordbook/load/{id}";

	/**
	 * @see com.shawn.sys.controller.WordBookController#loadByCode(String)
	 */
	public static final String WORDBOOK_LOAD_CODE = COMMON + "/wordbook/loadCode/{code}";

	/**
	 * @see com.shawn.sys.controller.WordBookController#items(Long,
	 *      org.springframework.ui.Model)
	 */
	public static final String WORDBOOK_ITEMS = "/wordbook/items/{id}";

	/**
	 * @see com.shawn.sys.controller.WordBookController#save(com.shawn.sys.entity.WordBook)
	 */
	public static final String WORDBOOK_SAVE = "/wordbook/save";

	/**
	 * @see com.shawn.sys.controller.WordBookController#delete(com.shawn.sys.entity.WordBook)
	 */
	public static final String WORDBOOK_DELETE = "/wordbook/delete";

	/**
	 * @see com.shawn.sys.controller.DistrictController#getProvinceList()
	 */
	public static final String PROVINCE_SELECT = "/common/district/province";
	/**
	 * @see com.shawn.sys.controller.DistrictController#getCityList(String)
	 */
	public static final String CITY_SELECT = "/common/district/city";
	/**
	 * @see com.shawn.sys.controller.DistrictController#getAreaList(String)
	 */
	public static final String AREA_SELECT = "/common/district/area";

	/**
	 * @see com.shawn.sys.controller.BankController#queryBankList(org.springframework.ui.Model)
	 */
	public static final String BANK_LIST = "/bank/queryBankList";
	/**
	 * @see com.shawn.sys.controller.BankController#page(Integer,
	 *      Integer, com.shawn.sys.entity.BankInfo,
	 *      org.springframework.ui.Model)
	 */
	public static final String BANK_PAGE = "/bank/page/{pageNumber}";
	/**
	 * @see com.shawn.sys.controller.BankController#save(com.shawn.sys.entity.BankInfo)
	 */
	public static final String BANK_SAVE = "/bank/save";
	/**
	 * @see com.shawn.sys.controller.BankController#getBankInfo(Long)
	 */
	public static final String BANKINFO = "/bank/getBankInfo";
	/**
	 * @see com.shawn.sys.controller.BankController#update(com.shawn.sys.entity.BankInfo)
	 */
	public static final String BANK_UPDATE = "/bank/update";
	/**
	 * @see com.shawn.sys.controller.BankController#del(Long)
	 */
	public static final String BANK_DEL = "/bank/del";
	
	/**
     * @see com.shawn.sys.controller.IndustryController#list(com.shawn.sys.entity.Industry, org.springframework.ui.Model)
     */
    public static final String INDUSTRY_LIST = "admin/industry/list";
    /**
     * @see com.shawn.sys.controller.IndustryController#listPage(Integer, Integer, com.shawn.sys.entity.Industry)
     */
    public static final String INDUSTRY_LISTPAGE = "admin/industry/list-page/{" + PageContext.PAGE_NUMBER + "}";
    /**
     * @see com.shawn.sys.controller.IndustryController#view(Long)
     */
    public static final String INDUSTRY_VIEW = "admin/industry/view/{" + ID + ":\\d+}";
    /**
     * @see com.shawn.sys.controller.IndustryController#create(com.shawn.sys.entity.Industry)
     */
    public static final String INDUSTRY_CREATE = "admin/industry/create";
    /**
     * @see com.shawn.sys.controller.IndustryController#update(com.shawn.sys.entity.Industry)
     */
    public static final String INDUSTRY_UPDATE = "admin/industry/update";
    /**
     * @see com.shawn.sys.controller.IndustryController#remove(Long)
     */
    public static final String INDUSTRY_REMOVE = "admin/industry/remove/{" + ID + ":\\d+}";
    /**
     * @see com.shawn.sys.controller.IndustryController#isExists(String)
     */
    public static final String INDUSTRY_ISEXISTS = "admin/industry/isExists";
    

}
