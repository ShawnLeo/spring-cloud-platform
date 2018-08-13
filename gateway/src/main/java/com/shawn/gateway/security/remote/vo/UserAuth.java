package com.shawn.gateway.security.remote.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by wanglu-jf on 17/9/6.
 */
public class UserAuth implements Serializable {

    private static final long serialVersionUID = -993022480404121880L;



    /**
     * 认证账号
     */
    private String authId;

    /**
     * 用户Id
     */
    private String userId;

    /**
     *用户状态 	LOCKED(-1, "锁定"),NORMAL(0, "正常"),UNLOCK(1, "解锁");
     */
    private String userStatus;

    /**
     * 认证方式 1：手机号 2：email  3：昵称
     */
    private String authType;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 认证密码
     */
    private String authPass;

    /**
     * 登陆次数
     */
    private String loginCount;

    /**
     * 最后登陆时间
     */
    private Date lastLoginTime;

    /**
     * 修改密码时间
     */
    private Date passTime;

    /**
     *创建时间
     */
    private Date createTime = new Date();

    /**
     *更新者
     */
    private Date updateBy;

    /**
     *更新时间
     */
    private Date updateTime;

    private Set<Role> roles;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAuthPass() {
        return authPass;
    }

    public void setAuthPass(String authPass) {
        this.authPass = authPass;
    }

    public String getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(String loginCount) {
        this.loginCount = loginCount;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Date updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
