package com.project.banksystem.application.backend.verification.internal.body;

import com.project.banksystem.core.enums.auth.AuthenticationType;

import java.util.Map;

public class TwoFactorResponseBody {
    private AuthenticationType authenticationType;
    private Map<String, String> values;

    public TwoFactorResponseBody(AuthenticationType authenticationType,
                                 Map<String, String> values) {
        this.authenticationType = authenticationType;
        this.values = values;
    }

    public AuthenticationType getAuthenticationType() {
        return authenticationType;
    }

    public Map<String, String> getValues() {
        return values;
    }
}
