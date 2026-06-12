package com.project.banksystem.application.backend.credential.internal;

import com.project.banksystem.application.backend.PasswordEncoderService;
import com.project.banksystem.application.backend.credential.CredentialManager;
import com.project.banksystem.api.request.dto.credential.CredentialInternalCreateDTO;
import com.project.banksystem.infrastructure.model.Credential;
import com.project.banksystem.infrastructure.model.User;
import org.springframework.stereotype.Service;

@Service
public class InternalCredentialCreateService {
    private final PasswordEncoderService passwordEncoderService;
    private final CredentialManager credentialManager;

    public InternalCredentialCreateService(PasswordEncoderService passwordEncoderService,
                                           CredentialManager credentialManager) {
        this.passwordEncoderService = passwordEncoderService;
        this.credentialManager = credentialManager;
    }

    public void createCredential(final CredentialInternalCreateDTO credentialInternalCreateDTO) {
        User user = credentialInternalCreateDTO.getUser();
        Credential credential = user.getCredential();
        String passwordHash = passwordEncoderService.encode(credentialInternalCreateDTO.getPassword());
        String pinHash = passwordEncoderService.encode(credentialInternalCreateDTO.getPin());

        if (credential == null) {
            credentialManager.save(Credential.builder()
                    .user(user)
                    .passwordHash(passwordHash)
                    .passwordAlgorithm(passwordEncoderService.getEncodeAlgorithm(passwordHash))
                    .pinHash(pinHash)
                    .pinAlgorithm(passwordEncoderService.getEncodeAlgorithm(pinHash))
                    .build());
        }else {
            credential.changePasswordHash(passwordHash);
            credential.changePasswordAlgorithm(passwordEncoderService.getEncodeAlgorithm(passwordHash));
            credential.changePinHash(pinHash);
            credential.changePinAlgorithm(passwordEncoderService.getEncodeAlgorithm(pinHash));
        }


    }
}
