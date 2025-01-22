package com.newfastcampuspay.money.application.port.in;

import com.newfastcampuspay.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class FindMemberMoneyListByMembershipIdsCommand extends SelfValidating<FindMemberMoneyListByMembershipIdsCommand> {

    @NotNull
    private final List<String> membershipIds;


    public FindMemberMoneyListByMembershipIdsCommand(List<String> targetMembershipId) {
        this.membershipIds = targetMembershipId;

        this.validateSelf();
    }
}
