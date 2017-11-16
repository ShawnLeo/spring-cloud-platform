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
import org.springframework.data.jpa.domain.Specification;
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
//		final Role role = new Role();
//		BeanUtils.copyProperties(roleVO,role);
		Pageable pageable = new PageRequest(role.getPage()-1, role.getSize(), Sort.Direction.ASC, new String[]{"dispOrder","id"});
		Page<Role> roleByPage = this.roleRepository.findAll(new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
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
			}
		},pageable);
		return roleByPage;
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

			//查询角色名称唯一
//			Role roleByRoleCode = roleRepository.findByRoleCode(roleVO.getRoleCode());
//			if(null != roleByRoleCode){
//                throw new ValidationException(RetCode.VALIDATEERROR.getCode(),"该角色名称已被使用");
//            }

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
	public void deleteById(Long roleId) throws ValidationException,EditDomainException{
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
			Map<String,Object> map = new HashMap<String,Object>();
			Role role = this.roleRepository.findOne(roleId);
			if(null == role){
                throw new ValidationException(RetCode.INTERNALEXCEP.getCode(),"该角色不存在");
            }
			//查询资源
			Set<Resource> resources = role.getResources();
			map.put("role",role);
			JsTreeNode<NodeType> root = new JsTreeNode<NodeType>("root_0", "资源权限");
			root.getState().setOpened(true);
			root.getState().setDisabled(true);
			List<Resource> byParentId = this.resourceRepository.findByParentId("0");
			iteratorInitResourceJsTree(root, byParentId, resources);
			map.put("trees",root);
			return map;
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("[=======初始化权限设置出现异常]，errorMSG:{}",e.getMessage());
			}
			if(e instanceof ValidationException){
				ValidationException ce = (ValidationException)e;
				throw new ValidationException(ce.getCode(),ce.getMessage());
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
			JsTreeNode<NodeType> children = new JsTreeNode<NodeType>(resource.getId().toString(), resource.getName(),NodeType.MENU, mselected);
			parent.getChildren().add(children);
			List<Resource> byParentId = this.resourceRepository.findByParentId(String.valueOf(resource.getId()));
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
	private boolean isChecked(Long id, Set<Resource> resources) {
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
	 * @param userId
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

			//根据id查询资源
//			Set<Resource> resources = roleVO.getResources();
//			if(resources.size() > 0){
//                for(Resource resource :resources){
//                    Resource resourceById = resourceRepository.findOne(resource.getId());
//                    if(null == resourceById){
//                        if(logger.isInfoEnabled()){
//                            logger.info("========[根据角色id设置修改资源,资源权限不存在,resource:{}]",resourceById);
//                        }
//                        throw new ValidationException(RetCode.VALIDATEERROR.getCode(), MessageUtils.REQUESTEXCEPTION.getMessage());
//                    }
//                }
//            }
//			BeanUtils.copyProperties(roleVO,roleById);
			roleById.getResources().clear();
			this.roleRepository.saveAndFlush(roleById);
			String[] roleResources=roleVO.getRoleResources();

			for(int i=0;i<roleResources.length;i++){
				Resource resource=new Resource();
				resource.setId(Long.valueOf(roleResources[i]));
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

/*

	@Autowired
	private AppVars appVars;
	@Autowired
	private RoleRepository roleRep;

	@Autowired
	private PageService pageService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrganService organService;
	@Autowired
	private ActionRepository actionRepos;
	@Autowired
	private UserRepository userRepos;
	@Autowired
	private ResourceRepository resourceRepos;

	public Role findByName(String name) {
		return roleRep.findByName(name);
	}

	public Page<Role> findAllRole(Integer pageNumber, Integer pageSize) {
		return roleRep.findAll(pageService.getPageable(pageNumber, pageSize, null));
	}

	public Role findById(Long id) {
		return roleRep.findOne(id);
	}

	@Transactional
	public void deleteById(Long id) {
		if (!userService.findUserByRole(findById(id)).isEmpty()) {
			throw new RuntimeException("当前角色下还有用户，不能删除.");
		}
		roleRep.delete(id);
	}

	@Transactional
	public Role saveOrUpdate(Role role) {
		roleRep.save(role);
		if (role.getResourceSet().size() > 0) {
			for (Resource resource : role.getResourceSet()) {
				if (resource != null) {
					resource.setRole(role);
				}
			}
		}
		return roleRep.save(role);
	}

	public Set<Role> getMyRoles(Set<String> roleNames) {
		Set<Role> myRoles = new HashSet<Role>();
		for (String roleName : roleNames) {
			myRoles.add(roleRep.findByName(roleName));
		}
		return myRoles;
	}

	public Set<String> getRoleNames(String permission) {
		Set<String> roleNames = new HashSet<String>();
		String[] roles = permission.split("\\[")[1].split("\\]")[0].split(",");
		for (String role : roles) {
			roleNames.add(role);
		}
		return roleNames;
	}

	public List<Role> findAll() {
		return roleRep.findAll();
	}

	public PageContext<Role> findRolePageContext(Integer pageNumber, Integer pageSize, final Role criteria) {
		LOG.info("查询角色列表");
		DelegatingRoleSpecificationExecutor executor = new DelegatingRoleSpecificationExecutor(roleRep);
		Sort sort = new Sort(new Order(Sort.Direction.DESC, Role_.id.getName()));
		Pageable pageable = pageService.getPageable(pageNumber, pageSize, sort);
		return pageService.buildPageContext(executor.findAll(criteria, pageable));
	}

	public String getPermission(String[] roleNames) {
		if (roleNames.length != 0) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("anyRoles[");
			stringBuilder.append(appVars.admin + ",");
			if (roleNames.length == 1) {
				stringBuilder.append(roleNames[0] + "]");
			} else {
				for (String roleName : roleNames) {
					stringBuilder.append(roleName + ",");
				}
				stringBuilder.append("]");
			}
			return stringBuilder.toString().replace(",]", "]");
		} else {
			return null;
		}
	}

	public boolean exists(Role role) {

		if (role == null) {
			return false;
		}

		if (!StringUtils.isEmpty(role.getDescription())) {
			Role role_ = roleRep.findByDescription(role.getDescription());
			if (role_ != null && role_.getId() != null && !role_.getId().equals(role.getId())) {
				return true;
			}
		}

		if (!StringUtils.isEmpty(role.getName())) {
			Role role_ = roleRep.findByName(role.getName());
			if (role_ != null && role_.getId() != null && !role_.getId().equals(role.getId())) {
				return true;
			}
		}
		return false;
	}

	@Transactional
	public Role updateRole(Role role) {
		LOG.debug("edit role");
		Role _role = findById(role.getId());
		_role.setName(role.getName());
		_role.setDescription(role.getDescription());
		return roleRep.save(_role);
	}

	public JsTreeNode<NodeType> resourceJsTree(Long id) {
		Role role = findById(id);
		Set<Resource> resources = null;
		if (role != null) {
			resources = role.getResourceSet();
		}
		JsTreeNode<NodeType> root = new JsTreeNode<NodeType>("root_0", "资源权限");
		root.getState().setOpened(true);
		root.getState().setDisabled(true);
		iteratorInitResourceJsTree(root, menuService.findAllTopMenus(), resources);

		return root;
	}

	*//**
	 * 迭代树结构
	 * 
	 * @param parent
	 * @param childrens
	 * @param resources
	 *//*
	private void iteratorInitResourceJsTree(JsTreeNode<NodeType> parent, List<Menu> childrens,
			Set<Resource> resources) {
		for (Menu menu : childrens) {
			// 添加菜单节点
			boolean mselected = isChecked(menu.getUrl(), resources);
			JsTreeNode<NodeType> children = new JsTreeNode<NodeType>("m_" + menu.getId().toString(), menu.getText(),
					NodeType.MENU, mselected);
			parent.getChildren().add(children);
			// 添加页面资源
			for (Action action : menu.getActions()) {
				if (!G.z(action.getAuthorized())) {
					boolean aselected = isChecked(action.getUrl(), resources);
					JsTreeNode<NodeType> achildren = new JsTreeNode<NodeType>("a_" + action.getId().toString(),
							action.getText(), NodeType.ACTION, aselected);
					children.getChildren().add(achildren);
				}
			}
			iteratorInitResourceJsTree(children, menu.getChildren(), resources);
		}
	}

	*//**
	 * 判断是否选中
	 * 
	 * @param url
	 * @param resources
	 * @return
	 *//*
	private boolean isChecked(String url, Set<Resource> resources) {
		if (resources != null) {
			for (Resource resource : resources) {
				if (resource.getPattern().equals(url)) {
					return true;
				}
			}
		}
		return false;
	}

	@Transactional
	public Role editRoleAndResouce(RoleManage roleManage) {
		// 删除角色原有资源权限
		Role oldRole = findById(roleManage.getRole().getId());
		resourceRepos.deleteInBatch(oldRole.getResourceSet());

		Role role = roleManage.getRole();
		setMenusAndActions(roleManage.getSelectedIds(), role);

		role.getResourceSet().remove(null);
		roleRep.save(role);

		return role;
	}

	private void setMenusAndActions(List<String> ids, Role role) {
		List<Menu> menus = new ArrayList<>();
		List<Action> actions = new ArrayList<>();
		for (String id : ids) {
			if (id.startsWith("m_")) {
				Menu menu = menuService.findById(G.l(id.replace("m_", "")));
				selectedMenu(menus, menu);
			} else if (id.startsWith("a_")) {
				Action action = actionRepos.findOne(G.l(id.replace("a_", "")));
				actions.add(action);
				selectedMenu(menus, action.getMenu());
			}
		}
		for (Menu menu : menus) {
			role.getResourceSet().add(buildResource(menu.getUrl(), role));
			for (Action action : menu.getActions()) {
				if (G.z(action.getAuthorized())) {
					actions.add(action);
				}
			}
		}
		for (Action action : actions) {
			role.getResourceSet().add(buildResource(action.getUrl(), role));
		}
	}

	private void selectedMenu(List<Menu> menus, Menu menu) {
		if (menu != null) {
			for (Menu _menu : menus) {
				if (_menu.getId().equals(menu.getId())) {
					return;
				}
			}
			menus.add(menu);
		}
	}

	*//**
	 * 角色资源权限 permission => anyRoles[user]
	 * 
	 * @param url
	 * @param role
	 * @return
	 *//*
	private Resource buildResource(String url, Role role) {
		Resource resource = null;
		if (!StringUtils.isEmpty(url)) {
			resource = new Resource();
			resource.setPattern(url);
			String[] str = { role.getName() };
			resource.setPermission(getPermission(str));
			resource.setRole(role);
		}
		return resource;
	}

	public JsTreeNode<NodeType> organuserJsTree(Long id) {
		Role role = findById(id);
		List<User> _users = userService.findUserByRole(role);
		List<Organ> organs = organService.findAllTopOrgan();

		JsTreeNode<NodeType> root = new JsTreeNode<NodeType>("0", "组织机构－用户", NodeType.ORGAN);
		root.getState().setOpened(true);
		iteratorInitOrganUserJsTree(root, _users, organs);

		// 其他非机构下的用户
		List<User> users = userRepos.findByOrganIsNull();
		JsTreeNode<NodeType> other = new JsTreeNode<NodeType>("-1", "其它用户", NodeType.ORGAN);
		for (User user : users) {
			JsTreeNode<NodeType> unode = new JsTreeNode<NodeType>("u_" + user.getId().toString(), user.getRealName(),
					NodeType.USER, isCheckedUser(user.getId(), _users));
			other.getChildren().add(unode);
		}
		root.getChildren().add(other);

		return root;
	}

	*//**
	 * 递归机构－用户jstree
	 * 
	 * @param root
	 * @param _users
	 * @param organs
	 *//*
	private void iteratorInitOrganUserJsTree(JsTreeNode<NodeType> root, List<User> _users, List<Organ> organs) {
		for (Organ organ : organs) {
			JsTreeNode<NodeType> onode = new JsTreeNode<NodeType>(organ.getId().toString(), organ.getName(),
					NodeType.ORGAN);
			root.getChildren().add(onode);
			for (User user : organ.getUsers()) {
				JsTreeNode<NodeType> unode = new JsTreeNode<NodeType>("u_" + user.getId().toString(),
						user.getRealName(), NodeType.USER, isCheckedUser(user.getId(), _users));
				onode.getChildren().add(unode);
			}
			iteratorInitOrganUserJsTree(onode, _users, organ.getChildren());
		}
	}

	*//**
	 * 判断是否选择（角色是否拥有某用户对象）
	 * 
	 * @param id
	 * @param _users
	 * @return
	 *//*
	private boolean isCheckedUser(Long id, List<User> _users) {
		for (User _user : _users) {
			if (_user.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	@Transactional
	public Role editRoleAndTarget(RoleManage roleManage) {
		Role role = roleManage.getRole();
		List<User> users = userRepos.findAll();
		for (User user : users) {
			Role drole = null;
			for (Role _role : user.getRoleSet()) {
				if (_role.getId().equals(role.getId())) {
					drole = _role;
					break;
				}
			}
			user.getRoleSet().remove(drole);
			if (roleManage.getSelectedIds().contains("u_" + user.getId())) {
				user.getRoleSet().add(role);
			}
		}
		userRepos.save(users);
		return roleManage.getRole();
	}

	public void appendResourceByAction(Action action) {
		List<Resource> resources = resourceRepos.findByPattern(action.getMenu().getUrl());
		List<Role> roles = new ArrayList<Role>();
		for (Resource resource : resources) {
			Role role = resource.getRole();
			role.getResourceSet().add(buildResource(action.getUrl(), role));
			roles.add(role);
		}
		roleRep.save(roles);

	}

	public void deleteResourceByUrl(String url) {
		if (!StringUtils.isEmpty(url)) {
			List<Resource> resources = resourceRepos.findByPattern(url);
			if (!CollectionUtils.isEmpty(resources)) {
				for (Resource r : resources) {
					r.setRole(null);
				}
				resourceRepos.delete(resources);

			}
		}
	}*/

}
