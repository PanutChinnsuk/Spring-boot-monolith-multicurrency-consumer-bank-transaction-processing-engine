package com.project.banksystem.application.backend.cardcontact.contact;

import com.project.banksystem.infrastructure.model.CardContact;
import com.project.banksystem.infrastructure.repository.CardContactRepository;
import org.springframework.stereotype.Component;

@Component
public class CardContactManager {
    private final CardContactRepository cardContactRepository;

    public CardContactManager(CardContactRepository cardContactRepository) {
        this.cardContactRepository = cardContactRepository;
    }


    public CardContact save(final CardContact cardContact) {
        return cardContactRepository.save(cardContact);
    }
}
