package com.shawn.sys.controller;

import com.shawn.common.ComParams;
import com.shawn.common.RetCode;
import com.shawn.common.Response;
import com.shawn.sys.entity.Role;
import com.shawn.sys.entity.User;
import com.shawn.sys.entity.UserAuth;
import com.shawn.sys.exception.ValidationException;
import com.shawn.sys.service.UserService;
import com.shawn.sys.util.ValidateUtils;
import com.shawn.sys.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private static final Pattern phonePattern = Pattern.compile("^1[34578]\\d{9}$");


	@Autowired
	private UserService userService;

	/**
	 * 用户保存
	 * @param userVo
	 * @param result
	 * @param userId
	 * @return
	 * @throws ValidationException
	 */
	@PostMapping("/save")
	public Response<Object> saveUser(@Valid @RequestBody UserVO userVo, BindingResult result, @RequestParam(ComParams.X_USERID)String userId) throws ValidationException {
		Response response=new Response();

		//验证表单数据
		if(userVo.getId()==null) {
			ValidateUtils.isLoginName(userVo.getAuthId(),result);
			if(result.hasErrors()){
				throw new ValidationException(RetCode.VALIDATEERROR.getCode(),ValidateUtils.addError(result));
			}
			//去空格
			userVo.setAuthId(userVo.getAuthId().trim());
			userVo.setRealName(userVo.getRealName().trim());
			userVo.setAuthPass(userVo.getAuthPass().trim());
			userVo.setEmail(userVo.getEmail().trim());
		}
		//保存数据
		if (userVo.getRoles()!=null && userVo.getRoles().length>0){
			Set<Role> roleSet=new HashSet<>();
			for (String roleId:userVo.getRoles()) {
				Role role=new Role();
				role.setId(Long.valueOf(roleId));
				roleSet.add(role);
			}
			userVo.setRoleSet(roleSet);
		}

		try{
			this.userService.saveUser(userVo,userId);
			response.getHeader().setCode(RetCode.SUCCESS.getCode());
		}catch (Exception e){
			logger.error(" user save exception：", e);
			response = Response.error(e.getMessage());

		}
		return response;
	}



	/**
	 * 修改用户
	 * @param userVo
	 * @param result
	 * @param loginUserId
	 * @rturn
	 * @throws ValidationException
	 */
	@RequestMapping("/update")
	public Response updateUser(@Valid @RequestBody UserVO userVo, BindingResult result, @RequestParam(ComParams.X_USERID)String loginUserId) throws ValidationException{

		Response response=new Response();
		if(StringUtils.isBlank(userVo.getRealName())) return  Response.error("用户姓名输入有误！");
		if(StringUtils.isBlank(userVo.getPhone()) || !phonePattern.matcher(userVo.getPhone()).matches() ) return  Response.error("手机号码输入有误！");
		if(StringUtils.isBlank(userVo.getGender())) return  Response.error("用户性别输入有误！");
		if(userVo.getRoles()==null || userVo.getRoles().length==0) return  Response.error("用户角色输入有误！");


		if (userVo.getRoles()!=null && userVo.getRoles().length>0){
			Set<Role> roleSet=new HashSet<>();
			for (String roleId:userVo.getRoles()) {
				Role role=new Role();
				role.setId(Long.valueOf(roleId));
				roleSet.add(role);
			}
			userVo.setRoleSet(roleSet);
		}

		try{
			this.userService.updateUser(userVo,loginUserId);
			response.getHeader().setCode(RetCode.SUCCESS.getCode());
		}catch (Exception e){
			logger.error(" user update exception：", e);
			response = Response.error(e.getMessage());

		}
		return response;
	}


	/**
	 * 修改密码
	 * @param userVO
	 * @param result
	 * @param loginUserId
	 * @return
	 */
	@RequestMapping("/updatePwd")
	@ResponseBody
	public Response updatePwd(@RequestBody UserVO userVO, BindingResult result,@RequestParam(ComParams.X_LOGINNAME)String authId, @RequestParam(ComParams.X_USERID)String loginUserId) throws ValidationException {
		Response response = new Response();
		userVO.setAuthId(authId);
		ValidateUtils.validationUpdatePwd(userVO,result);
		if(result.hasErrors()){
			throw new ValidationException(RetCode.VALIDATEERROR.getCode(),ValidateUtils.addError(result));
		}
		try {
			this.userService.updatePwdById(userVO,loginUserId);
			response.getHeader().setCode(RetCode.SUCCESS.getCode());
		} catch (Exception e) {
			logger.error(" Password edit exception：", e);
			response = Response.error(e.getMessage());
		}
		return response;
	}




	/**
	 * 重置密码
	 * @param loginUserId
	 * @return
	 */
	@RequestMapping("/resetPwd/{id}")
	public Response resetPwd(@RequestBody UserVO userVO,BindingResult result,@RequestParam(ComParams.X_USERID)String loginUserId,@PathVariable("id") Long userId) throws ValidationException {
		Response response = new Response();
		ValidateUtils.validationAuthId(userVO,result);
		if(result.hasErrors()){
			throw new ValidationException(RetCode.VALIDATEERROR.getCode(),ValidateUtils.addError(result));
		}

		try {
			this.userService.resetPwd(userVO,loginUserId,userId);
			response.getHeader().setCode(RetCode.SUCCESS.getCode());
		} catch (Exception e) {
			logger.error(" Password reset exception：", e);
			response = Response.error(e.getMessage());
		}
		return response;

	}

	/**
	 * 锁定、解锁账户
	 * @return
	 */
	@RequestMapping("/lock/{id}")
	public Response lockUser(@RequestParam(ComParams.X_USERID)String loginUserId,@PathVariable("id") Long userId) throws ValidationException {
		Response response = new Response();
		try {
			this.userService.lockUser(loginUserId,userId);
			response.getHeader().setCode(RetCode.SUCCESS.getCode());
		} catch (Exception e) {
			logger.error(" user account lock exception：", e);
			response = Response.error(e.getMessage());
		}
		return response;
	}


	/**
	 *  用户分页
	 * @param pageNumber
	 * @param pageSize
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value = "/page")
	@ResponseBody
	public Response<Page> page(@RequestParam(value = "pageNumber") Integer pageNumber,
									  @RequestParam(value = "pageSize", required = false) Integer pageSize,@RequestBody UserVO userVo) {

		User user=new User();
		BeanUtils.copyProperties(userVo,user);
		if (userVo.getRoles()!=null && userVo.getRoles().length>0){
			Set<Role> roleSet=new HashSet<>();
			for (String roleId:userVo.getRoles()) {
				Role role=new Role();
				role.setId(Long.valueOf(roleId));
				roleSet.add(role);
			}
			user.setRoles(roleSet);
		}

		if (!StringUtils.isBlank(userVo.getAuthId())){
			Set<UserAuth> authSet=new HashSet<>();
			UserAuth auth=new UserAuth();
			auth.setAuthId(userVo.getAuthId());
			authSet.add(auth);
			user.setUserAuths(authSet);
		}
		Page<User> pages = userService.findUserPageContext(pageNumber-1, pageSize, user);

		return Response.success(pages);
	}

}
