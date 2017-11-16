package com.shawn.gateway.util;

import com.alibaba.fastjson.JSONObject;
import com.shawn.common.Response;
import com.shawn.common.RetCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <B>系统名称：</B>美美金融平台<BR>
 * <B>模块名称：</B>模块<BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 *
 * @author 北京鹏润美美科技有限公司
 * @since 2017年07月27日 09时56分
 */
public class ResponseUtil {

    private static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    public static void write(HttpServletResponse servletResponse, RetCode retcode) {
        logger.debug("ResponseUtil.write()");
        Response response = new Response();
        response.getHeader().setCode(retcode.getCode());
        response.getHeader().setMessage(retcode.getMessage());
        try {
            servletResponse.setContentType("text/html;charset=utf-8");
            servletResponse.getWriter().write(JSONObject.toJSONString(response));
        } catch (IOException e) {
            logger.error("IOException:"+e.getMessage());
        }
    }

}
