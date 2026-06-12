package com.project.banksystem.application.backend.overdraft.internal.line;

import com.project.banksystem.infrastructure.model.OverdraftPaybackLine;
import com.project.banksystem.infrastructure.repository.OverdraftPaybackLineRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OverdraftPaybackLineManager {
    private final OverdraftPaybackLineRepository overdraftPaybackLineRepository;

    public OverdraftPaybackLineManager(OverdraftPaybackLineRepository overdraftPaybackLineRepository) {
        this.overdraftPaybackLineRepository = overdraftPaybackLineRepository;
    }

    public List<OverdraftPaybackLine> saveAll(List<OverdraftPaybackLine> overdraftPaybackLines) {
        return overdraftPaybackLineRepository.saveAll(overdraftPaybackLines);
    }
}
