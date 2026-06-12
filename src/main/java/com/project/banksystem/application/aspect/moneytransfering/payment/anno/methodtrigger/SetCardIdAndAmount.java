package com.project.banksystem.application.aspect.moneytransfering.payment.anno.methodtrigger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SetCardIdAndAmount {
}
