package com.project.banksystem.application.backend.cardcontact.code.schedule;

import com.project.banksystem.infrastructure.repository.CardContactReferenceCodeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CardReferenceCodeRevokedClearingSchedule {
    private final CardContactReferenceCodeRepository cardContactReferenceCodeRepository;

    public CardReferenceCodeRevokedClearingSchedule(CardContactReferenceCodeRepository cardContactReferenceCodeRepository) {
        this.cardContactReferenceCodeRepository = cardContactReferenceCodeRepository;
    }


    @Scheduled(fixedDelay = 48 * 60 * 60 * 1000)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void clearRevokedReferenceCodes() {
        cardContactReferenceCodeRepository.deleteAllRevoked();
    }
}
