package com.project.banksystem.application.backend.card.schedule;

import com.project.banksystem.core.enums.card.CardStatus;
import com.project.banksystem.infrastructure.repository.CardRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class CardStatusSchedule {
    private final CardRepository cardRepository;

    public CardStatusSchedule(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void setPendingApproval() {
        cardRepository.setPendingApproval(CardStatus.ACTIVATION_PENDING,
                LocalDateTime.now(),
                CardStatus.DELIVERED);
    }

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void setExpired() {
        cardRepository.setExpired(CardStatus.EXPIRED, LocalDate.now());
    }

}
