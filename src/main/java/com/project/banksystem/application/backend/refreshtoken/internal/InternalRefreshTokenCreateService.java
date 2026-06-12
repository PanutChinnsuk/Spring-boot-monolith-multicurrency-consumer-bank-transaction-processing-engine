package com.project.banksystem.application.backend.refreshtoken.internal;

import com.project.banksystem.application.backend.refreshtoken.RefreshTokenManager;
import com.project.banksystem.infrastructure.model.AccessSession;
import com.project.banksystem.infrastructure.model.RefreshToken;
import com.project.banksystem.infrastructure.model.User;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.time.LocalDateTime;

@Service
public class InternalRefreshTokenCreateService {
    private final RefreshTokenManager refreshTokenManager;

    public InternalRefreshTokenCreateService(RefreshTokenManager refreshTokenManager) {
        this.refreshTokenManager = refreshTokenManager;
    }


    public RefreshToken createRefreshToken(final User user,
                                           final AccessSession accessSession,
                                           final LocalDateTime expiry,
                                           final String deviceIp) {
        return refreshTokenManager.save(RefreshToken.builder()
                .user(user)
                .expiresAt(expiry)
                .accessSession(accessSession)
                .deviceIp(deviceIp)
                .build());
    }
}
