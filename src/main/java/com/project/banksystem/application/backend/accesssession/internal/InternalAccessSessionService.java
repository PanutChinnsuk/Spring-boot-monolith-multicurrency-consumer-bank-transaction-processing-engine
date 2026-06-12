package com.project.banksystem.application.backend.accesssession.internal;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.backend.accesssession.query.AccessSessionFinder;
import com.project.banksystem.application.backend.accesssession.validator.AccessSessionValidator;
import com.project.banksystem.infrastructure.exception.ex.Unauthorized;
import com.project.banksystem.infrastructure.model.AccessSession;
import com.project.banksystem.infrastructure.security.session.caching.body.AccessSessionCacheBody;
import com.project.banksystem.infrastructure.security.session.caching.caffeine.InMemoryCaffeineJwtTokenBlacklist;
import com.project.banksystem.infrastructure.security.session.caching.redis.RedisAccessSessionCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class InternalAccessSessionService {
    private final RedisAccessSessionCache redisAccessSessionCache;
    private final AccessSessionFinder accessSessionFinder;
    private final InternalAccessSessionCreateService internalAccessSessionCreateService;
    private final AccessSessionValidator accessSessionValidator;
    private final InMemoryCaffeineJwtTokenBlacklist inMemoryCaffeineJwtTokenBlacklist;

    public InternalAccessSessionService(RedisAccessSessionCache redisAccessSessionCache,
                                        AccessSessionFinder accessSessionFinder,
                                        InternalAccessSessionCreateService internalAccessSessionCreateService,
                                        AccessSessionValidator accessSessionValidator,
                                        InMemoryCaffeineJwtTokenBlacklist inMemoryCaffeineJwtTokenBlacklist) {
        this.redisAccessSessionCache = redisAccessSessionCache;
        this.accessSessionFinder = accessSessionFinder;
        this.internalAccessSessionCreateService = internalAccessSessionCreateService;
        this.accessSessionValidator = accessSessionValidator;
        this.inMemoryCaffeineJwtTokenBlacklist = inMemoryCaffeineJwtTokenBlacklist;
    }

    private boolean isCacheBodyValid(AccessSessionCacheBody body,
                                     String userIdFromJwtToken,
                                     String deviceIp,
                                     Long version) {

        String sessionBodyUserId = body.getUserId().toString();
        String currentUserId = CurrentUser.getCurrentUserId().toString();

        boolean sessionUserIdMatchedTokenUserId = sessionBodyUserId.equals(userIdFromJwtToken);
        boolean deviceIpMatched = body.getDeviceIp().equals(deviceIp);
        boolean revoked = body.getRevoked();
        boolean latestVersion = body.getVersion().equals(version);
        boolean sessionUserIdMatchedCurrentUserId = sessionBodyUserId.equals(currentUserId);


        return sessionUserIdMatchedTokenUserId && deviceIpMatched && !revoked && latestVersion
                && sessionUserIdMatchedCurrentUserId;
    }

    @Transactional
    public void checkForValidSession(final String sessionId,
                                     final String userIdFromJwtToken,
                                     final String deviceIp,
                                     final Long version,
                                     final String tokenForBlacklist) {
        AccessSessionCacheBody fromRedis = redisAccessSessionCache.getCache(sessionId);
        if (fromRedis != null && isCacheBodyValid(fromRedis, userIdFromJwtToken, deviceIp, version)) {
            return;
        }

        AccessSession accessSession = accessSessionFinder.findValidSessionById(
                UUID.fromString(sessionId)
        );



        boolean isValid = accessSessionValidator.isAccessSessionValid(accessSession, deviceIp, version);
        if (!isValid) {
            inMemoryCaffeineJwtTokenBlacklist.blacklistToken(tokenForBlacklist);
            throw new Unauthorized("Invalid session.");
        }

        internalAccessSessionCreateService.applyCacheSession(
                accessSession.getUser().getUserId(),
                accessSession.getDeviceIp(),
                accessSession.getAccessSessionId(),
                accessSession.getVersion(),
                accessSession.getRevoked()
        );


    }


}
