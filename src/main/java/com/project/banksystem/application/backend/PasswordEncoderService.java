package com.project.banksystem.application.backend;

import com.project.banksystem.core.enums.credential.PasswordAlgorithm;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderService {
    private final PasswordEncoder passwordEncoder;

    public PasswordEncoderService() {
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    public String encode(String raw) {
        return passwordEncoder.encode(raw);
    }

    public PasswordAlgorithm getEncodeAlgorithm(String encoded) {
        String errorMessage = "Invalid encoding algorithm.";

        if (encoded == null || !encoded.startsWith("{")) {
            throw new IllegalArgumentException(errorMessage);
        }

        int endIndex = encoded.indexOf('}');
        if (endIndex == -1) {
            throw new IllegalArgumentException(errorMessage);
        }

        try {
            String algorithm = encoded.substring(1, endIndex).toUpperCase();
            return PasswordAlgorithm.valueOf(algorithm);
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public boolean isMatched(String raw, String hashed) {
        return passwordEncoder.matches(raw, hashed);
    }

}
