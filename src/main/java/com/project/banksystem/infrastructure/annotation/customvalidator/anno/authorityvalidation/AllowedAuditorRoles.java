package com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@PreAuthorize("hasRole('AUDITOR')")
public @interface AllowedAuditorRoles {
}
