package com.project.banksystem.application.backend.accesssession.internal;

import com.project.banksystem.application.backend.accesssession.AccessSessionManager;
import com.project.banksystem.application.backend.accesssession.body.AccessSessionCreatedResult;
import com.project.banksystem.application.backend.refreshtoken.internal.InternalRefreshTokenCreateService;
import com.project.banksystem.application.spring.SpringTransactionSynchronization;
import com.project.banksystem.infrastructure.model.AccessSession;
import com.project.banksystem.infrastructure.model.RefreshToken;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.security.session.caching.body.AccessSessionCacheBody;
import com.project.banksystem.infrastructure.security.session.caching.redis.RedisAccessSessionCache;
import com.project.banksystem.infrastructure.security.session.jwt.JwtService;
import com.project.banksystem.infrastructure.security.session.jwt.builder.JwtTokenBuilder;
import com.project.banksystem.infrastructure.security.session.properties.SessionConfig;
import com.project.banksystem.infrastructure.security.session.properties.body.SessionExpiry;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class InternalAccessSessionCreateService {
    private final AccessSessionManager accessSessionManager;
    private final SessionConfig sessionConfig;
    private final JwtService jwtService;
    private final JwtTokenBuilder jwtTokenBuilder;
    private final RedisAccessSessionCache redisAccessSessionCache;
    private final InternalRefreshTokenCreateService internalRefreshTokenCreateService;

    public InternalAccessSessionCreateService(AccessSessionManager accessSessionManager,
                                              SessionConfig sessionConfig,
                                              JwtService jwtService,
                                              JwtTokenBuilder jwtTokenBuilder,
                                              RedisAccessSessionCache redisAccessSessionCache,
                                              InternalRefreshTokenCreateService internalRefreshTokenCreateService) {
        this.accessSessionManager = accessSessionManager;
        this.sessionConfig = sessionConfig;
        this.jwtService = jwtService;
        this.jwtTokenBuilder = jwtTokenBuilder;
        this.redisAccessSessionCache = redisAccessSessionCache;
        this.internalRefreshTokenCreateService = internalRefreshTokenCreateService;
    }


    public boolean applyCacheSession(final UUID userId,
                                     final String deviceIp,
                                     final UUID accessSessionId,
                                     final Long version,
                                     final boolean revoked) {

        String asString = accessSessionId.toString();
        AccessSessionCacheBody accessSessionCacheBody = AccessSessionCacheBody.builder()
                .accessSessionId(accessSessionId)
                .userId(userId)
                .deviceIp(deviceIp)
                .revoked(revoked)
                .version(version)
                .build();

        return redisAccessSessionCache.cacheSession(asString, accessSessionCacheBody);
    }

    public AccessSessionCreatedResult createAccessSession(final User user,
                                                          final String deviceIp) {
        SessionExpiry sessionExpiry = sessionConfig.getAccessSessionExpiry();
        Instant expiry = sessionExpiry.asInstant();
        LocalDateTime expiryAsLocalDateTime = sessionExpiry.asLocalDateTime();

        AccessSession accessSession = accessSessionManager.save(AccessSession.builder()
                .user(user)
                .deviceIp(deviceIp)
                .expiresAt(expiryAsLocalDateTime)
                .build());

        RefreshToken refreshToken = internalRefreshTokenCreateService.createRefreshToken(user,
                accessSession,
                expiryAsLocalDateTime,
                deviceIp);

        Long version = accessSession.getVersion();
        SpringTransactionSynchronization.runAfterCommit(
                () -> applyCacheSession(user.getUserId(),
                        deviceIp,
                        accessSession.getAccessSessionId(),
                        version,
                        accessSession.getRevoked())
        );

        String accessJwtToken = jwtService.createAccessSessionJwtToken(user,
                accessSession, version, expiry);

        String refreshJwtToken = jwtTokenBuilder.generateRefreshToken(user,
                refreshToken.getTokenId(), expiry);


        return new AccessSessionCreatedResult(accessSession, accessJwtToken, refreshJwtToken);
    }
}
