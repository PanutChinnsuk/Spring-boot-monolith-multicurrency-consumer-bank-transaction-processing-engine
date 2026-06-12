package com.project.banksystem.application.backend.cardcontact.code.impl;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.backend.card.query.CardFinder;
import com.project.banksystem.application.backend.card.validator.CardValidator;
import com.project.banksystem.application.backend.cardcontact.code.CardContactReferenceCodeGenerator;
import com.project.banksystem.application.backend.cardcontact.code.CardContactReferenceCodeManager;
import com.project.banksystem.application.backend.cardcontact.code.service.CardContactReferenceCodeRequestService;
import com.project.banksystem.application.backend.user.query.UserFinder;
import com.project.banksystem.application.config.properties.CardConfig;
import com.project.banksystem.infrastructure.model.Card;
import com.project.banksystem.infrastructure.model.CardContactReferenceCode;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.repository.CardContactReferenceCodeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CardContactReferenceCodeRequestServiceImpl implements CardContactReferenceCodeRequestService {
    private final CardValidator cardValidator;
    private final CardContactReferenceCodeManager cardContactReferenceCodeManager;
    private final CardFinder cardFinder;
    private final UserFinder userFinder;
    private final CardConfig cardConfig;
    private final CardContactReferenceCodeGenerator generator;
    private final CardContactReferenceCodeRepository cardContactReferenceCodeRepository;

    public CardContactReferenceCodeRequestServiceImpl(CardValidator cardValidator,
                                                      CardContactReferenceCodeManager cardContactReferenceCodeManager,
                                                      CardFinder cardFinder,
                                                      UserFinder userFinder,
                                                      CardConfig cardConfig,
                                                      CardContactReferenceCodeGenerator generator,
                                                      CardContactReferenceCodeRepository cardContactReferenceCodeRepository) {
        this.cardValidator = cardValidator;
        this.cardContactReferenceCodeManager = cardContactReferenceCodeManager;
        this.cardFinder = cardFinder;
        this.userFinder = userFinder;
        this.cardConfig = cardConfig;
        this.generator = generator;
        this.cardContactReferenceCodeRepository = cardContactReferenceCodeRepository;
    }


    @Override
    public CardContactReferenceCode requestReferenceCode(final UUID cardId) {
        Card card = cardFinder.findOperationalById(cardId);
        cardValidator.validateCardBelongToCustomer(card,
                CurrentUser.getCurrentUserCustomerId());

        UUID currentUserId = CurrentUser.getCurrentUserId();
        User user = userFinder.findNonTerminatedById(currentUserId);
        String code = generator.generateReferenceCode();
        cardContactReferenceCodeRepository.revokeAllPreviousCode(currentUserId,
                LocalDateTime.now());

        return cardContactReferenceCodeManager.save(
                CardContactReferenceCode.builder()
                        .card(card)
                        .user(user)
                        .code(code)
                        .expiresAt(cardConfig.getReferenceCodeExpiry())
                        .build()
        );
    }


}
