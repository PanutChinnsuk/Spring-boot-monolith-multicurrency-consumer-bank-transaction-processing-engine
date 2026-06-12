package com.project.banksystem.application.config.properties;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.system.transaction")
@Slf4j
public class TransactionConfig {
    private static final Logger log = LoggerFactory.getLogger(TransactionConfig.class);
    private int expiresDay;

    @PostConstruct
    private void log() {
        log.info("expiresDay " + expiresDay);
    }

    public void setExpiresDay(int expiresDay) {
        this.expiresDay = expiresDay;
    }

    public int getExpiresDay() {
        return expiresDay;
    }
}
