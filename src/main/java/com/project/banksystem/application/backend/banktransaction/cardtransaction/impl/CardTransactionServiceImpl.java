package com.project.banksystem.application.backend.banktransaction.cardtransaction.impl;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.application.backend.banktransaction.body.TransactionContext;
import com.project.banksystem.application.backend.banktransaction.body.TransferTemplate;
import com.project.banksystem.application.backend.banktransaction.builder.TransactionContextBuilder;
import com.project.banksystem.application.backend.banktransaction.cardtransaction.service.CardTransactionService;
import com.project.banksystem.application.backend.banktransaction.service.ForeignExchangeTransferService;
import com.project.banksystem.application.backend.banktransaction.service.TransferService;
import com.project.banksystem.application.backend.card.impl.CardMigrationService;
import com.project.banksystem.application.backend.card.query.CardFinder;
import com.project.banksystem.application.backend.banktransaction.cardtransaction.body.CardTransactionTemplate;
import com.project.banksystem.application.backend.banktransaction.cardtransaction.data.CardTransactionCreateData;
import com.project.banksystem.application.backend.banktransaction.cardtransaction.internal.InternalCardTransactionCreateService;
import com.project.banksystem.core.enums.transaction.TransactionPrincipal;
import com.project.banksystem.application.aspect.moneytransfering.payment.anno.methodtrigger.SetCardIdAndAmount;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Card;
import com.project.banksystem.infrastructure.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;


@Service
public class CardTransactionServiceImpl implements CardTransactionService {
    private final ForeignExchangeTransferService foreignExchangeTransferService;
    private final TransferService transferService;
    private final CardFinder cardFinder;
    private final AccountValidator accountValidator;
    private final CardMigrationService cardMigrationService;
    private final InternalCardTransactionCreateService internalCardTransactionCreateService;

    public CardTransactionServiceImpl(ForeignExchangeTransferService foreignExchangeTransferService,
                                      TransferService transferService,
                                      CardFinder cardFinder,
                                      AccountValidator accountValidator,
                                      CardMigrationService cardMigrationService,
                                      InternalCardTransactionCreateService internalCardTransactionCreateService) {
        this.foreignExchangeTransferService = foreignExchangeTransferService;
        this.transferService = transferService;
        this.cardFinder = cardFinder;
        this.accountValidator = accountValidator;
        this.cardMigrationService = cardMigrationService;
        this.internalCardTransactionCreateService = internalCardTransactionCreateService;
    }

    private Set<UUID> getBaseIdSet(final CardTransactionTemplate template) {

        UUID baseCardId = template.getBaseCardId();
        UUID targetCardId = template.getTargetCardId();

        Set<UUID> idSet = new HashSet<>();
        idSet.add(targetCardId);

        if (baseCardId != null) {idSet.add(baseCardId);}

        return idSet;
    }


    private Transaction executeCardTransaction(final CardTransactionTemplate cardTransactionTemplate,
                                               final BiFunction<TransferTemplate, TransactionContext, Transaction> transactionExecute) {

        UUID baseCardId = cardTransactionTemplate.getBaseCardId();
        UUID targetCardId = cardTransactionTemplate.getTargetCardId();
        BigDecimal amount = cardTransactionTemplate.getAmount();



        Map<UUID, Card> cards = cardFinder.findOperationalByIdIn(
                getBaseIdSet(cardTransactionTemplate)
        );



        Card baseCard = cards.get(baseCardId);
        Card targetCard = cards.get(targetCardId);


        cardMigrationService.forceCheckCardMigration(baseCard);
        cardMigrationService.forceCheckCardMigration(targetCard);

        Account baseAccount = baseCard.getAccount();
        if (!CurrentUser.isUserSystemAdministrator()) {
            accountValidator.validateAccountCustomerIdMatchedCurrentCustomerId(baseAccount);
        }

        Account targetAccount = targetCard.getAccount();



        TransferTemplate transferTemplate = TransferTemplate.builder()
                .fromAccount(baseAccount)
                .toAccount(targetAccount)
                .baseAmount(amount)
                .build();

        TransactionContext transactionContext = TransactionContextBuilder
                .getFor(TransactionPrincipal.CARD_FOR_TRANSACTION);
        transactionContext.setCardTransactionTemplate(cardTransactionTemplate);

        Transaction transaction = transactionExecute.apply(transferTemplate,
                transactionContext);

        CardTransactionCreateData data = CardTransactionCreateData.builder()
                .card(baseCard)
                .transaction(transaction)
                .build();

        internalCardTransactionCreateService.createCardTransaction(data);


        return transaction;
    }

    @Override
    @SetCardIdAndAmount
    public Transaction sellBaseCurrency(final CardTransactionTemplate cardTransactionTemplate) {
        return executeCardTransaction(cardTransactionTemplate,
                foreignExchangeTransferService::sellBaseCurrency);
    }

    @Override
    @SetCardIdAndAmount
    public Transaction buyBaseCurrency(final CardTransactionTemplate cardTransactionTemplate) {
        return executeCardTransaction(cardTransactionTemplate,
                foreignExchangeTransferService::buyBaseCurrency);
    }

    @Override
    @SetCardIdAndAmount
    public Transaction baseTransfer(final CardTransactionTemplate cardTransactionTemplate) {
        return executeCardTransaction(cardTransactionTemplate,
                transferService::transfer);
    }


}
