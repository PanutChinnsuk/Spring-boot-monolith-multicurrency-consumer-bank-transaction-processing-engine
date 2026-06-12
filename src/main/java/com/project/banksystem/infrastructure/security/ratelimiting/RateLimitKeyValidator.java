package com.project.banksystem.infrastructure.security.ratelimiting;

public final class RateLimitKeyValidator {
    private RateLimitKeyValidator() {

    }


    public static final boolean isKeyValid(final String key) {
        return key != null && !key.isBlank();
    }
}
