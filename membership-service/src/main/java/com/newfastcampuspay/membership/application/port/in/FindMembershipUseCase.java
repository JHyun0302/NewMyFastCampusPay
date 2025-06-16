package com.newfastcampuspay.membership.application.port.in;

import com.newfastcampuspay.membership.domain.Membership;
import java.util.List;

public interface FindMembershipUseCase {

    Membership findMembership(FindMembershipCommand command);

    List<Membership> findMembershipListByAddress(FindMembershipListByAddressCommand command);
}
