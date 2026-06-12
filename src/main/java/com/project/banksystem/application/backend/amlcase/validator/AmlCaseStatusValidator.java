package com.project.banksystem.application.backend.amlcase.validator;

import com.project.banksystem.core.enums.aml.AmlCaseStatus;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.AmlCase;

import java.util.EnumSet;

public final class AmlCaseStatusValidator {
    private AmlCaseStatusValidator() {

    }

    private static final EnumSet<AmlCaseStatus> notUpdatableAmlCaseStatus = EnumSet.of(
            AmlCaseStatus.CLOSED,
            AmlCaseStatus.OPEN
    );

    public static void assertUpdateStatus(final AmlCase amlCase,
                                        final AmlCaseStatus amlCaseStatus) {
        if (notUpdatableAmlCaseStatus.contains(amlCaseStatus)) {
            throw new BusinessException("Aml case status is prohibited.");
        }
        if (amlCase.getAmlCaseStatus() == amlCaseStatus) {
            throw new BusinessException("Aml case already have this aml case status.");
        }
    }

    public static void assertClose(final AmlCase amlCase) {
        if (amlCase.getAmlCaseStatus() == AmlCaseStatus.CLOSED) {
            throw new BusinessException("Aml case is already closed.");
        }
    }


    public static void assertReinstate(final AmlCase amlCase) {
        if (amlCase.getAmlCaseStatus() != AmlCaseStatus.CLOSED) {
            throw new BusinessException("Aml case is not closed to be reinstate.");
        }
    }

}
