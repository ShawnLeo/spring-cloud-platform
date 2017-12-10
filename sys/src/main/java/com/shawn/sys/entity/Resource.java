package com.shawn.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shawn.sys.dialect.AbstractResource;
import com.shawn.sys.dialect.Schema;
import com.shawn.sys.dialect.Schema.Tables;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.REFRESH;


/**
 * Created by wanglu-jf on 17/9/6.
 */
@Entity
@Table(name = Tables.RESOURCE
//        ,indexes = {@Index(name = "IDX_RESOURCE_NAMEPARENTIDPATH",columnList = "parent_id,name,path")}
        )
public class Resource extends AbstractResource {

    private static final long serialVersionUID = -1639299970159239350L;

    /**
     *资源名称
     */
    @Column(name = "NAME",length = 100)
    private String name;

    /**
     *资源类型 1-API资源  2-功能模块
     */
    @Column(name = "RES_TYPE",length = 1)
    private String resType;

    /**
     *模块类型 1-平台 2-菜单  3-功能
     */
    @Column(name = "MOD_TYPE",length = 1)
    private String modType;

    /**
     *资源路径
     */
    @Column(name = "PATH",length = 256)
    private String path;

    /**
     *资源路径
     */
    @Column(name = "STYLE",length = 256)
    private String style;

    /**
     *父节点
     */
    @Column(name = "PARENT_ID",length = 40)
    private String parentId;

    /**
     *资源层级
     */
    @Column(name = "RES_LEVEL",length = 1)
    private String resLevel;

    /**
     *排列顺序
     */
    @Column(name = "DISP_ORDER",length = 6)
    private Integer dispOrder;

    /**
     *备注说明
     */
    @Column(name = "REMARKS",length = 40)
    private String remarks;

    /**
     *创建者
     */
    @Column(name = "CREATE_BY",length = 40)
    private String  createBy;

    /**
     *创建时间
     */
    @Column(name = "CREATE_TIME")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime = new Date();

    /**
     *更新者
     */
    @Column(name = "UPDATE_BY",length = 40)
    private String updateBy;

    /**
     *更新时间
     */
    @Column(name = "UPDATE_TIME")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updateTime;


    @ManyToMany(targetEntity = Role.class,fetch = FetchType.EAGER, cascade = { REFRESH, DETACH/* ,REMOVE*/})
    @JoinTable(
            name = Tables.ROLERESOURCE,
            joinColumns = @JoinColumn(name = Schema.Columns.ROLERESOURCE_RESOURCEID) ,
            inverseJoinColumns = @JoinColumn(name = Schema.Columns.ROLERESOURCE_ROLEID)
    )
    @JsonIgnore
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

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
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

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Resource{");
        sb.append("name='").append(name).append('\'');
        sb.append(", resType='").append(resType).append('\'');
        sb.append(", modType='").append(modType).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", style='").append(style).append('\'');
        sb.append(", parentId='").append(parentId).append('\'');
        sb.append(", resLevel='").append(resLevel).append('\'');
        sb.append(", dispOrder='").append(dispOrder).append('\'');
        sb.append(", remarks='").append(remarks).append('\'');
        sb.append(", createBy='").append(createBy).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy='").append(updateBy).append('\'');
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
