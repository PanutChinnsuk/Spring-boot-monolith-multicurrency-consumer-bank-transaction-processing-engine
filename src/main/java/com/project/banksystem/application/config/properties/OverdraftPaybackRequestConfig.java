package com.project.banksystem.application.config.properties;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@ConfigurationProperties(prefix = "app.system.overdraft.request")
@Slf4j
public class OverdraftPaybackRequestConfig {
    private static final Logger log = LoggerFactory.getLogger(OverdraftPaybackRequestConfig.class);
    private int expiresDay;

    @PostConstruct
    private void log() {
        log.info("expiresDay " + expiresDay);
    }

    public LocalDateTime getExpiry() {
        return LocalDateTime.now().plus(expiresDay, ChronoUnit.DAYS);
    }

    public void setExpiresDay(int expiresDay) {
        this.expiresDay = expiresDay;
    }

    public int getExpiresDay() {
        return expiresDay;
    }
}
