package com.shawn.sys.controller;

import com.shawn.common.ComParams;
import com.shawn.common.RetCode;
import com.shawn.common.Response;
import com.shawn.sys.entity.SysLog;
import com.shawn.sys.service.SyslogService;
import com.shawn.sys.util.URI;
import com.shawn.sys.vo.PageContext;
import com.shawn.sys.vo.SysLogVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 日志管理
 *
 * @author Shawn
 */
@Controller
public class SysLogController {

    @Autowired
    private SyslogService syslogService;

    /**
     * 日志分页查询
     *
     * @param pageNumber
     * @param pageSize
     * @param logVO
     * @return
     */
    @RequestMapping(value = URI.SYSLOG_LISTPAGE, method = RequestMethod.POST)
    @ResponseBody
    public Response<PageContext> listPage(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                          @RequestBody SysLogVO logVO) {
        Response<PageContext> response = new Response();
        PageContext<SysLog> pageContext = syslogService.findLog(pageNumber, pageSize, logVO,"operTime desc");
        response.setBody(pageContext);
        response.getHeader().setCode("0");
        return response;
    }

    /**
     * 保存日志
     * @param userId
     * @param logVO
     * @return
     */
    @RequestMapping(value = URI.SYSLOG_ADD, method = RequestMethod.POST)
    @ResponseBody
    public Response add(@RequestParam(value = ComParams.X_USERID) String userId, @RequestBody SysLogVO logVO,HttpServletRequest request) {
        SysLog log = new SysLog();
        BeanUtils.copyProperties(logVO,log);
        log.setUserId(userId);
        log.setOperTime(new Date());
        log.setHost(this.getIp(request));
        syslogService.save(log);

        Response response = new Response();
        response.getHeader().setCode(RetCode.SUCCESS.getCode());
        response.getHeader().setMessage("保存成功");
        return response;
    }

    /**
     * 获取远程Ip
     * @param request
     * @return
     */
    private String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(',');
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
}
