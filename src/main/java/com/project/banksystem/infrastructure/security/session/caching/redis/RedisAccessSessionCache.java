package com.project.banksystem.infrastructure.security.session.caching.redis;

import com.project.banksystem.infrastructure.security.cache.healthcheck.RedisHealthService;
import com.project.banksystem.infrastructure.security.session.caching.body.AccessSessionCacheBody;
import com.project.banksystem.infrastructure.security.session.properties.AccessSessionCacheConfig;
import com.project.banksystem.infrastructure.security.session.properties.SessionConfig;
import io.lettuce.core.RedisConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RedisAccessSessionCache {
    private static final Logger log = LoggerFactory.getLogger(RedisAccessSessionCache.class);
    private final RedisTemplate<String, AccessSessionCacheBody> redisSessionCache;
    private final SessionConfig sessionConfig;
    private final AccessSessionCacheConfig accessSessionCacheConfig;
    private final RedisHealthService redisHealthService;

    public RedisAccessSessionCache(@Qualifier(value = "redisSessionCache")
                             RedisTemplate<String, AccessSessionCacheBody> redisSessionCache,
                                   SessionConfig sessionConfig,
                                   AccessSessionCacheConfig accessSessionCacheConfig,
                                   RedisHealthService redisHealthService) {
        this.redisSessionCache = redisSessionCache;
        this.sessionConfig = sessionConfig;
        this.accessSessionCacheConfig = accessSessionCacheConfig;
        this.redisHealthService = redisHealthService;
    }

    private void errLog(String msg, Exception e) {
        log.error(msg + e.getMessage());
    }

    private String toKey(final String accessSessionId) {
        return accessSessionCacheConfig.getKeyPrefixed() + accessSessionId;
    }

    private void setKey(final String accessSessionId,
                        final AccessSessionCacheBody accessSessionCacheBody) {
        redisSessionCache.opsForValue().set(toKey(accessSessionId), accessSessionCacheBody,
                Duration.ofMinutes(sessionConfig.getAccessLifeTimeMinute()));
    }

    public boolean cacheSession(final String accessSessionId, final AccessSessionCacheBody accessSessionCacheBody) {
        try {
            redisHealthService.throwConnectionExceptionIfInternalRedisStatusIsDown();
            setKey(accessSessionId, accessSessionCacheBody);
            log.info("Successfully caching access session to redis.");
            return true;
        }catch (Exception e) {
            errLog("Failed to cache access session in redis: ", e);
            return false;
        }

    }

    public AccessSessionCacheBody getCache(final String accessSessionId) {
        try {
            redisHealthService.throwConnectionExceptionIfInternalRedisStatusIsDown();
            AccessSessionCacheBody get = redisSessionCache.opsForValue().get(toKey(accessSessionId));
            log.info("Successfully get access session from redis.");
            return get;
        }catch (Exception e) {
            errLog("Failed to get redis access session cache: ", e);
            return null;
        }
    }

    public boolean revokeSession(final String accessSessionId) {
        try {
            redisSessionCache.delete(toKey(accessSessionId));

            log.info("Successfully clear access session from redis.");
            return true;
        }catch (Exception e) {
            errLog("Failed to clear access session cache: ", e);

            return false;
        }
    }

    public boolean revokeSessions(Set<String> keys) {
        try {
            Set<String> asCacheKeys = keys.stream().map(s -> toKey(s)).collect(Collectors.toSet());

            redisSessionCache.delete(asCacheKeys);

            log.info("Successfully revoke access sessions from redis.");
            return true;
        }catch (Exception e) {
            errLog("Failed to clear all access session caches: ", e);

            return false;
        }
    }

}
