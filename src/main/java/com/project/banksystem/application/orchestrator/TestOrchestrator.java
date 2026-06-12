package com.project.banksystem.application.orchestrator;

import com.project.banksystem.application.aspect.auditing.anno.methodtrigger.Audit;
import com.project.banksystem.application.aspect.auditing.anno.parameteridentifier.PureId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TestOrchestrator {



    @Transactional
    @Audit(action = "Testing", entity = "Zero", alwaysAudit = true)
    public void soAss(@PureId UUID id) {
       // throw new SystemMisusedException("ass");
        System.out.println("Called");
    }
}
