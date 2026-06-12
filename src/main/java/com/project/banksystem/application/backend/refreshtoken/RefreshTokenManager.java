package com.project.banksystem.application.backend.refreshtoken;

import com.project.banksystem.infrastructure.model.RefreshToken;
import com.project.banksystem.infrastructure.repository.RefreshTokenRepository;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenManager {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenManager(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }


    public RefreshToken save(final RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }
}
