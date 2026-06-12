package com.project.banksystem.application.backend.moneytransactionsession.payment.validator;

import com.project.banksystem.api.request.dto.paymentsession.PaymentSessionRequestDTO;
import com.project.banksystem.application.backend.card.validator.CardValidator;
import com.project.banksystem.application.backend.moneytransactionsession.payment.body.PaymentPrincipal;
import com.project.banksystem.application.backend.moneytransactionsession.payment.body.PaymentTarget;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentSessionValidator {
    private final CardValidator cardValidator;

    public PaymentSessionValidator(CardValidator cardValidator) {
        this.cardValidator = cardValidator;
    }




    public void validateCanRequest(final PaymentSessionRequestDTO paymentSessionRequestDTO) {
        UUID cardId = paymentSessionRequestDTO.getCardId();
        UUID accountId = paymentSessionRequestDTO.getAccountId();
        UUID targetCardId = paymentSessionRequestDTO.getTargetCardId();
        String targetAccountNumber = paymentSessionRequestDTO.getTargetAccountNumber();

        boolean bothAreNull = cardId == null && accountId == null;
        boolean bothAreNotNull = cardId != null && accountId != null;

        boolean bothTargetAreNotNull = targetCardId != null && targetAccountNumber != null;
        boolean bothTargetAreNull = targetCardId == null && targetAccountNumber == null;

        boolean onlyTargetCardNotNull = cardId == null && targetCardId != null;
        boolean baseCardAndTargetCardNotNull = cardId != null &&
                targetCardId != null;

        if (bothAreNotNull || bothAreNull) {
            throw new BusinessException("Required only one principal at a time for payment session.");
        }

        if (bothTargetAreNotNull || bothTargetAreNull) {
            throw new BusinessException("Required only one target at a time for payment session.");
        }

        if (onlyTargetCardNotNull) {
            throw new BusinessException("Base card must not be null if target card exist.");
        }

        if (baseCardAndTargetCardNotNull) {
            if (cardId.equals(targetCardId)) {
                throw new BusinessException("Base and target card are the same.");
            }
        }



    }

    public void validatePaymentAndTargetPrincipal(final PaymentPrincipal paymentPrincipal,
                                                  final PaymentTarget paymentTarget) {
        String baseAccountNumber = paymentPrincipal.account().getAccountNumber();
        String targetAccountNumber = paymentTarget.targetAccount().getAccountNumber();

        if (baseAccountNumber.equals(targetAccountNumber)) {
            throw new BusinessException("Base and target account number are the same.");
        }
    }




}
