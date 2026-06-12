package com.project.banksystem.application.backend.amlcase.query;

import com.project.banksystem.application.backend.amlcase.validator.AmlCaseValidator;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.AmlCase;
import com.project.banksystem.infrastructure.repository.AmlCaseRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AmlCaseFinder {
    private final AmlCaseRepository amlCaseRepository;
    private final AmlCaseValidator amlCaseValidator;

    public AmlCaseFinder(AmlCaseRepository amlCaseRepository,
                         AmlCaseValidator amlCaseValidator) {
        this.amlCaseRepository = amlCaseRepository;
        this.amlCaseValidator = amlCaseValidator;
    }

    private AmlCase id(final UUID amlCaseId) {
        return amlCaseRepository.findByAmlCaseId(amlCaseId)
                .orElseThrow(() -> new NotFoundException("Aml case not found."));
    }

    public AmlCase findById(final UUID amlCaseId) {
        return id(amlCaseId);
    }

    public AmlCase findNonClosedById(final UUID amlCaseId) {
        AmlCase amlCase = id(amlCaseId);
        amlCaseValidator.validateAmlCaseNotClosed(amlCase);

        return amlCase;
    }
}
