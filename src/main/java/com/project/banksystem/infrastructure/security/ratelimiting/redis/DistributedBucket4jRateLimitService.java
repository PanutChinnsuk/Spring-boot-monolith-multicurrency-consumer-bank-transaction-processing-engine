package com.project.banksystem.infrastructure.security.ratelimiting.redis;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.infrastructure.security.ratelimiting.RateLimitKeyValidator;
import com.project.banksystem.infrastructure.security.ratelimiting.config.properties.SharedRateLimitingConfig;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class DistributedBucket4jRateLimitService {
    private final BucketConfiguration distributedApiBucketConfig;
    private final BucketConfiguration distributedGlobalApiBucketConfig;
    private final ProxyManager<String> redisDistributedProxyManager;
    private final BucketConfiguration distributedAuthBucketConfig;
    private final SharedRateLimitingConfig sharedRateLimitingConfig;
    private final BucketConfiguration distributedGlobalAuthBucketConfig;
    private final BucketConfiguration distributedTwoFactorBucketConfig;
    private final BucketConfiguration distributedGlobalTwoFactorBucketConfig;
    private final BucketConfiguration distributedRequestBucketConfig;
    private final BucketConfiguration distributedGlobalRequestBucketConfig;

    public DistributedBucket4jRateLimitService(@Qualifier("distributedApiBucketConfig")
                                               BucketConfiguration distributedApiBucketConfig,

                                               @Qualifier("distributedGlobalApiBucketConfig")
                                               BucketConfiguration distributedGlobalApiBucketConfig,

                                               @Lazy
                                               @Qualifier("redisDistributedProxyManager")
                                               ProxyManager<String> redisDistributedProxyManager,

                                               @Qualifier("distributedAuthBucketConfig")
                                               BucketConfiguration distributedAuthBucketConfig,

                                               SharedRateLimitingConfig sharedRateLimitingConfig,

                                               @Qualifier("distributedGlobalAuthBucketConfig")
                                               BucketConfiguration distributedGlobalAuthBucketConfig,

                                               @Qualifier("distributedTwoFactorBucketConfig")
                                               BucketConfiguration distributedTwoFactorBucketConfig,

                                               @Qualifier("distributedGlobalTwoFactorBucketConfig")
                                               BucketConfiguration distributedGlobalTwoFactorBucketConfig,

                                               @Qualifier("distributedRequestBucketConfig")
                                               BucketConfiguration distributedRequestBucketConfig,

                                               @Qualifier("distributedGlobalRequestBucketConfig")
                                               BucketConfiguration distributedGlobalRequestBucketConfig) {
        this.distributedApiBucketConfig = distributedApiBucketConfig;
        this.distributedGlobalApiBucketConfig = distributedGlobalApiBucketConfig;
        this.redisDistributedProxyManager = redisDistributedProxyManager;
        this.distributedAuthBucketConfig = distributedAuthBucketConfig;
        this.sharedRateLimitingConfig = sharedRateLimitingConfig;
        this.distributedGlobalAuthBucketConfig = distributedGlobalAuthBucketConfig;
        this.distributedTwoFactorBucketConfig = distributedTwoFactorBucketConfig;
        this.distributedGlobalTwoFactorBucketConfig = distributedGlobalTwoFactorBucketConfig;
        this.distributedRequestBucketConfig = distributedRequestBucketConfig;
        this.distributedGlobalRequestBucketConfig = distributedGlobalRequestBucketConfig;
    }


    private boolean consume(@Nullable String customKey,
                            String globalKeyPrefixed,
                            String keyPrefixed,
                            BucketConfiguration globalBucketConfiguration,
                            BucketConfiguration bucketConfiguration) throws Exception {
        String key = RateLimitKeyValidator.isKeyValid(customKey)
                ? customKey
                : CurrentUser.getCurrentUserId().toString();

        String globalKey = globalKeyPrefixed;
        String perRequestKey = keyPrefixed + key;

        Bucket globalBucket = redisDistributedProxyManager.builder()
                .build(globalKey, globalBucketConfiguration);

        Bucket perIpBucket = redisDistributedProxyManager.builder()
                .build(perRequestKey, bucketConfiguration);

        if (globalBucket.tryConsume(1) && perIpBucket.tryConsume(1)) {
            return false;
        }


        return true;
    }

    public boolean apiRateExceeded(@Nullable String customKey) throws Exception {
        return consume(customKey, sharedRateLimitingConfig.getGlobalApiRateKeyPrefixed(),
                sharedRateLimitingConfig.getApiRateKeyPrefixed(),
                distributedGlobalApiBucketConfig,
                distributedApiBucketConfig);
    }

    public boolean authRateExceeded(String key) throws Exception {
        return consume(key, sharedRateLimitingConfig.getGlobalAuthRateKeyPrefixed(),
                sharedRateLimitingConfig.getAuthRateKeyPrefixed(),
                distributedGlobalAuthBucketConfig,
                distributedAuthBucketConfig);
    }

    public boolean twoFactorRateExceeded(String key) throws Exception {
        return consume(key, sharedRateLimitingConfig.getGlobalTwoFactorRateKeyPrefixed(),
                sharedRateLimitingConfig.getTwoFactorRateKeyPrefixed(),
                distributedGlobalTwoFactorBucketConfig,
                distributedTwoFactorBucketConfig);
    }

    public boolean requestRateExceeded(@Nullable String customKey) throws Exception {
        return consume(customKey, sharedRateLimitingConfig.getGlobalRequestRateKeyPrefixed(),
                sharedRateLimitingConfig.getRequestRateKeyPrefixed(),
                distributedGlobalRequestBucketConfig,
                distributedRequestBucketConfig);
    }
}
