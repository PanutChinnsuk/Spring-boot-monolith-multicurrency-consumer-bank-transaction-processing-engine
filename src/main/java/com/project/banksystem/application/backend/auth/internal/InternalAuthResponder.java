package com.project.banksystem.application.backend.auth.internal;

import com.project.banksystem.application.backend.accesssession.body.AccessSessionCreatedResult;
import com.project.banksystem.application.backend.accesssession.internal.InternalAccessSessionCreateService;
import com.project.banksystem.application.backend.auth.body.AuthResponseBody;
import com.project.banksystem.application.backend.verification.twofactorcode.internal.InternalTwoFactorCodeCreateService;
import com.project.banksystem.core.enums.auth.AuthenticationType;
import com.project.banksystem.infrastructure.model.User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InternalAuthResponder {
    private final InternalAccessSessionCreateService internalAccessSessionCreateService;
    private final InternalTwoFactorCodeCreateService internalTwoFactorCodeCreateService;

    public InternalAuthResponder(InternalAccessSessionCreateService internalAccessSessionCreateService,
                                 InternalTwoFactorCodeCreateService internalTwoFactorCodeCreateService) {
        this.internalAccessSessionCreateService = internalAccessSessionCreateService;
        this.internalTwoFactorCodeCreateService = internalTwoFactorCodeCreateService;
    }

    public AuthResponseBody loginSuccessResponse(final User user,
                                           final String deviceIp) {
        AccessSessionCreatedResult result = internalAccessSessionCreateService
                .createAccessSession(user, deviceIp);

        Map<String,String> responseValues = Map.of(
                "AccessSessionToken", result.accessSessionJwtToken(),
                "RefreshToken", result.refreshJwtToken()
        );


        return new AuthResponseBody(AuthenticationType.LOGIN, responseValues);
    }



}
