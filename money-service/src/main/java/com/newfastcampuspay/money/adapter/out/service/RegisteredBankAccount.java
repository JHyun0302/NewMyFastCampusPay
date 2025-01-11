package com.newfastcampuspay.money.adapter.out.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredBankAccount {

    private String registeredBankAccountId; //jpa에서 기존에 사용하던 id

    private String membershipId;

    private String bankName;

    private String bankAccountNumber;

    private boolean linkedStatusIsValid; //고객에서 연결된 registeredBankAccount의 연결 상태

    private String aggregateIdentifier; //axon framework에서 사용하는 어그리거트의 identifier

}
