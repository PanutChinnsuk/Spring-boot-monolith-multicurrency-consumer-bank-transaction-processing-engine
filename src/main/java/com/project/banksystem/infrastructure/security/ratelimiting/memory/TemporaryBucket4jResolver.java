package com.project.banksystem.infrastructure.security.ratelimiting.memory;

import com.github.benmanes.caffeine.cache.Cache;
import com.project.banksystem.infrastructure.security.ratelimiting.config.Bucket4jConfig;
import com.project.banksystem.infrastructure.security.ratelimiting.config.properties.*;
import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TemporaryBucket4jResolver {
    private final Cache<String, Bucket> temporaryApiRateLimitCache;
    private final Cache<String, Bucket> temporaryGlobalApiRateLimitCache;
    private final Cache<String, Bucket> temporaryAuthenticationRateLimitCache;
    private final ApiRateLimitingConfig apiRateLimitingConfig;
    private final AuthenticationRateLimitingConfig authRateLimitingConfig;
    private final Cache<String, Bucket> temporaryGlobalAuthenticationRateLimitCache;
    private final Bucket4jConfig bucket4jConfig;
    private final TwoFactorRateLimitingConfig twoFactorRateLimitingConfig;
    private final RequestRateLimitingConfig requestRateLimitingConfig;
    private final Cache<String, Bucket> temporaryRequestRateLimitCache;
    private final Cache<String, Bucket> temporaryGlobalRequestRateLimitCache;

    public TemporaryBucket4jResolver(@Qualifier("temporaryApiRateLimitCache")
                                     Cache<String, Bucket> temporaryApiRateLimitCache,

                                     @Qualifier("temporaryGlobalApiRateLimitCache")
                                     Cache<String, Bucket> temporaryGlobalApiRateLimitCache,

                                     @Qualifier("temporaryAuthenticationRateLimitCache")
                                     Cache<String, Bucket> temporaryAuthenticationRateLimitCache,

                                     ApiRateLimitingConfig apiRateLimitingConfig,

                                     AuthenticationRateLimitingConfig authRateLimitingConfig,

                                     @Qualifier("temporaryGlobalAuthenticationRateLimitCache")
                                     Cache<String, Bucket> temporaryGlobalAuthenticationRateLimitCache,

                                     Bucket4jConfig bucket4jConfig,

                                     TwoFactorRateLimitingConfig twoFactorRateLimitingConfig,

                                     RequestRateLimitingConfig requestRateLimitingConfig,

                                     @Qualifier("temporaryRequestRateLimitCache")
                                     Cache<String, Bucket> temporaryRequestRateLimitCache,

                                     @Qualifier("temporaryGlobalRequestRateLimitCache")
                                     Cache<String, Bucket> temporaryGlobalRequestRateLimitCache) {
        this.temporaryApiRateLimitCache = temporaryApiRateLimitCache;
        this.temporaryGlobalApiRateLimitCache = temporaryGlobalApiRateLimitCache;
        this.temporaryAuthenticationRateLimitCache = temporaryAuthenticationRateLimitCache;
        this.apiRateLimitingConfig = apiRateLimitingConfig;
        this.authRateLimitingConfig = authRateLimitingConfig;
        this.temporaryGlobalAuthenticationRateLimitCache = temporaryGlobalAuthenticationRateLimitCache;
        this.bucket4jConfig = bucket4jConfig;
        this.twoFactorRateLimitingConfig = twoFactorRateLimitingConfig;
        this.requestRateLimitingConfig = requestRateLimitingConfig;
        this.temporaryRequestRateLimitCache = temporaryRequestRateLimitCache;
        this.temporaryGlobalRequestRateLimitCache = temporaryGlobalRequestRateLimitCache;
    }



    private Bucket apiBucket() {
        return Bucket.builder().addLimit(bucket4jConfig.bandwidth(apiRateLimitingConfig))
                .build();
    }

    private Bucket globalApiBucket() {
        return Bucket.builder().addLimit(bucket4jConfig.globalBandwidth(apiRateLimitingConfig))
                .build();
    }


    private Bucket authBucket() {
        return Bucket.builder().addLimit(bucket4jConfig.bandwidth(authRateLimitingConfig))
                .build();
    }

    private Bucket globalAuthBucket() {
        return Bucket.builder().addLimit(bucket4jConfig.globalBandwidth(authRateLimitingConfig))
                .build();
    }

    private Bucket twoFactorBucket() {
        return Bucket.builder().addLimit(bucket4jConfig.bandwidth(twoFactorRateLimitingConfig))
                .build();
    }

    private Bucket globalTwoFactorBucket() {
        return Bucket.builder().addLimit(bucket4jConfig.globalBandwidth(twoFactorRateLimitingConfig))
                .build();
    }

    private Bucket requestBucket() {
        return Bucket.builder().addLimit(bucket4jConfig.bandwidth(requestRateLimitingConfig))
                .build();
    }

    private Bucket globalRequestBucket() {
        return Bucket.builder().addLimit(bucket4jConfig.globalBandwidth(requestRateLimitingConfig))
                .build();
    }




    public Bucket resolveApiBucket(final String key) {
        return temporaryApiRateLimitCache.get(key, k -> apiBucket());
    }

    public Bucket resolveGlobalApiBucket(final String key) {
        return temporaryGlobalApiRateLimitCache.get(key, k -> apiBucket());
    }

    public Bucket resolveAuthBucket(final String key) {
        return temporaryAuthenticationRateLimitCache.get(key, k -> authBucket());
    }

    public Bucket resolveGlobalAuthBucket(final String key) {
        return temporaryGlobalAuthenticationRateLimitCache.get(key, k -> globalAuthBucket());
    }

    public Bucket resolveTwoFactorBucket(final String key) {
        return temporaryAuthenticationRateLimitCache.get(key, k -> twoFactorBucket());
    }

    public Bucket resolveGlobalTwoFactorBucket(final String key) {
        return temporaryGlobalAuthenticationRateLimitCache.get(key, k -> globalTwoFactorBucket());
    }

    public Bucket resolveRequestBucket(final String key) {
        return temporaryAuthenticationRateLimitCache.get(key, k -> requestBucket());
    }

    public Bucket resolveGlobalRequestBucket(final String key) {
        return temporaryGlobalAuthenticationRateLimitCache.get(key, k -> globalRequestBucket());
    }
}
