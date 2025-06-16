package com.newfastcampuspay.money.aggregation.adapter.out.service;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindMemberMoneyRequest {

    private List<String> membershipIds;
}
