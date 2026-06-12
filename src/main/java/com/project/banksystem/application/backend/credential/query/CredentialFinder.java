package com.project.banksystem.application.backend.credential.query;

import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.Credential;
import com.project.banksystem.infrastructure.repository.CredentialRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CredentialFinder {
    private final CredentialRepository credentialRepository;

    public CredentialFinder(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    public Credential findById(final UUID credentialId) {
        return credentialRepository.findCredentialById(credentialId)
                .orElseThrow(() -> new NotFoundException("Credential not found."));
    }
}
