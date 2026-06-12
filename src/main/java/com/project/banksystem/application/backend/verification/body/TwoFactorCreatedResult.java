package com.project.banksystem.application.backend.verification.body;

import com.project.banksystem.application.backend.auth.body.AuthResponseBody;
import com.project.banksystem.application.backend.verification.internal.body.TwoFactorResponseBody;
import com.project.banksystem.core.enums.auth.AuthenticationType;

import java.util.Map;

public class TwoFactorCreatedResult {
    private String twoFactorVerificationJwtToken;

    public TwoFactorCreatedResult(String twoFactorVerificationJwtToken) {
        this.twoFactorVerificationJwtToken = twoFactorVerificationJwtToken;
    }

    private Map<String, String> getValues() {
        return Map.of(
                "TwoFactorVerificationToken", twoFactorVerificationJwtToken
        );
    }

    public AuthResponseBody toAuthResponseBody() {
        return new AuthResponseBody(AuthenticationType.TWO_FACTOR, getValues());
    }

    public TwoFactorResponseBody toTwoFactorResponseBody() {
        return new TwoFactorResponseBody(AuthenticationType.TWO_FACTOR, getValues());
    }

    public String getTwoFactorVerificationJwtToken() {
        return twoFactorVerificationJwtToken;
    }
}
