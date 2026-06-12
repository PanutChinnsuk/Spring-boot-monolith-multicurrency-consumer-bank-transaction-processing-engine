package com.project.banksystem.api.restcontroller.cardcontact;

import com.project.banksystem.api.request.wrapper.cardcontact.CardContactAddRequest;
import com.project.banksystem.api.request.wrapper.cardcontact.CardContactReferenceCodeRequest;
import com.project.banksystem.api.restcontroller.RestControllerResponder;
import com.project.banksystem.application.orchestrator.cardcontact.CardContactOrchestrator;
import com.project.banksystem.application.orchestrator.cardcontact.CardContactReferenceCodeOrchestrator;
import com.project.banksystem.core.response.Result;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAllRoles;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/card/contact")
@AllowedAllRoles
public class CardContactController {
    private final CardContactOrchestrator cardContactOrchestrator;

    public CardContactController(CardContactOrchestrator cardContactOrchestrator) {
        this.cardContactOrchestrator = cardContactOrchestrator;
    }


    @PostMapping("/add")
    public ResponseEntity<Result> addCard(@RequestBody @Valid
                                                           CardContactAddRequest r) {
        return RestControllerResponder.successResponse(
                cardContactOrchestrator.addCardContact(
                        r.getCode()
                )
        );
    }
}
