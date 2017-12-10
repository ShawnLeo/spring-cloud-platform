package com.shawn.sys.vo;

import com.shawn.sys.entity.Resource;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ResourceVO implements Comparable,Serializable {
    private static final long serialVersionUID = 1814672959354752265L;

    private String id;
    /**
     *资源名称
     */
    @NotBlank(message = "资源名称不能为空")
    @Length(min = 1,max = 100,message = "资源名称的长度在1~100个字符")
    private String name;

    /**
     *资源类型 1：API资源 2：功能模块
     */
    @NotBlank(message = "请选择资源类型")
    @Max(value = 2,message = "资源类型选择有误")
    private String resType;

    /**
     *模块类型  1： 平台 2：菜单 3：功能
     */
    private String modType = "0";

    /**
     *资源路径
     */
    @Length(max = 256,message = "url链接的长度在1~256个字符")
    private String path;

    private String style;
    /**
     *父节点 0:父节点
     */
    private String parentId = "0";

    /**
     *资源层级
     */
    private String resLevel = "0";

    /**
     *排列顺序
     */
    private Integer dispOrder = 999;

    /**
     *备注说明
     */
//    @Max(value = 40,message = "备注说明的长度在1~40个字符")
    @Length(max = 40,message = "备注说明的长度在1~40个字符")
    private String remarks;

    private Set<RoleVO> roles = new HashSet<RoleVO>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Set<RoleVO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleVO> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ResourceVO{");
        sb.append("id='").append(id).append('\'');
        sb.append("name='").append(name).append('\'');
        sb.append(", resType='").append(resType).append('\'');
        sb.append(", modType='").append(modType).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", style='").append(style).append('\'');
        sb.append(", parentId='").append(parentId).append('\'');
        sb.append(", resLevel='").append(resLevel).append('\'');
        sb.append(", dispOrder='").append(dispOrder).append('\'');
        sb.append(", remarks='").append(remarks).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        if(null != o){
            Resource resource = (Resource)o;
            String resType = resource.getResType();
            if(!this.name.equalsIgnoreCase(resource.getName())) return -1;
            if(!this.resType.equalsIgnoreCase(resource.getResType())) return -1;
            if(!this.remarks.equalsIgnoreCase(resource.getRemarks())) return -1;
            if(!this.resLevel.equalsIgnoreCase(resource.getResLevel())) return -1;
            if(!this.parentId.equalsIgnoreCase(resource.getParentId())) return -1;
            if("1".equals(resType)){ //1:功能模块
                if(!this.modType.equalsIgnoreCase(resource.getModType())) return -1;
            }else if("0".equals(resType)){
                if(!this.path.equalsIgnoreCase(resource.getPath())) return -1;
            }
        }
        return 0;
    }
}