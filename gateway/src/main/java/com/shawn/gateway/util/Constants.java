package com.shawn.gateway.util;

/**
 * <B>系统名称：</B>美美金融平台<BR>
 * <B>模块名称：</B>模块<BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 *
 * @author 北京鹏润美美科技有限公司
 * @since 2017年07月27日 09时40分
 */
public class Constants {

    //表示返回不再做后续过滤处理
    public static final String KEY_NOT_FILTER = "key_not_filter";

    //缓存保存已验证正确token会话key
    public static final String KEY_HASH_TOKEN = "key_hash_token";

    //缓存保存已验证正确token会话key时长
    public static final long TIME_KEY_HASH_TOKEN = 30;

    // 网关门户登陆token 会话时效
    public static final String KEY_PORTAL_TOKEN = "key_portal_token";

    // 网关门户登陆token 会话时效
    public static final long TIME_KEY_PORTAL_TOKEN = 30;

}
