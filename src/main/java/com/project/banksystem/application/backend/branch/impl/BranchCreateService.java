package com.project.banksystem.application.backend.branch.impl;

import com.project.banksystem.api.request.dto.branch.BranchCreateDTO;
import com.project.banksystem.application.backend.branch.BranchManager;
import com.project.banksystem.application.backend.branch.validation.BranchValidator;
import com.project.banksystem.infrastructure.model.Branch;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BranchCreateService {
    private final BranchManager branchManager;
    private final BranchValidator branchValidator;

    public BranchCreateService(BranchManager branchManager,
                               BranchValidator branchValidator) {
        this.branchManager = branchManager;
        this.branchValidator = branchValidator;
    }




    public Branch createBranch(final BranchCreateDTO branchCreateDTO) {
        branchValidator.validateCanCreate(branchCreateDTO);

        return branchManager.save(Branch.builder()
                .branchName(branchCreateDTO.getBranchName())
                .branchCode(branchCreateDTO.getBranchCode())
                .build());
    }
}
