package com.project.banksystem.application.backend.cardcontact.contact.validator;

import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.repository.CardContactRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CardContactValidator {
    private final CardContactRepository cardContactRepository;

    public CardContactValidator(CardContactRepository cardContactRepository) {
        this.cardContactRepository = cardContactRepository;
    }


    public void validateContactAlreadyAdded(final UUID cardId) {
        if (cardContactRepository.alreadyAdded(cardId)) {
            throw new BusinessException("Already have card contact added.");
        }
    }
}
