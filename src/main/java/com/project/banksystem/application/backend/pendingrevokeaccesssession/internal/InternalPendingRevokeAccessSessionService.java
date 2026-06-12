package com.project.banksystem.application.backend.pendingrevokeaccesssession.internal;

import com.project.banksystem.infrastructure.repository.PendingRevokeAccessSessionRepository;
import com.project.banksystem.infrastructure.security.session.caching.redis.RedisAccessSessionCache;
import com.project.banksystem.infrastructure.security.session.properties.AccessSessionCacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class InternalPendingRevokeAccessSessionService {
    private final PendingRevokeAccessSessionRepository pendingRevokeAccessSessionRepository;
    private final AccessSessionCacheConfig accessSessionCacheConfig;
    private final RedisAccessSessionCache redisAccessSessionCache;

    public InternalPendingRevokeAccessSessionService(PendingRevokeAccessSessionRepository pendingRevokeAccessSessionRepository,
                                                     AccessSessionCacheConfig accessSessionCacheConfig,
                                                     RedisAccessSessionCache redisAccessSessionCache) {
        this.pendingRevokeAccessSessionRepository = pendingRevokeAccessSessionRepository;
        this.accessSessionCacheConfig = accessSessionCacheConfig;
        this.redisAccessSessionCache = redisAccessSessionCache;
    }

    @Transactional
    public Boolean successResult() {
        Set<String> keys = new HashSet<>(pendingRevokeAccessSessionRepository.getPendingsForBatchRevoke(
                LocalDateTime.now(),
                accessSessionCacheConfig.getBulkRevokeElementLimit()
        ));

        if (keys.isEmpty()) {
            return true;
        }

        boolean success = redisAccessSessionCache.revokeSessions(keys);
        if (!success) {
            return false;
        }

        pendingRevokeAccessSessionRepository.completePending(keys);
        return null;
    }

    public boolean successfullyApply() {
        Boolean result = null;
        while (result == null) {
           result = successResult();

           if (result != null) {
               return result;
           }
        }

        return false;
    }


}
