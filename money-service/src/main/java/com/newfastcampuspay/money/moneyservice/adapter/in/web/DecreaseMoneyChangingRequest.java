package com.newfastcampuspay.money.moneyservice.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecreaseMoneyChangingRequest {

    private String targetMembershipId;

    //무조건 감액 요청
    private int amount;
}
