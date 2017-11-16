package com.shawn.gateway.security.core;

import com.google.common.collect.Lists;
import com.shawn.common.Response;
import com.shawn.common.RetCode;
import com.shawn.gateway.security.remote.vo.UserAuth;
import com.shawn.gateway.security.remote.UserAuthService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 *
 * Created by Shawn on 2017/8/15.
 */
@Component("userDetailsService")
public class CustomUserService implements UserDetailsService {

    /**
     *
     */
    @Autowired
    private UserAuthService userAuthService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        // Feign远程获取
        Response<UserAuth> response = userAuthService.findByAuthId(userName);

        if(!response.getHeader().getCode().equals(RetCode.SUCCESS.getCode())){
            throw new AuthenticationServiceException(String.format("查询用户异常！", userName));
        }

        UserAuth  userAuth  = response.getBody();

        if( userAuth == null ){
            throw new UsernameNotFoundException(String.format("用户：%s ，不存在", userName));
        }
        List<GrantedAuthority> authorities = Lists.newArrayList();
        userAuth.getRoles().forEach((role) -> {
            if(StringUtils.isNotEmpty(role.getRoleCode())){
                authorities.add(new SimpleGrantedAuthority(role.getRoleCode()));
            }

        });
        return new JwtUserDetails(Long.parseLong(userAuth.getUserId()),userAuth.getAuthId(),userAuth.getAuthPass(),
                authorities, !userAuth.getUserStatus().equals("-1"));
//        return new JwtUserDetails(Long.parseLong(userAuth.getUserId()),userAuth.getAuthId(),userAuth.getAuthPass(),
//                AuthorityUtils.createAuthorityList(UserRole.INVESTOR.getCode()), !userAuth.getUserStatus().equals("-1"));
    }
}
