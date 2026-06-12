package com.project.banksystem.application.backend.fxrate;

import com.project.banksystem.api.response.read.adminres.fxrate.FxRateAdminResponse;
import com.project.banksystem.infrastructure.model.FxRate;

public final class FxRateResponseMapper {
    private FxRateResponseMapper() {

    }


    public static final FxRateAdminResponse toAdmin(final FxRate fxRate) {
        return FxRateAdminResponse.builder()
                .fxId(fxRate.getFxId())
                .baseCurrency(fxRate.getBaseCurrency())
                .quoteCurrency(fxRate.getQuoteCurrency())
                .bidRate(fxRate.getBidRate())
                .askRate(fxRate.getAskRate())
                .midRate(fxRate.getMidRate())
                .timestamp(fxRate.getTimestamp())
                .build();
    }
}
