package com.project.banksystem.application.backend.overdraft.internal.line.validator;

import com.project.banksystem.core.enums.overdraftpayback.OverdraftPaybackRole;

import java.util.EnumSet;

public final class IgnoredOverdraftPaybackRoles {
    private IgnoredOverdraftPaybackRoles() {

    }

    public static final EnumSet<OverdraftPaybackRole> ignoredRoles = EnumSet.of(
            OverdraftPaybackRole.PAYBACK_OVERPAID_CLEARING);
}
