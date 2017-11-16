package com.shawn.sys.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shawn.sys.entity.Role;

import java.io.Serializable;
import java.util.List;


/**
 * @ClassName: RoleManage
 * @Description: 系统管理DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class RoleManage implements Serializable {
	private static final long serialVersionUID = -8419584301693467823L;

	private List<String> selectedIds;
	private Role role;

	/**
	 * @return 获取 selectedIds
	 */
	public List<String> getSelectedIds() {
		return selectedIds;
	}
	/**
	 * @param  selectedIds
	 */
	public void setSelectedIds(List<String> selectedIds) {
		this.selectedIds = selectedIds;
	}
	/**
	 * @return 获取 role
	 */
	public Role getRole() {
		return role;
	}
	/**
	 * @param  role
	 */
	public void setRole(Role role) {
		this.role = role;
	}
	
}
