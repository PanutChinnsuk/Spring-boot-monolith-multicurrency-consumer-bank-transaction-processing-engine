package com.project;

import com.project.banksystem.BankSystemApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest(classes = {BankSystemApplication.class})
@ActiveProfiles("dev")
@Transactional
public abstract class DefaultTest {


}
