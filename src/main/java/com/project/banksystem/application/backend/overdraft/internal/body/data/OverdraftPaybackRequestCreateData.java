package com.project.banksystem.application.backend.overdraft.internal.body.data;

import com.project.banksystem.infrastructure.model.OverdraftedAccount;

public class OverdraftPaybackRequestCreateData {
    private OverdraftedAccount overdraftedAccount;

    public OverdraftPaybackRequestCreateData(OverdraftedAccount overdraftedAccount) {
        this.overdraftedAccount = overdraftedAccount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        OverdraftedAccount overdraftedAccount;

        public Builder overdraftedAccount(OverdraftedAccount overdraftedAccount) {
            this.overdraftedAccount = overdraftedAccount;
            return this;
        }

        public OverdraftPaybackRequestCreateData build() {
            if (overdraftedAccount == null) {
                throw new IllegalStateException("Overdrafted account is null.");
            }

            return new OverdraftPaybackRequestCreateData(overdraftedAccount);
        }
    }



    public OverdraftedAccount getOverdraftedAccount() {
        return overdraftedAccount;
    }
}
