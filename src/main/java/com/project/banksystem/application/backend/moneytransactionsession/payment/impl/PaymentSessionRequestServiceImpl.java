package com.project.banksystem.application.backend.moneytransactionsession.payment.impl;

import com.project.banksystem.api.request.dto.paymentsession.PaymentSessionRequestDTO;
import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.backend.account.query.AccountFinder;
import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.application.backend.card.query.CardFinder;
import com.project.banksystem.application.backend.card.validator.CardValidator;
import com.project.banksystem.application.backend.moneytransactionsession.payment.body.PaymentPrincipal;
import com.project.banksystem.application.backend.moneytransactionsession.payment.body.PaymentTarget;
import com.project.banksystem.application.backend.moneytransactionsession.payment.internal.InternalPaymentSessionCreateService;
import com.project.banksystem.application.backend.moneytransactionsession.payment.service.PaymentSessionRequestService;
import com.project.banksystem.application.backend.moneytransactionsession.payment.validator.PaymentSessionValidator;
import com.project.banksystem.application.backend.moneytransactionsession.resultrecords.ValidatedTransactionSessionRequestData;
import com.project.banksystem.application.backend.moneytransactionsession.validator.MoneyTransactionSessionValidator;
import com.project.banksystem.infrastructure.model.*;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Card;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.PaymentSession;
import com.project.banksystem.infrastructure.repository.PaymentSessionRepository;
import com.project.banksystem.infrastructure.security.session.jwt.body.PaymentSessionContext;
import com.project.banksystem.infrastructure.security.session.jwt.builder.JwtTokenBuilder;
import com.project.banksystem.infrastructure.security.session.properties.SessionConfig;
import com.project.banksystem.infrastructure.security.session.properties.body.SessionExpiry;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class PaymentSessionRequestServiceImpl implements PaymentSessionRequestService {
    private final InternalPaymentSessionCreateService internalPaymentSessionCreateService;
    private final JwtTokenBuilder jwtTokenBuilder;
    private final CardFinder cardFinder;
    private final SessionConfig sessionConfig;
    private final CardValidator cardValidator;
    private final PaymentSessionRepository paymentSessionRepository;
    private final AccountFinder accountFinder;
    private final AccountValidator accountValidator;
    private final MoneyTransactionSessionValidator moneyTransactionSessionValidator;
    private final PaymentSessionValidator paymentSessionValidator;


    public PaymentSessionRequestServiceImpl(InternalPaymentSessionCreateService internalPaymentSessionCreateService,
                                            JwtTokenBuilder jwtTokenBuilder,
                                            CardFinder cardFinder,
                                            SessionConfig sessionConfig,
                                            CardValidator cardValidator,
                                            PaymentSessionRepository paymentSessionRepository,
                                            AccountFinder accountFinder,
                                            AccountValidator accountValidator,
                                            MoneyTransactionSessionValidator moneyTransactionSessionValidator,
                                            PaymentSessionValidator paymentSessionValidator) {
        this.internalPaymentSessionCreateService = internalPaymentSessionCreateService;
        this.jwtTokenBuilder = jwtTokenBuilder;
        this.cardFinder = cardFinder;
        this.sessionConfig = sessionConfig;
        this.cardValidator = cardValidator;
        this.paymentSessionRepository = paymentSessionRepository;
        this.accountFinder = accountFinder;
        this.accountValidator = accountValidator;
        this.moneyTransactionSessionValidator = moneyTransactionSessionValidator;
        this.paymentSessionValidator = paymentSessionValidator;
    }


    public Map<UUID,Card> getCardBaseAndTarget(final UUID cardId,
                                               final UUID targetCardId) {
        if (cardId == null && targetCardId == null) {
            return Collections.emptyMap();
        }

        return cardFinder.findOperationalByIdIn(
                Set.of(cardId, targetCardId)
        );
    }

    private PaymentPrincipal getPaymentPrincipal(final UUID cardId,
                                                 final UUID accountId,
                                                 final UUID customerId,
                                                 final Map<UUID, Card> cardMap) {

        Card card = cardMap.get(cardId);
        Account account = accountId == null
                ? null
                : accountFinder.findOperationalById(accountId);

        if (card != null) {
            cardValidator.validateCardBelongToCustomer(card, customerId);
            account = card.getAccount();
        }

        accountValidator.validateAccountBelongToCustomer(account,
                customerId);

        return new PaymentPrincipal(card, account);
    }

    public PaymentTarget getPaymentTarget(final UUID targetCardId,
                                          final String targetAccountNumber,
                                          final Map<UUID, Card> cardMap) {
        Card targetCard = cardMap.get(targetCardId);
        Account targetAccount = targetAccountNumber == null
                ? null
                : accountFinder.findOperationalByAccountNumber(targetAccountNumber);

        if (targetCard != null) {
            targetAccount = targetCard.getAccount();
        }

        return new PaymentTarget(targetCard, targetAccount);
    }


    @Override
    public String requestPaymentSession(final PaymentSessionRequestDTO dto,
                                        final String deviceIp) {
        ValidatedTransactionSessionRequestData validatedResult = moneyTransactionSessionValidator
                .validateSessionRequestDTO(dto);

        UUID cardId = dto.getCardId();
        UUID accountId = dto.getAccountId();
        UUID customerId = CurrentUser.getCurrentUserCustomerId();
        User user = validatedResult.currentUser();
        BigDecimal amount = dto.getAmount();

        UUID targetCardId = dto.getTargetCardId();
        String targetAccountNumber = dto.getTargetAccountNumber();

        Map<UUID, Card> cardMap = getCardBaseAndTarget(cardId, targetCardId);

        PaymentPrincipal paymentPrincipal = getPaymentPrincipal(cardId, accountId, customerId, cardMap);
        PaymentTarget paymentTarget = getPaymentTarget(targetCardId, targetAccountNumber, cardMap);

        paymentSessionValidator.validatePaymentAndTargetPrincipal(paymentPrincipal,
                paymentTarget);

        SessionExpiry sessionExpiry = sessionConfig.getPaymentSessionExpiry();
        Instant expiry = sessionExpiry.asInstant();
        LocalDateTime expiryAsLocalDateTime = sessionExpiry.asLocalDateTime();


        PaymentSession paymentSession = internalPaymentSessionCreateService.createPaymentSession(
                user,
                amount,
                deviceIp,
                paymentPrincipal.card(),
                paymentPrincipal.account(),
                paymentTarget.targetCard(),
                paymentTarget.targetAccount(),
                expiryAsLocalDateTime
        );

        paymentSessionRepository.revokeAllPreviousPaymentSession(LocalDateTime.now(),
                user.getUserId(), paymentSession.getPaymentSessionId());

        return jwtTokenBuilder.generatePaymentToken(
                user,
                paymentSession.getPaymentSessionId(),
                expiry,
                paymentSession.getTokenType(),
                amount,
                cardId,
                targetCardId
        );
    }

}
