package com.project.banksystem.api.request.wrapper.twofactor;

import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import jakarta.validation.constraints.NotNull;

public class TwoFactorRequest {
    @NotNull
    private TwoFactorPurpose twoFactorPurpose;

    public TwoFactorRequest() {

    }

    public void setTwoFactorPurpose(TwoFactorPurpose twoFactorPurpose) {
        this.twoFactorPurpose = twoFactorPurpose;
    }

    public TwoFactorPurpose getTwoFactorPurpose() {
        return twoFactorPurpose;
    }
}
