package com.project.banksystem.application.aspect.moneytransfering.payment;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.aspect.moneytransfering.AspectParameterFinder;
import com.project.banksystem.application.aspect.moneytransfering.resultrecords.AspectParameterResult;
import com.project.banksystem.application.aspect.moneytransfering.validator.PaymentSessionTokenAspectValidator;
import com.project.banksystem.application.aspect.moneytransfering.validator.resultrecords.ValidatedPaymentSessionToken;
import com.project.banksystem.application.backend.banktransaction.cardtransaction.body.CardTransactionTemplate;
import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.application.aspect.moneytransfering.payment.anno.methodtrigger.SetCardIdAndAmount;
import com.project.banksystem.application.aspect.moneytransfering.payment.body.ResolvedCardPaymentDataFromPaymentToken;
import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.security.session.jwt.validator.JwtValidator;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;


@Aspect
@Component
public class CardTransferTemplateResolverAspect {
    private final JwtValidator jwtValidator;
    private final PaymentSessionTokenAspectValidator paymentSessionTokenAspectValidator;

    public CardTransferTemplateResolverAspect(JwtValidator jwtValidator,
                                              PaymentSessionTokenAspectValidator paymentSessionTokenAspectValidator) {
        this.jwtValidator = jwtValidator;
        this.paymentSessionTokenAspectValidator = paymentSessionTokenAspectValidator;
    }


    private ResolvedCardPaymentDataFromPaymentToken getResolvedValues(final Claims tokenBody) {
        Object resolvedCardId = tokenBody.get("resolvedCardId");
        Object resolvedAmount = tokenBody.get("resolvedAmount");
        Object resolvedTargetCardId = tokenBody.get("resolvedTargetCardId");

        boolean allAreNull = resolvedCardId == null ||
                resolvedAmount == null ||
                resolvedTargetCardId == null;

        if (allAreNull) {
            throw new Forbidden("Required claims does not exist" +
                    " from token.");
        }

        UUID cardId = UUID.fromString(resolvedCardId.toString());
        UUID targetCardId = UUID.fromString(resolvedTargetCardId.toString());

        BigDecimal amount;
        if (resolvedAmount instanceof Number n) {
            amount = new BigDecimal(n.toString());
        }else {
            throw new Forbidden("Invalid type to convert as big decimal.");
        }

        return new ResolvedCardPaymentDataFromPaymentToken(cardId, amount, targetCardId);
    }

    @Around("@annotation(setCardIdAndAmount)")
    public Object setCardIdAndAmountBeforeConsume(ProceedingJoinPoint joinPoint,
                                                  SetCardIdAndAmount setCardIdAndAmount) throws Throwable {
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
        Object[] parameters = joinPoint.getArgs();

        jwtValidator.validateMatchedSessionTokenType(tokenBody, TokenType.CARD_PAYMENT);

        AspectParameterResult<CardTransactionTemplate> result = AspectParameterFinder
                .getParameterWithIndex(CardTransactionTemplate.class, parameters);

        CardTransactionTemplate template = result.expectedClass();

        ResolvedCardPaymentDataFromPaymentToken values = getResolvedValues(tokenBody);
        template.setValue(values.cardId(),
                values.amount(),
                values.targetCardId());

        parameters[result.parameterIndex()] = template;

        return joinPoint.proceed();
    }
}
