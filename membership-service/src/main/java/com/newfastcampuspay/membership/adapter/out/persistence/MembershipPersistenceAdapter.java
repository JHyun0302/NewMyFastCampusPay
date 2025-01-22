package com.newfastcampuspay.membership.adapter.out.persistence;

import com.newfastcampuspay.common.PersistenceAdapter;
import com.newfastcampuspay.membership.application.port.out.FindMembershipPort;
import com.newfastcampuspay.membership.application.port.out.ModifyMembershipPort;
import com.newfastcampuspay.membership.application.port.out.RegisterMembershipPort;
import com.newfastcampuspay.membership.domain.Membership;
import com.newfastcampuspay.membership.domain.Membership.MembershipAddress;
import com.newfastcampuspay.membership.domain.Membership.MembershipEmail;
import com.newfastcampuspay.membership.domain.Membership.MembershipId;
import com.newfastcampuspay.membership.domain.Membership.MembershipIsCorp;
import com.newfastcampuspay.membership.domain.Membership.MembershipIsValid;
import com.newfastcampuspay.membership.domain.Membership.MembershipName;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;

    @Override
    public MembershipJpaEntity createMembership(MembershipName membershipName, MembershipEmail membershipEmail,
                                 MembershipAddress membershipAddress, MembershipIsValid membershipIsValid,
                                 MembershipIsCorp membershipIsCorp) {
        return membershipRepository.save(
                new MembershipJpaEntity(
                        membershipName.getNameValue()
                        , membershipEmail.getEmailValue()
                        , membershipAddress.getAddressValue()
                        , membershipIsValid.isValidValue()
                        , membershipIsCorp.isCorpValue()
                )
        );
    }

    @Override
    public MembershipJpaEntity findMembership(MembershipId membershipId) {
        return membershipRepository.getById(Long.valueOf(membershipId.getMembershipId()));
    }

    @Override
    public List<MembershipJpaEntity> findMembershipListByAddress(MembershipAddress membershipAddress) {
        // 관악구, 서초구, 강남구 중 하나
        String address = membershipAddress.getAddressValue();
        return membershipRepository.findByAddress(address);
    }

    @Override
    public MembershipJpaEntity modifyMembership(MembershipId membershipId, MembershipName membershipName,
                                                MembershipEmail membershipEmail, MembershipAddress membershipAddress,
                                                MembershipIsValid membershipIsValid,
                                                MembershipIsCorp membershipIsCorp) {

        MembershipJpaEntity entity = membershipRepository.getById(Long.valueOf(membershipId.getMembershipId()));
        entity.setName(membershipName.getNameValue());
        entity.setAddress(membershipAddress.getAddressValue());
        entity.setEmail(membershipEmail.getEmailValue());
        entity.setCorp(membershipIsCorp.isCorpValue());
        entity.setValid(membershipIsValid.isValidValue());

        return membershipRepository.save(entity);
    }
}
