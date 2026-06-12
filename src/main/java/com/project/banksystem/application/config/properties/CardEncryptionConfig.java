package com.project.banksystem.application.config.properties;

import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "app.system.card.encryption")
@Slf4j
public class CardEncryptionConfig {
    private static final Logger log = LoggerFactory.getLogger(CardEncryptionConfig.class);
    private int keyVersion;

    private Map<Integer, String> keys;

    public void setKeys(Map<Integer, String> keys) {
        this.keys = keys;
    }

    @PostConstruct
    private void log() {
        log.info("keyVersion " + keyVersion);

        keys.forEach((k, v) -> {
            log.info("Key " + k);
            log.info("Value " + v);
        });
    }

    public void setKeyVersion(int keyVersion) {
        this.keyVersion = keyVersion;
    }

    public int getKeyVersion() {
        return keyVersion;
    }

    public String getKeyByVersion(Integer keyVersion) {
        String thisKey = keys.get(keyVersion);

        if (thisKey == null) {
            throw new IllegalStateException("Key not found.");
        }

        return thisKey;
    }
}
