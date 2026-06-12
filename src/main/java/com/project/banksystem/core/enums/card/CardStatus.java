package com.project.banksystem.core.enums.card;

public enum CardStatus {

    // --- Issuance Lifecycle ---

    REQUESTED,
    // Customer has requested a card but it has not been processed yet.

    APPROVED,
    // Card request has been approved internally but not yet created.

    ISSUED,
    // Card has been generated but not yet delivered to the customer.

    DELIVERED,
    // Card has been delivered but not activated.

    ACTIVATION_PENDING,
    // Waiting for customer activation (e.g., via app or ATM).


    // --- Operational States ---

    ACTIVE,
    // Card is fully usable for transactions.

    SUSPENDED,
    // Temporarily disabled (manual action or temporary restriction).

    BLOCKED,
    // Blocked due to suspicious activity or security reasons.

    FROZEN,
    // Frozen due to compliance or AML investigation.


    // --- Customer Reported Issues ---

    LOST,
    // Customer reported the card as lost.

    STOLEN,
    // Customer reported the card as stolen.


    // --- Lifecycle End States ---

    EXPIRED,
    // Card has reached its expiry date and is no longer valid.

    REPLACED,
    // Card has been replaced by a newly issued card.

    CANCELLED
    // Card permanently cancelled and cannot be reactivated.
}

