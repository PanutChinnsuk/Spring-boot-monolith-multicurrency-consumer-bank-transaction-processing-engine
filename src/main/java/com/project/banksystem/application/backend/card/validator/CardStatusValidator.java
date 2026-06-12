package com.project.banksystem.application.backend.card.validator;

import com.project.banksystem.core.enums.card.CardStatus;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Card;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;


public final class CardStatusValidator {
    private CardStatusValidator() {

    }

    private static final Map<CardStatus, Set<CardStatus>> transitionMap = Map.of(
            CardStatus.ISSUED, Set.of(CardStatus.APPROVED),
            CardStatus.DELIVERED, Set.of(CardStatus.ISSUED)
    );

    private static final EnumSet<CardStatus> approvablePendingState = EnumSet.of(
            CardStatus.DELIVERED,
            CardStatus.ACTIVATION_PENDING
    );

    private static final EnumSet<CardStatus> restrictedState = EnumSet.of(
            CardStatus.SUSPENDED,
            CardStatus.FROZEN,
            CardStatus.BLOCKED
    );

    private static final EnumSet<CardStatus> terminatedState = EnumSet.of(
            CardStatus.CANCELLED,
            CardStatus.REPLACED,
            CardStatus.STOLEN,
            CardStatus.EXPIRED,
            CardStatus.LOST
    );

    private static final EnumSet<CardStatus> incidentState = EnumSet.of(
            CardStatus.STOLEN,
            CardStatus.LOST
    );


    public static void assertApprove(final Card card) {
        if (card.getCardStatus() != CardStatus.REQUESTED) {
            throw new BusinessException("Card can't be approve at this state.");
        }
    }

    public static void assertReplace(final Card card) {
        if (card.getCardStatus() == CardStatus.REPLACED) {
            throw new BusinessException("Card is already replaced.");
        }
    }

    public static void assertApprovePending(final Card card) {
        CardStatus cardStatus = card.getCardStatus();

        if (!approvablePendingState.contains(cardStatus)) {
            throw new BusinessException("Card can't be approve by customer at this state.");
        }
    }

    public static void assertUpdate(final Card card,
                                  final CardStatus cardStatus) {
        if (card.getCardStatus() == cardStatus) {
            throw new BusinessException("Card already have this status.");
        }
    }

    public static void assertRestrict(final Card card,
                                    final CardStatus cardStatus) {
        if (restrictedState.contains(card.getCardStatus())) {
            throw new BusinessException("Card already have been restricted.");
        }
        if (!restrictedState.contains(cardStatus)) {
            throw new BusinessException("Invalid restrict status.");
        }
    }

    public static void assertTerminated(final Card card) {
        if (terminatedState.contains(card.getCardStatus())) {
            throw new BusinessException("Card is already terminated.");
        }
    }

    public static void assertIncidentStatus(final Card card,
                                            final CardStatus cardStatus) {
        if (!incidentState.contains(cardStatus)) {
            throw new BusinessException("Card status is prohibited.");
        }

        if (incidentState.contains(card.getCardStatus())) {
            throw new BusinessException("Card already have been incident reported.");
        }
    }

    public static void assertTransitionalStatus(final Card card,
                                           final CardStatus cardStatus) {
        CardStatus readStatus = card.getCardStatus();

        Set<CardStatus> set = transitionMap.get(cardStatus);

        if (set == null) {
            throw new BusinessException("Invalid card status for status update.");
        }

        if (!set.contains(readStatus)) {
            throw new BusinessException("Invalid status transition state.");
        }
    }
}
