package com.shawn.gateway.security.remote;

import com.shawn.common.Response;
import com.shawn.gateway.security.remote.vo.Resource;

import java.util.List;

/**
 * 用户服务（认证）
 *
 * Created by Shawn on 2017/9/12.
 */

public class ResourceServiceHystrix implements ResourceService{


    @Override
    public Response<List<Resource>> findAll() {
        return null;
    }
}
