package com.project.banksystem.application.backend.cardcontact.code;

import com.project.banksystem.infrastructure.model.CardContactReferenceCode;
import com.project.banksystem.infrastructure.repository.CardContactReferenceCodeRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CardContactReferenceCodeManager {
    private final CardContactReferenceCodeRepository cardContactReferenceCodeRepository;

    public CardContactReferenceCodeManager(CardContactReferenceCodeRepository cardContactReferenceCodeRepository) {
        this.cardContactReferenceCodeRepository = cardContactReferenceCodeRepository;
    }


    public CardContactReferenceCode save(final CardContactReferenceCode cardContactReferenceCode) {
        return cardContactReferenceCodeRepository.save(cardContactReferenceCode);
    }


}
