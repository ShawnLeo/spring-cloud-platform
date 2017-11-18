package com.shawn.gateway.util;

import java.io.Serializable;
import java.util.Collection;

public final class UserSession implements Serializable {

    private static final long serialVersionUID = -2970205842358364430L;

    private Long id;

    private String loginName;

    private boolean disabled;

    private String mobile;

    private Collection<String> roleNames;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Collection<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(Collection<String> roleNames) {
        this.roleNames = roleNames;
    }

}
