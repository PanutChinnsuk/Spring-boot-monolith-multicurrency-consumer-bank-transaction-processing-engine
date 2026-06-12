package com.project.banksystem.application.backend.verification;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class TwoFactorCodeGenerator {
    private final SecureRandom secureRandom;

    public TwoFactorCodeGenerator(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }

    public String generate2FAcode() {
        Long seq = 100_000 + (long)(secureRandom.nextDouble() * 900_000);
        return seq.toString();
    }
}
