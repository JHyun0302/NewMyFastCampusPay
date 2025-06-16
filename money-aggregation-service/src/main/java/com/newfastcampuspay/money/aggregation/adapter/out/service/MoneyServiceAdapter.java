package com.newfastcampuspay.money.aggregation.adapter.out.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newfastcampuspay.common.CommonHttpClient;
import com.newfastcampuspay.common.ExternalSystemAdapter;
import com.newfastcampuspay.money.aggregation.application.port.out.GetMoneySumPort;
import com.newfastcampuspay.money.aggregation.application.port.out.MemberMoney;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@ExternalSystemAdapter
//@RequiredArgsConstructor
public class MoneyServiceAdapter implements GetMoneySumPort {

    private final CommonHttpClient moneyServiceHttpClient;

    @Value("${service.money.url}")
    private String moneyServiceEndpoint;

    public MoneyServiceAdapter(CommonHttpClient commonHttpClient,
                                    @Value("${service.money.url}") String moneyServiceEndpoint) {
        this.moneyServiceHttpClient = commonHttpClient;
        this.moneyServiceEndpoint = moneyServiceEndpoint;
    }

    @Override
    public List<MemberMoney> getMoneySumByMembershipIds(List<String> membershipIds) {
        String url = String.join("/", moneyServiceEndpoint, "money/member-money");
        ObjectMapper mapper = new ObjectMapper();

        try {
            FindMemberMoneyRequest request = new FindMemberMoneyRequest(membershipIds);
            String jsonResponse = moneyServiceHttpClient.sendPostRequest(url, mapper.writeValueAsString(request)).get().body();

            // N 명 고객의, 각 고객의 MemberMoney 를 가져와요.
            List<MemberMoney> memberMoneyList = mapper.readValue(jsonResponse, new TypeReference<>(){});
            return memberMoneyList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
