package com.project.banksystem.infrastructure.security.ratelimiting.config.properties;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.system.ratelimiting")
@Slf4j
public class SharedRateLimitingConfig {
    private static final Logger log = LoggerFactory.getLogger(SharedRateLimitingConfig.class);
    private int deleteAfterAccessMinute;
    private String apiRateKeyPrefixed;
    private String authRateKeyPrefixed;
    private String twoFactorRateKeyPrefixed;
    private String requestRateKeyPrefixed;
    private String globalAuthRateKeyPrefixed;
    private String globalApiRateKeyPrefixed;
    private String globalTwoFactorRateKeyPrefixed;
    private String globalRequestRateKeyPrefixed;

    @PostConstruct
    private void log() {
        log.info("deleteAfterAccessMinute " + deleteAfterAccessMinute);
        log.info("apiRateKeyPrefixed " + apiRateKeyPrefixed);
        log.info("authRateKeyPrefixed " + authRateKeyPrefixed);
        log.info("twoFactorRateKeyPrefixed " + twoFactorRateKeyPrefixed);
        log.info("requestRateKeyPrefixed " + requestRateKeyPrefixed);
        log.info("globalAuthRateKeyPrefixed " + globalAuthRateKeyPrefixed);
        log.info("globalApiRateKeyPrefixed " + globalApiRateKeyPrefixed);
        log.info("globalTwoFactorRateKeyPrefixed " + globalTwoFactorRateKeyPrefixed);
        log.info("globalRequestRateKeyPrefixed " + globalRequestRateKeyPrefixed);
    }

    public void setDeleteAfterAccessMinute(int deleteAfterAccessMinute) {
        this.deleteAfterAccessMinute = deleteAfterAccessMinute;
    }

    public void setGlobalTwoFactorRateKeyPrefixed(String globalTwoFactorRateKeyPrefixed) {
        this.globalTwoFactorRateKeyPrefixed = globalTwoFactorRateKeyPrefixed;
    }

    public void setTwoFactorRateKeyPrefixed(String twoFactorRateKeyPrefixed) {
        this.twoFactorRateKeyPrefixed = twoFactorRateKeyPrefixed;
    }

    public void setGlobalRequestRateKeyPrefixed(String globalRequestRateKeyPrefixed) {
        this.globalRequestRateKeyPrefixed = globalRequestRateKeyPrefixed;
    }

    public void setRequestRateKeyPrefixed(String requestRateKeyPrefixed) {
        this.requestRateKeyPrefixed = requestRateKeyPrefixed;
    }

    public void setApiRateKeyPrefixed(String apiRateKeyPrefixed) {
        this.apiRateKeyPrefixed = apiRateKeyPrefixed;
    }

    public void setAuthRateKeyPrefixed(String authRateKeyPrefixed) {
        this.authRateKeyPrefixed = authRateKeyPrefixed;
    }

    public int getDeleteAfterAccessMinute() {
        return deleteAfterAccessMinute;
    }

    public void setGlobalApiRateKeyPrefixed(String globalApiRateKeyPrefixed) {
        this.globalApiRateKeyPrefixed = globalApiRateKeyPrefixed;
    }

    public void setGlobalAuthRateKeyPrefixed(String globalAuthRateKeyPrefixed) {
        this.globalAuthRateKeyPrefixed = globalAuthRateKeyPrefixed;
    }

    public String getApiRateKeyPrefixed() {
        return apiRateKeyPrefixed;
    }

    public String getAuthRateKeyPrefixed() {
        return authRateKeyPrefixed;
    }

    public String getGlobalAuthRateKeyPrefixed() {
        return globalAuthRateKeyPrefixed;
    }

    public String getGlobalApiRateKeyPrefixed() {
        return globalApiRateKeyPrefixed;
    }

    public String getGlobalTwoFactorRateKeyPrefixed() {
        return globalTwoFactorRateKeyPrefixed;
    }

    public String getTwoFactorRateKeyPrefixed() {
        return twoFactorRateKeyPrefixed;
    }

    public String getGlobalRequestRateKeyPrefixed() {
        return globalRequestRateKeyPrefixed;
    }

    public String getRequestRateKeyPrefixed() {
        return requestRateKeyPrefixed;
    }
}
