package com.project.banksystem.api.restcontroller.accesssession.admin;

import com.project.banksystem.api.request.wrapper.accesssession.RevokeAllSessionsOfUserRequest;
import com.project.banksystem.api.request.wrapper.accesssession.RevokeSessionBySessionIdRequest;
import com.project.banksystem.api.restcontroller.RestControllerResponder;
import com.project.banksystem.application.orchestrator.accesssession.AccessSessionOrchestrator;
import com.project.banksystem.core.response.Result;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAdmin;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAllRoles;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/access-session")
@AllowedAdmin
public class AdminAccessSessionController {
    private final AccessSessionOrchestrator accessSessionOrchestrator;

    public AdminAccessSessionController(AccessSessionOrchestrator accessSessionOrchestrator) {
        this.accessSessionOrchestrator = accessSessionOrchestrator;
    }

    @PostMapping("/revoke-all")
    public ResponseEntity<Result> revokeAllAccessSessionByUserId(@RequestBody @Valid RevokeAllSessionsOfUserRequest r) {
        return RestControllerResponder.successResponse(
                accessSessionOrchestrator.revokeAllAccessSessionByUserId(r.getUserId())
        );
    }

    @PostMapping("/revoke")
    public ResponseEntity<Result> revokeSessionBySessionId(@RequestBody @Valid RevokeSessionBySessionIdRequest r) {
        return RestControllerResponder.successResponse(
                accessSessionOrchestrator.revokeAccessSessionBySessionId(r.getAccessSessionId())
        );
    }



}
