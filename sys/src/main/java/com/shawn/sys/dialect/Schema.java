package com.shawn.sys.dialect;

public abstract class Schema {

	public static final String SEQ_SUFFIX = "_SEQ";

	/**
	 * 表名
	 */
	public static interface Tables {
		/**
		 * 表命名空间
		 */
		public static final String NS = "SYS_";
		/**
		 * 菜单
		 */
		public String MENU = NS + "MENU";
		/**
		 * 权限
		 */
		public String RESOURCE = NS + "RESOURCE";
		/**
		 * 用户
		 */
		public String USER = NS + "USER";
		/**
		 * 角色
		 */
		public String ROLE = NS + "ROLE";
		/**
		 * 组织机构
		 */
		public String ORGAN = NS + "ORGAN";
		/**
		 * 用户角色
		 */
		public String USERROLE = NS + "USER_ROLE";

		/**
		 * 用户角色
		 */
		public String ROLERESOURCE = NS + "ROLE_RESOURCE";
		/**
		 * 按钮级操作
		 */
		public String ACTION = NS + "ACTION";
		/**
		 * 系统日志
		 */
		public String SYSLOG = NS + "LOG";
		/**
		 * 系统数据字典
		 */
		public String WORDBOOK = NS + "WORDBOOK";
		/**
		 * 行政区划表
		 */
		public String DISTRICT = NS + "DISTRICT";
		/**
		 * 银行信息
		 */
		public String BANKINFO = NS + "BANKINFO";
		/**
		 * 用户扩展属性
		 */
		public String USEREXTEN = "CRD_CR_USER_EXTEND";
		/**
		 * 行业信息
		 */
		public String INDUSTRY = NS + "INDUSTRY";
		/**
		 * 用户认证
		 */
		public String USERAUTH = NS + "USER_AUTH";

	}

	/**
	 * 列名
	 */
	public static interface Columns {
		/**
		 * USERROLE.ROLEID
		 */
		public static final String USERROLE_ROLEID = "ROLEID";

		public static final String ROLERESOURCE_ROLEID = "ROLEID";

		public static final String ROLERESOURCE_RESOURCEID = "RESOURCEID";

		/**
		 * USERROLE.USERID
		 */
		public static final String USERROLE_USERID = "USERID";
		/**
		 * RESOURCE.ROLEID
		 */
		public static final String RESOURCE_ROLEID = "ROLEID";
		/**
		 * MENU.LEVEL
		 */
		public static final String MENU_LEVEL = "NODE_LEVEL";
		/**
		 * ACTION.LEVEL
		 */
		public static final String ACTION_LEVEL = "NODE_LEVEL";
		/**
		 * ORGAN.LEVEL
		 */
		public static final String ORGAN_LEVEL = "NODE_LEVEL";
		/**
		 * WORDBOOK.LEVEL
		 */
		public static final String WORDBOOK_LEVEL = "NODE_LEVEL";
	}

}
