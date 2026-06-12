package com.project.banksystem.application.backend.card.query;

import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.application.backend.card.validator.CardValidator;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.Card;
import com.project.banksystem.infrastructure.repository.CardRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CardFinder {
    private final CardRepository cardRepository;
    private final CardValidator cardValidator;
    private final AccountValidator accountValidator;

    public CardFinder(CardRepository cardRepository,
                      CardValidator cardValidator,
                      AccountValidator accountValidator) {
        this.cardRepository = cardRepository;
        this.cardValidator = cardValidator;
        this.accountValidator = accountValidator;
    }

    private Card idWithFkLoaded(final UUID cardId) {
        return cardRepository.findByCardId(cardId)
                .orElseThrow(() -> new NotFoundException("Card not found."));
    }

    private Card id(final UUID cardId) {
        return cardRepository.findPlainByCardId(cardId)
                .orElseThrow(() -> new NotFoundException("Card not found."));
    }


    public Card findOperationalById(final UUID cardId) {
        Card card = idWithFkLoaded(cardId);

        cardValidator.validateCardOperational(card);

        return card;
    }


    public Card findByIdWithActiveAccountAndCustomer(final UUID cardId) {
        Card card = idWithFkLoaded(cardId);

        accountValidator.validateAccountOperational(card.getAccount());

        return card;
    }

    public Card findById(final UUID cardId) {
        return id(cardId);
    }



    public Map<UUID, Card> findOperationalByIdIn(final Set<UUID> cardIds) {
        List<Card> cards = cardRepository.findByCardIdIn(cardIds);


        if (cards.size() != cardIds.size()) {
            throw new NotFoundException("One or more card is not valid for transaction.");
        }

        cards.forEach(c -> cardValidator.validateCardOperational(c));

        return cards.stream().collect(Collectors.toMap(
                Card::getCardId,
                Function.identity()
        ));
    }
}
