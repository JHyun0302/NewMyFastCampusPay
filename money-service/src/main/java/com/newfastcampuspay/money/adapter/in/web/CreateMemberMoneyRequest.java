package com.newfastcampuspay.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 특정 고객의 지갑 정보
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMemberMoneyRequest {

    private String targetMembershipId;


}
