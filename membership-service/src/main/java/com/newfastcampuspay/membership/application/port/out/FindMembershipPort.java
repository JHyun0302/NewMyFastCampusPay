package com.newfastcampuspay.membership.application.port.out;

import com.newfastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.newfastcampuspay.membership.domain.Membership;
import java.util.List;

public interface FindMembershipPort {

    MembershipJpaEntity findMembership(
            Membership.MembershipId membershipId
    );

    List<MembershipJpaEntity> findMembershipListByAddress(
            Membership.MembershipAddress membershipAddress
    );


}
