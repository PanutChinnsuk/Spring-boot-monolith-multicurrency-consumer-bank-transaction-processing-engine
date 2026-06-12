package com.project.banksystem.application.backend.amlcase;

import com.project.banksystem.infrastructure.model.AmlCase;
import com.project.banksystem.infrastructure.repository.AmlCaseRepository;
import org.springframework.stereotype.Component;

@Component
public class AmlCaseManager {
    private final AmlCaseRepository amlCaseRepository;

    public AmlCaseManager(AmlCaseRepository amlCaseRepository) {
        this.amlCaseRepository = amlCaseRepository;
    }

    public AmlCase save(final AmlCase amlCase) {
        return amlCaseRepository.save(amlCase);
    }
}
