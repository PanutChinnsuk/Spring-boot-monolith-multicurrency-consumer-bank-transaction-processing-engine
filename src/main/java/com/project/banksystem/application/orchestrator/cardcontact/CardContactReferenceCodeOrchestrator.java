package com.project.banksystem.application.orchestrator.cardcontact;

import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.cardcontact.code.service.CardContactReferenceCodeRequestService;
import com.project.banksystem.core.response.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CardContactReferenceCodeOrchestrator {
    private final CardContactReferenceCodeRequestService cardContactReferenceCodeRequestService;


    public CardContactReferenceCodeOrchestrator(CardContactReferenceCodeRequestService cardContactReferenceCodeRequestService) {
        this.cardContactReferenceCodeRequestService = cardContactReferenceCodeRequestService;
    }


    @Transactional
    public Result requestCardReferenceCode(final UUID cardId) {
        return DataResultBuilder.build(cardContactReferenceCodeRequestService
                .requestReferenceCode(cardId).getCode());
    }
}
