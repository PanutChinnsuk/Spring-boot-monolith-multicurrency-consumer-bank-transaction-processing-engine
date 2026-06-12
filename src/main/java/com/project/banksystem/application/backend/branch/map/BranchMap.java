package com.project.banksystem.application.backend.branch.map;

import com.project.banksystem.core.enums.branch.BranchNameKey;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;

import java.util.Map;

public final class BranchMap {
    private BranchMap() {

    }

    private static final Map<BranchNameKey, String> branchNames = Map.of(
            BranchNameKey.TH_CENTRAL_BANK, "Thailand Central Bank",
            BranchNameKey.CA_CENTRAL_BANK, "Canada Central Bank"
    );

    public static final String getBranchNameByBranchNameKey(final BranchNameKey branchNameKey) {
        String branchName = branchNames.get(branchNameKey);

        if (branchName == null) {
            throw new NotFoundException("Branch name not found by key.");
        }

        return branchName;
    }
}
