package com.project.banksystem.application.backend.useractivation;

import com.project.banksystem.api.response.read.adminres.activationcode.ActivationCodeAdminResponse;
import com.project.banksystem.api.response.read.adminres.activationcode.body.ActivationCodeUserAdminSummary;
import com.project.banksystem.application.backend.user.UserResponseMapper;
import com.project.banksystem.infrastructure.model.ActivationCode;
import com.project.banksystem.infrastructure.model.User;

public final class ActivationCodeResponseMapper {
    private ActivationCodeResponseMapper() {

    }




    public static final ActivationCodeAdminResponse toAdminResponse(final ActivationCode activationCode) {
        return ActivationCodeAdminResponse.builder()
                .activationCodeId(activationCode.getActivationCodeId())

                .activationCodeUserAdminSummary(UserResponseMapper
                        .toActivationCodeUserAdminSummary(activationCode.getUser()))

                .revoked(activationCode.getRevoked())
                .expiresAt(activationCode.getExpiresAt())
                .build();
    }
}
