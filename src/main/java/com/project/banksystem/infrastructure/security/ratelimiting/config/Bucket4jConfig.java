package com.project.banksystem.infrastructure.security.ratelimiting.config;

import com.project.banksystem.infrastructure.security.ratelimiting.config.properties.*;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Bucket4jConfig {
    private final ApiRateLimitingConfig apiRateLimitingConfig;
    private final AuthenticationRateLimitingConfig authenticationRateLimitingConfig;
    private final TwoFactorRateLimitingConfig twoFactorRateLimitingConfig;
    private final RequestRateLimitingConfig requestRateLimitingConfig;

    public Bucket4jConfig(ApiRateLimitingConfig apiRateLimitingConfig,
                          AuthenticationRateLimitingConfig authenticationRateLimitingConfig,
                          TwoFactorRateLimitingConfig twoFactorRateLimitingConfig,
                          RequestRateLimitingConfig requestRateLimitingConfig) {
        this.apiRateLimitingConfig = apiRateLimitingConfig;
        this.authenticationRateLimitingConfig = authenticationRateLimitingConfig;
        this.twoFactorRateLimitingConfig = twoFactorRateLimitingConfig;
        this.requestRateLimitingConfig = requestRateLimitingConfig;
    }

    public Bandwidth bandwidth(RateLimitConfig rateLimitConfig) {
        return Bandwidth.builder()
                .capacity(rateLimitConfig.getMaxCapacity())
                .refillIntervally(rateLimitConfig.getRefillAmount(),
                        Duration.ofMinutes(rateLimitConfig.getMinutePerRefill()))
                .build();
    }

    public Bandwidth globalBandwidth(RateLimitConfig rateLimitConfig) {
        return Bandwidth.builder()
                .capacity(rateLimitConfig.getGlobalMaxCapacity())
                .refillIntervally(rateLimitConfig.getGlobalRefillAmount(),
                        Duration.ofMinutes(rateLimitConfig.getGlobalMinutePerRefill()))
                .build();
    }




    @Bean
    public BucketConfiguration distributedApiBucketConfig() {
        return BucketConfiguration.builder().addLimit(bandwidth(apiRateLimitingConfig))
                .build();
    }

    @Bean
    public BucketConfiguration distributedGlobalApiBucketConfig() {
        return BucketConfiguration.builder().addLimit(globalBandwidth(apiRateLimitingConfig))
                .build();
    }

    @Bean
    public BucketConfiguration distributedAuthBucketConfig() {
        return BucketConfiguration.builder().addLimit(bandwidth(authenticationRateLimitingConfig))
                .build();
    }

    @Bean
    public BucketConfiguration distributedGlobalAuthBucketConfig() {
        return BucketConfiguration.builder().addLimit(globalBandwidth(authenticationRateLimitingConfig))
                .build();
    }

    @Bean
    public BucketConfiguration distributedTwoFactorBucketConfig() {
        return BucketConfiguration.builder().addLimit(bandwidth(twoFactorRateLimitingConfig))
                .build();
    }

    @Bean
    public BucketConfiguration distributedGlobalTwoFactorBucketConfig() {
        return BucketConfiguration.builder().addLimit(globalBandwidth(twoFactorRateLimitingConfig))
                .build();
    }

    @Bean
    public BucketConfiguration distributedRequestBucketConfig() {
        return BucketConfiguration.builder().addLimit(bandwidth(requestRateLimitingConfig))
                .build();
    }

    @Bean
    public BucketConfiguration distributedGlobalRequestBucketConfig() {
        return BucketConfiguration.builder().addLimit(globalBandwidth(requestRateLimitingConfig))
                .build();
    }



}
