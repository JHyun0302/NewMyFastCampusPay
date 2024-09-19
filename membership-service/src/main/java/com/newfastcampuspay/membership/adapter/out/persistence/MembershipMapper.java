package com.newfastcampuspay.membership.adapter.out.persistence;

import com.newfastcampuspay.membership.domain.Membership;
import com.newfastcampuspay.membership.domain.Membership.MembershipAddress;
import com.newfastcampuspay.membership.domain.Membership.MembershipEmail;
import com.newfastcampuspay.membership.domain.Membership.MembershipIsCorp;
import com.newfastcampuspay.membership.domain.Membership.MembershipIsValid;
import org.springframework.stereotype.Component;

@Component
public class MembershipMapper {
    public Membership mapToDomainEntity(MembershipJpaEntity membershipJpaEntity) {
        return Membership.generateMember(
                new Membership.MembershipId(membershipJpaEntity.getMembershipId() + ""),
                new Membership.MembershipName(membershipJpaEntity.getName()),
                new MembershipEmail(membershipJpaEntity.getEmail()),
                new MembershipAddress(membershipJpaEntity.getAddress()),
                new MembershipIsValid(membershipJpaEntity.isValid()),
                new MembershipIsCorp(membershipJpaEntity.isCorp())
        );
    }
}
