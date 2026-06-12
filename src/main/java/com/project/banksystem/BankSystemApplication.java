package com.project.banksystem;


import com.project.banksystem.api.request.dto.customer.CustomerContactCreateDTO;
import com.project.banksystem.application.backend.account.query.AccountFinder;
import com.project.banksystem.application.backend.accountbalance.impl.AdminAccountBalanceManagingServiceImpl;
import com.project.banksystem.application.backend.banktransaction.impl.ForeignExchangeTransferServiceImpl;
import com.project.banksystem.application.backend.banktransaction.impl.TransferServiceImpl;
import com.project.banksystem.application.backend.account.AccountNumberGenerator;
import com.project.banksystem.application.backend.account.impl.*;
import com.project.banksystem.application.backend.amlcase.impl.AmlCaseClosingService;
import com.project.banksystem.application.backend.amlcase.impl.AmlCaseOpenService;
import com.project.banksystem.application.backend.amlcase.impl.AmlCaseReinstateService;
import com.project.banksystem.application.backend.amlcase.impl.AmlCaseStatusService;
import com.project.banksystem.application.backend.banktransaction.internal.initial.TransactionInitializer;
import com.project.banksystem.application.backend.banktransaction.transaction.impl.TransactionApprovalServiceImpl;
import com.project.banksystem.application.backend.banktransaction.transaction.impl.TransactionAuthorizeServiceImpl;
import com.project.banksystem.application.backend.banktransaction.transaction.impl.TransactionReverseServiceImpl;
import com.project.banksystem.application.backend.banktransaction.transaction.impl.TransactionTerminationServiceImpl;
import com.project.banksystem.application.backend.branch.impl.BranchCreateService;
import com.project.banksystem.application.backend.card.CardNumberEncryption;
import com.project.banksystem.application.backend.card.impl.*;
import com.project.banksystem.application.backend.card.query.CardFinder;
import com.project.banksystem.application.backend.banktransaction.cardtransaction.impl.CardTransactionServiceImpl;
import com.project.banksystem.application.backend.banktransaction.cardtransaction.validator.CardTransactionValidator;
import com.project.banksystem.application.backend.credential.impl.CredentialUpdateService;
import com.project.banksystem.application.backend.currency.CurrencyConversionService;
import com.project.banksystem.application.backend.email.internal.InternalEmailSenderService;
import com.project.banksystem.application.backend.fxrate.impl.FxRateCreateService;
import com.project.banksystem.application.backend.internalaccount.impl.InternalAccountAddService;
import com.project.banksystem.application.backend.internalaccount.query.InternalAccountFinder;
import com.project.banksystem.application.backend.ledgeraccount.impl.LedgerAccountCreateService;
import com.project.banksystem.application.backend.ledgeraccount.impl.LedgerAccountUpdateService;
import com.project.banksystem.application.backend.loan.loanpayment.impl.LoanPaymentServiceImpl;
import com.project.banksystem.application.backend.overdraft.impl.OverdraftedAccountTerminationServiceImpl;
import com.project.banksystem.application.backend.overdraft.request.impl.OverdraftPaybackRequestApprovalServiceImpl;
import com.project.banksystem.application.backend.overdraft.body.mapper.OverdraftPaybackTemplateMapper;
import com.project.banksystem.application.backend.role.impl.RoleDeleteService;
import com.project.banksystem.application.backend.sms.otp.InternalSmsOtpSenderService;
import com.project.banksystem.application.backend.verification.TwoFactorCodeGenerator;
import com.project.banksystem.application.backend.user.impl.UserStatusService;
import com.project.banksystem.application.backend.user.impl.UserTerminationService;
import com.project.banksystem.application.backend.user.impl.UserUpdateService;
import com.project.banksystem.application.backend.useractivation.impl.ActivationCodeCreateService;
import com.project.banksystem.application.backend.customer.impl.CustomerCreateService;
import com.project.banksystem.application.backend.customer.impl.CustomerTerminationService;
import com.project.banksystem.application.backend.customer.impl.CustomerUpdateService;
import com.project.banksystem.application.backend.customer.query.CustomerFinder;
import com.project.banksystem.application.backend.customeraddress.impl.CustomerAddressCreateService;
import com.project.banksystem.application.backend.customeraddress.impl.CustomerAddressUpdateService;
import com.project.banksystem.application.backend.customercontact.impl.CustomerContactCreateService;
import com.project.banksystem.application.backend.customercontact.impl.CustomerContactUpdateService;
import com.project.banksystem.application.backend.customerprofile.impl.CustomerProfileCreateService;
import com.project.banksystem.application.backend.customerprofile.impl.CustomerProfileUpdateService;
import com.project.banksystem.application.backend.role.impl.RoleCreateService;
import com.project.banksystem.application.backend.user.impl.UserCreateService;
import com.project.banksystem.application.backend.useractivation.impl.UserActivationService;
import com.project.banksystem.application.backend.userrole.impl.UserRoleAddService;
import com.project.banksystem.application.config.properties.CardEncryptionConfig;
import com.project.banksystem.application.config.properties.SystemConfig;
import com.project.banksystem.application.orchestrator.banktransaction.transaction.TransactionManagingOrchestrator;
import com.project.banksystem.application.orchestrator.overdraft.PublicOverdraftPaymentOrchestrator;
import com.project.banksystem.application.orchestrator.moneytransactionsession.MoneyTransactionSessionRequestOrchestrator;
import com.project.banksystem.core.enums.customer.ContactType;
import com.project.banksystem.core.enums.customer.CountryDialingPrefix;
import com.project.banksystem.infrastructure.repository.AmlCaseRepository;
import com.project.banksystem.infrastructure.repository.CardRepository;
import com.project.banksystem.infrastructure.repository.CustomerRepository;
import com.project.banksystem.infrastructure.repository.FxRateRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.UUID;

