package com.project.banksystem.infrastructure.security.ratelimiting.memory;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.infrastructure.security.cache.healthcheck.RedisHealthService;
import com.project.banksystem.infrastructure.security.ratelimiting.RateLimitKeyValidator;
import com.project.banksystem.infrastructure.security.ratelimiting.config.properties.SharedRateLimitingConfig;
import io.github.bucket4j.Bucket;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TemporaryRateLimitingService {
    private final TemporaryBucket4jResolver temporaryBucket4jResolver;
    private final SharedRateLimitingConfig sharedRateLimitingConfig;


    public TemporaryRateLimitingService(TemporaryBucket4jResolver temporaryBucket4jResolver,
                                        SharedRateLimitingConfig sharedRateLimitingConfig) {
        this.temporaryBucket4jResolver = temporaryBucket4jResolver;
        this.sharedRateLimitingConfig = sharedRateLimitingConfig;
    }

    private boolean consume(@Nullable String customKey,
                            String globalKeyPrefixed,
                            String keyPrefixed,
                            Function<String, Bucket> bucketResolve,
                            Function<String, Bucket> globalBucketResolve) {
        String key = RateLimitKeyValidator.isKeyValid(customKey)
                ? customKey
                : CurrentUser.getCurrentUserId().toString();

        String perUserKey = keyPrefixed + key;
        String globalKey = globalKeyPrefixed;

        Bucket perUserBucket = bucketResolve.apply(perUserKey);
        Bucket globalBucket = globalBucketResolve.apply(globalKeyPrefixed);
        if (perUserBucket.tryConsume(1) && globalBucket.tryConsume(1)) {
            return false;
        }

        return true;
    }

    public boolean apiRateExceeded(@Nullable String customKey) {
        return consume(customKey,
                sharedRateLimitingConfig.getGlobalApiRateKeyPrefixed(),
                sharedRateLimitingConfig.getApiRateKeyPrefixed(),
                temporaryBucket4jResolver::resolveApiBucket,
                temporaryBucket4jResolver::resolveGlobalApiBucket);
    }

    public boolean authRateExceeded(String key) {
        return consume(key,
                sharedRateLimitingConfig.getGlobalAuthRateKeyPrefixed(),
                sharedRateLimitingConfig.getAuthRateKeyPrefixed(),
                temporaryBucket4jResolver::resolveAuthBucket,
                temporaryBucket4jResolver::resolveGlobalAuthBucket);
    }

    public boolean twoFactorRateExceeded(String key) {
        return consume(key,
                sharedRateLimitingConfig.getGlobalTwoFactorRateKeyPrefixed(),
                sharedRateLimitingConfig.getTwoFactorRateKeyPrefixed(),
                temporaryBucket4jResolver::resolveTwoFactorBucket,
                temporaryBucket4jResolver::resolveGlobalTwoFactorBucket);
    }

    public boolean requestRateExceeded(@Nullable String customKey) {
        return consume(customKey,
                sharedRateLimitingConfig.getGlobalRequestRateKeyPrefixed(),
                sharedRateLimitingConfig.getRequestRateKeyPrefixed(),
                temporaryBucket4jResolver::resolveRequestBucket,
                temporaryBucket4jResolver::resolveGlobalRequestBucket);
    }
}
