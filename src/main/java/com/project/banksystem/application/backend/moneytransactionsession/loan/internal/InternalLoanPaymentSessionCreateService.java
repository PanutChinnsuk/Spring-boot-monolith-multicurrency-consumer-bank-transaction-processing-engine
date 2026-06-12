package com.project.banksystem.application.backend.moneytransactionsession.loan.internal;

import com.project.banksystem.application.backend.moneytransactionsession.builder.MoneyTransactionSessionEntityBuilder;
import com.project.banksystem.application.backend.moneytransactionsession.loan.LoanPaymentSessionManager;
import com.project.banksystem.application.backend.moneytransactionsession.overdraft.OverdraftPaymentSessionManager;
import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Loan;
import com.project.banksystem.infrastructure.model.OverdraftedAccount;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.LoanPaymentSession;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.OverdraftPaymentSession;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.entitybuilder.MoneyTransactionSessionEntityBuilderBody.Builder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class InternalLoanPaymentSessionCreateService {
    private final LoanPaymentSessionManager loanPaymentSessionManager;
    private final MoneyTransactionSessionEntityBuilder moneyTransactionSessionEntityBuilder;

    public InternalLoanPaymentSessionCreateService(LoanPaymentSessionManager loanPaymentSessionManager,
                                                   MoneyTransactionSessionEntityBuilder moneyTransactionSessionEntityBuilder) {
        this.loanPaymentSessionManager = loanPaymentSessionManager;
        this.moneyTransactionSessionEntityBuilder = moneyTransactionSessionEntityBuilder;
    }

    public LoanPaymentSession createPaymentSession(final User user,
                                                        final BigDecimal paybackAmount,
                                                        final String deviceIp,
                                                        final Loan loan,
                                                        final LocalDateTime expiryAsLocalDateTime,
                                                        final Account account) {
        Builder defaultBuilder =
                moneyTransactionSessionEntityBuilder.getDefaultBuilder(user, paybackAmount,
                        deviceIp, expiryAsLocalDateTime);

        return loanPaymentSessionManager.save(new LoanPaymentSession(defaultBuilder
                        .loan(loan)
                        .account(account)
                        .tokenType(TokenType.ACCOUNT_LOAN_PAYMENT)
                        .build()));
    }
}
