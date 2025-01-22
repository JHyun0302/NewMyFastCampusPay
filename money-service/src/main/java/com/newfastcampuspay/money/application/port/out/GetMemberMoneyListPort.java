package com.newfastcampuspay.money.application.port.out;

import com.newfastcampuspay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import java.util.List;

public interface GetMemberMoneyListPort {
    List<MemberMoneyJpaEntity> getMemberMoneyPort(List<String> membershipIds);
}
