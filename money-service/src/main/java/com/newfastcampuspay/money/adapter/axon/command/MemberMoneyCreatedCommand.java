package com.newfastcampuspay.money.adapter.axon.command;

import com.newfastcampuspay.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class MemberMoneyCreatedCommand extends SelfValidating<MemberMoneyCreatedCommand> {

    @NotNull
    private String membershipId;

    public MemberMoneyCreatedCommand(String membershipId) {
        this.membershipId = membershipId;

        this.validateSelf();
    }

    public MemberMoneyCreatedCommand() {
    }
}
