package com.newfastcampuspay.banking.adapter.in.web;

import com.newfastcampuspay.banking.application.port.in.GetRegisteredBankAccountCommand;
import com.newfastcampuspay.banking.application.port.in.GetRegisteredBankAccountUseCase;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount;
import com.newfastcampuspay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class GetRegisteredBankAccountController {

    private final GetRegisteredBankAccountUseCase getRegisteredBankAccountUseCase;

    @GetMapping("/banking/account/{membershipId}")
    RegisteredBankAccount getRegisteredBankAccount(@PathVariable String membershipId) {
        //편의상 사용.
        GetRegisteredBankAccountCommand command = GetRegisteredBankAccountCommand.builder().membershipId(membershipId).build();

        return getRegisteredBankAccountUseCase.getRegisteredBankAccount(command);
    }
}
