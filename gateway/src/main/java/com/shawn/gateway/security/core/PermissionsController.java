package com.shawn.gateway.security.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermissionsController {

    private static Logger logger = LoggerFactory.getLogger(PermissionsController.class);

    @Autowired
    PermissionsService permissionsService;

    @RequestMapping(value = "/permissions/refresh" ,method = RequestMethod.GET)
    public void refresh(){
        logger.info("刷新资源权限缓存.");
        permissionsService.refresh();
        logger.info("刷新完成.");
    }

}
