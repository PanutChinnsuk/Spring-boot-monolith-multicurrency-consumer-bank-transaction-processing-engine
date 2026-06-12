package com.project.banksystem.application.backend.branch;

import com.project.banksystem.infrastructure.model.Branch;
import com.project.banksystem.infrastructure.repository.BranchRepository;
import org.springframework.stereotype.Component;

@Component
public class BranchManager {
    private final BranchRepository branchRepository;

    public BranchManager(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Branch save(final Branch branch) {
        return branchRepository.save(branch);
    }
}
