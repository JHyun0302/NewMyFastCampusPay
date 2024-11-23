package com.newfastcampuspay.banking.adapter.in.web;

import com.newfastcampuspay.banking.application.port.in.FindBankAccountCommand;
import com.newfastcampuspay.banking.application.port.in.FindBankAccountUseCase;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount;
import com.newfastcampuspay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindBankAccountController {

    private final FindBankAccountUseCase findBankAccountUseCase;

    @GetMapping("/banking/account/{membershipId}")
    ResponseEntity<RegisteredBankAccount> findBankAccount(@PathVariable String membershipId) {
        FindBankAccountCommand command = FindBankAccountCommand.builder()
                .membershipId(membershipId)
                .build();

        return ResponseEntity.ok(findBankAccountUseCase.findBankAccount(command));
    }
}
