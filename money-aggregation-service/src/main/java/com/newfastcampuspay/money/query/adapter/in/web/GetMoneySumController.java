package com.newfastcampuspay.money.query.adapter.in.web;

import com.newfastcampuspay.common.WebAdapter;
import com.newfastcampuspay.money.query.application.port.in.GetMoneySumBtyAddressUseCase;
import com.newfastcampuspay.money.query.application.port.in.GetMoneySumByAddressCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class GetMoneySumController {

    private final GetMoneySumBtyAddressUseCase getMoneySumBtyAddressUseCase;

    @PostMapping("/money/aggregation/get-money-sum-by-address")
    int getMoneySumByAddress(@RequestBody GetMoneySumByAddressRequest request) {
        return getMoneySumBtyAddressUseCase.getMoneySumByAddress(
                GetMoneySumByAddressCommand.builder()
                        .address(request.getAddress()).build()
        );
    }
}
