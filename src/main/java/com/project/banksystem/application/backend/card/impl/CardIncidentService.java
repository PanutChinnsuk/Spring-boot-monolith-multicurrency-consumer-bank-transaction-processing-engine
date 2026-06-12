package com.project.banksystem.application.backend.card.impl;

import com.project.banksystem.application.backend.banktransaction.transaction.impl.TransactionTerminationServiceImpl;
import com.project.banksystem.application.backend.card.validator.CardStatusValidator;
import com.project.banksystem.application.backend.card.query.CardFinder;
import com.project.banksystem.application.backend.banktransaction.cardtransaction.query.CardTransactionFinder;
import com.project.banksystem.core.enums.card.CardStatus;
import com.project.banksystem.core.enums.transaction.TransactionStatus;
import com.project.banksystem.infrastructure.model.Card;
import com.project.banksystem.infrastructure.model.CardTransaction;
import com.project.banksystem.infrastructure.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CardIncidentService {
    private final TransactionTerminationServiceImpl transactionTerminationServiceImpl;
    private final CardTransactionFinder cardTransactionFinder;
    private final TransactionRepository transactionRepository;
    private final CardFinder cardFinder;

    public CardIncidentService(TransactionTerminationServiceImpl transactionTerminationServiceImpl,
                               CardTransactionFinder cardTransactionFinder,
                               TransactionRepository transactionRepository,
                               CardFinder cardFinder) {
        this.transactionTerminationServiceImpl = transactionTerminationServiceImpl;
        this.cardTransactionFinder = cardTransactionFinder;
        this.transactionRepository = transactionRepository;
        this.cardFinder = cardFinder;
    }



    public void reportCardCompromised(final UUID cardId,
                                             final CardStatus incidentStatus) {
        Card card = cardFinder.findById(cardId);

        CardStatusValidator.assertIncidentStatus(card, incidentStatus);

        List<CardTransaction> cardTransactions = cardTransactionFinder.
                findAllCancellableByCardId(cardId);

        Set<UUID> transactionIdSet = cardTransactions
                .stream()
                .map(ct -> ct.getTransaction())
                .filter(t -> !t.isReversedTransaction())
                .map(t -> t.getTransactionId())
                .collect(Collectors.toSet());

        if (transactionIdSet.size() > 0) {
            transactionRepository.bulkTerminateTransaction(TransactionStatus.CANCELLED, transactionIdSet);
        }


        cardTransactions.stream().filter(ct -> ct.getTransaction()
                        .getTransactionStatus() == TransactionStatus.AUTHORIZED)
                        .forEach(ct -> {
                            transactionTerminationServiceImpl
                                    .cardIncidentTerminateTransaction(ct.getTransaction());
                        });

        card.changeCardStatus(incidentStatus);
    }


}
