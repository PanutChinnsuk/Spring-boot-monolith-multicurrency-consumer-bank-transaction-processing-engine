package com.project.banksystem.application.backend.useractivation;

import com.project.banksystem.infrastructure.model.ActivationCode;
import com.project.banksystem.infrastructure.repository.ActivationCodeRepository;
import org.springframework.stereotype.Component;

@Component
public class ActivationCodeManager {
    private final ActivationCodeRepository activationCodeRepository;

    public ActivationCodeManager(ActivationCodeRepository activationCodeRepository) {
        this.activationCodeRepository = activationCodeRepository;
    }

    public ActivationCode save(final ActivationCode activationCode) {
        return activationCodeRepository.save(activationCode);
    }
}
