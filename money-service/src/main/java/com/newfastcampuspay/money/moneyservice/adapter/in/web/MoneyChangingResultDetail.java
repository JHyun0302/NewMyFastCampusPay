package com.newfastcampuspay.money.moneyservice.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingResultDetail {

    private String moneyChangingRequestId;

    //증액, 감액
    private int moneyChangingType; // enum. 0: 증액, 1: 감액

    private int moneyChangingResultStatus; // 0: 성공, 1: 실패

    private int amount;
}

enum MoneyChangingType {
    INCREASING, // 증액
    DECREASING // 감액
}
enum MoneyChangingResultStatus {
    SUCCEEDED, //성공
    FAILED, //실패
    FAILED_NOT_ENOUGH_MONEY, // 실패 - 잔액 부족
    FAILED_NOT_EXIST_MEMBERSHIP, // 실패 - 멤버십 없음
    FAILED_NOT_EXIST_MONEY_CHANGING_REQUEST, // 실패 - 머니 변액 요청 없음
}