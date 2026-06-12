package com.project.banksystem.application.backend.account;

import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AccountNumberGenerator {
    private final AccountRepository accountRepository;
    private final SecureRandom secureRandom;

    public AccountNumberGenerator(AccountRepository accountRepository,
                                  SecureRandom secureRandom) {
        this.accountRepository = accountRepository;
        this.secureRandom = secureRandom;
    }

    private final int MAX_ACCOUNT_NUMBER_GENERATE_ATTEMPTS = 5;

    private String random() {
        long seq = 100_000_000_000L + (long)(secureRandom.nextDouble() * 900_000_000_000L);
        return String.valueOf(seq);
    }

    public String generateAccountNumber() {
        int attempt = 0;

        while (attempt < MAX_ACCOUNT_NUMBER_GENERATE_ATTEMPTS) {
            attempt++;
            String value = random();
            if (!accountRepository.existsByAccountNumber(value)) {
                return value;
            }
        }

        throw new IllegalStateException("Failed to generate bank account number.");

    }
}
