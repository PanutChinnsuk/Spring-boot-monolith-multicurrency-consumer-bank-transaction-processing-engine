package com.project.banksystem.infrastructure.security.ratelimiting.config.properties;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.system.ratelimiting.request")
@Slf4j
public class RequestRateLimitingConfig implements RateLimitConfig{
    private static final Logger log = LoggerFactory.getLogger(RequestRateLimitingConfig.class);
    private int maxCapacity;
    private int refillAmount;
    private int minutePerRefill;

    private int globalMaxCapacity;
    private int globalRefillAmount;
    private int globalMinutePerRefill;

    @PostConstruct
    private void log() {
        log.info("REQUEST RATE maxCapacity " + maxCapacity);
        log.info("REQUEST RATE refillAmount " + refillAmount);
        log.info("REQUEST RATE minutePerRefill " + minutePerRefill);

        log.info("REQUEST RATE globalMaxCapacity " + globalMaxCapacity);
        log.info("REQUEST RATE globalRefillAmount " + globalRefillAmount);
        log.info("REQUEST RATE globalMinutePerRefill " + globalMinutePerRefill);
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setMinutePerRefill(int minutePerRefill) {
        this.minutePerRefill = minutePerRefill;
    }

    public void setRefillAmount(int refillAmount) {
        this.refillAmount = refillAmount;
    }

    public void setGlobalMaxCapacity(int globalMaxCapacity) {
        this.globalMaxCapacity = globalMaxCapacity;
    }

    public void setGlobalMinutePerRefill(int globalMinutePerRefill) {
        this.globalMinutePerRefill = globalMinutePerRefill;
    }

    public void setGlobalRefillAmount(int globalRefillAmount) {
        this.globalRefillAmount = globalRefillAmount;
    }

    @Override
    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public int getMinutePerRefill() {
        return minutePerRefill;
    }

    @Override
    public int getRefillAmount() {
        return refillAmount;
    }

    @Override
    public int getGlobalMaxCapacity() {
        return globalMaxCapacity;
    }

    @Override
    public int getGlobalRefillAmount() {
        return globalRefillAmount;
    }

    @Override
    public int getGlobalMinutePerRefill() {
        return globalMinutePerRefill;
    }
}
