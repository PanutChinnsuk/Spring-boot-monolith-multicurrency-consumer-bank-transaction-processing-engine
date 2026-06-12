package com.project.banksystem.application.backend.refreshtoken.service;

import com.project.banksystem.application.backend.refreshtoken.body.RefreshResponseBody;

import java.util.UUID;

public interface AccessSessionRefreshService {
    RefreshResponseBody refresh(UUID refreshTokenId,
                                String deviceIp,
                                UUID userIdFromJwtToken);
}
