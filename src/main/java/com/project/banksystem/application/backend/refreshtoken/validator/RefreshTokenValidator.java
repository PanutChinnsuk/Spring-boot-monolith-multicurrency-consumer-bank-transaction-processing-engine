package com.project.banksystem.application.backend.refreshtoken.validator;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.model.AccessSession;
import com.project.banksystem.infrastructure.model.RefreshToken;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.security.session.properties.SessionConfig;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
public class RefreshTokenValidator {
    private final SessionConfig sessionConfig;

    public RefreshTokenValidator(SessionConfig sessionConfig) {
        this.sessionConfig = sessionConfig;
    }

    public void validateRefreshToken(final RefreshToken refreshToken,
                                     final String deviceIp,
                                     final UUID userIdFromJwtToken,
                                     final AccessSession accessSession,
                                     final User accessSessionUser) {
        String accessSessionDeviceIp = refreshToken.getDeviceIp();
        String refreshTokenDeviceIp = refreshToken.getDeviceIp();

        UUID currentUserId = CurrentUser.getCurrentUserId();
        User refreshTokenUser = refreshToken.getUser();

        LocalDateTime accessSessionExpiry = accessSession.getExpiresAt();
        LocalDateTime whenRefreshable = accessSessionExpiry
                .minus(sessionConfig.getRefreshableThresholdMinute(), ChronoUnit.MINUTES);
        LocalDateTime now = LocalDateTime.now();



        boolean isRevoked = refreshToken.getRevoked();
        boolean isExpired = refreshToken.getExpiresAt().isBefore(now);
        boolean isUserIdMatch = currentUserId.equals(refreshTokenUser.getUserId());
        boolean canRefresh = whenRefreshable.isBefore(now) || whenRefreshable.isEqual(now);
        boolean deviceIpMatched = refreshTokenDeviceIp.equals(deviceIp);
        boolean refreshIpMatchedAccessSessionIp = accessSessionDeviceIp.equals(refreshTokenDeviceIp);
        boolean isTokenUserIdMatchedRefreshTokenUserId = refreshTokenUser.getUserId()
                .equals(userIdFromJwtToken);
        boolean isTokenUserIdMatchedAccessSessionUserId = accessSessionUser.getUserId()
                .equals(userIdFromJwtToken);
        boolean isCurrentUserIdMatchedTokenUserId = currentUserId.equals(userIdFromJwtToken);


        if (!canRefresh) {
            throw new Forbidden("Too early to use refresh token.");
        }



        if (isRevoked || isExpired || !isUserIdMatch || !deviceIpMatched
        || !isTokenUserIdMatchedRefreshTokenUserId || !isTokenUserIdMatchedAccessSessionUserId
        || !isCurrentUserIdMatchedTokenUserId || !refreshIpMatchedAccessSessionIp) {
            throw new Forbidden("Invalid refresh token.");
        }
    }
}
