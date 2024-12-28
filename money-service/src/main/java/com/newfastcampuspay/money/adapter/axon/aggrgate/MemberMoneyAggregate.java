package com.newfastcampuspay.money.adapter.axon.aggrgate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import com.newfastcampuspay.money.adapter.axon.command.IncreaseMemberMoneyCommand;
import com.newfastcampuspay.money.adapter.axon.command.MemberMoneyCreatedCommand;
import com.newfastcampuspay.money.adapter.axon.event.IncreaseMemberMoneyEvent;
import com.newfastcampuspay.money.adapter.axon.event.MemberMoneyCreatedEvent;
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
     */
    @CommandHandler
    public MemberMoneyAggregate(MemberMoneyCreatedCommand command) {
        log.info("MemberMoneyCreatedCommand Handler");

        apply(new MemberMoneyCreatedEvent(command.getMembershipId())); //@EventSourcingHandler 진행
    }

    /**
     * apply 이후 Event Souring 진행
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
     */
    @CommandHandler
    public String handle(@NotNull IncreaseMemberMoneyCommand command) {
        log.info("IncreaseMemberMoneyCommand Handler");
        id = command.getAggregateIdentifier();

        //store event
        apply(new IncreaseMemberMoneyEvent(id, command.getMembershipId(), command.getAmount()));
        return id;
    }


    @EventSourcingHandler
    public void on(IncreaseMemberMoneyEvent event) {
        log.info("IncreaseMemberMoneyEven Sourcing Handler");
        id = event.getAggregateIdentifier();
        membershipId = Long.parseLong(event.getTargetMembershipId());
        balance = event.getAmount();
    }


    public MemberMoneyAggregate() {
    }
}
