package com.project.banksystem.application.backend.moneytransactionsession.overdraft.impl;

import com.project.banksystem.api.request.dto.paymentsession.OverdraftPaymentSessionRequestDTO;
import com.project.banksystem.application.backend.account.query.AccountFinder;
import com.project.banksystem.application.backend.moneytransactionsession.overdraft.internal.InternalOverdraftPaymentSessionCreateService;
import com.project.banksystem.application.backend.moneytransactionsession.overdraft.service.OverdraftPaymentSessionRequestService;
import com.project.banksystem.application.backend.moneytransactionsession.resultrecords.ValidatedTransactionSessionRequestData;
import com.project.banksystem.application.backend.moneytransactionsession.validator.MoneyTransactionSessionValidator;
import com.project.banksystem.application.backend.overdraft.internal.account.query.OverdraftedAccountFinder;
import com.project.banksystem.application.backend.overdraft.internal.account.validator.OverdraftedAccountValidator;
import com.project.banksystem.infrastructure.model.*;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.OverdraftPaymentSession;
import com.project.banksystem.infrastructure.repository.OverdraftPaymentSessionRepository;
import com.project.banksystem.infrastructure.security.session.jwt.builder.JwtTokenBuilder;
import com.project.banksystem.infrastructure.security.session.properties.SessionConfig;
import com.project.banksystem.infrastructure.security.session.properties.body.SessionExpiry;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;




@Service
public class OverdraftPaymentSessionRequestServiceImpl implements OverdraftPaymentSessionRequestService {
    private final InternalOverdraftPaymentSessionCreateService internalOverdraftPaymentSessionCreateService;
    private final JwtTokenBuilder jwtTokenBuilder;
    private final SessionConfig sessionConfig;
    private final AccountFinder accountFinder;
    private final OverdraftPaymentSessionRepository overdraftPaymentSessionRepository;
    private final MoneyTransactionSessionValidator moneyTransactionSessionValidator;
    private final OverdraftedAccountFinder overdraftedAccountFinder;
    private final OverdraftedAccountValidator overdraftedAccountValidator;


    public OverdraftPaymentSessionRequestServiceImpl(InternalOverdraftPaymentSessionCreateService internalOverdraftPaymentSessionCreateService,
                                                     JwtTokenBuilder jwtTokenBuilder,
                                                     SessionConfig sessionConfig,
                                                     AccountFinder accountFinder,
                                                     OverdraftPaymentSessionRepository overdraftPaymentSessionRepository,
                                                     MoneyTransactionSessionValidator moneyTransactionSessionValidator,
                                                     OverdraftedAccountFinder overdraftedAccountFinder,
                                                     OverdraftedAccountValidator overdraftedAccountValidator) {
        this.internalOverdraftPaymentSessionCreateService = internalOverdraftPaymentSessionCreateService;
        this.jwtTokenBuilder = jwtTokenBuilder;
        this.sessionConfig = sessionConfig;
        this.accountFinder = accountFinder;
        this.overdraftPaymentSessionRepository = overdraftPaymentSessionRepository;
        this.moneyTransactionSessionValidator = moneyTransactionSessionValidator;
        this.overdraftedAccountFinder = overdraftedAccountFinder;
        this.overdraftedAccountValidator = overdraftedAccountValidator;
    }



    @Override
    public String requestPaymentSession(final OverdraftPaymentSessionRequestDTO dto,
                                        final String deviceIp) {
        ValidatedTransactionSessionRequestData validatedResult = moneyTransactionSessionValidator
                .validateSessionRequestDTO(dto);

        OverdraftedAccount overdraftedAccount = overdraftedAccountFinder
                .findById(dto.getOverdraftId());
        overdraftedAccountValidator.validateOverdraftedAccountBelongToCurrentUser(
                overdraftedAccount
        );
        overdraftedAccountValidator.validateOperational(overdraftedAccount);

        User user = validatedResult.currentUser();
        UUID userId = user.getUserId();
        BigDecimal amount = dto.getAmount();
        Account account = overdraftedAccount.getAccount();

        SessionExpiry sessionExpiry = sessionConfig.getPaymentSessionExpiry();
        Instant expiry = sessionExpiry.asInstant();
        LocalDateTime expiryAsLocalDateTime = sessionExpiry.asLocalDateTime();

        OverdraftPaymentSession overdraftPaymentSession = internalOverdraftPaymentSessionCreateService
                .createPaymentSession(
                user,
                amount,
                deviceIp,
                overdraftedAccount,
                expiryAsLocalDateTime
        );

        overdraftPaymentSessionRepository.revokeAllPreviousOverdraftPaymentSession(
                LocalDateTime.now(),
                userId, overdraftPaymentSession.getPaymentSessionId());

        return jwtTokenBuilder.generateOverdraftPaymentToken(
                user,
                overdraftPaymentSession.getPaymentSessionId(),
                expiry,
                overdraftPaymentSession.getTokenType()
        );
    }

}
