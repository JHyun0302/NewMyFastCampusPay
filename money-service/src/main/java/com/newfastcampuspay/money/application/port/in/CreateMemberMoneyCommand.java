package com.newfastcampuspay.money.application.port.in;

import com.newfastcampuspay.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateMemberMoneyCommand extends SelfValidating<CreateMemberMoneyCommand> {

    @NotNull
    private final String membershipId;

    public CreateMemberMoneyCommand(String membershipId) {
        this.membershipId = membershipId;

        this.validateSelf();
    }
}
