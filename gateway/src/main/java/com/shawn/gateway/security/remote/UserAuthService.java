package com.shawn.gateway.security.remote;

import com.shawn.common.Response;
import com.shawn.gateway.security.remote.vo.UserAuth;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户服务（认证）
 *
 * Created by Shawn on 2017/9/12.
 */
@FeignClient(name = "platform-sys"/*,fallback = UserAuthServiceHystrix.class*/)
public interface UserAuthService {

    @RequestMapping(value = "/auth/get/{authId}")
    Response<UserAuth> findByAuthId(@PathVariable("authId") String authId);

}
