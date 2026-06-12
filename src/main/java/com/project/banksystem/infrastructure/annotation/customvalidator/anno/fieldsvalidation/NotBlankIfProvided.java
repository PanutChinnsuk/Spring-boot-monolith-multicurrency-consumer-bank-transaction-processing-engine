package com.project.banksystem.infrastructure.annotation.customvalidator.anno.fieldsvalidation;

import com.project.banksystem.infrastructure.annotation.customvalidator.validator.NotBlankIfProvidedValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotBlankIfProvidedValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotBlankIfProvided {
    String message() default "Field must not be blank if provided.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
