package com.newfastcampuspay.membership.application.port.in;

import com.newfastcampuspay.membership.domain.Membership;

public interface ModifyMembershipUseCase {

    Membership modifyMembership(ModifyMembershipCommand command);
}
