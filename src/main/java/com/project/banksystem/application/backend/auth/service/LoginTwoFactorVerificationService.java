package com.project.banksystem.application.backend.auth.service;

import com.project.banksystem.application.backend.auth.body.AuthResponseBody;

import java.util.UUID;

public interface LoginTwoFactorVerificationService {
    AuthResponseBody verify2FA(UUID codeId,
                               String deviceIp,
                               UUID userIdFromToken,
                               String verificationCode);
}
