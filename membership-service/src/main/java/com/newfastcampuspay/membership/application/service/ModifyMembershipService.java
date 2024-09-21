package com.newfastcampuspay.membership.application.service;

import com.newfastcampuspay.common.UseCase;
import com.newfastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.newfastcampuspay.membership.adapter.out.persistence.MembershipMapper;
import com.newfastcampuspay.membership.application.port.in.ModifyMembershipCommand;
import com.newfastcampuspay.membership.application.port.in.ModifyMembershipUseCase;
import com.newfastcampuspay.membership.application.port.out.ModifyMembershipPort;
import com.newfastcampuspay.membership.domain.Membership;
import com.newfastcampuspay.membership.domain.Membership.MembershipAddress;
import com.newfastcampuspay.membership.domain.Membership.MembershipEmail;
import com.newfastcampuspay.membership.domain.Membership.MembershipId;
import com.newfastcampuspay.membership.domain.Membership.MembershipIsCorp;
import com.newfastcampuspay.membership.domain.Membership.MembershipIsValid;
import com.newfastcampuspay.membership.domain.Membership.MembershipName;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class ModifyMembershipService implements ModifyMembershipUseCase {

    private final ModifyMembershipPort modifyMembershipPort;

    private final MembershipMapper membershipMapper;

    @Override
    public Membership modifyMembership(ModifyMembershipCommand command) {
        // port.out -> jpaEntity
        MembershipJpaEntity jpaEntity = modifyMembershipPort.modifyMembership(
                new MembershipId(command.getMembershipId()),
                new MembershipName(command.getName()),
                new MembershipEmail(command.getEmail()),
                new MembershipAddress(command.getAddress()),
                new MembershipIsValid(command.isValid()),
                new MembershipIsCorp(command.isCorp())
        );

        // jpaEntity -> domain
        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}
