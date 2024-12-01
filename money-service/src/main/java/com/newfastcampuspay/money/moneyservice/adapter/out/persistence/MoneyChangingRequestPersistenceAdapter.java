package com.newfastcampuspay.money.moneyservice.adapter.out.persistence;

import com.newfastcampuspay.common.PersistenceAdapter;
import com.newfastcampuspay.money.moneyservice.application.port.out.IncreaseMoneyPort;
import com.newfastcampuspay.money.moneyservice.domain.MemberMoney;
import com.newfastcampuspay.money.moneyservice.domain.MemberMoney.MembershipId;
import com.newfastcampuspay.money.moneyservice.domain.MoneyChangingRequest.ChangingMoneyAmount;
import com.newfastcampuspay.money.moneyservice.domain.MoneyChangingRequest.MoneyChangingStatus;
import com.newfastcampuspay.money.moneyservice.domain.MoneyChangingRequest.MoneyChangingType;
import com.newfastcampuspay.money.moneyservice.domain.MoneyChangingRequest.TargetMembershipId;
import com.newfastcampuspay.money.moneyservice.domain.MoneyChangingRequest.Uuid;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort {

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
    public MemberMoneyJpaEntity increaseMoney(MemberMoney.MembershipId memberId, int increaseMoneyAmount) {
        MemberMoneyJpaEntity entity;
        try {
            List<MemberMoneyJpaEntity> entityList = memberMoneyRepository.findByMembershipId(Long.parseLong(memberId.getMembershipId()));
            entity = entityList.get(0);

            entity.setBalance(entity.getBalance() + increaseMoneyAmount);
            return memberMoneyRepository.save(entity);
        } catch (Exception e) {
            entity = new MemberMoneyJpaEntity(
                    Long.parseLong(memberId.getMembershipId()),
                    increaseMoneyAmount
            );
            entity = memberMoneyRepository.save(entity);
            return entity;
        }
    }
}
