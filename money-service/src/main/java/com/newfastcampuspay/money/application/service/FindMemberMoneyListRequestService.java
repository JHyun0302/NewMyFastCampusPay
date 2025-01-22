package com.newfastcampuspay.money.application.service;

import com.newfastcampuspay.common.UseCase;
import com.newfastcampuspay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.newfastcampuspay.money.adapter.out.persistence.MemberMoneyMapper;
import com.newfastcampuspay.money.application.port.in.FindMemberMoneyListByMembershipIdsCommand;
import com.newfastcampuspay.money.application.port.in.FindMemberMoneyListByMembershipIdsRequestUseCase;
import com.newfastcampuspay.money.application.port.out.GetMemberMoneyListPort;
import com.newfastcampuspay.money.domain.MemberMoney;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional
public class FindMemberMoneyListRequestService implements FindMemberMoneyListByMembershipIdsRequestUseCase {

    private final MemberMoneyMapper mapper;

    private final GetMemberMoneyListPort getMemberMoneyListPort;

    @Override
    public List<MemberMoney> findMemberMoneyListByMembershipIds(FindMemberMoneyListByMembershipIdsCommand command) {
        // 여러개의 membership Ids를 기준으로, memberMoney 정보를 가져와야 해요.
        List<MemberMoneyJpaEntity> memberMoneyJpaEntityList = getMemberMoneyListPort.getMemberMoneyPort(command.getMembershipIds());
        List<MemberMoney> memberMoneyList = new ArrayList<>();

        for (MemberMoneyJpaEntity memberMoneyJpaEntity : memberMoneyJpaEntityList) {
            memberMoneyList.add(mapper.mapToDomainEntity(memberMoneyJpaEntity));
        }

        return memberMoneyList;
    }
}