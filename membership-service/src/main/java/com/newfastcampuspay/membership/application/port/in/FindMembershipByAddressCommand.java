package com.newfastcampuspay.membership.application.port.in;

import com.newfastcampuspay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class FindMembershipByAddressCommand extends SelfValidating<FindMembershipByAddressCommand> {

    private final String addressName; // 관악구, 서초구, 강남구

}
