package com.project.banksystem.application.backend.cardcontact.code;

import com.project.banksystem.infrastructure.repository.CardContactReferenceCodeRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CardContactReferenceCodeGenerator {
    private final CardContactReferenceCodeRepository cardContactReferenceCodeRepository;

    public CardContactReferenceCodeGenerator(CardContactReferenceCodeRepository cardContactReferenceCodeRepository) {
        this.cardContactReferenceCodeRepository = cardContactReferenceCodeRepository;
    }

    private final int MAX_CODE_GENERATE_ATTEMPTS = 2;

    private String generate() {
        String code = UUID.randomUUID().toString();
        return code.replace("-","").substring(0, 16);
    }

    public String generateReferenceCode() {
        int current = 0;

        while (current < MAX_CODE_GENERATE_ATTEMPTS) {
            current++;
            String code = generate();

            if (!cardContactReferenceCodeRepository.existsByCode(code)) {
                return code;
            }
        }

        throw new IllegalStateException("Failed to generate card reference code.");
    }
}
