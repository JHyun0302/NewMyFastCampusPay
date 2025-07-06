package com.newfastcampuspay.money.query.application.port.out;

import java.util.List;

public interface GetMoneySumPort {

    // membership ids 로, member money 정보 List로 가져온다.
    List<MemberMoney> getMoneySumByMembershipIds(List<String> membershipIds);
}
