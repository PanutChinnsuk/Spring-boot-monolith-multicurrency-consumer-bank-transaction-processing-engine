package com.project.banksystem.core.enums.customer;

public enum CustomerStatus {
    PENDING,        // Registered but not verified
    ACTIVE,         // Fully usable
    SUSPENDED,      // Temporarily restricted
    LOCKED,         // Locked due to security issues
    TERMINATED      // Permanently closed
}