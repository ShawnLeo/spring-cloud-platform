package com.shawn.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.shawn.common.BasicErrorPojo;
import com.shawn.common.Response;
import com.shawn.common.RetCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class SendResponsePreFilter extends ZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(SendResponsePreFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 999;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        return context.getThrowable() == null
                && (context.getResponseDataStream() != null || context.getResponseBody() != null)
                && this.vote(context) > 0;
    }

    @Override
    public Object run() {

        RequestContext context = RequestContext.getCurrentContext();
        String body = context.getResponseBody();

        if (StringUtils.isBlank(body)) {
            try {
                InputStream stream = context.getResponseDataStream();
                body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            } catch (IOException e) {
                log.error("SendResponsePreFilter -> 处理异常：{}" , e.getMessage());
                return null;
            }
        }

        log.debug("SendResponsePreFilter -> responseBody(old): {}", body);

        if (StringUtils.isBlank(body)) {
            return null;
        }

        try {
            BasicErrorPojo basicErrorPojo = JSON.parseObject(body, BasicErrorPojo.class);
            if (!StringUtils.isBlank(basicErrorPojo.getError())) {
                Response response = new Response();

                if (!StringUtils.isBlank(basicErrorPojo.getException())
                        && basicErrorPojo.getException().contains("com.shawn.common.BizException")) {
                    response.getHeader().setCode(RetCode.VALIDATEERROR.getCode());
                } else {
                    response.getHeader().setCode(RetCode.INTERNALEXCEP.getCode());
                }

                response.getHeader().setMessage(basicErrorPojo.getMessage() + " path: " + basicErrorPojo.getPath());

                body = JSON.toJSONString(response);
            }
        } catch (Exception e) {
            log.debug("SendResponsePreFilter -> 处理异常：{}" , e.getMessage());
        }

        log.debug("SendResponsePreFilter -> responseBody(new): {}" , body);
        context.setResponseBody(body);

        return null;
    }

    private int vote(RequestContext context){

        int[] a = {0};

        context.getOriginResponseHeaders().forEach((Pair<String, String> pair)  ->{
            if(pair.first().equals("Content-Type") && pair.second().equals("application/json;charset=UTF-8")){
                a[0]++;
            }
        });
        return a[0];
    }

}
