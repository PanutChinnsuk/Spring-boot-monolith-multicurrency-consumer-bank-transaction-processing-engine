package com.project.banksystem.application.backend.branch.query;

import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.Branch;
import com.project.banksystem.infrastructure.repository.BranchRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BranchFinder {
    private final BranchRepository branchRepository;

    public BranchFinder(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }


    public Branch findById(final String branchCode) {
        return branchRepository.findByBranchCode(branchCode)
                .orElseThrow(() -> new NotFoundException("Branch not found."));
    }
}
