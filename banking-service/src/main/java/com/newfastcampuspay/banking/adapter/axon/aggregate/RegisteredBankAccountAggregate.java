package com.newfastcampuspay.banking.adapter.axon.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import com.newfastcampuspay.banking.adapter.axon.command.CreateRegisteredBankAccountCommand;
import com.newfastcampuspay.banking.adapter.axon.event.CreateRegisteredBankAccountEvent;
import com.newfastcampuspay.banking.adapter.out.external.bank.BankAccount;
import com.newfastcampuspay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.newfastcampuspay.banking.application.port.out.RequestBankAccountInfoPort;
import com.newfastcampuspay.common.event.CheckRegisteredBankAccountCommand;
import com.newfastcampuspay.common.event.CheckedRegisteredBankAccountEvent;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

/**
 *  등록된 계좌 정보에 대한 Aggregate
 */
@Slf4j
@Aggregate()
public class RegisteredBankAccountAggregate {

    @AggregateIdentifier
    private String id;

    private String membershipId;

    private String bankName;

    private String bankAccountNumber;

    @CommandHandler
    public RegisteredBankAccountAggregate(@NotNull CreateRegisteredBankAccountCommand command) {
        log.info("CreateRegisteredBankAccountEvent Sourcing Handler");
        apply(new CreateRegisteredBankAccountEvent(command.getMembershipId(), command.getBankName(), command.getBankAccountName()));
    }

    @EventSourcingHandler
    public void on(CreateRegisteredBankAccountEvent event) {
        log.info("CreateRegisteredBankAccountEvent Souring Handler");

        id = UUID.randomUUID().toString();
        membershipId = event.getMembershipId();
        bankName = event.getBankName();
        bankAccountNumber = event.getBankAccountNumber();
    }

    @CommandHandler
    public void handle(@NotNull CheckRegisteredBankAccountCommand command, RequestBankAccountInfoPort bankAccountInfoPort) {
        log.info("CheckRegisteredBankAccountCommand Handler");

        // command를 통해, 이 어그리거트(RegisteredBankAccount)가 정상인지를 확인해야해요.
        id = command.getAggregateIdentifier();

        // Check! Registered Bank Account
        BankAccount account = bankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(command.getBankAccountNumber(), command.getBankName()));
        boolean isValidAccount = account.isValid();

        String firmbankingUUID = UUID.randomUUID().toString();

        //CheckedRegisteredBankAccountEvent (banking -> money)
        apply(new CheckedRegisteredBankAccountEvent(
                command.getRechargingRequestId(),
                command.getCheckRegisteredBankAccountId(),
                command.getMembershipId(),
                isValidAccount,
                command.getAmount(),
                firmbankingUUID,
                account.getBankName(),
                account.getBankAccountNumber()
        ));
    }

    public RegisteredBankAccountAggregate() {
    }
}