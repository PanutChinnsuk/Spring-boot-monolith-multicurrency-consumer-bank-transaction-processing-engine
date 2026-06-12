package com.project.banksystem.application.backend.cardcontact.contact.impl;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.backend.cardcontact.code.query.CardContactReferenceCodeFinder;
import com.project.banksystem.application.backend.cardcontact.code.validator.CardContactReferenceCodeValidator;
import com.project.banksystem.application.backend.cardcontact.contact.CardContactManager;
import com.project.banksystem.application.backend.cardcontact.contact.service.CardContactAddService;
import com.project.banksystem.application.backend.cardcontact.contact.validator.CardContactValidator;
import com.project.banksystem.application.backend.user.query.UserFinder;
import com.project.banksystem.infrastructure.model.Card;
import com.project.banksystem.infrastructure.model.CardContact;
import com.project.banksystem.infrastructure.model.CardContactReferenceCode;
import com.project.banksystem.infrastructure.model.User;
import org.springframework.stereotype.Service;

@Service
public class CardContactAddServiceImpl implements CardContactAddService {
    private final CardContactReferenceCodeFinder cardContactReferenceCodeFinder;
    private final CardContactValidator cardContactValidator;
    private final CardContactManager cardContactManager;
    private final UserFinder userFinder;

    public CardContactAddServiceImpl(CardContactReferenceCodeFinder cardContactReferenceCodeFinder,
                                     CardContactValidator cardContactValidator,
                                     CardContactManager cardContactManager,
                                     UserFinder userFinder) {
        this.cardContactReferenceCodeFinder = cardContactReferenceCodeFinder;
        this.cardContactValidator = cardContactValidator;
        this.cardContactManager = cardContactManager;
        this.userFinder = userFinder;
    }

    @Override
    public CardContact addCardContact(final String code) {
        CardContactReferenceCode ref = cardContactReferenceCodeFinder
                .findOperationalForAddingCardContact(code);

        Card card = ref.getCard();
        cardContactValidator.validateContactAlreadyAdded(card.getCardId());

        User user = userFinder.findOperationalById(CurrentUser.getCurrentUserId());
        ref.revokeCode();

        return cardContactManager.save(CardContact.builder()
                .card(card)
                .user(user)
                .builder());
    }
}
