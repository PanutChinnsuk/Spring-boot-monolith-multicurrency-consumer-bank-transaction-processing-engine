package com.project.banksystem.application.backend.overdraft.internal.line.validator;

import com.project.banksystem.application.backend.overdraft.internal.body.data.OverdraftPaybackLineCreateData;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftPaybackLineDirection;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftPaybackRole;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.exception.ex.SystemMisusedException;
import com.project.banksystem.infrastructure.model.OverdraftPaybackLine;
import com.project.banksystem.infrastructure.model.OverdraftedAccount;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class OverdraftPaybackLineValidator {

    private final EnumSet<OverdraftPaybackLineDirection> MUST_PROVIDED = EnumSet.of(
            OverdraftPaybackLineDirection.PAYBACK,
            OverdraftPaybackLineDirection.GAIN
    );

    private Set<UUID> getDirectionSide(final List<OverdraftPaybackLineCreateData> createDatas,
                                       final OverdraftPaybackLineDirection direction) {
        return createDatas.stream()
                .filter(cd -> cd.getOverdraftPaybackLineDirection() ==
                        direction)
                .map(cd -> cd.getAccount().getAccountId())
                .collect(Collectors.toSet());
    }

    public void validateOnlyOnePaybackPrincipal(final List<OverdraftPaybackLineCreateData> createDatas) {
        long count = createDatas.stream()
                .filter(cd -> cd.getOverdraftPaybackRole()
                == OverdraftPaybackRole.PAYBACK_PRINCIPAL).limit(2).count();

        if (count != 1) {
            throw new BusinessException("Payback must have only one payback principal account.");
        }

    }

    public Map<OverdraftPaybackLineDirection, BigDecimal> validateNet(final List<OverdraftPaybackLineCreateData> createDatas) {
        Map<OverdraftPaybackLineDirection, BigDecimal> netMap =
                createDatas.stream().collect(Collectors.toMap(
                        cd -> cd.getOverdraftPaybackLineDirection(),
                        cd -> cd.getPaybackAmount(), BigDecimal::add
                ));

        if (!netMap.keySet().equals(MUST_PROVIDED)) {
            throw new BusinessException("Payback lines must have payback and gain directions.");
        }

        return netMap;
    }

    public void validateTotalPaybackAndGainMatched(final Map<OverdraftPaybackLineDirection, BigDecimal> netMap) {
        BigDecimal totalPayback = netMap.get(OverdraftPaybackLineDirection.PAYBACK);
        BigDecimal totalGain = netMap.get(OverdraftPaybackLineDirection.GAIN);


        if (totalPayback.compareTo(totalGain) != 0) {
            throw new BusinessException("Total payback and gain amount must be equal.");
        }
    }

    public void validateCircularPattern(final List<OverdraftPaybackLineCreateData> createDatas) {
        Set<UUID> paybackSide = getDirectionSide(createDatas,
                OverdraftPaybackLineDirection.PAYBACK);

        Set<UUID> gainSide = getDirectionSide(createDatas,
                OverdraftPaybackLineDirection.GAIN);

        if (!Collections.disjoint(paybackSide, gainSide)) {
            throw new BusinessException("Each side should not have an account in common.");
        }

    }

    public void validatePrincipalIdPaybackMatched(final UUID principalId,
                                                final UUID accountId) {
        if (!principalId.equals(accountId)) {
            throw new BusinessException("Principal payback account id must matched account id from" +
                    " overdrafted account.");
        }
    }


    public void validateTotalGainNotExceeded(final List<OverdraftPaybackLine> lines,
                                             final BigDecimal baseOwnedAmount) {

        BigDecimal totalGain = lines.stream().filter(l ->
                        l.getOverdraftPaybackLineDirection().isGainSide() &&
                        !IgnoredOverdraftPaybackRoles.ignoredRoles.contains(
                                l.getOverdraftPaybackRole()))
                .map(l -> l.getFinalAmount()).reduce(
                        BigDecimal.ZERO, BigDecimal::add);


        if (totalGain.compareTo(baseOwnedAmount) > 0) {
            throw new BusinessException("Total amount of payback gain exceeded owned amount.");
        }

    }
}
