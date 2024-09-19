package com.newfastcampuspay.membership.application.port.out;

import com.newfastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.newfastcampuspay.membership.domain.Membership;
import com.newfastcampuspay.membership.domain.Membership.MembershipName;

public interface ModifyMembershipPort {
    MembershipJpaEntity modifyMembership(
            Membership.MembershipId membershipId
            , Membership.MembershipName membershipName
            , Membership.MembershipEmail membershipEmail
            , Membership.MembershipAddress membershipAddress
            , Membership.MembershipIsValid membershipIsValid
            , Membership.MembershipIsCorp membershipIsCorp);

}
