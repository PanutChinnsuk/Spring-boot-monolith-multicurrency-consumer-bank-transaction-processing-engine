package com.project.banksystem.infrastructure.security.cache.config.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.project.banksystem.infrastructure.security.ratelimiting.config.properties.SharedRateLimitingConfig;
import com.project.banksystem.infrastructure.security.session.properties.SessionConfig;
import io.github.bucket4j.Bucket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CaffeineCacheConfig {
    private final SharedRateLimitingConfig sharedRateLimitingConfig;
    private final SessionConfig sessionConfig;

    public CaffeineCacheConfig(SharedRateLimitingConfig sharedRateLimitingConfig,
                               SessionConfig sessionConfig) {
        this.sharedRateLimitingConfig = sharedRateLimitingConfig;
        this.sessionConfig = sessionConfig;
    }



    @Bean
    public Cache<String, Bucket> temporaryApiRateLimitCache() {
        return Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofMinutes(sharedRateLimitingConfig.getDeleteAfterAccessMinute()))
                .maximumSize(50000)
                .build();
    }

    @Bean
    public Cache<String, Bucket> temporaryGlobalApiRateLimitCache() {
        return Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofMinutes(sharedRateLimitingConfig.getDeleteAfterAccessMinute()))
                .maximumSize(1000)
                .build();
    }

    @Bean
    public Cache<String, Bucket> temporaryAuthenticationRateLimitCache() {
        return Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofMinutes(sharedRateLimitingConfig.getDeleteAfterAccessMinute()))
                .maximumSize(50000)
                .build();
    }

    @Bean
    public Cache<String, Bucket> temporaryGlobalAuthenticationRateLimitCache() {
        return Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofMinutes(sharedRateLimitingConfig.getDeleteAfterAccessMinute()))
                .maximumSize(1000)
                .build();
    }

    @Bean
    public Cache<String, Bucket> temporaryRequestRateLimitCache() {
        return Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofMinutes(sharedRateLimitingConfig.getDeleteAfterAccessMinute()))
                .maximumSize(50000)
                .build();
    }

    @Bean
    public Cache<String, Bucket> temporaryGlobalRequestRateLimitCache() {
        return Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofMinutes(sharedRateLimitingConfig.getDeleteAfterAccessMinute()))
                .maximumSize(1000)
                .build();
    }




    @Bean
    public Cache<String, String> inMemoryJwtAccessTokenBlacklistCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(sessionConfig.getBlacklistLifeTimeMinute()))
                .maximumSize(25000)
                .build();
    }


}
