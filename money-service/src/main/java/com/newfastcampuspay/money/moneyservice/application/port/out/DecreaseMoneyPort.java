package com.newfastcampuspay.money.moneyservice.application.port.out;

import com.newfastcampuspay.money.moneyservice.adapter.out.persistence.MemberMoneyJpaEntity;
import com.newfastcampuspay.money.moneyservice.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import com.newfastcampuspay.money.moneyservice.domain.MemberMoney;
import com.newfastcampuspay.money.moneyservice.domain.MoneyChangingRequest;

public interface DecreaseMoneyPort {
    MoneyChangingRequestJpaEntity createMoneyChangingRequest(
            MoneyChangingRequest.TargetMembershipId targetMembershipId,
            MoneyChangingRequest.MoneyChangingType moneyChangingType,
            MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount,
            MoneyChangingRequest.MoneyChangingStatus moneyChangingStatus,
            MoneyChangingRequest.Uuid uuid
    );

    MemberMoneyJpaEntity decreaseMoney(
            MemberMoney.MembershipId memberId,
            int decreaseMoneyAmount
    );
}
