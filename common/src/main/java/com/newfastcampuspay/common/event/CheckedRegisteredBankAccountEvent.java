package com.newfastcampuspay.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckedRegisteredBankAccountEvent {

    private String rechargingRequestId;

    private String checkRegisteredBankAccountId;

    private String membershipId;

    private boolean isChecked;

    private int amount;

    private String firmbankingRequestAggregateIdentifier;

    private String fromBankName;  // 고객 계좌

    private String fromBankAccountNumber; // 고객 계좌 번호

    @Override
    public String toString() {
        return "CheckedRegisteredBankAccountEvent{" +
                "amount=" + amount +
                ", rechargingRequestId='" + rechargingRequestId + '\'' +
                ", checkRegisteredBankAccountId='" + checkRegisteredBankAccountId + '\'' +
                ", membershipId='" + membershipId + '\'' +
                ", isChecked=" + isChecked +
                ", firmbankingRequestAggregateIdentifier='" + firmbankingRequestAggregateIdentifier + '\'' +
                ", fromBankName='" + fromBankName + '\'' +
                ", fromBankAccountNumber='" + fromBankAccountNumber + '\'' +
                '}';
    }
}
