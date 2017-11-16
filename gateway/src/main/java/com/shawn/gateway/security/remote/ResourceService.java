package com.shawn.gateway.security.remote;

import com.shawn.common.Response;
import com.shawn.gateway.security.remote.vo.Resource;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 角色资源权限（鉴权）
 * <p>
 * Created by Shawn on 2017/9/12.
 */
@FeignClient(name = "platform-sys"/*,fallback = UserAuthServiceHystrix.class*/)
public interface ResourceService {

    @RequestMapping(value = "/resource/get/all")
    Response<List<Resource>> findAll();

}
