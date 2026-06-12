package com.project.banksystem.infrastructure.annotation.clarification;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RequiredForAdmin {
}
