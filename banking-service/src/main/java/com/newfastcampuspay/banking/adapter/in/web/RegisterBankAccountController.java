package com.newfastcampuspay.banking.adapter.in.web;

import com.newfastcampuspay.banking.application.port.in.RegisterBankAccountCommand;
import com.newfastcampuspay.banking.application.port.in.RegisterBankAccountUseCase;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount;
import com.newfastcampuspay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterBankAccountController {

    private final RegisterBankAccountUseCase registeredBankAccountUseCase;

    @PostMapping("/banking/account/register")
    RegisteredBankAccount registeredBankAccount(@RequestBody RegisterBankAccountRequest request) {
        //request -> command (Port.in)
        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
                .membershipId(request.getMembershipId())
                .bankName(request.getBankName())
                .bankAccountNumber(request.getBankAccountNumber())
                .isValid(request.isValid())
                .build();

        RegisteredBankAccount registeredBankAccount = registeredBankAccountUseCase.registerBankAccount(command);
        if (registeredBankAccount == null) {
            //TODO: Error Handling
            return null;
        }

        return registeredBankAccount;
    }
}
