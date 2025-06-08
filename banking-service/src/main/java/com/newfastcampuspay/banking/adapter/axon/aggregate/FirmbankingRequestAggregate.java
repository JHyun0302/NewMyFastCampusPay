package com.newfastcampuspay.banking.adapter.axon.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import com.newfastcampuspay.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import com.newfastcampuspay.banking.adapter.axon.command.UpdateFirmbankingRequestCommand;
import com.newfastcampuspay.banking.adapter.axon.event.FirmbankingRequestCreatedEvent;
import com.newfastcampuspay.banking.adapter.axon.event.FirmbankingRequestUpdatedEvent;
import com.newfastcampuspay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.newfastcampuspay.banking.adapter.out.external.bank.FirmbankingResult;
import com.newfastcampuspay.banking.application.port.out.RequestExternalFirmbanking;
import com.newfastcampuspay.banking.application.port.out.RequestFirmbankingPort;
import com.newfastcampuspay.banking.domain.FirmbankingRequest;
import com.newfastcampuspay.common.event.RequestFirmbankingCommand;
import com.newfastcampuspay.common.event.RequestFirmbankingFinishedEvent;
import com.newfastcampuspay.common.event.RollbackFirmbankingFinishedEvent;
import com.newfastcampuspay.common.event.RollbankFirmbankingRequestCommand;
import jakarta.validation.constraints.NotNull;
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

    // Firmbanking Update Handler
    @CommandHandler
    public String handle(UpdateFirmbankingRequestCommand command) {
        log.info("UpdateFirmbankingRequestCommand Handler");

        id = command.getAggregateIdentifier();
        apply(new FirmbankingRequestUpdatedEvent(command.getFirmbankingStatus()));

        return id;
    }

    @EventSourcingHandler
    public void on(FirmbankingRequestUpdatedEvent event) {
        log.info("FirmbankingRequestUpdatedEvent Sourcing Handler");

        firmbankingStatus = event.getFirmbankingStatus();
    }

    @CommandHandler
    public FirmbankingRequestAggregate(RequestFirmbankingCommand command, RequestFirmbankingPort firmbankingPort,
                                       RequestExternalFirmbanking externalFirmbankingPort) {
        log.info("RequestFirmbankingCommand Handler");
        id = command.getAggregateIdentifier();

        // from -> to (고객 계좌에서 법인 계좌로 송금)
        // 펌뱅킹 수행!
        firmbankingPort.createFirmbankingRequest(
                new FirmbankingRequest.FromBankName(command.getToBankName()), // 고객 계좌
                new FirmbankingRequest.FromBankAccountNumber(command.getToBankAccountNumber()),
                new FirmbankingRequest.ToBankName("fastcampus-bank"), // 법인 계좌
                new FirmbankingRequest.ToBankAccountNumber("123-333-9999"),
                new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(0), // 0: 성공
                new FirmbankingRequest.FirmbankingAggregateIdentifier(id)
        );

        //firmbanking!
        FirmbankingResult firmbankingResult = externalFirmbankingPort.requestExternalFirmbanking(
                new ExternalFirmbankingRequest(
                        command.getFromBankName(),
                        command.getFromBankAccountNumber(),
                        command.getToBankName(),
                        command.getToBankAccountNumber(),
                        command.getMoneyAmount()
                ));

        int resultCode = firmbankingResult.getResultCode();

        // 0. 성공, 1. 실패
        // RequestFirmbankingFinishedEvent : (banking -> money)
        apply(new RequestFirmbankingFinishedEvent(
                command.getRequestFirmbankingId(),
                command.getRechargingRequestId(),
                command.getMembershipId(),
                command.getToBankName(),
                command.getToBankAccountNumber(),
                command.getMoneyAmount(),
                resultCode,
                id
        ));
    }

    /**
     * `고객 계좌 -> 법인 계좌`로 진행되었던 요청을 `법인 계좌 -> 고객 계좌`로 다시 입금하는 요청을 하나의 펌뱅킹 request를 생성하는 CommandHandler
     */

    @CommandHandler
    public FirmbankingRequestAggregate(@NotNull RollbankFirmbankingRequestCommand command,
                                       RequestFirmbankingPort firmbankingPort,
                                       RequestExternalFirmbanking externalFirmbanking) {
        log.info("RollbackFirmbankingRequestCommand Handler");
        id = UUID.randomUUID().toString();

        // rollback 수행 (법인 계좌 -> 고객 계좌 펌뱅킹)
        firmbankingPort.createFirmbankingRequest(
                new FirmbankingRequest.FromBankName("fastcampus"), // 법인 계좌
                new FirmbankingRequest.FromBankAccountNumber("123-333-9999"),
                new FirmbankingRequest.ToBankName(command.getBankName()), // 고객 계좌
                new FirmbankingRequest.ToBankAccountNumber(command.getBankAccountNumber()),
                new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(0),
                new FirmbankingRequest.FirmbankingAggregateIdentifier(id)
        );

        // firmbanking!
        FirmbankingResult result = externalFirmbanking.requestExternalFirmbanking(
                new ExternalFirmbankingRequest(
                        "fastcampus",
                        "123-333-9999",
                        command.getBankName(),
                        command.getBankAccountNumber(),
                        command.getMoneyAmount()
                ));

        int res = result.getResultCode();

        apply(new RollbackFirmbankingFinishedEvent(
                command.getRollbackFirmbankingId(),
                command.getMembershipId(),
                id)
        );

    }


    public FirmbankingRequestAggregate() {
    }
}
