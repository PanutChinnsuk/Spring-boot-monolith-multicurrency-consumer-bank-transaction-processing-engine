package com.project.banksystem.application.backend.cardcontact.code.validator;

import com.project.banksystem.application.backend.card.validator.CardValidator;
import com.project.banksystem.application.backend.user.validator.UserValidator;
import com.project.banksystem.application.entityvalidator.CommonEntityFieldValidator;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.CardContactReferenceCode;
import org.springframework.stereotype.Component;

@Component
public class CardContactReferenceCodeValidator {
    private final CardValidator cardValidator;
    private final UserValidator userValidator;

    public CardContactReferenceCodeValidator(CardValidator cardValidator,
                                             UserValidator userValidator) {
        this.cardValidator = cardValidator;
        this.userValidator = userValidator;
    }

    public void validateOperational(final CardContactReferenceCode code) {
        cardValidator.validateCardOperational(code.getCard());
        userValidator.validateUserNotTerminated(code.getUser());

        boolean revoked = code.getRevoked();
        boolean expired = CommonEntityFieldValidator
                .isLocalDateTimeExpired(code.getExpiresAt());


        if (revoked || expired) {
            throw new BusinessException("Invalid card reference code.");
        }
    }
}
