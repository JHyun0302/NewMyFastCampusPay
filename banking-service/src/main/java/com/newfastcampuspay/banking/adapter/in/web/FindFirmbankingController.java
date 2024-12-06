package com.newfastcampuspay.banking.adapter.in.web;

import com.newfastcampuspay.banking.application.port.in.FindFirmbankingCommand;
import com.newfastcampuspay.banking.application.port.in.FindFirmbankingUseCase;
import com.newfastcampuspay.banking.domain.FirmbankingRequest;
import com.newfastcampuspay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindFirmbankingController {
    private final FindFirmbankingUseCase findFirmBankingUseCase;

    @GetMapping("/banking/firmbanking/request/{membershipId}")
    ResponseEntity<FirmbankingRequest> findMembershipByMemberId(@PathVariable String membershipId) {
        FindFirmbankingCommand command = FindFirmbankingCommand.builder()
                .membershipId(membershipId)
                .build();

        return ResponseEntity.ok(findFirmBankingUseCase.findFirmbankingRequest(command));
    }
}
