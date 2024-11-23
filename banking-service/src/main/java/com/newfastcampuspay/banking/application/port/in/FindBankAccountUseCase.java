package com.newfastcampuspay.banking.application.port.in;

import com.newfastcampuspay.banking.domain.RegisteredBankAccount;

public interface FindBankAccountUseCase {

    RegisteredBankAccount findBankAccount(FindBankAccountCommand command);
}
