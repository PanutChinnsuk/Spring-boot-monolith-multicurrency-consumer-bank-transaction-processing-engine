
# Multicurrency Consumer Banking Engine

> **DISCLAIMER:** This project predates [Reactive Microservice Multicurrency Non-Consumer Bank Transaction Processing Engine](https://github.com/PanutChinnsuk/Spring-boot-reactive-microservice-multicurrency-non-consumer-bank-transaction-processing-engine). Although both are separate projects with different banking domains and architectural approaches, the knowledge and engineering experience gained from this project served as a foundation for the subsequent development of the reactive microservice project, which adopts a completely new approach to system design, architecture, and implementation.

A monolithic consumer banking backend independently designed and implemented from the ground up using **Java**, **Spring Boot**, and **Spring MVC**.

The project was developed to deepen practical understanding of banking systems, financial transaction processing, accounting principles, and backend engineering through a monolithic architecture.

## Core Features

### 1. Consumer Banking & Transaction Processing

Designed and implemented centralized banking transaction processing supporting:

- Same currency account transfers.
- Foreign exchange buy and sell transactions.
- Custom transfer workflows.
- Multi-stage transaction approval.
- Transaction lifecycle management.
- Transaction cancellation and expiration.
- Reversals and refunds.
- Overdraft handling for insufficient balances during financial corrections.

### 2. Loan & Consumer Card Management

Implemented consumer lending and card management workflows covering:

- Loan applications and approval workflows.
- Loan disbursement and repayment.
- Outstanding loan balance management.
- Consumer card payment processing.
- Card migration workflows.

### 3. Customer & Account Management

Designed and implemented customer and account management with controlled onboarding and account provisioning.

Features include:

- Bank authorized customer registration using bank issued registration codes.
- Customer initiated registration with deferred code redemption.
- Registration code validation and account provisioning.
- Customer profile and account management.
- Customer and account lifecycle management, including blocking, closure, and termination.
- Financial crime compliance workflows, including money laundering investigation ticket creation and management.
- Account authentication and verification.
- Integration with centralized banking transaction workflows.

### 4. Customer Security & Authorization

Designed and implemented centralized security mechanisms to protect customer accounts and financial operations.

Features include:

- JWT based authentication with access and refresh tokens.
- Two factor authentication.
- Transaction PIN verification.
- Payment session management.
- Session management and validation.
- Request rate limiting.
- Authorization and access control.
- Audit logging for sensitive operations.


## Architecture & Design

The project adopts a monolithic backend architecture using **Spring Boot** and **Spring MVC**, bringing customer management, account management, lending, card management, security, and transaction processing together within a single application.

The primary focus of this project was to explore and experiment with how different banking domains interact, how financial transactions move through their respective workflows, and how transaction processing can be coordinated within a centralized application.

Particular attention was given to understanding financial operations, transaction lifecycles, accounting considerations, data consistency and concurrency control.

Rather than targeting a production banking platform, the project served as a practical engineering exercise to develop a deeper understanding of banking system design and transaction processing through hands on experimentation.


## Engineering Focus

The project explores practical backend engineering challenges in consumer banking systems, particularly:

- Financial transaction correctness and accounting integrity.
- Multicurrency transaction processing.
- Transaction lifecycle and recovery management.
- Concurrent financial operations and database locking.
- Secure customer authentication and authorization.
- Centralized auditing and session management.
- Monolithic architecture and domain workflow coordination.
