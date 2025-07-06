package com.newfastcampuspay.money.query.adapter.out.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newfastcampuspay.common.CommonHttpClient;
import com.newfastcampuspay.money.query.application.port.out.GetMembershipPort;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MembershipServiceAdapter implements GetMembershipPort {

    private final CommonHttpClient commonHttpClient;

    private final String membershipServiceUrl;

    public MembershipServiceAdapter(CommonHttpClient commonHttpClient,
                                    @Value("${service.membership.url}") String membershipServiceUrl) {
        this.commonHttpClient = commonHttpClient;
        this.membershipServiceUrl = membershipServiceUrl;
    }


    @Override
    public List<String> getMembershipByAddress(String address) {
        String url = String.join("/", membershipServiceUrl, "membership/address", address);
        try {
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();
            // json Membership
            System.out.println("DEBUG JSON: " + jsonResponse);

            // 특정 지역(주소)를 가지는 고객들의 리스트를 가져와야 했어요.
            ObjectMapper mapper = new ObjectMapper();
            // Membership.class
            // TypeReference

            List<Membership> membershipList = mapper.readValue(jsonResponse, new TypeReference<>() {});
            List<String> membershipIds = membershipList.stream()
                    .map(Membership::getMembershipId)
                    .collect(Collectors.toList());

            return membershipIds;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
