package com.project.banksystem.infrastructure.security.key;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
@ConfigurationProperties(prefix = "app.system.internal")
@Slf4j
public class ApiKeyProvider {
    private static final Logger log = LoggerFactory.getLogger(ApiKeyProvider.class);
    private String apiKey;

    @PostConstruct
    private void log() {
        log.info("apiKey loaded " + (apiKey != null || apiKey.isBlank()));
    }

    public Key getApiKey() {
        return Keys.hmacShaKeyFor(apiKey.getBytes(StandardCharsets.UTF_8));
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
