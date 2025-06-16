package com.newfastcampuspay.membership.application.service;

import com.newfastcampuspay.common.UseCase;
import com.newfastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.newfastcampuspay.membership.adapter.out.persistence.MembershipMapper;
import com.newfastcampuspay.membership.application.port.in.FindMembershipCommand;
import com.newfastcampuspay.membership.application.port.in.FindMembershipListByAddressCommand;
import com.newfastcampuspay.membership.application.port.in.FindMembershipUseCase;
import com.newfastcampuspay.membership.application.port.out.FindMembershipPort;
import com.newfastcampuspay.membership.domain.Membership;
import com.newfastcampuspay.membership.domain.Membership.MembershipAddress;
import com.newfastcampuspay.membership.domain.Membership.MembershipId;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindMembershipService implements FindMembershipUseCase {

    private final FindMembershipPort findMembershipPort;

    private final MembershipMapper membershipMapper;

    @Override
    public Membership findMembership(FindMembershipCommand command) {
        MembershipJpaEntity entity = findMembershipPort.findMembership(new MembershipId(command.getMembershipId()));
        return membershipMapper.mapToDomainEntity(entity);
    }

    @Override
    public List<Membership> findMembershipListByAddress(FindMembershipListByAddressCommand command) {
        List<MembershipJpaEntity> membershipJpaEntities = findMembershipPort.findMembershipListByAddress(
                new MembershipAddress(command.getAddressName()));
        List<Membership> memberships = new ArrayList<>();

        for (MembershipJpaEntity membershipJpaEntity : membershipJpaEntities) {
            memberships.add(membershipMapper.mapToDomainEntity(membershipJpaEntity));
        }

        return memberships;
    }
}
