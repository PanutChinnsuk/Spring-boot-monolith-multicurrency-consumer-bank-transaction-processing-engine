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
@Pattern(regexp = "\\d+")
@Size(min = 6, max = 6)
public @interface Pin {
    String message() default "Pin must be digit only.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


}
