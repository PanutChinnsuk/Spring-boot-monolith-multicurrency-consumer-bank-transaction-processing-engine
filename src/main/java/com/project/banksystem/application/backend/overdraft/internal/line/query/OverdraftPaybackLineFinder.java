package com.project.banksystem.application.backend.overdraft.internal.line.query;

import com.project.banksystem.core.enums.account.AccountStatus;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.OverdraftPaybackLine;
import com.project.banksystem.infrastructure.model.OverdraftPaybackRequest;
import com.project.banksystem.infrastructure.repository.OverdraftPaybackLineRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OverdraftPaybackLineFinder {
    private final OverdraftPaybackLineRepository overdraftPaybackLineRepository;

    public OverdraftPaybackLineFinder(OverdraftPaybackLineRepository overdraftPaybackLineRepository) {
        this.overdraftPaybackLineRepository = overdraftPaybackLineRepository;
    }


    public List<OverdraftPaybackLine> findByOverdraftPaybackRequest
            (final OverdraftPaybackRequest overdraftPaybackRequest,
             final boolean requiredActiveAccounts) {
        List<OverdraftPaybackLine> lines = overdraftPaybackLineRepository
                .findForProcessing(overdraftPaybackRequest.getRequestId());


        if (requiredActiveAccounts) {
            boolean invalid = lines.stream()
                    .anyMatch(a -> a.getAccount().getAccountStatus() !=
                            AccountStatus.ACTIVE);

            if (invalid) {
                throw new BusinessException("One or more account is invalid for overdraft approval.");
            }

        }

        return lines;
    }
}
