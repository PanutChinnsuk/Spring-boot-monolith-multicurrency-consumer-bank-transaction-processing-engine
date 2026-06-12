package com.project.banksystem.application.backend.cardcontact.code.query;

import com.project.banksystem.application.backend.cardcontact.code.validator.CardContactReferenceCodeValidator;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.CardContactReferenceCode;
import com.project.banksystem.infrastructure.repository.CardContactReferenceCodeRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CardContactReferenceCodeFinder {
    private final CardContactReferenceCodeRepository cardContactReferenceCodeRepository;
    private final CardContactReferenceCodeValidator cardContactReferenceCodeValidator;

    public CardContactReferenceCodeFinder(CardContactReferenceCodeRepository cardContactReferenceCodeRepository,
                                          CardContactReferenceCodeValidator cardContactReferenceCodeValidator) {
        this.cardContactReferenceCodeRepository = cardContactReferenceCodeRepository;
        this.cardContactReferenceCodeValidator = cardContactReferenceCodeValidator;
    }

    public CardContactReferenceCode findOperationalForAddingCardContact(final String code) {
        CardContactReferenceCode ref = cardContactReferenceCodeRepository.findForAddingCardContact(code)
                .orElseThrow(() -> new NotFoundException("Card reference code not found."));

        cardContactReferenceCodeValidator.validateOperational(ref);

        return ref;
    }
}
