package com.shawn.sys.vo;

import com.shawn.sys.entity.Resource;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wanglu-jf on 17/9/12.
 */
public class RoleVO implements Serializable {

    private static final long serialVersionUID = -6424139059745921768L;
    private Long id;
    /**
     * 角色CODE
     */
    @NotNull(message = "角色CODE不能为空")
    @Length(min = 1,max = 100,message = "角色名称的长度在1~100个字符")
    public String roleCode;

    /**
     * 角色CODE
     */
    @NotNull(message = "角色名称不能为空")
    @Length(min = 1,max = 100,message = "角色CODE的长度在1~100个字符")
    public String name;

    /**
     * 排列顺序
     */
//    @DecimalMax(value = "10",message = "角色排列顺序有误")
    public String dispOrder = "999";

    /**
     * 备注说明
     */
    @Length(max = 200,message = "角色备注说明的长度不能超过200个字符")
    public String remarks;

    /**
     * 当前页
     */
    public int page = 0;

    /**
     * 每页条数
     */
    public int size = 10;

//    /**
//     *创建时间
//     */
//    @Future
//    private Date createTime = new Date();
//
//    /**
//     *更新者
//     */
//    private String updateBy;
//
//    /**
//     *更新时间
//     */
//    @Future
//    private Date updateTime;

    private Set<Resource> resources = new HashSet<Resource>();

    private String[] roleResources=null;

    public String[] getRoleResources() {
        return roleResources;
    }

    public void setRoleResources(String[] roleResources) {
        this.roleResources = roleResources;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        if(roleCode!=null){
            this.roleCode=roleCode.trim();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name!=null){
            this.name = name.trim();
        }
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
        if(remarks!=null){
            this.remarks = remarks.trim();
        }
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

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
        final StringBuffer sb = new StringBuffer("RoleVO{");
        sb.append(", id='").append(id).append('\'');
        sb.append(", roleCode='").append(roleCode).append('\'');
        sb.append(", dispOrder='").append(dispOrder).append('\'');
        sb.append(", remarks='").append(remarks).append('\'');
        sb.append(", page=").append(page);
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }
}
