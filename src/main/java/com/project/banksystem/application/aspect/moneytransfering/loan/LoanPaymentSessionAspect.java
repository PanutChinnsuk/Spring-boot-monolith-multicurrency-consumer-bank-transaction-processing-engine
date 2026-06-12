package com.project.banksystem.application.aspect.moneytransfering.loan;


import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.aspect.moneytransfering.AspectParameterFinder;
import com.project.banksystem.application.aspect.moneytransfering.PaymentSessionRevokeHandling;
import com.project.banksystem.application.aspect.moneytransfering.loan.anno.methodtrigger.ConsumeLoanPaymentSession;
import com.project.banksystem.application.aspect.moneytransfering.resultrecords.AspectParameterResult;
import com.project.banksystem.application.aspect.moneytransfering.validator.PaymentSessionTokenAspectValidator;
import com.project.banksystem.application.aspect.moneytransfering.validator.resultrecords.ValidatedPaymentSessionToken;
import com.project.banksystem.application.backend.loan.loanpayment.body.LoanPaymentTemplate;
import com.project.banksystem.application.backend.moneytransactionsession.loan.query.LoanPaymentSessionFinder;
import com.project.banksystem.application.backend.overdraft.body.OverdraftPaybackTemplate;
import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.LoanPaymentSession;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.OverdraftPaymentSession;
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
public class LoanPaymentSessionAspect {
    private final LoanPaymentSessionFinder loanPaymentSessionFinder;
    private final JwtValidator jwtValidator;
    private final PaymentSessionTokenAspectValidator paymentSessionTokenAspectValidator;
    private final PaymentSessionRevokeHandling paymentSessionRevokeHandling;

    public LoanPaymentSessionAspect(LoanPaymentSessionFinder loanPaymentSessionFinder,
                                    JwtValidator jwtValidator,
                                    PaymentSessionTokenAspectValidator paymentSessionTokenAspectValidator,
                                    PaymentSessionRevokeHandling paymentSessionRevokeHandling) {
        this.loanPaymentSessionFinder = loanPaymentSessionFinder;
        this.jwtValidator = jwtValidator;
        this.paymentSessionTokenAspectValidator = paymentSessionTokenAspectValidator;
        this.paymentSessionRevokeHandling = paymentSessionRevokeHandling;
    }


    @Around("@annotation(consumeLoanPaymentSession)")
    public Object consumeOverdraftPaymentSession(ProceedingJoinPoint joinPoint,
                                                 ConsumeLoanPaymentSession consumeLoanPaymentSession) throws Throwable {
        if (CurrentUser.isUserSystemAdministrator()) {
            return joinPoint.proceed();
        }

        ValidatedPaymentSessionToken validated = paymentSessionTokenAspectValidator
                .getValidatedToken("LoanPaymentToken");
        Claims tokenBody = validated.tokenBody();
        String token = validated.token();
        String deviceIp = validated.deviceIp();
        HttpServletRequest request = validated.request();
        UUID sessionId = UUID.fromString(tokenBody.getId());
        Object[] parameters = joinPoint.getArgs();

        LoanPaymentSession session = loanPaymentSessionFinder
                .findForPaybackVerification(sessionId);
        jwtValidator.validateMatchedSessionTokenType(tokenBody,
                session.getTokenType());

        paymentSessionTokenAspectValidator.validateAndBlacklistOnThrow(session, deviceIp,
                token);

        AspectParameterResult<LoanPaymentTemplate> result = AspectParameterFinder
                .getParameterWithIndex(LoanPaymentTemplate.class, parameters);

        LoanPaymentTemplate template = result.expectedClass();

        template.resolve(session.getLoan(),
                session.getAccount(),
                session.getAmount());

        parameters[result.parameterIndex()] = template;

        paymentSessionRevokeHandling.consumePaymentSession(session, token);

        return joinPoint.proceed();
    }
}
