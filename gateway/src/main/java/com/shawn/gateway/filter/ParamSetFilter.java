package com.shawn.gateway.filter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.shawn.common.ComParams;
import com.shawn.gateway.util.HttpHelper;
import com.shawn.gateway.util.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ParamSetFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(ParamSetFilter.class);

    @Value("${name.token:xToken}")
    private String token;

    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getRequest().getAttribute("userSession") == null) {
            return false;
        }
        return true;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();

        UserSession userSession = (UserSession) ctx.getRequest().getAttribute("userSession");

        Map<String, List<String>> params = ctx.getRequestQueryParams();
        if (params == null) {
            params = Maps.newHashMap();
        }

        HttpHelper.setRequestParams(params, ctx.getRequest().getQueryString());

        // 设置用户id及用户角色参数
        params.put(ComParams.X_USERID, Lists.newArrayList(userSession.getId().toString()));
        params.put(ComParams.X_LOGINNAME, Lists.newArrayList(userSession.getLoginName()));
        params.put(ComParams.X_ROLECODE, Lists.newArrayList(userSession.getRoleNames()));
        params.put(ComParams.X_MOBILE, Lists.newArrayList(userSession.getMobile()));

        ctx.setRequestQueryParams(params);

        return null;
    }

}
