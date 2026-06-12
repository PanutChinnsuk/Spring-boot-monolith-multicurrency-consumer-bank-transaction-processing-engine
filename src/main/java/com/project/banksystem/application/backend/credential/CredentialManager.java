package com.project.banksystem.application.backend.credential;

import com.project.banksystem.infrastructure.model.Credential;
import com.project.banksystem.infrastructure.repository.CredentialRepository;
import org.springframework.stereotype.Component;

@Component
public class CredentialManager {
    private final CredentialRepository credentialRepository;

    public CredentialManager(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    public void save(final Credential credential) {
        credentialRepository.save(credential);
    }
}
