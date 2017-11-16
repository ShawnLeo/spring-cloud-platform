package com.shawn.sys.entity;

import com.shawn.sys.dialect.AbstractSyslog;
import com.shawn.sys.dialect.Schema;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志
 * Created by wanglu-jf on 17/9/7.
 */
@Entity
@Table(name = Schema.Tables.SYSLOG)
public class SysLog extends AbstractSyslog implements Serializable {

    private static final long serialVersionUID = -7476778062831725299L;

    /**
     * 用户ID
     */
    @Column(name = "USER_ID",length = 40)
    private String userId;

    /**
     * 日志类型
     */
    @Column(name = "LOG_TYPE",length = 40)
    private String logType;

    /**
     * 日志模块
     */
    @Column(name = "LOG_MODULE",length = 40)
    private String logModule;

    /**
     * 日志详情
     */
    @Column(name = "REMARKS",length = 600)
    private String remarks;

    /**
     * 操作主机
     */
    @Column(name = "HOST",length = 20)
    private String host;

    /**
     * 操作时间
     */
    @Column(name = "OPER_TIME",length = 20)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date operTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogModule() {
        return logModule;
    }

    public void setLogModule(String logModule) {
        this.logModule = logModule;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
