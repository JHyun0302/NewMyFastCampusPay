package com.newfastcampuspay.membership.application.port.in;

import com.newfastcampuspay.membership.domain.Membership;

/**
 * 구현체 : service
 */
public interface RegisterMembershipUseCase {

    Membership registerMembership(RegisterMembershipCommand command);
}
