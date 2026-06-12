package com.project.banksystem.application.aspect.moneytransfering.payment;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.aspect.moneytransfering.AspectParameterFinder;
import com.project.banksystem.application.aspect.moneytransfering.PaymentSessionRevokeHandling;
import com.project.banksystem.application.aspect.moneytransfering.resultrecords.AspectParameterResult;
import com.project.banksystem.application.aspect.moneytransfering.validator.PaymentSessionTokenAspectValidator;
import com.project.banksystem.application.aspect.moneytransfering.validator.resultrecords.ValidatedPaymentSessionToken;
import com.project.banksystem.application.backend.banktransaction.body.TransactionContext;
import com.project.banksystem.application.backend.banktransaction.body.TransferTemplate;
import com.project.banksystem.application.backend.moneytransactionsession.payment.query.PaymentSessionFinder;
import com.project.banksystem.application.aspect.moneytransfering.payment.anno.methodtrigger.ConsumePaymentSession;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.PaymentSession;
import com.project.banksystem.infrastructure.security.session.jwt.validator.JwtValidator;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class PaymentSessionAspect {
    private final PaymentSessionFinder paymentSessionFinder;
    private final PaymentSessionRevokeHandling paymentSessionRevokeHandling;
    private final JwtValidator jwtValidator;
    private final PaymentSessionTokenAspectValidator paymentSessionTokenAspectValidator;

    public PaymentSessionAspect(PaymentSessionFinder paymentSessionFinder,
                                PaymentSessionRevokeHandling paymentSessionRevokeHandling,
                                JwtValidator jwtValidator,
                                PaymentSessionTokenAspectValidator paymentSessionTokenAspectValidator) {
        this.paymentSessionFinder = paymentSessionFinder;
        this.paymentSessionRevokeHandling = paymentSessionRevokeHandling;
        this.jwtValidator = jwtValidator;
        this.paymentSessionTokenAspectValidator = paymentSessionTokenAspectValidator;
    }





    @Around("@annotation(consumePaymentSession)")
    public Object consumePaymentSession(ProceedingJoinPoint joinPoint,
                                        ConsumePaymentSession consumePaymentSession) throws Throwable {
        if (CurrentUser.isUserSystemAdministrator()) {
            return joinPoint.proceed();
        }

        ValidatedPaymentSessionToken validated = paymentSessionTokenAspectValidator
                .getValidatedToken("PaymentToken");
        Claims tokenBody = validated.tokenBody();
        String token = validated.token();
        String deviceIp = validated.deviceIp();
        HttpServletRequest request = validated.request();
        UUID sessionId = UUID.fromString(tokenBody.getId());

        UUID paymentSessionId = UUID.fromString(tokenBody.getId());
        PaymentSession session = paymentSessionFinder.findById(paymentSessionId);
        jwtValidator.validateMatchedSessionTokenType(tokenBody, session.getTokenType());
        paymentSessionTokenAspectValidator.validateAndBlacklistOnThrow(session,
                deviceIp, token);

        Object[] parameters = joinPoint.getArgs();
        TransactionContext transactionContext = AspectParameterFinder
                .getParameterObject(parameters, TransactionContext.class);

        paymentSessionTokenAspectValidator
                .validateContextMatched(session, transactionContext);

        if (session.isCardPaymentSession()) {
            paymentSessionTokenAspectValidator.
                    validateOnCardPayment(transactionContext.getCardTransactionTemplate(),
                            session);
        }

        AspectParameterResult<TransferTemplate> result = AspectParameterFinder
                .getParameterWithIndex(TransferTemplate.class, parameters);

        TransferTemplate currentTemplate = result.expectedClass();
        currentTemplate.resolve(session.getAccount(),
                session.getAmount(),
                session.getTargetAccount());
    
        parameters[result.parameterIndex()] = currentTemplate;

        paymentSessionRevokeHandling.consumePaymentSession(session, token);

        return joinPoint.proceed();
    }

}
