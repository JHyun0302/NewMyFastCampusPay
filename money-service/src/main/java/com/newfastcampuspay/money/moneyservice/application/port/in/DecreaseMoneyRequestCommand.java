package com.newfastcampuspay.money.moneyservice.application.port.in;

import com.newfastcampuspay.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false) //`Equals` 메서드 쓸 때 super 값 제외하고 해싱
public class DecreaseMoneyRequestCommand extends SelfValidating<DecreaseMoneyRequestCommand> {

    @NotNull
    private final String targetMembershipId;

    @NotNull
    private final int amount;

    public DecreaseMoneyRequestCommand(String targetMembershipId, int amount) {
        this.targetMembershipId = targetMembershipId;
        this.amount = amount;

        this.validateSelf();
    }
}
