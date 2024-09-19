package com.newfastcampuspay.membership.application.port.out;

import com.newfastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.newfastcampuspay.membership.domain.Membership;

public interface FindMembershipPort {

    MembershipJpaEntity findMembership(
            Membership.MembershipId membershipId
    );
}
