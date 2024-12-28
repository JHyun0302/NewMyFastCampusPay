package com.newfastcampuspay.money.application.port.in;

import com.newfastcampuspay.money.domain.MemberMoney;

public interface CreatedMemberMoneyPort {
    void createMemberMoney(
            MemberMoney.MemberMoneyId memberMoneyId,
            MemberMoney.MoneyAggregateIdentifier aggregateIdentifier
    );
}
