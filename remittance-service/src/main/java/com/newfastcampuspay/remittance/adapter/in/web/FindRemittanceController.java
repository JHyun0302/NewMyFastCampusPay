package com.newfastcampuspay.remittance.adapter.in.web;

import com.newfastcampuspay.common.WebAdapter;
import com.newfastcampuspay.remittance.application.port.in.FindRemittanceCommand;
import com.newfastcampuspay.remittance.application.port.in.FindRemittanceUseCase;
import com.newfastcampuspay.remittance.domain.RemittanceRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
class FindRemittanceController {

    private final FindRemittanceUseCase findRemittanceUseCase;

    @GetMapping(path = "/remittance/{membershipId}")
    List<RemittanceRequest> findRemittanceHistory(@PathVariable String membershipId) {
        FindRemittanceCommand command = FindRemittanceCommand.builder()
                .fromMembershipId(membershipId)
                .build();

        return findRemittanceUseCase.findRemittanceHistory(command);
    }

}
