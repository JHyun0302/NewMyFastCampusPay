package com.newfastcampuspay.money.application.port.in;

import com.newfastcampuspay.money.domain.MemberMoney;
import java.util.List;

/**
 * 구현체 : service
 */
public interface FindMemberMoneyListByMembershipIdsRequestUseCase {

    List<MemberMoney> findMemberMoneyListByMembershipIds(FindMemberMoneyListByMembershipIdsCommand command);
}
