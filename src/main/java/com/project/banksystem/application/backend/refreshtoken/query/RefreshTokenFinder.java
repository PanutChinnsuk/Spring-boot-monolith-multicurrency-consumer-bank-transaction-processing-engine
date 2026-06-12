package com.project.banksystem.application.backend.refreshtoken.query;

import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.RefreshToken;
import com.project.banksystem.infrastructure.repository.RefreshTokenRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Supplier;

@Component
public class RefreshTokenFinder {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenFinder(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken findById(final UUID refreshTokenId) {
        RefreshToken refreshToken = refreshTokenRepository.findByTokenIdWithAccessSessionAndUserLoaded(refreshTokenId)
                .orElseThrow(() -> new NotFoundException("Refresh token not found."));
        return refreshToken;
    }
}
