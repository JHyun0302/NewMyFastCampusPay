package com.newfastcampuspay.money.adapter.out.persistence;

import com.newfastcampuspay.common.PersistenceAdapter;
import com.newfastcampuspay.money.application.port.in.CreatedMemberMoneyPort;
import com.newfastcampuspay.money.application.port.in.GetMemberMoneyPort;
import com.newfastcampuspay.money.application.port.out.DecreaseMoneyPort;
import com.newfastcampuspay.money.application.port.out.IncreaseMoneyPort;
import com.newfastcampuspay.money.domain.MemberMoney;
import com.newfastcampuspay.money.domain.MemberMoney.MembershipId;
import com.newfastcampuspay.money.domain.MoneyChangingRequest.ChangingMoneyAmount;
import com.newfastcampuspay.money.domain.MoneyChangingRequest.MoneyChangingStatus;
import com.newfastcampuspay.money.domain.MoneyChangingRequest.MoneyChangingType;
import com.newfastcampuspay.money.domain.MoneyChangingRequest.TargetMembershipId;
import com.newfastcampuspay.money.domain.MoneyChangingRequest.Uuid;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort, DecreaseMoneyPort,
        CreatedMemberMoneyPort, GetMemberMoneyPort {

    private final SpringDataMoneyChangingRequestRepository moneyChangingRequestRepository;

    private final SpringDataMemberMoneyRepository memberMoneyRepository;

    @Override
    public MoneyChangingRequestJpaEntity createMoneyChangingRequest(TargetMembershipId targetMembershipId,MoneyChangingType moneyChangingType,ChangingMoneyAmount changingMoneyAmount,MoneyChangingStatus moneyChangingStatus, Uuid uuid) {
        return moneyChangingRequestRepository.save(
                new MoneyChangingRequestJpaEntity(
                        targetMembershipId.getTargetMembershipId(),
                        moneyChangingType.getMoneyChangingType(),
                        changingMoneyAmount.getChangingMoneyAmount(),
                        new Timestamp(System.currentTimeMillis()),
                        moneyChangingStatus.getChangingMoneyStatus(),
                        UUID.randomUUID()
                )
        );
    }

    @Override
    public MemberMoneyJpaEntity decreaseMoney(MembershipId memberId, int decreaseMoneyAmount) {
        MemberMoneyJpaEntity entity;
        try {
            List<MemberMoneyJpaEntity> entityList = memberMoneyRepository.findByMembershipId(Long.parseLong(memberId.getMembershipId()));
            entity = entityList.get(0);

            entity.setBalance(entity.getBalance() + decreaseMoneyAmount);
            return memberMoneyRepository.save(entity);
        } catch (Exception e) {
            entity = new MemberMoneyJpaEntity(Long.parseLong(memberId.getMembershipId()), decreaseMoneyAmount, "");
            entity = memberMoneyRepository.save(entity);
            return entity;
        }
    }

    @Override
    public MemberMoneyJpaEntity increaseMoney(MemberMoney.MembershipId memberId, int increaseMoneyAmount) {
        MemberMoneyJpaEntity entity;
        try {
            List<MemberMoneyJpaEntity> entityList = memberMoneyRepository.findByMembershipId(Long.parseLong(memberId.getMembershipId()));
            entity = entityList.get(0);

            entity.setBalance(entity.getBalance() + increaseMoneyAmount);
            return memberMoneyRepository.save(entity);
        } catch (Exception e) {
            entity = new MemberMoneyJpaEntity(Long.parseLong(memberId.getMembershipId()), increaseMoneyAmount, "");
            entity = memberMoneyRepository.save(entity);
            return entity;
        }
    }

    @Override
    public void createMemberMoney(MemberMoney.MemberMoneyId memberId, MemberMoney.MoneyAggregateIdentifier aggregateIdentifier) {
        MemberMoneyJpaEntity entity = new MemberMoneyJpaEntity(
                Long.parseLong(memberId.getMemberMoneyId()),
                0, aggregateIdentifier.getAggregateIdentifier()
        );
        memberMoneyRepository.save(entity);
    }

    @Override
    public MemberMoneyJpaEntity getMemberMoney(MembershipId memberId) {
        MemberMoneyJpaEntity entity;
        List<MemberMoneyJpaEntity> entitiyList = memberMoneyRepository.findByMembershipId(Long.parseLong(memberId.getMembershipId()));
        if (entitiyList.size() == 0) {
            entity = new MemberMoneyJpaEntity(
                    Long.parseLong(memberId.getMembershipId()), 0, "");
            entity = memberMoneyRepository.save(entity);
            return entity;
        }
        return entitiyList.get(0);
    }
}
