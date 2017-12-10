package com.shawn.sys.service;

import com.shawn.common.RetCode;
import com.shawn.sys.entity.Resource;
import com.shawn.sys.entity.Role;
import com.shawn.sys.exception.EditDomainException;
import com.shawn.sys.exception.ValidationException;
import com.shawn.sys.repository.ResourceRepository;
import com.shawn.sys.repository.RoleRepository;
import com.shawn.sys.util.NodeType;
import com.shawn.sys.vo.JsTreeNode;
import com.shawn.sys.vo.RoleVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class RoleService {
	private static Logger logger = LoggerFactory.getLogger(RoleService.class);

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ResourceRepository resourceRepository;

	/**
	 * 保存角色
	 * @param roleVO
	 * @param userId
	 */
	@Transactional
	public void saveRole(RoleVO roleVO, String userId) throws ValidationException{
		if(logger.isInfoEnabled()){
			logger.info("========[保存角色]，roleVO：{}，userId：{}",roleVO,userId);
		}
		try {
			Role role = new Role();
			BeanUtils.copyProperties(roleVO,role);
			//查询角色名称唯一
			Role roleByName = roleRepository.findByRoleCode(role.getRoleCode());
			if(null != roleByName){
				throw new ValidationException(RetCode.VALIDATEERROR.getCode(),"该角色名称已被使用");
			}
			role.setCreateBy(userId);//创建者
			this.roleRepository.save(role);
		} catch (BeansException e) {
			if(logger.isErrorEnabled()){
				logger.error("[=======保存角色时属性copy赋值信息出现异常]，errorMSG:{}",e.getMessage());
			}
			throw new ValidationException(RetCode.INTERNALEXCEP.getCode(),RetCode.INTERNALEXCEP.getMessage());
		}catch (ValidationException e) {
			if(logger.isErrorEnabled()){
				logger.error("[=======保存角色信息出现异常]，errorMSG:{}",e.getMessage());
			}
			throw new ValidationException(e.getCode(),e.getMessage());
		}
	}


	/**
	 * 按条件分页查询角色及角色列表
	 * @param role
	 * @return
	 */
	public Page<Role> findRoleByCriteria(final RoleVO role){
		if(logger.isInfoEnabled()){
			logger.info("========[按条件分页查询角色]，roleVO：{}",role);
		}
		String[] datas=new String[]{"dispOrder","id"};
		Pageable pageable = new PageRequest(role.getPage()-1, role.getSize(), Sort.Direction.ASC, datas);
		return this.roleRepository.findAll((Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)-> {
			List<Predicate> list = new ArrayList<>();
			if(StringUtils.isNotBlank(role.getRoleCode())){
				list.add(criteriaBuilder.equal(root.get("roleCode").as(String.class), role.getRoleCode().trim()));
			}

			if(StringUtils.isNotBlank(role.getName())){
				list.add(criteriaBuilder.equal(root.get("name").as(String.class), role.getName().trim()));
			}

			if(StringUtils.isNotBlank(role.getRemarks())){
				list.add(criteriaBuilder.like(root.get("remarks").as(String.class), role.getRemarks().trim()));
			}
			Predicate[] p = new Predicate[list.size()];
			return criteriaBuilder.and(list.toArray(p));
		},pageable);
	}

	/**
	 * 修改角色
	 * @param roleVO
	 * @param userId
	 * @throws ValidationException
	 */
	@Transactional
	public void updateRole(RoleVO roleVO, String userId) throws ValidationException{
		if(logger.isInfoEnabled()){
			logger.info("========[修改角色]，roleVO：{}，userId：{},roleId:{}",roleVO,userId,roleVO.getId());
		}
		try {
			//根据id查询角色
			Role roleById = roleRepository.findOne(roleVO.getId());
			if(null == roleById){
				throw new ValidationException(RetCode.VALIDATEERROR.getCode(),"角色不存在");
			}

			BeanUtils.copyProperties(roleVO,roleById);
			roleById.setUpdateBy(userId);
			roleById.setUpdateTime(new Date());
			this.roleRepository.saveAndFlush(roleById);
		} catch (BeansException e) {
			if(logger.isErrorEnabled()){
				logger.error("[=======修改角色时属性copy赋值信息出现异常]，errorMSG:{}",e.getMessage());
			}
			throw new ValidationException(RetCode.INTERNALEXCEP.getCode(),RetCode.INTERNALEXCEP.getMessage());
		}catch (ValidationException e) {
			if(logger.isErrorEnabled()){
				logger.error("[=======修改角色验证信息出现异常]，errorMSG:{}",e.getMessage());
			}
			throw new ValidationException(e.getCode(),e.getMessage());
		}
	}

	/**
	 * 根据角色id查询角色
	 * @param roleId
	 * @return
	 */
	public Role findById(Long roleId) throws EditDomainException {
		if(logger.isInfoEnabled()){
			logger.info("========[根据角色id查询角色]，roleId：{}",roleId);
		}
		try {
			return this.roleRepository.findOne(roleId);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("[=======根据角色id查询角色出现异常]，errorMSG:{}",e.getMessage());
			}
			throw new EditDomainException(RetCode.INTERNALEXCEP.getCode(),RetCode.INTERNALEXCEP.getMessage());
		}
	}

	/**
	 * 根据角色id删除角色
	 * @param roleId
	 * @return
	 */
	@Transactional
	public void deleteById(Long roleId) throws ValidationException{
		if(logger.isInfoEnabled()){
			logger.info("========[根据角色id删除角色]，roleId：{}",roleId);
		}
		Role role = this.roleRepository.findOne(roleId);
		if(null == role){
			throw new ValidationException(RetCode.INTERNALEXCEP.getCode(),"该角色不存在");
		}
		int size=this.roleRepository.findByRoleUserId(roleId).size();
		logger.info("size:{}",size);
		if(0 != size){
			throw new ValidationException(RetCode.VALIDATEERROR.getCode(),"该角色下有用户，不能删除");
		}
		try {
			this.roleRepository.delete(roleId);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("[=======根据角色id删除角色出现异常]，errorMSG:{}",e.getMessage());
			}
			throw new EditDomainException(RetCode.INTERNALEXCEP.getCode(),RetCode.INTERNALEXCEP.getMessage());
		}
	}

	/**
	 *  初始化权限设置
	 */
	public Map<String,Object> initPermissions(Long roleId) throws ValidationException {
		try {
			Map<String,Object> map = new HashMap<>();
			Role role = this.roleRepository.findOne(roleId);
			if(null == role){
				throw new ValidationException(RetCode.INTERNALEXCEP.getCode(),"该角色不存在");
			}
			//查询资源
			Set<Resource> resources = role.getResources();
			map.put("role",role);
			JsTreeNode<NodeType> root = new JsTreeNode<>("root_0", "资源权限");
			root.getState().setOpened(true);
			root.getState().setDisabled(true);
			List<Resource> byParentId = this.resourceRepository.findByParentIdOrderByDispOrder("0");
			iteratorInitResourceJsTree(root, byParentId, resources);
			map.put("trees",root);
			return map;
		}catch (ValidationException e) {
			if(logger.isErrorEnabled()){
				logger.error("[=======初始化权限设置出现异常]，errorMSG:{}",e.getMessage());
			}
			throw new ValidationException(e.getCode(),e.getMessage());
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("[=======初始化权限设置出现异常]，errorMSG:{}",e.getMessage());
			}
			throw new ValidationException(RetCode.INTERNALEXCEP.getCode(),RetCode.INTERNALEXCEP.getMessage());
		}
	}

	/**
	 * 迭代树结构
	 *
	 * @param parent
	 * @param resources 所有的资源
	 * @param resourcesByRole 该角色所携带的资源
	 */
	private void iteratorInitResourceJsTree(JsTreeNode<NodeType> parent, List<Resource> resources,Set<Resource> resourcesByRole) {
		for (Resource resource : resources) {
			// 添加菜单节点
			boolean mselected = isChecked(resource.getId(), resourcesByRole);
			JsTreeNode<NodeType> children = new JsTreeNode<>(resource.getId().toString(), resource.getName(),NodeType.MENU, mselected);
			parent.getChildren().add(children);
			List<Resource> byParentId = this.resourceRepository.findByParentIdOrderByDispOrder(String.valueOf(resource.getId()));
			iteratorInitResourceJsTree(children,byParentId,resourcesByRole);
		}
	}

	/**
	 * 判断是否选中
	 *
	 * @param id
	 * @param resources
	 * @return
	 */
	private boolean isChecked(String id, Set<Resource> resources) {
		if (resources != null) {
			for (Resource resource : resources) {
				if (resource.getId().equals(id)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 权限设置修改保存
	 * @param roleVO
	 */
	@Transactional
	public void setPermissionsByRoleId(RoleVO roleVO) throws ValidationException {
		if(logger.isInfoEnabled()){
			logger.info("========[根据角色id设置修改资源]，roleVO,{}",roleVO);
		}
		try {
			//根据id查询角色
			Role roleById = roleRepository.findOne(roleVO.getId());
			if(null == roleById){
				throw new ValidationException(RetCode.VALIDATEERROR.getCode(),"角色不存在");
			}

			roleById.getResources().clear();
			this.roleRepository.saveAndFlush(roleById);
			String[] roleResources=roleVO.getRoleResources();

			for(int i=0;i<roleResources.length;i++){
				Resource resource=new Resource();
				resource.setId(roleResources[i]);
				roleById.getResources().add(resource);
			}
			this.roleRepository.saveAndFlush(roleById);
		} catch (ValidationException e) {
			if(logger.isErrorEnabled()){
				logger.error("[=======根据角色id设置修改资源出现异常]，errorMSG:{}",e.getMessage());
			}
			throw new ValidationException(e.getCode(),e.getMessage());
		}catch (BeansException e) {
			if(logger.isErrorEnabled()){
				logger.error("[=======根据角色id设置修改资源时属性copy赋值信息出现异常]，errorMSG:{}",e.getMessage());
			}
			throw new ValidationException(RetCode.INTERNALEXCEP.getCode(),RetCode.INTERNALEXCEP.getMessage());
		}


	}
}
