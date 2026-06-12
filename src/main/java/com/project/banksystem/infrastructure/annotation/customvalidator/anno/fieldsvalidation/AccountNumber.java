package com.project.banksystem.infrastructure.annotation.customvalidator.anno.fieldsvalidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ReportAsSingleViolation
@NotBlank
@Size(min = 12, max = 12)
@Pattern(regexp = "^[1-9][0-9]*$")
public @interface AccountNumber {
    String message() default "Account number must be number only.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
