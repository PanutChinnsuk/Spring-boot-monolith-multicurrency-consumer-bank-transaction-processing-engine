package com.project.banksystem.infrastructure.security.cache.healthcheck;

import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class RedisHealthService {
    private AtomicBoolean REDIS_DOWN = new AtomicBoolean(true);
    private final String PREFIXED = "healthCheck:";


    public String getKeyPrefixed() {
        return PREFIXED;
    }

    public boolean getStatus() {
        return REDIS_DOWN.get();
    }

    public void setRedisStatusDown() {
        REDIS_DOWN.compareAndSet(false, true);
    }

    public void setRedisStatusUp() {
        REDIS_DOWN.compareAndSet(true, false);
    }

    public void throwConnectionExceptionIfInternalRedisStatusIsDown() {
        if (REDIS_DOWN.get()) {
            throw new RedisConnectionFailureException("System redis status is down.");
        }
    }

}
