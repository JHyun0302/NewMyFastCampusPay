package com.newfastcampuspay.remittance.application.port.out.money;

// 송금서비스에서 필요한 머니의 정보

import lombok.Data;

@Data
public class MoneyInfo {

    private String membershipId;

    private int balance;
}
