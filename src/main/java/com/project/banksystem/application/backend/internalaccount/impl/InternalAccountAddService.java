package com.project.banksystem.application.backend.internalaccount.impl;

import com.project.banksystem.api.request.dto.internalaccount.InternalAccountCreateDTO;
import com.project.banksystem.application.backend.account.query.AccountFinder;
import com.project.banksystem.application.backend.branch.query.BranchFinder;
import com.project.banksystem.application.backend.internalaccount.InternalAccountManager;
import com.project.banksystem.application.backend.internalaccount.validator.InternalAccountValidator;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Branch;
import com.project.banksystem.infrastructure.model.InternalAccount;
import org.springframework.stereotype.Service;

@Service
public class InternalAccountAddService {
    private final BranchFinder branchFinder;
    private final AccountFinder accountFinder;
    private final InternalAccountManager internalAccountManager;
    private final InternalAccountValidator internalAccountValidator;

    public InternalAccountAddService(BranchFinder branchFinder,
                                     AccountFinder accountFinder,
                                     InternalAccountManager internalAccountManager,
                                     InternalAccountValidator internalAccountValidator) {
        this.branchFinder = branchFinder;
        this.accountFinder = accountFinder;
        this.internalAccountManager = internalAccountManager;
        this.internalAccountValidator = internalAccountValidator;
    }



    public InternalAccount accountToInternalAccount(final InternalAccountCreateDTO internalAccountCreateDTO) {
        internalAccountValidator.validateCanCreate(internalAccountCreateDTO);

        Branch branch = branchFinder.findById(internalAccountCreateDTO.getBranchCode());
        Account account = accountFinder.findOperationalById(internalAccountCreateDTO.getAccountId());


        return internalAccountManager.save(InternalAccount.builder()
                .account(account)
                .branch(branch)
                .accountPurpose(internalAccountCreateDTO.getAccountPurpose())
                .label(internalAccountCreateDTO.getLabel())
                .internalCode(internalAccountCreateDTO.getInternalCode())
                .build());
    }
}
