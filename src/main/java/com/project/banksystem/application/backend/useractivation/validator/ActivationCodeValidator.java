package com.project.banksystem.application.backend.useractivation.validator;

import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.core.enums.user.UserStatus;
import com.project.banksystem.infrastructure.model.ActivationCode;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.repository.ActivationCodeRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ActivationCodeValidator {
    private final ActivationCodeRepository activationCodeRepository;

    public ActivationCodeValidator(ActivationCodeRepository activationCodeRepository) {
        this.activationCodeRepository = activationCodeRepository;
    }

    public void validateNonRevoked(final ActivationCode activationCode) {
        Boolean revoked = activationCode.getRevoked();
        LocalDateTime expiresAt = activationCode.getExpiresAt();
        LocalDateTime now = LocalDateTime.now();

        if (revoked) {
            throw new BusinessException("Activation code is revoked.");
        }

        if (expiresAt.isBefore(now) || expiresAt.isEqual(now)) {
            throw new BusinessException("Activation code is expired.");
        }


    }










}
