package com.project.banksystem.application.backend.useractivation.impl;

import com.project.banksystem.application.backend.PasswordEncoderService;
import com.project.banksystem.application.backend.user.validator.UserStatusValidator;
import com.project.banksystem.application.backend.useractivation.ActivationCodeManager;
import com.project.banksystem.application.backend.useractivation.validator.ActivationCodeValidator;
import com.project.banksystem.application.backend.user.query.UserFinder;
import com.project.banksystem.application.config.properties.UserConfig;
import com.project.banksystem.api.request.dto.activationcode.ActivationCodeCreateDTO;
import com.project.banksystem.infrastructure.model.ActivationCode;
import com.project.banksystem.infrastructure.model.User;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class ActivationCodeCreateService {
    private final ActivationCodeManager activationCodeManager;
    private final ActivationCodeValidator activationCodeValidator;
    private final PasswordEncoderService passwordEncoderService;
    private final UserConfig userConfig;
    private final UserFinder userFinder;


    public ActivationCodeCreateService(ActivationCodeManager activationCodeManager,
                                       ActivationCodeValidator activationCodeValidator,
                                       UserFinder userFinder,
                                       PasswordEncoderService passwordEncoderService,
                                       UserConfig userConfig) {
        this.activationCodeManager = activationCodeManager;
        this.activationCodeValidator = activationCodeValidator;
        this.userFinder = userFinder;
        this.passwordEncoderService = passwordEncoderService;
        this.userConfig = userConfig;
    }


    public ActivationCode createActivationCode(final ActivationCodeCreateDTO activationCodeCreateDTO) {
        UUID userId = activationCodeCreateDTO.getUserId();
        User user = userFinder.findOperationalById(userId);

        UserStatusValidator.assertVerify(user);

        return activationCodeManager.save(ActivationCode.builder()
                .user(user)
                .codeHash(passwordEncoderService.encode(activationCodeCreateDTO.getCode()))
                .expiresAt(LocalDateTime.now().plus(userConfig.getActivationCodeExpiresDay(), ChronoUnit.DAYS))
                .build());
    }
}
