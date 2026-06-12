package com.project.banksystem.application.orchestrator.auth;

import com.project.banksystem.api.request.dto.auth.UserLoginDTO;
import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.auth.body.AuthResponseBody;
import com.project.banksystem.application.backend.auth.service.LoginService;
import com.project.banksystem.application.backend.auth.service.LoginTwoFactorVerificationService;
import com.project.banksystem.core.enums.auth.AuthenticationType;
import com.project.banksystem.core.response.DataResult;
import com.project.banksystem.infrastructure.exception.ex.AuthenticationException;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthenticationOrchestrator {
    private final LoginService loginService;
    private final LoginTwoFactorVerificationService loginTwoFactorVerificationService;

    public AuthenticationOrchestrator(LoginService loginService,
                                      LoginTwoFactorVerificationService loginTwoFactorVerificationService) {
        this.loginService = loginService;
        this.loginTwoFactorVerificationService = loginTwoFactorVerificationService;
    }


    @Transactional
    public DataResult<AuthResponseBody> login(final UserLoginDTO userLoginDTO,
                                              final String deviceIp) {
        try {
            AuthResponseBody body = loginService.login(userLoginDTO, deviceIp);
            String message = "Successfully login.";

            if (body.getAuthenticationType() == AuthenticationType.TWO_FACTOR) {
                message = "Successfully requested 2-step authentication.";
            }

            return DataResultBuilder.build(body,
                    message);
        }catch (NotFoundException | AuthenticationException e) {
            throw new AuthenticationException("Username or password is invalid.");
        }


    }

    @Transactional
    public DataResult<AuthResponseBody> verify2FA(final UUID codeId,
                                                  final String verificationCode,
                                                  final UUID userIdFromToken,
                                                  final String deviceIp) {
        try {
            return  DataResultBuilder.build(loginTwoFactorVerificationService
                    .verify2FA(codeId, deviceIp, userIdFromToken, verificationCode),
                    "Successfully login.");
        }catch (NotFoundException e) {
            throw new AuthenticationException("Invalid verification code.");
        }
    }
}
