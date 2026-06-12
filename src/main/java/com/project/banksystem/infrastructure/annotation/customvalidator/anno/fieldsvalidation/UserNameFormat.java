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
@NotBlank
@Size(min = 3, max = 100)
@Pattern(regexp = "^[A-Za-z0-9]+$")
@ReportAsSingleViolation
public @interface UserNameFormat {
    String message() default "Username format must be english and number only.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
