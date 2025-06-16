package com.newfastcampuspay.membership.adapter.in.web;

import com.newfastcampuspay.common.WebAdapter;
import com.newfastcampuspay.membership.application.port.in.FindMembershipCommand;
import com.newfastcampuspay.membership.application.port.in.FindMembershipListByAddressCommand;
import com.newfastcampuspay.membership.application.port.in.FindMembershipUseCase;
import com.newfastcampuspay.membership.domain.Membership;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindMembershipController {

    private final FindMembershipUseCase findMembershipUseCase;

    @GetMapping("/membership/{membershipId}")
    ResponseEntity<Membership> findMembershipByMemberId(@PathVariable String membershipId) {
        //request -> command (Port.in)
        FindMembershipCommand command = FindMembershipCommand.builder()
                .membershipId(membershipId)
                .build();

        return ResponseEntity.ok(findMembershipUseCase.findMembership(command));
    }

    @GetMapping("/membership/address/{addressName}")
    ResponseEntity<List<Membership>> findMembershipListByAddress(@PathVariable String addressName) {
        FindMembershipListByAddressCommand command = FindMembershipListByAddressCommand.builder()
                .addressName(addressName)
                .build();

        return ResponseEntity.ok(findMembershipUseCase.findMembershipListByAddress(command));
    }


}
