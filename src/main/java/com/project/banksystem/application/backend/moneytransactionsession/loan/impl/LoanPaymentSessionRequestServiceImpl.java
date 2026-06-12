package com.project.banksystem.application.backend.moneytransactionsession.loan.impl;

import com.project.banksystem.api.request.dto.paymentsession.LoanPaymentSessionRequestDTO;
import com.project.banksystem.api.request.dto.paymentsession.OverdraftPaymentSessionRequestDTO;
import com.project.banksystem.application.backend.account.query.AccountFinder;
import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.application.backend.loan.query.LoanFinder;
import com.project.banksystem.application.backend.loan.validator.LoanPaymentValidator;
import com.project.banksystem.application.backend.loan.validator.LoanValidator;
import com.project.banksystem.application.backend.moneytransactionsession.loan.internal.InternalLoanPaymentSessionCreateService;
import com.project.banksystem.application.backend.moneytransactionsession.loan.service.LoanPaymentSessionRequestService;
import com.project.banksystem.application.backend.moneytransactionsession.overdraft.internal.InternalOverdraftPaymentSessionCreateService;
import com.project.banksystem.application.backend.moneytransactionsession.overdraft.service.OverdraftPaymentSessionRequestService;
import com.project.banksystem.application.backend.moneytransactionsession.resultrecords.ValidatedTransactionSessionRequestData;
import com.project.banksystem.application.backend.moneytransactionsession.validator.MoneyTransactionSessionValidator;
import com.project.banksystem.application.backend.overdraft.internal.account.query.OverdraftedAccountFinder;
import com.project.banksystem.application.backend.overdraft.internal.account.validator.OverdraftedAccountValidator;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Loan;
import com.project.banksystem.infrastructure.model.OverdraftedAccount;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.LoanPaymentSession;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.OverdraftPaymentSession;
import com.project.banksystem.infrastructure.repository.LoanPaymentSessionRepository;
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
public class LoanPaymentSessionRequestServiceImpl implements LoanPaymentSessionRequestService {
    private final InternalLoanPaymentSessionCreateService internalLoanPaymentSessionCreateService;
    private final JwtTokenBuilder jwtTokenBuilder;
    private final SessionConfig sessionConfig;
    private final AccountFinder accountFinder;
    private final LoanPaymentSessionRepository loanPaymentSessionRepository;
    private final MoneyTransactionSessionValidator moneyTransactionSessionValidator;
    private final LoanValidator loanValidator;
    private final LoanFinder loanFinder;
    private final LoanPaymentValidator loanPaymentValidator;
    private final AccountValidator accountValidator;

    public LoanPaymentSessionRequestServiceImpl(InternalLoanPaymentSessionCreateService internalLoanPaymentSessionCreateService,
                                                JwtTokenBuilder jwtTokenBuilder,
                                                SessionConfig sessionConfig,
                                                AccountFinder accountFinder,
                                                LoanPaymentSessionRepository loanPaymentSessionRepository,
                                                MoneyTransactionSessionValidator moneyTransactionSessionValidator,
                                                LoanValidator loanValidator,
                                                LoanFinder loanFinder,
                                                LoanPaymentValidator loanPaymentValidator,
                                                AccountValidator accountValidator) {
        this.internalLoanPaymentSessionCreateService = internalLoanPaymentSessionCreateService;
        this.jwtTokenBuilder = jwtTokenBuilder;
        this.sessionConfig = sessionConfig;
        this.accountFinder = accountFinder;
        this.loanPaymentSessionRepository = loanPaymentSessionRepository;
        this.moneyTransactionSessionValidator = moneyTransactionSessionValidator;
        this.loanValidator = loanValidator;
        this.loanFinder = loanFinder;
        this.loanPaymentValidator = loanPaymentValidator;
        this.accountValidator = accountValidator;
    }

    @Override
    public String requestPaymentSession(final LoanPaymentSessionRequestDTO dto,
                                        final String deviceIp) {
        ValidatedTransactionSessionRequestData validatedResult = moneyTransactionSessionValidator
                .validateSessionRequestDTO(dto);

        Account account = accountFinder.findOperationalById(dto.getAccountId());
        accountValidator.validateAccountCustomerIdMatchedCurrentCustomerId(account);

        Loan loan = loanFinder.findForPaymentRequest(dto.getLoanId());
        loanValidator.validateLoanClosed(loan);
        loanPaymentValidator.validateLoanOwnership(loan, account);

        User user = validatedResult.currentUser();
        UUID userId = user.getUserId();
        BigDecimal amount = dto.getAmount();

        SessionExpiry sessionExpiry = sessionConfig.getPaymentSessionExpiry();
        Instant expiry = sessionExpiry.asInstant();
        LocalDateTime expiryAsLocalDateTime = sessionExpiry.asLocalDateTime();

        LoanPaymentSession loanPaymentSession = internalLoanPaymentSessionCreateService
                .createPaymentSession(
                user,
                amount,
                deviceIp,
                loan,
                expiryAsLocalDateTime,
                account
        );

        loanPaymentSessionRepository.revokeAllPreviousLoanPaymentSession(
                LocalDateTime.now(),
                userId, loanPaymentSession.getPaymentSessionId());

        return jwtTokenBuilder.generateLoanPaymentToken(
                user,
                loanPaymentSession.getPaymentSessionId(),
                expiry,
                loanPaymentSession.getTokenType()
        );
    }

}
