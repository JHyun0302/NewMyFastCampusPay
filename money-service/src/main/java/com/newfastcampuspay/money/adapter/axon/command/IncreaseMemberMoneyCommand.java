package com.newfastcampuspay.money.adapter.axon.command;

import com.newfastcampuspay.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class IncreaseMemberMoneyCommand extends SelfValidating<MemberMoneyCreatedCommand> {

    @NotNull
    @TargetAggregateIdentifier
    private String aggregateIdentifier;

    @NotNull
    private final String membershipId;

    @NotNull
    private final int amount;

}