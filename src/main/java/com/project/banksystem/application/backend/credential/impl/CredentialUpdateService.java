package com.project.banksystem.application.backend.credential.impl;

import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.PasswordEncoderService;
import com.project.banksystem.application.backend.credential.CredentialManager;
import com.project.banksystem.application.backend.credential.validator.CredentialValidator;
import com.project.banksystem.application.backend.user.query.UserFinder;
import com.project.banksystem.api.request.dto.credential.CredentialForceUpdatePasswordDTO;
import com.project.banksystem.api.request.dto.credential.CredentialForceUpdatePinDTO;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.core.response.Result;
import com.project.banksystem.infrastructure.model.Credential;
import com.project.banksystem.infrastructure.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@Service
public class CredentialUpdateService {
    private final UserFinder userFinder;
    private final PasswordEncoderService passwordEncoderService;
    private final CredentialValidator credentialValidator;

    public CredentialUpdateService(UserFinder userFinder,
                                   PasswordEncoderService passwordEncoderService,
                                   CredentialValidator credentialValidator) {
        this.userFinder = userFinder;
        this.passwordEncoderService = passwordEncoderService;
        this.credentialValidator = credentialValidator;
    }

    private Credential getCredentialFromUser(final UUID userId) {
        User user = userFinder.findNonTerminatedById(userId);

        Credential credential = user.getCredential();

        if (credential == null) {
            throw new BusinessException("User don't have credential set up.");
        }

        return credential;

    }



    public void forceUpdatePassword(final CredentialForceUpdatePasswordDTO credentialForceUpdatePasswordDTO) {
        Credential credential = getCredentialFromUser(credentialForceUpdatePasswordDTO.getUserId());

        String newForcedPassword = credentialForceUpdatePasswordDTO.getPassword();

        credentialValidator.validateNotMatchOldCredential(newForcedPassword,
                credential.getPasswordHash());

        String newForcedPasswordHash = passwordEncoderService.encode(newForcedPassword);

        credential.changePasswordHash(newForcedPasswordHash);
        credential.changePasswordAlgorithm(passwordEncoderService.getEncodeAlgorithm(newForcedPasswordHash));
    }


    public void forceUpdatePin(final CredentialForceUpdatePinDTO credentialForceUpdatePinDTO) {
        Credential credential = getCredentialFromUser(credentialForceUpdatePinDTO.getUserId());

        String newForcedPin = credentialForceUpdatePinDTO.getPin();

        credentialValidator.validateNotMatchOldCredential(newForcedPin,
                credential.getPinHash());

        String newForcedPinHash = passwordEncoderService.encode(newForcedPin);

        credential.changePinHash(newForcedPinHash);
        credential.changePinAlgorithm(passwordEncoderService.getEncodeAlgorithm(newForcedPinHash));
    }
}
