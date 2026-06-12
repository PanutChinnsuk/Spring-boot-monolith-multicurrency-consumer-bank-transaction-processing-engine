package com.project.banksystem.application.backend.branch.validation;

import com.project.banksystem.api.request.dto.branch.BranchCreateDTO;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.repository.BranchRepository;
import org.springframework.stereotype.Component;

@Component
public class BranchValidator {
    private final BranchRepository branchRepository;

    public BranchValidator(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }


    public void validateCanCreate(final BranchCreateDTO branchCreateDTO) {
        if (branchRepository.existsByBranchNameOrBranchCode(
                branchCreateDTO.getBranchName(),
                branchCreateDTO.getBranchCode()
        )) {
            throw new BusinessException("Branch name or branch code already exist.");
        }
    }
}
