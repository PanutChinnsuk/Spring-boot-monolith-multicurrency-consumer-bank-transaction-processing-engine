package com.project.banksystem.application.config.properties;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.system.authentication")
@Slf4j
public class SystemAuthenticationConfig {
    private static final Logger log = LoggerFactory.getLogger(SystemAuthenticationConfig.class);
    private int attemptExpiresMinute;
    private int maxAttempt;

    @PostConstruct
    private void log() {
        log.info("attemptExpiresMinute " + attemptExpiresMinute);
        log.info("maxAttempt " + maxAttempt);

    }

    public void setAttemptExpiresMinute(int attemptExpiresMinute) {
        this.attemptExpiresMinute = attemptExpiresMinute;
    }

    public void setMaxAttempt(int maxAttempt) {
        this.maxAttempt = maxAttempt;
    }

    public int getAttemptExpiresMinute() {
        return attemptExpiresMinute;
    }

    public int getMaxAttempt() {
        return maxAttempt;
    }
}
