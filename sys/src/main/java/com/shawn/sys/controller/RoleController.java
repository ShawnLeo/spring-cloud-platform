package com.shawn.sys.controller;

import com.shawn.common.ComParams;
import com.shawn.common.RetCode;
import com.shawn.common.Response;
import com.shawn.sys.entity.Role;
import com.shawn.sys.exception.EditDomainException;
import com.shawn.sys.exception.ValidationException;
import com.shawn.sys.service.RoleService;
import com.shawn.sys.util.ValidateUtils;
import com.shawn.sys.vo.RoleVO;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: RoleController
 * @Description: 系统角色维护
 */
@RestController
@RequestMapping("/role")
public class RoleController {
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;

    @Autowired
    private DiscoveryClient discoveryClient;
	/**
	 * 保存角色
	 * @param roleVO
	 * @param result
	 * @param userId
	 * @return
	 * @throws ValidationException
	 */
	@PostMapping("/save")
	public Response saveRole(@Valid @RequestBody RoleVO roleVO, BindingResult result, @RequestParam(ComParams.X_USERID)String userId) throws ValidationException{
		//验证表单数据
		if(result.hasErrors()){
			throw new ValidationException(RetCode.VALIDATEERROR.getCode(), ValidateUtils.addError(result));
		}
		//保存数据
		this.roleService.saveRole(roleVO,userId);
		return Response.success(null);
	}

	/**
	 * 角色列表
	 * @return
	 */
	@RequestMapping("/list")
	public Response queryAllRoles(@RequestBody RoleVO roleVO){
		Page<Role> roleCriteria = this.roleService.findRoleByCriteria(roleVO);
		return Response.success(roleCriteria);
	}

	/**
	 * 修改角色
	 * @param roleVO
	 * @param result
	 * @param userId
	 * @return
	 * @throws ValidationException
	 */
	@RequestMapping("/update")
	public Response updateRole(@Valid @RequestBody RoleVO roleVO, BindingResult result, @RequestParam(ComParams.X_USERID)String userId) throws ValidationException{
		logger.info("id:{}",roleVO.getId());
		logger.info("roleCode:{}",roleVO.getRoleCode());
		logger.info("name:{}",roleVO.getName());
		//验证表单数据
		if(result.hasErrors()){
			throw new ValidationException(RetCode.VALIDATEERROR.getCode(), ValidateUtils.addError(result));
		}
		//保存数据
		this.roleService.updateRole(roleVO,userId);
		return Response.success(null);
	}

	/**
	 * 根据角色id查询角色
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/get/{id}")
	public Response getById(@PathVariable("id") Long roleId) throws EditDomainException {
		Role role = this.roleService.findById(roleId);
		return Response.success(role);
	}

	/**
	 * 根据角色id删除角色
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public Response deleteById(@PathVariable("id") Long roleId) throws ValidationException{
		logger.info("id:{}",roleId);
		this.roleService.deleteById(roleId);
		return Response.success(null);
	}

	/**
	 *  初始化权限设置
	 */
	@RequestMapping("/permissions/{id}")
	public Response initPermissions(@PathVariable(value = "id") Long roleId) throws ValidationException {
		Map<String,Object> map = this.roleService.initPermissions(roleId);
		return Response.success(map);
	}

	/**
	 * 权限设置修改保存
	 */
	@RequestMapping("/set")
	public Response setPermissions(@RequestBody RoleVO roleVO) throws ValidationException{
		this.roleService.setPermissionsByRoleId(roleVO);
        notifyGatewayRefresh();
		return Response.success(null);
	}

    /**
     *
     * 通知每一个网管刷新资源权限列表
     *
     * @throws ValidationException
     */
	/**
	 *
	 * 通知每一个网管刷新资源权限列表
	 *
	 *
	 */
	private void notifyGatewayRefresh() {
		List<ServiceInstance> gatewayList  = discoveryClient.getInstances("platform-gateway");
		for(ServiceInstance serviceInstance : gatewayList){
			URI uri = serviceInstance.getUri();
			logger.info("刷新网关地址[{}]资源权限缓存.",uri);
			try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
				HttpGet get = new HttpGet(serviceInstance.getUri()+"/permissions/refresh");
				httpClient.execute(get);
			} catch (IOException e) {
				logger.warn("刷新网关地址[{}]资源权限缓存失败：{}",uri.toString(),e);
			}
		}

	}

}
