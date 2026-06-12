package com.project.banksystem.application.backend.credential.validator;

import com.project.banksystem.application.backend.PasswordEncoderService;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.repository.CredentialRepository;
import org.springframework.stereotype.Component;

@Component
public class CredentialValidator {
    private final CredentialRepository credentialRepository;
    private final PasswordEncoderService passwordEncoderService;

    public CredentialValidator(CredentialRepository credentialRepository,
                               PasswordEncoderService passwordEncoderService) {
        this.credentialRepository = credentialRepository;
        this.passwordEncoderService = passwordEncoderService;
    }

    public void validateCanCreate(final User user) {
        if (credentialRepository.existsByUser(user)) {
            throw new BusinessException("User already have credential.");
        }
    }

    public void validateCanCreatePassword(final String password,
                                      final String confirmedPassword) {
        if (!password.equals(confirmedPassword)) {
            throw new BusinessException("Password is not matched to confirmed password.");
        }
    }

    public void validateCanCreatePin(final String pin,
                                          final String confirmedPin) {
        if (!pin.equals(confirmedPin)) {
            throw new BusinessException("Pin is not matched to confirmed pin.");
        }
    }

    public void validateNotMatchOldCredential(final String newRaw,
                                              final String oldHash) {
        if (passwordEncoderService.isMatched(newRaw, oldHash)) {
            throw new BusinessException("Credential value is the same as old credential.");
        }
    }


}
