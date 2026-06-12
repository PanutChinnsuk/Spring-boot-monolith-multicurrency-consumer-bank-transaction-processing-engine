package com.project.banksystem.application.backend.cardcontact.code.service;

import com.project.banksystem.infrastructure.model.CardContactReferenceCode;

import java.util.UUID;

public interface CardContactReferenceCodeRequestService {
    CardContactReferenceCode requestReferenceCode(UUID cardId);
}
