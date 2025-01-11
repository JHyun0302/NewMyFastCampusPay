package com.newfastcampuspay.money.adapter.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * "충전" 동작을 요청이 생성되었다는 이벤트
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RechargingRequestCreatedEvent {

    private String rechargingRequestId; //1개의 충전 요청 saga에 대해서 1개만 생성

    private String membershipId;

    private int amount;

    private String registeredBankAccountAggregateIdentifier;

    private String bankName;

    private String bankAccountNumber;
}
