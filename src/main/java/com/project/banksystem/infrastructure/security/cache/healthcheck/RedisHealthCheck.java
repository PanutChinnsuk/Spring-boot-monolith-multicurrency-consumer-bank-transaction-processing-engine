package com.project.banksystem.infrastructure.security.cache.healthcheck;

import com.project.banksystem.application.backend.pendingrevokeaccesssession.internal.InternalPendingRevokeAccessSessionService;
import io.lettuce.core.RedisCommandTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisHealthCheck {
    private static final Logger log = LoggerFactory.getLogger(RedisHealthCheck.class);
    private final RedisTemplate<String, String> redisHealthCache;
    private final InternalPendingRevokeAccessSessionService internalPendingRevokeAccessSessionService;
    private final RedisHealthService redisHealthService;

    public RedisHealthCheck(@Qualifier("redisHealthCache") RedisTemplate<String, String> redisHealthCache,
                            InternalPendingRevokeAccessSessionService internalPendingRevokeAccessSessionService,
                            RedisHealthService redisHealthService) {
        this.redisHealthCache = redisHealthCache;
        this.internalPendingRevokeAccessSessionService = internalPendingRevokeAccessSessionService;
        this.redisHealthService = redisHealthService;
    }



    @Scheduled(fixedDelay = 2 * 60 * 1000)
    protected void healthCheck() {
        boolean REDIS_DOWN = redisHealthService.getStatus();
        try {
            redisHealthCache.opsForValue().set(redisHealthService.getKeyPrefixed(), "check");
            boolean successCleanUpPendings = internalPendingRevokeAccessSessionService
                    .successfullyApply();
            if (successCleanUpPendings && REDIS_DOWN) {
                log.info("Successfully revoked all pendings.");
                redisHealthService.setRedisStatusUp();
            }
            log.info("Redis status is up from health check.");
        }catch (RedisConnectionFailureException | RedisCommandTimeoutException e) {
            redisHealthService.setRedisStatusDown();
            log.error("Redis status is down from health check.");
        }
    }


}
