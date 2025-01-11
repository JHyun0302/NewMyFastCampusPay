package com.newfastcampuspay.banking.adapter.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 은행 계좌 등록을 위한 이벤트 소싱에 사용
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRegisteredBankAccountEvent {

    private String membershipId;

    private String bankName;

    private String bankAccountNumber;
}
