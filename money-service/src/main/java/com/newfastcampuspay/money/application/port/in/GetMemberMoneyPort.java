package com.newfastcampuspay.money.application.port.in;

import com.newfastcampuspay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.newfastcampuspay.money.domain.MemberMoney;

public interface GetMemberMoneyPort {
    MemberMoneyJpaEntity getMemberMoney(
            MemberMoney.MemberMoneyId memberMoneyId
    );
}
