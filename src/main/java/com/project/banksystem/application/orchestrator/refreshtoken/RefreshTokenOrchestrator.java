package com.project.banksystem.application.orchestrator.refreshtoken;

import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.refreshtoken.body.RefreshResponseBody;
import com.project.banksystem.application.backend.refreshtoken.service.AccessSessionRefreshService;
import com.project.banksystem.core.response.DataResult;
import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RefreshTokenOrchestrator {
    private final AccessSessionRefreshService accessSessionRefreshService;

    public RefreshTokenOrchestrator(AccessSessionRefreshService accessSessionRefreshService) {
        this.accessSessionRefreshService = accessSessionRefreshService;
    }

    @Transactional
    public DataResult<RefreshResponseBody> refreshAccessSession(final UUID refreshTokenId,
                                                                final UUID userIdFromToken,
                                                                final String deviceIp) {
        try {
            return DataResultBuilder.build(accessSessionRefreshService.refresh(refreshTokenId,
                    deviceIp, userIdFromToken), "Successfully refresh current access session.");
        }catch (NotFoundException e) {
            throw new Forbidden("Invalid refresh token.");
        }

    }


}
