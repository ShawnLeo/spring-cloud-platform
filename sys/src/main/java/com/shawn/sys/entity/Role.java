package com.shawn.sys.entity;

import com.shawn.sys.dialect.AbstractRole;
import com.shawn.sys.dialect.Schema.Columns;
import com.shawn.sys.dialect.Schema.Tables;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wanglu-jf on 17/9/6.
 */
@Entity
@Table(name = Tables.ROLE
//        ,indexes = {@Index(name = "IDX_ROLE_NAME",columnList = "ROLE_CODE"),@Index(name = "IDX_ROLE_REMARKS",columnList = "remarks")}
        )
public class Role extends AbstractRole implements Serializable {

    private static final long serialVersionUID = 5701326659682617666L;

    /**
     * 角色名称
     */
    @Column(name = "ROLE_CODE",length = 100,unique = true)
    public String roleCode;

    /**
     * 角色名称
     */
    @Column(name = "NAME",length = 100,unique = true)
    public String name;

    /**
     * 排列顺序
     */
    @Column(name = "DISP_ORDER",length = 6)
    public String dispOrder;

    /**
     * 备注说明
     */
    @Column(name = "REMARKS",length = 200)
    public String remarks;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = Tables.ROLERESOURCE,
            joinColumns = @JoinColumn(name = Columns.ROLERESOURCE_ROLEID) ,
            inverseJoinColumns = @JoinColumn(name = Columns.ROLERESOURCE_RESOURCEID)
    )
    private Set<Resource> resources = new HashSet<>();

    /**
     * 当前页
     */
    @Transient
    public int page = 1;

    /**
     * 每页条数
     */
    @Transient
    public int size = 10;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
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

    public Set<Resource> getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Role{");
        sb.append("roleCode='").append(roleCode).append('\'');
        sb.append("name='").append(name).append('\'');
        sb.append(", dispOrder='").append(dispOrder).append('\'');
        sb.append(", remarks='").append(remarks).append('\'');
        sb.append(", createBy='").append(createBy).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
//        sb.append(", resources=").append(resources);
        sb.append(", page=").append(page);
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }
}
