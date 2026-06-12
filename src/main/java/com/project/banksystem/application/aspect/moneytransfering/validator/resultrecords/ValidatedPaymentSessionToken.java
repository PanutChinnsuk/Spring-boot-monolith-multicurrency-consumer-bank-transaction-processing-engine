package com.project.banksystem.application.aspect.moneytransfering.validator.resultrecords;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

public record ValidatedPaymentSessionToken(Claims tokenBody,
                                           String token,
                                           String deviceIp,
                                           HttpServletRequest request) {
}
