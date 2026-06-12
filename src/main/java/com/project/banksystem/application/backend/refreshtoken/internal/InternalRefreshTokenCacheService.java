package com.project.banksystem.application.backend.refreshtoken.internal;

import com.project.banksystem.application.backend.accesssession.internal.InternalAccessSessionCreateService;
import com.project.banksystem.application.backend.pendingrevokeaccesssession.internal.InternalPendingRevokedAccessSessionCreateService;
import com.project.banksystem.application.backend.refreshtoken.query.RefreshTokenFinder;
import com.project.banksystem.application.backend.refreshtoken.validator.RefreshTokenValidator;
import com.project.banksystem.application.backend.user.validator.UserValidator;
import com.project.banksystem.infrastructure.security.session.caching.redis.RedisAccessSessionCache;
import com.project.banksystem.infrastructure.security.session.jwt.JwtService;
import com.project.banksystem.infrastructure.security.session.properties.SessionConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class InternalRefreshTokenCacheService {
    private final InternalAccessSessionCreateService internalAccessSessionCreateService;
    private final InternalPendingRevokedAccessSessionCreateService internalPendingRevokedAccessSessionCreateService;
    private final RedisAccessSessionCache redisAccessSessionCache;


    public InternalRefreshTokenCacheService(InternalAccessSessionCreateService internalAccessSessionCreateService,
                                           InternalPendingRevokedAccessSessionCreateService internalPendingRevokedAccessSessionCreateService,
                                           RedisAccessSessionCache redisAccessSessionCache) {
        this.internalAccessSessionCreateService = internalAccessSessionCreateService;
        this.internalPendingRevokedAccessSessionCreateService = internalPendingRevokedAccessSessionCreateService;
        this.redisAccessSessionCache = redisAccessSessionCache;


    }


    public boolean safeClearOldCache(final UUID accessSessionId) {
        return redisAccessSessionCache.revokeSession(accessSessionId.toString());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void applyCache(final UUID accessSessionId,
                           final UUID accessSessionUserId,
                           final String deviceIp,
                           final Long version,
                           final boolean revoked) {
        boolean success = internalAccessSessionCreateService.applyCacheSession(
                accessSessionUserId,
                deviceIp,
                accessSessionId,
                version,
                revoked);

        if (!success) {
            internalPendingRevokedAccessSessionCreateService
                    .pendingSessions(Set.of(accessSessionId));
        }


    }
}
