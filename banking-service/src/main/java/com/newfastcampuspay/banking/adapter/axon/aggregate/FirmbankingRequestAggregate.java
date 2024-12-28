package com.newfastcampuspay.banking.adapter.axon.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import com.newfastcampuspay.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import com.newfastcampuspay.banking.adapter.axon.event.FirmbankingRequestCreatedEvent;
import java.util.UUID;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Slf4j
@Aggregate()
@Data
public class FirmbankingRequestAggregate {

    @AggregateIdentifier
    private String id;

    private String fromBankName;

    private String fromBankAccountNumber;

    private String toBankName;

    private String toBankAccountNumber;

    private int moneyAmount;

    private int firmbankingStatus;

    @CommandHandler
    public FirmbankingRequestAggregate(CreateFirmbankingRequestCommand command) {
        log.info("CreateFirmbankingRequestCommand Handler");

        apply(new FirmbankingRequestCreatedEvent(command.getFromBankName(), command.getFromBankAccountNumber(),
                command.getToBankName(), command.getToBankAccountNumber(), command.getMoneyAmount()));
    }

    @EventSourcingHandler
    public void on(FirmbankingRequestCreatedEvent event) {
        log.info("FirmbankingRequestCreatedEvent Sourcing Handler");

        id = UUID.randomUUID().toString();
        fromBankName = event.getFromBankName();
        fromBankAccountNumber = event.getFromBankAccountNumber();
        toBankName = event.getToBankName();
        toBankAccountNumber = event.getToBankAccountNumber();
    }

    public FirmbankingRequestAggregate() {
    }
}