@EnableScheduling
@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class BankSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankSystemApplication.class, args);
	}



	@Bean
	public CommandLineRunner manualTest(CustomerTerminationService customerTerminationService,
								  CustomerUpdateService customerUpdateService,
								  CustomerCreateService customerCreateService,
								  CustomerFinder customerFinder,
								  CustomerRepository customerRepository,
								  CustomerProfileCreateService customerProfileCreateService,
								  CustomerProfileUpdateService customerProfileUpdateService,
								  CustomerContactCreateService customerContactCreateService,
								  CustomerContactUpdateService customerContactUpdateService,
								  CustomerAddressCreateService customerAddressCreateService,
								  CustomerAddressUpdateService customerAddressUpdateService,
								  RoleCreateService roleCreateService,
								  UserCreateService userCreateService,
								  ActivationCodeCreateService activationCodeCreateService,
								  UserActivationService userActivationService,
								  UserTerminationService userTerminationService,
								  UserStatusService userStatusService,
								  RoleDeleteService roleDeleteService,
								  CredentialUpdateService credentialUpdateService,
								  LedgerAccountCreateService ledgerAccountCreateService,
								  LedgerAccountUpdateService ledgerAccountUpdateService,
								  UserUpdateService userUpdateService,
								  AccountCreateService accountCreateService,
								  AccountClosingService accountClosingService,
								  AccountReinstateService accountReinstateService,
								  AccountApprovalService accountApprovalService,
								  AccountStatusService accountStatusService,
								  AmlCaseOpenService amlCaseOpenService,
								  AmlCaseClosingService amlCaseClosingService,
								  AmlCaseReinstateService amlCaseReinstateService,
								  AmlCaseStatusService amlCaseStatusService,
								  FxRateCreateService fxRateCreateService,
								  FxRateRepository fxRateRepository,
								  UnseriousTest test,
								  AccountNumberGenerator accountNumberGenerator,
								  TransactionInitializer transactionInitialService,
								  SystemConfig systemConfig,
								  CurrencyConversionService currencyConversionService,
								  AccountFinder accountFinder,
								  BranchCreateService branchCreateService,
								  InternalAccountAddService internalAccountAddService,
								  InternalAccountFinder internalAccountFinder,
								  ForeignExchangeTransferServiceImpl foreignExchangeTransferServiceImpl,
								  TransferServiceImpl transferServiceImpl,
								  TransactionApprovalServiceImpl transactionApprovalServiceImpl,
								  TransactionAuthorizeServiceImpl transactionAuthorizeServiceImpl,
								  TransactionTerminationServiceImpl transactionCancellationService,
								  TransactionReverseServiceImpl transactionReverseService,
								  CardNumberEncryption cardNumberEncryption,
								  CardEncryptionConfig cardEncryptionConfig,
								  CardCreateService cardCreateService,
								  CardMigrationService cardMigrationService,
								  CardApprovalService cardApprovalService,
								  CardStatusService cardStatusService,
								  CardFinder cardFinder,
								  CardRenewService cardRenewService,
								  CardIncidentService cardIncidentService,
								  CardSuspensionService cardSuspensionService,
								  CardTerminationService cardTerminationService,
								  CardTransactionServiceImpl cardTransactionServiceImpl,
								  CardTransactionValidator cardTransactionValidator,
								  CardRepository cardRepository,
								  MoneyTransactionSessionRequestOrchestrator moneyTransactionSessionRequestOrchestrator,
								  UserRoleAddService userRoleAddService,
								  InternalEmailSenderService internalEmailSenderService,
								  TwoFactorCodeGenerator twoFactorCodeGenerator,
								  AmlCaseRepository amlCaseRepository,
								  TransactionManagingOrchestrator transactionManagingOrchestrator,
								  PublicOverdraftPaymentOrchestrator publicOverdraftPaymentOrchestrator,
								  OverdraftPaybackTemplateMapper overdraftPaybackTemplateMapper,
								  OverdraftPaybackRequestApprovalServiceImpl overdraftApprovalService,
								  LoanPaymentServiceImpl loanPaymentService,
								  AdminAccountBalanceManagingServiceImpl adminAccountBalanceManagingService,
								  InternalSmsOtpSenderService internalSmsOtpService,
								  OverdraftedAccountTerminationServiceImpl overdraftedAccountTerminationService) {
		return args -> {
			UUID customerId = UUID.fromString("be5d3da4-46eb-4509-9a22-27846133e472");
			UUID contactId = UUID.fromString("35a37217-435f-4f42-a449-1cefc166b004");
			UUID addressId = UUID.fromString("4f2aedb5-37e8-4813-849b-19a17d681495");
			UUID userId = UUID.fromString("0aa2520d-2a52-4b23-a341-9ae3b13167c4");
			UUID ledgerAccountId = UUID.fromString("3fdbfeff-10ed-4df9-b7e1-d5e40a32eac4");
			UUID accountId = UUID.fromString("08f31a41-64ec-48dd-9567-ea466dc2255d");
			UUID th_accountId = UUID.fromString("76e99103-e767-41e2-8dc6-49ed4df132e0");
			UUID amlCaseId = UUID.fromString("f8102383-4f54-4803-abac-09733b63349c");


			UUID banksAccountId = UUID.fromString("08f31a41-64ec-48dd-9567-ea466dc2255d");
			UUID banksAccountId2 = UUID.fromString("7f816db1-c47f-40fc-8460-a0112f270364");
			UUID banksAccountId3 = UUID.fromString("a13eb927-a6ab-4c6f-b5a7-49fd477213d6");

			UUID branchId = UUID.fromString("b9fb58d6-73b1-4b01-9389-1ed0a2e41c8e");
			UUID branchId2 = UUID.fromString("d8b105a2-5a21-46ba-b9d3-66300028c172");

			UUID cardId = UUID.fromString("82efae75-a70f-4ff5-87f8-6e2cc68905c0");
			UUID cardId2 = UUID.fromString("0c586fa6-3594-443b-b20c-9d7088f59d48");


			UUID ovdId = UUID.fromString("fbacf7ad-a262-49d6-bbfb-65f4690448ca");

			UUID overdraft_r_id = UUID.fromString("d2af2d6c-dc4c-4622-947b-366f9bfc41f9");

			UUID loanId = UUID.fromString("d300398d-ddfe-4ee6-b4b4-473174f4f296");
			//transactionManagingOrchestrator.reverseTransaction(tId);
			//System.out.println(transactionManagingOrchestrator.reverseTransaction(tId).getTransactionId());

			//OverdraftPaybackTemplate template = overdraftPaybackTemplateMapper
					//.publicPaybackTemplate(ovdId, BigDecimal.valueOf(1250),
							//th_accountId);

			//publicOverdraftPaymentOrchestrator.test(template);

			//overdraftApprovalService.test(overdraft_r_id);
			//UUID tId = UUID.fromString("6bbcfc03-4f2c-4974-8882-09d98cde32b6");

			UUID oid = UUID.fromString("d99f494b-2795-4f79-8749-07dae453b07b");


			System.out.println("///////////BANK STARTED////////////");
		};
	}
}
