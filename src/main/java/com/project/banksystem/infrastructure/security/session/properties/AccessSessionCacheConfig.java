package com.project.banksystem.infrastructure.security.session.properties;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.system.caching.accesssession")
@Slf4j
public class AccessSessionCacheConfig {
    private static final Logger log = LoggerFactory.getLogger(AccessSessionCacheConfig.class);
    private String keyPrefixed;
    private int pendingDeleteExpiresMinute;
    private int BulkRevokeElementLimit;

    @PostConstruct
    private void log() {
        log.info("keyPrefixed " + keyPrefixed);
        log.info("pendingDeleteExpiresMinute " + pendingDeleteExpiresMinute);
        log.info("BulkRevokeElementLimit " + BulkRevokeElementLimit);
    }

    public void setKeyPrefixed(String keyPrefixed) {
        this.keyPrefixed = keyPrefixed;
    }

    public void setPendingDeleteExpiresMinute(int pendingDeleteExpiresMinute) {
        this.pendingDeleteExpiresMinute = pendingDeleteExpiresMinute;
    }

    public void setBulkRevokeElementLimit(int bulkRevokeElementLimit) {
        BulkRevokeElementLimit = bulkRevokeElementLimit;
    }

    public String getKeyPrefixed() {
        return keyPrefixed;
    }

    public int getPendingDeleteExpiresMinute() {
        return pendingDeleteExpiresMinute;
    }

    public int getBulkRevokeElementLimit() {
        return BulkRevokeElementLimit;
    }
}
