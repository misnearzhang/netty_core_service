package com.syuct.core_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 全局配置 包括线程池  端口等
 * Created by zhanglong on 17-3-21.
 */

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="systemconfig")
public class SystemConfig {
    private String delimiter;
    private int idleReadTime;
    private int idleWriteTime;
    private int tcpPort;
    private int threadCorePoolSize;
    private int threadMaximumPoolSize;
    private int threadKeepAliveTime;
    private int threadRetransFirstTime;
    private int threadRetransSecondTime;
    private int threadRetransThirdTime;

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public int getIdleReadTime() {
        return idleReadTime;
    }

    public void setIdleReadTime(int idleReadTime) {
        this.idleReadTime = idleReadTime;
    }

    public int getIdleWriteTime() {
        return idleWriteTime;
    }

    public void setIdleWriteTime(int idleWriteTime) {
        this.idleWriteTime = idleWriteTime;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    public int getThreadCorePoolSize() {
        return threadCorePoolSize;
    }

    public void setThreadCorePoolSize(int threadCorePoolSize) {
        this.threadCorePoolSize = threadCorePoolSize;
    }

    public int getThreadMaximumPoolSize() {
        return threadMaximumPoolSize;
    }

    public void setThreadMaximumPoolSize(int threadMaximumPoolSize) {
        this.threadMaximumPoolSize = threadMaximumPoolSize;
    }

    public int getThreadKeepAliveTime() {
        return threadKeepAliveTime;
    }

    public void setThreadKeepAliveTime(int threadKeepAliveTime) {
        this.threadKeepAliveTime = threadKeepAliveTime;
    }

    public int getThreadRetransFirstTime() {
        return threadRetransFirstTime;
    }

    public void setThreadRetransFirstTime(int threadRetransFirstTime) {
        this.threadRetransFirstTime = threadRetransFirstTime;
    }

    public int getThreadRetransSecondTime() {
        return threadRetransSecondTime;
    }

    public void setThreadRetransSecondTime(int threadRetransSecondTime) {
        this.threadRetransSecondTime = threadRetransSecondTime;
    }

    public int getThreadRetransThirdTime() {
        return threadRetransThirdTime;
    }

    public void setThreadRetransThirdTime(int threadRetransThirdTime) {
        this.threadRetransThirdTime = threadRetransThirdTime;
    }
}
