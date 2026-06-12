package com.project.banksystem.api.restcontroller.cardcontact;

import com.project.banksystem.api.request.wrapper.cardcontact.CardContactReferenceCodeRequest;
import com.project.banksystem.api.restcontroller.RestControllerResponder;
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
@RequestMapping("/api/public/card/contact/refer")
@AllowedAllRoles
public class CardContactReferenceCodeController {
    private final CardContactReferenceCodeOrchestrator cardContactReferenceCodeOrchestrator;

    public CardContactReferenceCodeController(CardContactReferenceCodeOrchestrator cardContactReferenceCodeOrchestrator) {
        this.cardContactReferenceCodeOrchestrator = cardContactReferenceCodeOrchestrator;
    }


    @PostMapping("/me")
    public ResponseEntity<Result> requestCardReferenceCode(@RequestBody @Valid
                                                           CardContactReferenceCodeRequest r) {
        return RestControllerResponder.successResponse(
                cardContactReferenceCodeOrchestrator.requestCardReferenceCode(
                        r.getCardId()
                )
        );
    }
}
