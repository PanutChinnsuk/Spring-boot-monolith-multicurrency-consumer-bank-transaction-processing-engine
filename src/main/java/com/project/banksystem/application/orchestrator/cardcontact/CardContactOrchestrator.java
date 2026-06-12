package com.project.banksystem.application.orchestrator.cardcontact;

import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.cardcontact.contact.service.CardContactAddService;
import com.project.banksystem.core.response.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardContactOrchestrator {
    private final CardContactAddService cardContactAddService;


    public CardContactOrchestrator(CardContactAddService cardContactAddService) {
        this.cardContactAddService = cardContactAddService;
    }


    @Transactional
    public Result addCardContact(final String code) {
        cardContactAddService.addCardContact(code);

        return DataResultBuilder.build("Successfully added card contact.");
    }
}
