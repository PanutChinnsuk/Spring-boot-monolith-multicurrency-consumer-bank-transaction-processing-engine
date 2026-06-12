package com.project.banksystem.application.backend.verification.builder;

import com.project.banksystem.application.backend.PasswordEncoderService;
import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.entitybuilder.VerificationCodeEntityBuilderBody;
import org.springframework.stereotype.Component;

import com.project.banksystem.infrastructure.model.sharedmodel.verification.entitybuilder.VerificationCodeEntityBuilderBody.Builder;

import java.time.LocalDateTime;

@Component
public class VerificationCodeEntityBuilder {
    private final PasswordEncoderService passwordEncoderService;

    public VerificationCodeEntityBuilder(PasswordEncoderService passwordEncoderService) {
        this.passwordEncoderService = passwordEncoderService;
    }

    public Builder getDefaultBuilder(final User user,
                              final String code,
                              final TwoFactorPurpose twoFactorPurpose,
                              final String deviceIp,
                              final LocalDateTime expiresAt) {
        return VerificationCodeEntityBuilderBody.builder()
                .user(user)
                .code(passwordEncoderService.encode(code))
                .twoFactorPurpose(twoFactorPurpose)
                .deviceIp(deviceIp)
                .expiresAt(expiresAt);
    }
}
