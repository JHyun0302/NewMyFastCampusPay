package com.newfastcampuspay.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMoney {

    @Getter
    private final String moneyChangingRequestId;

    @Getter
    private final String membershipId;

    // 잔액
    @Getter
    private final int balance;

//    @Getter
//    private final int linkedBankAccount;

    public static MemberMoney generateMemberMoney(
            MemberMoneyId memberMoneyId,
            MembershipId membershipId,
            MoneyBalance moneyBalance
    ) {
        return new MemberMoney(
                memberMoneyId.getMemberMoneyId(),
                membershipId.getMembershipId(),
                moneyBalance.getBalance()
        );
    }

    @Value
    public static class MemberMoneyId {
        public MemberMoneyId(String value) {
            this.memberMoneyId = value;
        }
        String memberMoneyId;
    }

    @Value
    public static class MembershipId {
        public MembershipId(String value) {
            this.membershipId = value;
        }
        String membershipId;
    }

    @Value
    public static class MoneyBalance {
        public MoneyBalance(int value) {
            this.balance = value;
        }
        int balance;
    }

    /**
     * RDB에서 event 식별을 위한 컬럼
     */
    @Value
    public static class MoneyAggregateIdentifier {
        public MoneyAggregateIdentifier(String aggregateIdentifier) {
            this.aggregateIdentifier = aggregateIdentifier;
        }
        String aggregateIdentifier;
    }


}
