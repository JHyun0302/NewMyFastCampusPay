package com.newfastcampuspay.money.adapter.axon.event;

import com.newfastcampuspay.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class MemberMoneyCreatedEvent extends SelfValidating<MemberMoneyCreatedEvent> {

    @NotNull
    private String membershipId;

    public MemberMoneyCreatedEvent(String membershipId) {
        this.membershipId = membershipId;

        this.validateSelf();
    }

    public MemberMoneyCreatedEvent() {
    }
}
