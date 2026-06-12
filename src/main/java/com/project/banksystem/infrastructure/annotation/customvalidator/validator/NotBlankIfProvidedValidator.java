package com.project.banksystem.infrastructure.annotation.customvalidator.validator;

import com.project.banksystem.infrastructure.annotation.customvalidator.anno.fieldsvalidation.NotBlankIfProvided;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankIfProvidedValidator implements ConstraintValidator<NotBlankIfProvided, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }

        return !s.isBlank();
    }
}
