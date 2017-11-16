package com.shawn.gateway.security.listener;

import com.shawn.gateway.security.core.PermissionsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {
    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private PermissionsService permissionsService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        // 刷新资源权限缓存
        permissionsService.refresh();
        logger.info("网关启动完成");
    }
}