package com.project.banksystem.application.aspect.moneytransfering.validator;

import com.project.banksystem.application.aspect.moneytransfering.validator.resultrecords.ValidatedPaymentSessionToken;
import com.project.banksystem.application.backend.banktransaction.body.TransactionContext;
import com.project.banksystem.application.backend.banktransaction.cardtransaction.body.CardTransactionTemplate;
import com.project.banksystem.application.backend.moneytransactionsession.validator.MoneyTransactionSessionValidator;
import com.project.banksystem.core.enums.transaction.TransactionPrincipal;
import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.http.request.HttpServletRequestHelper;
import com.project.banksystem.infrastructure.http.request.HttpServletRequestIpResolver;
import com.project.banksystem.infrastructure.http.request.HttpServletRequestTokenResolver;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.MoneyTransactionSession;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.PaymentSession;
import com.project.banksystem.infrastructure.security.session.caching.caffeine.InMemoryCaffeineJwtTokenBlacklist;
import com.project.banksystem.infrastructure.security.session.jwt.validator.JwtValidator;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class PaymentSessionTokenAspectValidator {
    private final InMemoryCaffeineJwtTokenBlacklist inMemoryCaffeineJwtTokenBlacklist;
    private final JwtValidator jwtValidator;
    private final MoneyTransactionSessionValidator moneyTransactionSessionValidator;
    
    public PaymentSessionTokenAspectValidator(InMemoryCaffeineJwtTokenBlacklist inMemoryCaffeineJwtTokenBlacklist,
                                              JwtValidator jwtValidator,
                                              MoneyTransactionSessionValidator moneyTransactionSessionValidator) {
        this.inMemoryCaffeineJwtTokenBlacklist = inMemoryCaffeineJwtTokenBlacklist;
        this.jwtValidator = jwtValidator;
        this.moneyTransactionSessionValidator = moneyTransactionSessionValidator;
    }
    
    public ValidatedPaymentSessionToken getValidatedToken(final String header) {
        HttpServletRequest request = HttpServletRequestHelper.getCurrentThreadServletRequest();
        String token = HttpServletRequestTokenResolver.getTokenFromHeader(
                request,
                header);

        if (token == null || inMemoryCaffeineJwtTokenBlacklist.isTokenBlacklisted(token)) {
            throw new Forbidden("Invalid payment token.");
        }
        Claims tokenBody = jwtValidator.getValidTokenBody(token);
        String deviceIp = HttpServletRequestIpResolver.getDeviceIpFromRequest(request);
        
        return new ValidatedPaymentSessionToken(tokenBody, token, deviceIp, request);
    }

    public void validateAndBlacklistOnThrow(final MoneyTransactionSession session,
                                            final String deviceIp,
                                            final String token) {
        try {
            moneyTransactionSessionValidator.validateSession(session, deviceIp);
        }catch (Exception e) {
            inMemoryCaffeineJwtTokenBlacklist.blacklistToken(token);
            throw new Forbidden("Invalid payment session.");
        }
    }

    public void validateOnCardPayment(final CardTransactionTemplate template,
                                       final PaymentSession paymentSession) {
        UUID cardId = template.getBaseCardId();
        UUID targetCardId = template.getTargetCardId();
        BigDecimal amount = template.getAmount();

        boolean isCardIdMatched = cardId.equals(paymentSession.getCard().getCardId());
        boolean isTargetCardIdMatched = targetCardId.equals(paymentSession.getTargetCard().getCardId());
        boolean isAmountMatched = amount.compareTo(paymentSession.getAmount()) == 0;

        if (!isCardIdMatched || !isAmountMatched || !isTargetCardIdMatched) {
            throw new Forbidden("Mismatched payment data.");
        }
    }

    public void validateContextMatched(final PaymentSession paymentSession,
                                        final TransactionContext transactionContext) {
        boolean isCardPayment = paymentSession.isCardPaymentSession();
        boolean isAccountPayment = paymentSession.isAccountPaymentSession();

        TransactionPrincipal principal = transactionContext.getTransactionPrincipal();

        if (isCardPayment && principal != TransactionPrincipal.CARD_FOR_TRANSACTION) {
            throw new IllegalStateException("Card payment session must be use for card payment only.");
        }
        if (isAccountPayment && principal != TransactionPrincipal.ACCOUNT_FOR_TRANSACTION) {
            throw new IllegalStateException("Account payment session must be use for account payment only.");
        }
    }




   
}
