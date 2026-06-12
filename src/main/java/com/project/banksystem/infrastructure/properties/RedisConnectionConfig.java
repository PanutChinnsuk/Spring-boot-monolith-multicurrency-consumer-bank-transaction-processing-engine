package com.project.banksystem.infrastructure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.redis")
public class RedisConnectionConfig {
    private int connectionTimeOutSecond;
    private int commandTimeOutSecond;
    private int codeConnectionTimeOutSecond;


    public void setCodeConnectionTimeOutSecond(int codeConnectionTimeOutSecond) {
        this.codeConnectionTimeOutSecond = codeConnectionTimeOutSecond;
    }

    public void setCommandTimeOutSecond(int commandTimeOutSecond) {
        this.commandTimeOutSecond = commandTimeOutSecond;
    }

    public void setConnectionTimeOutSecond(int connectionTimeOutSecond) {
        this.connectionTimeOutSecond = connectionTimeOutSecond;
    }

    public int getCodeConnectionTimeOutSecond() {
        return codeConnectionTimeOutSecond;
    }

    public int getCommandTimeOutSecond() {
        return commandTimeOutSecond;
    }

    public int getConnectionTimeOutSecond() {
        return connectionTimeOutSecond;
    }
}
