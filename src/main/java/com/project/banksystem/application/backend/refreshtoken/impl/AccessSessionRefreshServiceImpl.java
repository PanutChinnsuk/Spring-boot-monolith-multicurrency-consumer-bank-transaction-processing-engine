package com.project.banksystem.application.backend.refreshtoken.impl;

import com.project.banksystem.application.backend.accesssession.internal.InternalAccessSessionCreateService;
import com.project.banksystem.application.backend.pendingrevokeaccesssession.internal.InternalPendingRevokedAccessSessionCreateService;
import com.project.banksystem.application.backend.refreshtoken.body.RefreshResponseBody;
import com.project.banksystem.application.backend.refreshtoken.internal.InternalRefreshTokenCacheService;
import com.project.banksystem.application.backend.refreshtoken.internal.InternalRefreshTokenCreateService;
import com.project.banksystem.application.backend.refreshtoken.query.RefreshTokenFinder;
import com.project.banksystem.application.backend.refreshtoken.service.AccessSessionRefreshService;
import com.project.banksystem.application.backend.refreshtoken.validator.RefreshTokenValidator;
import com.project.banksystem.application.backend.user.query.UserFinder;
import com.project.banksystem.application.backend.user.validator.UserValidator;
import com.project.banksystem.application.spring.SpringTransactionSynchronization;
import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.model.AccessSession;
import com.project.banksystem.infrastructure.model.RefreshToken;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.security.session.caching.redis.RedisAccessSessionCache;
import com.project.banksystem.infrastructure.security.session.jwt.JwtService;
import com.project.banksystem.infrastructure.security.session.jwt.builder.JwtTokenBuilder;
import com.project.banksystem.infrastructure.security.session.properties.SessionConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class AccessSessionRefreshServiceImpl implements AccessSessionRefreshService {
    private final InternalAccessSessionCreateService internalAccessSessionCreateService;
    private final RefreshTokenFinder refreshTokenFinder;
    private final RefreshTokenValidator refreshTokenValidator;
    private final JwtService jwtService;
    private final JwtTokenBuilder jwtTokenBuilder;
    private final InternalPendingRevokedAccessSessionCreateService internalPendingRevokedAccessSessionCreateService;
    private final RedisAccessSessionCache redisAccessSessionCache;
    private final SessionConfig sessionConfig;
    private final InternalRefreshTokenCreateService internalRefreshTokenCreateService;
    private final UserValidator userValidator;
    private final InternalRefreshTokenCacheService internalRefreshTokenCacheService;

    public AccessSessionRefreshServiceImpl(InternalAccessSessionCreateService internalAccessSessionCreateService,
                                           RefreshTokenFinder refreshTokenFinder,
                                           RefreshTokenValidator refreshTokenValidator,
                                           JwtService jwtService,
                                           JwtTokenBuilder jwtTokenBuilder,
                                           InternalPendingRevokedAccessSessionCreateService internalPendingRevokedAccessSessionCreateService,
                                           RedisAccessSessionCache redisAccessSessionCache,
                                           SessionConfig sessionConfig,
                                           InternalRefreshTokenCreateService internalRefreshTokenCreateService,
                                           UserValidator userValidator,
                                           InternalRefreshTokenCacheService internalRefreshTokenCacheService) {
        this.internalAccessSessionCreateService = internalAccessSessionCreateService;
        this.refreshTokenFinder = refreshTokenFinder;
        this.refreshTokenValidator = refreshTokenValidator;
        this.jwtService = jwtService;
        this.jwtTokenBuilder = jwtTokenBuilder;
        this.internalPendingRevokedAccessSessionCreateService = internalPendingRevokedAccessSessionCreateService;
        this.redisAccessSessionCache = redisAccessSessionCache;
        this.sessionConfig = sessionConfig;
        this.internalRefreshTokenCreateService = internalRefreshTokenCreateService;
        this.userValidator = userValidator;
        this.internalRefreshTokenCacheService = internalRefreshTokenCacheService;

    }

    @Override
    public RefreshResponseBody refresh(final UUID refreshTokenId,
                                       final String deviceIp,
                                       final UUID userIdFromJwtToken) {
        RefreshToken refreshToken = refreshTokenFinder.findById(refreshTokenId);
        AccessSession accessSession = refreshToken.getAccessSession();
        User accessSessionUser = accessSession.getUser();


        userValidator.validateOperational(accessSessionUser);
        refreshTokenValidator.validateRefreshToken(refreshToken, deviceIp,
                userIdFromJwtToken, accessSession, accessSessionUser);



        UUID accessSessionId = accessSession.getAccessSessionId();

        Instant extendedExpiry = Instant.now().plus(sessionConfig.getAccessLifeTimeMinute(),
                ChronoUnit.MINUTES);
        LocalDateTime extendedExpiryAsLocalDateTime = LocalDateTime.ofInstant(extendedExpiry,
                ZoneId.systemDefault());

        RefreshToken newRefreshToken = internalRefreshTokenCreateService.createRefreshToken(
                accessSessionUser, accessSession, extendedExpiryAsLocalDateTime, deviceIp
        );

        accessSession.extendExpiresAT(extendedExpiryAsLocalDateTime);
        refreshToken.revokeRefreshToken();

        Long version = accessSession.getVersion() + 1;
        SpringTransactionSynchronization.runAfterCommit(
                () -> internalRefreshTokenCacheService
                        .applyCache(accessSessionId, accessSessionUser.getUserId(), deviceIp,
                        version, accessSession.getRevoked())
        );

        String newAccessSessionJwtToken = jwtService.createAccessSessionJwtToken(accessSessionUser,
                accessSession, version, extendedExpiry);

        String newRefreshJwtToken = jwtTokenBuilder.generateRefreshToken(accessSessionUser,
                newRefreshToken.getTokenId(), extendedExpiry);


        Map<String, String> values = Map.of(
                "NewAccessSessionToken", newAccessSessionJwtToken,
                "NewRefreshToken", newRefreshJwtToken
        );



        return new RefreshResponseBody(values);
    }
}
