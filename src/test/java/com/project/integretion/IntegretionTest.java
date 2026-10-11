package com.project.integretion;

import com.project.DefaultTest;
import com.project.banksystem.application.backend.account.query.AccountFinder;
import com.project.banksystem.infrastructure.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class IntegretionTest extends DefaultTest {
    @Autowired
    private AccountFinder accountFinder;



    @Test
    public void test() {
        UUID banksAccountId = UUID.fromString("08f31a41-64ec-48dd-9567-ea466dc2255d");
        System.out.println("295252");
        Account account = accountFinder.findOperationalById(banksAccountId);
        assertNotNull(account);
    }
}
