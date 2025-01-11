package com.newfastcampuspay.money.adapter.axon.aggrgate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import com.newfastcampuspay.money.adapter.axon.command.IncreaseMemberMoneyCommand;
import com.newfastcampuspay.money.adapter.axon.command.MemberMoneyCreatedCommand;
import com.newfastcampuspay.money.adapter.axon.command.RechargingMoneyRequestCreateCommand;
import com.newfastcampuspay.money.adapter.axon.event.IncreaseMemberMoneyEvent;
import com.newfastcampuspay.money.adapter.axon.event.MemberMoneyCreatedEvent;
import com.newfastcampuspay.money.adapter.axon.event.RechargingRequestCreatedEvent;
import com.newfastcampuspay.money.application.port.out.GetRegisteredBankAccountPort;
import com.newfastcampuspay.money.application.port.out.RegisteredBankAccountAggregateIdentifier;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

/**
 * Aggregate에서 변경은 이 클래스 안에서만 가능! DDD 패턴을 위해 Axon framework 강제함!
 */
@Aggregate()
@Data
@Slf4j
public class MemberMoneyAggregate {

    @AggregateIdentifier
    private String id;

    private Long membershipId;

    private int balance;

    /**
     * @PostMapping("/money/create-member-money")
     * Consumer -> Event Store 1. Create Event Save
     * 고객 계좌 만들라는 이벤트 생성
     */
    @CommandHandler
    public MemberMoneyAggregate(MemberMoneyCreatedCommand command) {
        log.info("MemberMoneyCreatedCommand Handler");

        apply(new MemberMoneyCreatedEvent(command.getMembershipId())); //@EventSourcingHandler 진행
    }

    /**
     * apply 이후 Event Souring 진행
     * 고객 계좌 만들기
     */
    @EventSourcingHandler
    public void on(MemberMoneyCreatedEvent event) {
        log.info("MemberMoneyCreatedEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        membershipId = Long.parseLong(event.getMembershipId());
        balance = 0;
    }

    /**
     * @PostMapping("/money/increase-eda")
     * 고객 머니 증액 이벤트 생성
     */
    @CommandHandler
    public String handle(@NotNull IncreaseMemberMoneyCommand command) {
        log.info("IncreaseMemberMoneyCommand Handler");
        id = command.getAggregateIdentifier();

        //store event
        apply(new IncreaseMemberMoneyEvent(id, command.getMembershipId(), command.getAmount()));
        return id;
    }

    /**
     * 고객 머니 증액
     */
    @EventSourcingHandler
    public void on(IncreaseMemberMoneyEvent event) {
        log.info("IncreaseMemberMoneyEvent Sourcing Handler");
        id = event.getAggregateIdentifier();
        membershipId = Long.parseLong(event.getTargetMembershipId());
        balance = event.getAmount();
    }

    /**
     * MoneyRechargeSage를 시작하게 할 어그리거트 커맨트 핸들러
     */
    @CommandHandler
    public void handler(RechargingMoneyRequestCreateCommand command,
                        GetRegisteredBankAccountPort getRegisteredBankAccountPort) {
        log.info("RechargingMoneyRequestCreateCommand Handler");
        id = command.getAggregateIdentifier();

        // new RechargingRequestCreatedEvent
        // banking 정보 필요 -> banking svc (getRegisteredBankAccount)를 위한 Port 생성해야함.
        RegisteredBankAccountAggregateIdentifier registeredBankAccountAggregateIdentifier =
                getRegisteredBankAccountPort.getRegisteredBankAccount(command.getMembershipId());

        // Saga Start
        apply(new RechargingRequestCreatedEvent(
                command.getRechargingRequestId(),
                command.getMembershipId(),
                command.getAmount(),
                registeredBankAccountAggregateIdentifier.getAggregateIdentifier(),
                registeredBankAccountAggregateIdentifier.getBankName(),
                registeredBankAccountAggregateIdentifier.getBankAccountNumber()
        ));
    }


    public MemberMoneyAggregate() {
    }
}
