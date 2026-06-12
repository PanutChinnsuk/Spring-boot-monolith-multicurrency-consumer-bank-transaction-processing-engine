package com.project.banksystem.application.backend.accesssession.impl;

import com.project.banksystem.application.backend.accesssession.service.AccessSessionTerminationService;
import com.project.banksystem.application.backend.pendingrevokeaccesssession.internal.InternalPendingRevokedAccessSessionCreateService;
import com.project.banksystem.infrastructure.repository.AccessSessionRepository;
import com.project.banksystem.infrastructure.repository.RefreshTokenRepository;
import com.project.banksystem.infrastructure.security.session.caching.redis.RedisAccessSessionCache;
import com.project.banksystem.infrastructure.security.session.properties.AccessSessionCacheConfig;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccessSessionTerminationServiceImpl implements AccessSessionTerminationService {
    private final AccessSessionRepository accessSessionRepository;
    private final RedisAccessSessionCache redisAccessSessionCache;
    private final InternalPendingRevokedAccessSessionCreateService internalPendingRevokedAccessSessionCreateService;
    private final AccessSessionCacheConfig accessSessionCacheConfig;
    private final RefreshTokenRepository refreshTokenRepository;

    public AccessSessionTerminationServiceImpl(AccessSessionRepository accessSessionRepository,
                                               RedisAccessSessionCache redisAccessSessionCache,
                                               InternalPendingRevokedAccessSessionCreateService internalPendingRevokedAccessSessionCreateService,
                                               AccessSessionCacheConfig accessSessionCacheConfig,
                                               RefreshTokenRepository refreshTokenRepository) {
        this.accessSessionRepository = accessSessionRepository;
        this.redisAccessSessionCache = redisAccessSessionCache;
        this.internalPendingRevokedAccessSessionCreateService = internalPendingRevokedAccessSessionCreateService;
        this.accessSessionCacheConfig = accessSessionCacheConfig;
        this.refreshTokenRepository = refreshTokenRepository;
    }



    @Override
    public void revokeAllAccessSessionByUserId(final UUID userId) {
        LocalDateTime now = LocalDateTime.now();

        Set<UUID> ids = accessSessionRepository.findAllAccessSessionByUserId(
                now,
                userId,
                accessSessionCacheConfig.getBulkRevokeElementLimit()
        ).stream().map(acs -> acs.getAccessSessionId())
                .collect(Collectors.toSet());

        Set<UUID> refreshIds = refreshTokenRepository
                .lockAllRefreshTokenOfAccessSessions(ids,
                        now).stream().collect(Collectors.toSet());

        boolean successClearCache = redisAccessSessionCache.revokeSessions(ids.stream().map(UUID::toString)
                .collect(Collectors.toSet()));
        if (!successClearCache) {
            internalPendingRevokedAccessSessionCreateService.pendingSessions(ids);
        }

        accessSessionRepository.revokeAllAccessSessionByIds(ids);
        refreshTokenRepository.revokedAllRefreshTokens(refreshIds, now);
    }

    @Override
    public void revokeAccessSessionByAccessSessionId(final UUID accessSessionId) {
        LocalDateTime now = LocalDateTime.now();
        Set<UUID> refreshIds = refreshTokenRepository
                .lockAllRefreshTokenOfAccessSessions(Set.of(accessSessionId),
                now).stream().collect(Collectors.toSet());

        boolean successClearCache = redisAccessSessionCache.revokeSession(accessSessionId.toString());
        if (!successClearCache) {
            internalPendingRevokedAccessSessionCreateService.pendingSessions(
                    Set.of(accessSessionId)
            );
        }

        accessSessionRepository.revokeAccessSessionById(accessSessionId);
        refreshTokenRepository.revokedAllRefreshTokens(refreshIds, now);
    }
}
