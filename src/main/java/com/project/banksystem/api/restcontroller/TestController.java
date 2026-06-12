package com.project.banksystem.api.restcontroller;


import com.project.banksystem.api.request.wrapper.banktransaction.accounttransaction.AdminCustomAccountTransactionRequest;
import com.project.banksystem.application.orchestrator.TestOrchestrator;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAllRoles;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/test")
@Validated
@AllowedAllRoles
public class TestController {
    private final TestOrchestrator testOrchestrator;

    public TestController(TestOrchestrator testOrchestrator) {
        this.testOrchestrator = testOrchestrator;
    }



    @PostMapping("/status")
    public ResponseEntity<String> test() {
        testOrchestrator.soAss(UUID.randomUUID());
        return ResponseEntity.ok("Active");
    }

    @PostMapping("/bs")
    public ResponseEntity<String> bs(@RequestBody @Valid AdminCustomAccountTransactionRequest r) {
        return ResponseEntity.ok("ass");
    }
}
