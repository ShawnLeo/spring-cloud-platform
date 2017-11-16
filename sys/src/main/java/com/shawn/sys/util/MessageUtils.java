package com.shawn.sys.util;

/**
 * Created by wanglu-jf on 17/9/7.
 */
public enum MessageUtils {
    /**
     * user error message
     */
    NULLNAME(-10000,"用户真实姓名不能为空"),SIZEERRORNAME(-10001,"真实姓名的长度在1~40个字符"),
    USERSTATUS(-10002,"请选择用户状态"),
    NULLEMAIL(-10003,"用户邮箱不能为空"),ERROREMAIL(-10004,"邮箱格式输入错误"),
    NULLGENDER(-10005,"请选择用户性别"),
    NULLPHONE(-10006,"用户手机号码不能为空"),ERRORPHONE(-10007,"用户手机号码输入错误"),
    NULLPORTRAITPATH(-10008,"请上传用户头像"),
    NULLPWD(-10009,"用户密码不能为空"),ERRORPWD(-10010,"密码格式输入错误(6~18位字母数字的组合)"),
    NULLCONFIRMPWD(-10011,"确认密码不能为空"),ERRORCONFIRMPWD(-10012,"密码与确认密码不一致"),
    NULLAUTHTYPE(-10013,"请选择登陆认证方式"),
    NULLAUTHID(-10014,"登陆账号不能为空"),ERRORAUTHID(-10015,"登陆账号不能是手机号码及邮箱"),


    REQUESTEXCEPTION(-10100,"操作被拒绝")
    ;


    private int code;
    private String message;

    MessageUtils(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
