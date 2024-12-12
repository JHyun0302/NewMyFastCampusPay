package com.newfastcampuspay.money.application.port.out;

import com.newfastcampuspay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.newfastcampuspay.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import com.newfastcampuspay.money.domain.MemberMoney;
import com.newfastcampuspay.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyPort {
    MoneyChangingRequestJpaEntity createMoneyChangingRequest (
            MoneyChangingRequest.TargetMembershipId targetMembershipId,
            MoneyChangingRequest.MoneyChangingType moneyChangingType,
            MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount,
            MoneyChangingRequest.MoneyChangingStatus moneyChangingStatus,
            MoneyChangingRequest.Uuid uuid
    );

    MemberMoneyJpaEntity increaseMoney(
            MemberMoney.MembershipId memberId,
            int increaseMoneyAmount
    );
}
