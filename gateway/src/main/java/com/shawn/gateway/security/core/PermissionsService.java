package com.shawn.gateway.security.core;


import com.shawn.gateway.security.remote.vo.Resource;

import java.util.List;

/**
 * 角色资源权限
 * <p>
 * Created by Shawn on 2017/9/12.
 */
public abstract class PermissionsService {

    //  true从数据库中获取细粒度资源权限
    //  false从配置中获取粗粒度资源权限
    protected boolean fromDb = false;

    protected boolean received = false;

    public void setFromDb(boolean fromDb) {
        this.fromDb = fromDb;
    }

    /**
     * 刷新资源权限
     * <p>
     * 前面的覆盖后面的
     *
     * @return
     */
    public abstract void refresh();

    /**
     * 数据库中获取
     * <p>
     * 前面的覆盖后面的
     *
     * @return
     */
    public abstract List<Resource> findAll();

}

