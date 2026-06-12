package com.project.banksystem.application.backend.card;

import com.project.banksystem.application.backend.card.body.GeneratedCardNumber;
import com.project.banksystem.application.config.properties.CardEncryptionConfig;
import com.project.banksystem.infrastructure.repository.CardRepository;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Map;

@Component
public class CardNumberGenerator {
    private final CardRepository cardRepository;
    private final SecureRandom secureRandom;
    private final CardNumberEncryption cardNumberEncryption;

    public CardNumberGenerator(CardRepository cardRepository,
                               SecureRandom secureRandom,
                               CardNumberEncryption cardNumberEncryption) {
        this.cardRepository = cardRepository;
        this.secureRandom = secureRandom;
        this.cardNumberEncryption = cardNumberEncryption;

    }




    private String random() {
        long sequence = 1000_0000L + (long)(secureRandom.nextDouble() * 9000_0000L);

        return String.valueOf(sequence);

    }

    public GeneratedCardNumber generateCardNumber() {

        int attempt = 3;
        int current = 0;
        try {
            while (current < attempt) {
                current++;

                String value = random() + random();
                String encrypted = cardNumberEncryption.plainEncrypt(value);

                if (!cardRepository.existsByPanEncrypt(encrypted)) {
                    return new GeneratedCardNumber(value, encrypted);
                }
            }
        }catch (Exception e) {
            throw new IllegalStateException("Failed to generate card number.");
        }

        throw new IllegalStateException("Failed to create card number.");

    }

}
