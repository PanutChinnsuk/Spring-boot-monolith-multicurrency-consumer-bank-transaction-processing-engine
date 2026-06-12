package com.project.banksystem.infrastructure.security.session.caching.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class InMemoryCaffeineJwtTokenBlacklist {
    private final Cache<String, String> inMemoryJwtAccessTokenBlacklistCache;

    public InMemoryCaffeineJwtTokenBlacklist(@Qualifier("inMemoryJwtAccessTokenBlacklistCache")
                                     Cache<String, String> inMemoryJwtAccessTokenBlacklistCache) {
        this.inMemoryJwtAccessTokenBlacklistCache = inMemoryJwtAccessTokenBlacklistCache;
    }


    public void blacklistToken(final String token) {
        inMemoryJwtAccessTokenBlacklistCache.put(token, "blacklisted");
    }

    public boolean isTokenBlacklisted(final String token) {
        return inMemoryJwtAccessTokenBlacklistCache.getIfPresent(token) != null;
    }
}
