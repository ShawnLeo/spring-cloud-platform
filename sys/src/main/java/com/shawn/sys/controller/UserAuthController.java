package com.shawn.sys.controller;

import com.google.common.collect.Sets;
import com.shawn.common.Response;
import com.shawn.sys.entity.Role;
import com.shawn.sys.entity.UserAuth;
import com.shawn.sys.service.UserAuthService;
import com.shawn.sys.vo.RoleVO;
import com.shawn.sys.vo.UserAuthVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 户管理
 */
@RestController
@RequestMapping("/auth")
public class UserAuthController {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);

    @Autowired
    private UserAuthService userAuthService;

    @RequestMapping(value = "/get/{authId}", method = RequestMethod.GET)
    Response<UserAuthVo> findByAuthId(@PathVariable("authId") String authId) {
        logger.info("查询用户：{}", authId);
        //保存数据
        UserAuth userAuth = userAuthService.findByAuthId(authId);
        UserAuthVo userAuthVo =null;

        if (userAuth != null) {
            userAuthVo = new UserAuthVo();
            userAuthVo.setAuthId(userAuth.getAuthId());
            userAuthVo.setAuthPass(userAuth.getAuthPass());
            userAuthVo.setAuthType(userAuth.getAuthType());
            userAuthVo.setUserId(userAuth.getUser().getId().toString());
            userAuthVo.setUserStatus(userAuth.getUser().getStatus());
            Set<RoleVO> roleVOs = Sets.newHashSet();
            for (Role role:userAuth.getUser().getRoles()) {
                RoleVO roleVO = new RoleVO();
                BeanUtils.copyProperties(role,roleVO);
                roleVOs.add(roleVO);
            }
            userAuthVo.setRoles(roleVOs);
        }

        return Response.success(userAuthVo);
    }

}
