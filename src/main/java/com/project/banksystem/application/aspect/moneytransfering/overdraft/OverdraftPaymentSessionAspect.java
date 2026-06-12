package com.project.banksystem.application.aspect.moneytransfering.overdraft;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.aspect.moneytransfering.AspectParameterFinder;
import com.project.banksystem.application.aspect.moneytransfering.PaymentSessionRevokeHandling;
import com.project.banksystem.application.aspect.moneytransfering.overdraft.anno.methodtrigger.ConsumeOverdraftPaymentSession;
import com.project.banksystem.application.aspect.moneytransfering.resultrecords.AspectParameterResult;
import com.project.banksystem.application.aspect.moneytransfering.validator.PaymentSessionTokenAspectValidator;
import com.project.banksystem.application.aspect.moneytransfering.validator.resultrecords.ValidatedPaymentSessionToken;
import com.project.banksystem.application.backend.moneytransactionsession.overdraft.query.OverdraftPaymentSessionFinder;
import com.project.banksystem.application.backend.overdraft.body.OverdraftPaybackTemplate;
import com.project.banksystem.core.enums.jwt.TokenType;
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
public class OverdraftPaymentSessionAspect {
    private final OverdraftPaymentSessionFinder overdraftPaymentSessionFinder;
    private final JwtValidator jwtValidator;
    private final PaymentSessionTokenAspectValidator paymentSessionTokenAspectValidator;
    private final PaymentSessionRevokeHandling paymentSessionRevokeHandling;

    public OverdraftPaymentSessionAspect(OverdraftPaymentSessionFinder overdraftPaymentSessionFinder,
                                         JwtValidator jwtValidator,
                                         PaymentSessionTokenAspectValidator paymentSessionTokenAspectValidator,
                                         PaymentSessionRevokeHandling paymentSessionRevokeHandling) {
        this.overdraftPaymentSessionFinder = overdraftPaymentSessionFinder;
        this.jwtValidator = jwtValidator;
        this.paymentSessionTokenAspectValidator = paymentSessionTokenAspectValidator;
        this.paymentSessionRevokeHandling = paymentSessionRevokeHandling;
    }



    @Around("@annotation(consumeOverdraftPaymentSession)")
    public Object consumeOverdraftPaymentSession(ProceedingJoinPoint joinPoint,
                                                 ConsumeOverdraftPaymentSession consumeOverdraftPaymentSession) throws Throwable {
        if (CurrentUser.isUserSystemAdministrator()) {
            return joinPoint.proceed();
        }

        ValidatedPaymentSessionToken validated = paymentSessionTokenAspectValidator
                .getValidatedToken("OverdraftPaymentToken");
        Claims tokenBody = validated.tokenBody();
        String token = validated.token();
        String deviceIp = validated.deviceIp();
        HttpServletRequest request = validated.request();
        UUID sessionId = UUID.fromString(tokenBody.getId());
        Object[] parameters = joinPoint.getArgs();

        OverdraftPaymentSession session = overdraftPaymentSessionFinder
                .findForPaybackVerification(sessionId);
        jwtValidator.validateMatchedSessionTokenType(tokenBody,
                session.getTokenType());

        paymentSessionTokenAspectValidator.validateAndBlacklistOnThrow(session, deviceIp,
                token);

        AspectParameterResult<OverdraftPaybackTemplate> result = AspectParameterFinder
                .getParameterWithIndex(OverdraftPaybackTemplate.class, parameters);

        OverdraftPaybackTemplate template = result.expectedClass();

        template.resolve(session.getAmount(),
                session.getAccount(), session.getOverdraftedAccount());

        parameters[result.parameterIndex()] = template;

        paymentSessionRevokeHandling.consumePaymentSession(session, token);
        return joinPoint.proceed();
    }
}
