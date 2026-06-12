package com.project.banksystem.application.backend.moneytransactionsession.validator;

import com.project.banksystem.api.request.dto.paymentsession.PaymentSessionRequestDTO;
import com.project.banksystem.api.request.dto.paymentsession.sharedinterface.MoneyTransactionRequestDTO;
import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.application.backend.card.validator.CardValidator;
import com.project.banksystem.application.backend.moneytransactionsession.payment.validator.PaymentSessionValidator;
import com.project.banksystem.application.backend.moneytransactionsession.resultrecords.ValidatedTransactionSessionRequestData;
import com.project.banksystem.application.backend.overdraft.internal.account.validator.OverdraftedAccountValidator;
import com.project.banksystem.application.backend.user.query.UserFinder;
import com.project.banksystem.application.backend.user.validator.UserValidator;
import com.project.banksystem.application.entityvalidator.CommonEntityFieldValidator;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.*;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.MoneyTransactionSession;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Card;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.OverdraftPaymentSession;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class MoneyTransactionSessionValidator {
    private final CardValidator cardValidator;
    private final AccountValidator accountValidator;
    private final UserValidator userValidator;
    private final PaymentSessionValidator paymentSessionValidator;
    private final UserFinder userFinder;
    private final OverdraftedAccountValidator overdraftedAccountValidator;

    public MoneyTransactionSessionValidator(CardValidator cardValidator,
                                            AccountValidator accountValidator,
                                            UserValidator userValidator,
                                            PaymentSessionValidator paymentSessionValidator,
                                            UserFinder userFinder,
                                            OverdraftedAccountValidator overdraftedAccountValidator) {
        this.cardValidator = cardValidator;
        this.accountValidator = accountValidator;
        this.userValidator = userValidator;
        this.paymentSessionValidator = paymentSessionValidator;
        this.userFinder = userFinder;
        this.overdraftedAccountValidator = overdraftedAccountValidator;
    }


    public ValidatedTransactionSessionRequestData
    validateSessionRequestDTO(final MoneyTransactionRequestDTO dto) {

        if (dto instanceof PaymentSessionRequestDTO psr) {
            paymentSessionValidator.validateCanRequest(psr);
        }

        UUID userId = CurrentUser.getCurrentUserId();

        User user = userFinder.findOperationalById(userId);
        Credential credential = user.getCredential();

        userValidator.validatePinMatched(dto.getPin(), credential);

        return new ValidatedTransactionSessionRequestData(user);

    }

    public void validateSession(final MoneyTransactionSession paymentSession,
                                       final String deviceIp) {
        LocalDateTime expiresAt = paymentSession.getExpiresAt();
        LocalDateTime now = LocalDateTime.now();
        Card card = paymentSession.getCard();
        Account account = paymentSession.getAccount();
        accountValidator.validateAccountCustomerIdMatchedCurrentCustomerId(account);

        boolean revoked = paymentSession.getRevoked();
        boolean expired = CommonEntityFieldValidator
                .isLocalDateTimeExpired(expiresAt);
        boolean ipMatched = paymentSession.getDeviceIp().equals(deviceIp);
        boolean userIdMatched = paymentSession.getUser().getUserId().equals(
                CurrentUser.getCurrentUserId());

        if (card != null) {
            cardValidator.validateCardOperational(card);
        }else if (card == null){
            accountValidator.validateAccountOperational(account);
        }

        if (paymentSession instanceof OverdraftPaymentSession ops) {
            OverdraftedAccount overdraftedAccount = ops.getOverdraftedAccount();
            overdraftedAccountValidator.validateOperational(overdraftedAccount);
            overdraftedAccountValidator.validateOverdraftedAccountBelongToCurrentUser(overdraftedAccount);
        }


        if (revoked || expired || !ipMatched || !userIdMatched) {
            throw new BusinessException("Invalid payment session.");
        }
    }


}
