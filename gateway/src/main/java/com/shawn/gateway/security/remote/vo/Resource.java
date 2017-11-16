package com.shawn.gateway.security.remote.vo;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Resource implements Serializable {
    private static final long serialVersionUID = -1639299970159239350L;

    /**
     *资源名称
     */
    private String name;

    /**
     *资源类型
     */
    private String resType;

    /**
     *模块类型
     */
    private String modType;

    /**
     *资源路径
     */
    private String path;

    /**
     *父节点
     */
    private String parentId;

    /**
     *资源层级
     */
    private String resLevel;

    /**
     *排列顺序
     */
    private String dispOrder;

    /**
     *备注说明
     */
    private String remarks;

    /**
     *创建者
     */
    private String  createBy;

    /**
     *创建时间
     */
    private Date createTime = new Date();

    /**
     *更新者
     */
    private String updateBy;

    /**
     *更新时间
     */
    private Date updateTime;

    private Set<Role> roles = new HashSet<Role>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getModType() {
        return modType;
    }

    public void setModType(String modType) {
        this.modType = modType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getResLevel() {
        return resLevel;
    }

    public void setResLevel(String resLevel) {
        this.resLevel = resLevel;
    }

    public String getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(String dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

