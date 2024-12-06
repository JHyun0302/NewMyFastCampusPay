package com.newfastcampuspay.banking.application.port.in;

import com.newfastcampuspay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class FindFirmbankingCommand extends SelfValidating<FindFirmbankingCommand> {
    private final String membershipId;
}
